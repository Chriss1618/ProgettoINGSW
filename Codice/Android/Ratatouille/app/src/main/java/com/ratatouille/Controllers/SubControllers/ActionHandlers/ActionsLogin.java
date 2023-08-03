package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

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
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsLogin extends ActionsViewHandler{
    private static final String TAG = "ActionsLogin";

    public final static int INDEX_ACTION_REGISTER_ADMIN     = 0;
    public final static int INDEX_ACTION_NORMAL_LOGIN       = 1;
    public final static int INDEX_ACTION_LOGIN              = 2;
    public final static int INDEX_ACTION_CONFIRM_LOGIN      = 3;
    public final static int INDEX_ACTION_LOGIN_ADMIN      = 4;

    public ActionsLogin(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_REGISTER_ADMIN, new RegistrarAdmin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_NORMAL_LOGIN, new NormalLogin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN, new Login_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN_ADMIN, new LoginAdmin_ActionHandler());
    }
    private static class RegistrarAdmin_ActionHandler implements ActionHandler{

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

    private static class NormalLogin_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: NormalLogin");

            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");

            
        }

    }

    private static class Login_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");
            if(getUserFromServer((Utente)action.getData())){
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
        private boolean getUserFromServer(Utente user){
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
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");
            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");
            new LocalStorage(context).putData("TypeUser","Amministratore");
        }

    }

}
