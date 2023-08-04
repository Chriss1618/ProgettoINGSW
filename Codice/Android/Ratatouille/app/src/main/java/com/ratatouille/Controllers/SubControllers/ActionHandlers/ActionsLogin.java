package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsLogin extends ActionsViewHandler{
    private static final String TAG = "ActionsLogin";

    public final static int INDEX_ACTION_START_REGISTER_ADMIN   = 0;
    public final static int INDEX_ACTION_START_LOGIN            = 1;
    public final static int INDEX_ACTION_LOGIN                  = 2;
    public final static int INDEX_ACTION_CONFIRM_LOGIN          = 3;
    public final static int INDEX_ACTION_LOGIN_ADMIN            = 4;

    public ActionsLogin(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_START_REGISTER_ADMIN, new StartRegisterAdmin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_START_LOGIN, new StartLogin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN, new Login_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN_ADMIN, new LoginAdmin_ActionHandler());
    }
    private static class StartRegisterAdmin_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");
            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");
            new LocalStorage(context).putData("TypeUser","Amministratore");
        }
    }

    private static class StartLogin_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: NormalLogin");

            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");
            new LocalStorage(context).putData("TypeUser","");
        }
    }

    private static class Login_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");
            if(getUserFromServer(action)){
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
        private boolean getUserFromServer(Action action){
            Utente user = (Utente)action.getData();
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Email", user.getEmail())
                    .appendQueryParameter("Password",user.getPassword());
            String url = EndPointer.StandardPath + "/Login.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG").contains("1")){
                    JSONArray DATA_Json = new JSONArray(BodyJSON.getString("DATA"));
                    JSONObject utente_Json = new JSONObject(DATA_Json.getString(0));

                    user.setId_utente(Integer.parseInt( utente_Json.getString("ID_Utente") ));
                    user.setId_Restaurant(Integer.parseInt( utente_Json.getString("ID_Ristorante") ));
                    user.setNome(utente_Json.getString("Nome"));
                    user.setCognome(utente_Json.getString("Cognome"));
                    user.setToken(utente_Json.getString("Token"));
                    user.setType_user(utente_Json.getString("Type_User"));

                    Log.d(TAG, "getUserFromServer: true");
                    return true;
                }else{
                    String messaggeError = BodyJSON.getString("MSG").replace("0 ","");
                    action.getManager().setData(messaggeError);
                    Log.d(TAG, "getUserFromServer: false");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }


    }

    private static class LoginAdmin_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {

            Log.d(TAG, "handleAction: RegistraADMIN");


            String token = "WAITING";
            ((Utente)action.getData()).setToken(token);
            getFCMToken(action);

            startRegistration(action);

        }

        private void startRegistration(Action action){
            Context context = action.getManager().context;
            if(stetUserToServer(action)){
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

        private boolean stetUserToServer(Action action){
            Utente user = (Utente)action.getData();
            user.setNome("Amministratore");
            user.setCognome("");
            Log.d(TAG, "stetUserToServer: Email->"+user.getEmail());
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Nome", user.getNome())
                    .appendQueryParameter("Token", user.getToken())
                    .appendQueryParameter("TypeUser", user.getType_user())
                    .appendQueryParameter("Cognome", user.getCognome())
                    .appendQueryParameter("Email", user.getEmail())
                    .appendQueryParameter("Password",user.getPassword());
            String url = EndPointer.StandardPath + "/RegisterUser.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG").contains("1")){
                    JSONObject DATA_Json = new JSONObject(BodyJSON.getString("DATA"));
                    Log.d(TAG, "setUserToServer: DATA_JSON->"+DATA_Json.toString(4));
//                    JSONObject utente_Json = new JSONObject(DATA_Json.getString(0));
//
//                    user.setId_utente(Integer.parseInt( utente_Json.getString("ID_Utente") ));
//                    user.setId_Restaurant(Integer.parseInt( utente_Json.getString("ID_Ristorante") ));
//                    user.setNome(utente_Json.getString("Nome"));
//                    user.setCognome(utente_Json.getString("Cognome"));
//                    user.setToken(utente_Json.getString("Token"));
//                    user.setType_user(utente_Json.getString("Type_User"));
                    Log.d(TAG, "setUserToServer: true");
                    return true;
                }else{
                    String messaggeError = BodyJSON.getString("MSG").replace("0 ","");
                    action.getManager().setData(messaggeError);
                    Log.d(TAG, "setUserToServer: false");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "setUserToServer: ",e);
                return false;
            }
        }
    }

}
