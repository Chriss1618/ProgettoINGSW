package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class ActionsLogin extends ActionsViewHandler{
    private static final String TAG = "ActionsLogin";

    public final static int INDEX_ACTION_START_REGISTER_ADMIN   = 0;
    public final static int INDEX_ACTION_START_LOGIN            = 1;
    public final static int INDEX_ACTION_CONFIRM_PASSWORD       = 2;
    public final static int INDEX_ACTION_LOGIN                  = 3;
    public final static int INDEX_ACTION_REGISTER_ADMIN         = 4;

    public ActionsLogin(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_START_REGISTER_ADMIN,     new StartRegisterAdmin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_START_LOGIN,              new StartLogin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN,                    new Login_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_REGISTER_ADMIN,           new RegisterAdmin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CONFIRM_PASSWORD,         new ConfirmPassword_ActionHandler());
    }

    private static class StartRegisterAdmin_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");
            new LocalStorage(action.getManager().context).putData("TypeUser","Amministratore");
        }
    }

    private static class StartLogin_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: NormalLogin");
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");
            new LocalStorage(context).putData("TypeUser","");
        }
    }

    protected static class Login_ActionHandler implements ActionHandler{
        Boolean isFirstTime;
        Utente user;
        String Msg_error = "Controlla la tua connessione";
        Context context;
        @Override
        public void handleAction(Action action) {
             context = action.getManager().context;
            user = (Utente) action.getData();
            getFCMToken(action);
            Log.d(TAG, "handleAction: Token user Created ->" + user.getToken());
            if( user.getToken() == null) user.setToken("NO_SERVICE_TOKEN_AVAILABLE");
            if(getUserFromServer(user.getEmail(), user.getPassword(), user.getToken())){
                new LocalStorage(context).putData("ID_Utente",      user.getId_utente());
                new LocalStorage(context).putData("ID_Ristorante",  user.getId_Restaurant());
                new LocalStorage(context).putData("Nome",           user.getNome());
                new LocalStorage(context).putData("Cognome",        user.getCognome());
                new LocalStorage(context).putData("Token",          user.getToken());
                new LocalStorage(context).putData("TypeUser",       user.getType_user());
                new LocalStorage(context).putData("Email",          user.getEmail());
                new LocalStorage(context).putData("Password",       user.getPassword());
                new LocalStorage(context).putData("isFirstTime",    isFirstTime);
                action.callBack(true);
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
                if( !isFirstTime )
                    action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_CONFIRM,"");
            }else{
                Log.d(TAG, "handleAction: Utente Non Trovato");
                action.getManager().setData(Msg_error);
                action.callBack(false);
            }
        }
        private void getFCMToken(Action action) {
            try {
                final CountDownLatch latch = new CountDownLatch(1);
                FirebaseApp.initializeApp(action.getManager().context);

                FirebaseMessaging.getInstance().getToken() .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        user.setToken(token);
                        Log.d(TAG, "getFCMToken: Ecco nuovo Token");
                    } else {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    }
                    latch.countDown();
                });
                latch.await();
            } catch (InterruptedException e) {
                user.setToken("NO_SERVICE_TOKEN_AVAILABLE");
            }
        }


        protected JSONObject getResponseServer(String Email, String Password, String Token){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Email",      Email)
                    .appendQueryParameter("Password",   Password)
                    .appendQueryParameter("Token",      Token);

            final String url = EndPointer.StandardPath + "/Login.php";

            return new ServerCommunication().getData( dataToSend, url);
        }

        protected void getUtenteFromJSON(JSONObject BodyJSON, String Token) throws JSONException {
            JSONArray DATA_Json = new JSONArray(BodyJSON.getString("DATA"));
            JSONObject utente_Json =  new JSONObject(DATA_Json.getString(0));

            user = new Utente();
            user.setId_utente(Integer.parseInt( utente_Json.getString("ID_Utente") ));
            user.setId_Restaurant(Integer.parseInt( utente_Json.getString("ID_Ristorante") ));
            user.setNome(utente_Json.getString("Nome"));
            user.setCognome(utente_Json.getString("Cognome"));
            user.setType_user(utente_Json.getString("Type_User"));
            user.setToken(Token);
            isFirstTime = utente_Json.getString("Token").equals("NO_TOKEN");
        }

        protected boolean CheckJSON(JSONObject BodyJSON) throws JSONException {
            return BodyJSON.getString("MSG_STATUS").contains("1");
        }

        protected boolean getUserFromServer(String Email, String Password, String Token){
            try {
                JSONObject BodyJSON = getResponseServer( Email, Password,Token);

                if( CheckJSON( BodyJSON) ){
                    getUtenteFromJSON( BodyJSON,  Token);
                    return true;
                }else{
                    Msg_error = BodyJSON.getString("MSG_STATUS").replace("0 ","");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }
    }

    private static class RegisterAdmin_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            String token = "WAITING";
            ((Utente)action.getData()).setToken(token);
            getFCMToken(action);

            startRegistration(action);
        }

        private void startRegistration(Action action){
            Context context = action.getManager().context;
            if( ((Utente) action.getData()).getToken() == null) ((Utente) action.getData()).setToken("NO_SERVICE_TOKEN_AVAILABLE");
            if(RegisterUserToServer(action)){
                new LocalStorage(context).putData("ID_Utente",((Utente) action.getData()).getId_utente());
                new LocalStorage(context).putData("ID_Ristorante",((Utente) action.getData()).getId_Restaurant());
                new LocalStorage(context).putData("Nome",((Utente) action.getData()).getNome());
                new LocalStorage(context).putData("Cognome",((Utente) action.getData()).getCognome());
                new LocalStorage(context).putData("Token",((Utente) action.getData()).getToken());
                new LocalStorage(context).putData("TypeUser",((Utente) action.getData()).getType_user());
                new LocalStorage(context).putData("Email",((Utente) action.getData()).getEmail());
                new LocalStorage(context).putData("Password",((Utente) action.getData()).getPassword());
                action.callBack(true);
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
                action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_CONFIRM,"");
            }else{
                Log.d(TAG, "handleAction: Utente Non Trovato");
                action.callBack(false);
            }
        }

        private void getFCMToken(Action action) {
            try {
                final CountDownLatch latch = new CountDownLatch(1);

                FirebaseMessaging.getInstance().getToken() .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        ((Utente) action.getData()).setToken(token);
                    } else {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    }
                    latch.countDown();
                });
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        private boolean RegisterUserToServer(Action action){
            Utente user = ((Utente) action.getData());
            Log.d(TAG, "stetUserToServer: Email->"+user.getEmail());
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Nome", "Amministratore" )
                    .appendQueryParameter("Token", user.getToken())
                    .appendQueryParameter("TypeUser", "Amministratore")
                    .appendQueryParameter("Cognome", "")
                    .appendQueryParameter("Email", user.getEmail())
                    .appendQueryParameter("Password",user.getPassword());
            String url = EndPointer.StandardPath + "/RegisterUser.php";
            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG").contains("1")){
                    JSONObject DATA_Json = new JSONObject(BodyJSON.getString("DATA"));
                    Log.d(TAG, "setUserToServer: DATA_JSON->"+DATA_Json.toString(4));
                    user.setId_utente(Integer.parseInt( DATA_Json.getString("Id_Utente") ));
                    user.setId_Restaurant(Integer.parseInt( DATA_Json.getString("Id_Ristorante") ));
                    user.setNome(DATA_Json.getString("Nome"));
                    user.setCognome(DATA_Json.getString("Cognome"));
                    user.setToken(DATA_Json.getString("Token"));
                    user.setType_user("Amministratore");
                    return true;
                }else{
                    assert BodyJSON != null;
                    String messageError = BodyJSON.getString("MSG").replace("0 ","");
                    action.getManager().setData(messageError);
                    Log.d(TAG, "setUserToServer: false");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "setUserToServer: ",e);
                return false;
            }
        }
    }

    private static class ConfirmPassword_ActionHandler implements ActionHandler{
        private Integer id_utente;
        private String password;
        @Override
        public void handleAction(Action action) {
            id_utente = (Integer) new LocalStorage(action.getManager().context).getData("ID_Utente", "Integer");
            password = (String) action.getData();

            boolean isUpdated = getUserFromServer(action);
            action.callBack(isUpdated);
            if(isUpdated)
                action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_CONFIRM,"");
        }

        private boolean getUserFromServer(Action action){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_Utente", id_utente+"")
                    .appendQueryParameter("Password",password);
            String url = EndPointer.StandardPath + "/ResetPassword.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG_STATUS").contains("1")){
                    Log.d(TAG, "getUserFromServer: true");
                    return true;
                }else{
                    assert BodyJSON != null;
                    String messageError = BodyJSON.getString("MSG").replace("0 ","");
                    action.getManager().setData(messageError);
                    Log.d(TAG, "getUserFromServer: false");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }
    }
}
