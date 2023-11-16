package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsStaff extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsStaff";

    //DATA
    //ACTIONS INDEX
    public final static int INDEX_ACTION_ADD_STAFF           = 0;
    public final static int INDEX_ACTION_REMOVE_STAFF        = 1;
    public final static int INDEX_ACTION_CREATE_STAFF        = 2;

    public ActionsStaff(){
        MapLocalActions();
    }

    @Override
    protected void MapLocalActions(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_ADD_STAFF,        new OpenNewStaff_ActionHandler() );
        actionHandlerMap.put(INDEX_ACTION_REMOVE_STAFF,     new RemoveStaff_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CREATE_STAFF,     new CreateStaff_ActionHandler());
    }

    private static class OpenNewStaff_ActionHandler implements IActionHandler {

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> Clicked Add Stuff");
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_STAFF_NEW,null);

        }

    }

    private static class RemoveStaff_ActionHandler implements IActionHandler {

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> Remove Stuff");
            Utente StaffToDelete = (Utente) action.getData();
            Log.d(TAG, "handleAction: id Utente to Delete ->" + StaffToDelete.getId_utente());
            if(sendDeleteStaffToServer(StaffToDelete)){
                action.callBack(StaffToDelete);
            }

        }

        private boolean sendDeleteStaffToServer(Utente staff){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_Utente", staff.getId_utente()+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/Staff.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG");
                    if(Msg.contains("1 Utente Eliminato")) return true;
                }else{
                    Log.d(TAG, "sendDeleteStaffToServer: false");
                    return false;
                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendDeleteStaffToServer: true");
            return false;
        }

    }

    private static class CreateStaff_ActionHandler implements IActionHandler {

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> Create Stuff");

            Utente utente = (Utente) action.getData();
            Log.d(TAG, "---------------------------------------------------------- ");
            Log.d(TAG, "handleAction: Email Utente ->" + utente.getEmail());
            Log.d(TAG, "handleAction: Nome Utente ->" + utente.getNome());
            Log.d(TAG, "handleAction: Cognome Utente ->" + utente.getCognome());
            Log.d(TAG, "handleAction: Password Utente ->" + utente.getPassword());
            Log.d(TAG, "handleAction: TypeUser Utente ->" + utente.getType_user());
            Log.d(TAG, "handleAction: Ristorante ID Utente ->" + new LocalStorage(action.getManager().context).getData("ID_Ristorante","Integer"));
            Log.d(TAG, "---------------------------------------------------------- ");

            boolean isInserted = sendNewStuffToServer(utente,action.getManager().context);
            action.callBack(isInserted);
            if(isInserted){
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1500));
                action.getManager().goBack();
            }
        }

        private boolean sendNewStuffToServer(Utente newUtente, Context context){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Nome",          newUtente.getNome())
                    .appendQueryParameter("Cognome",       newUtente.getCognome())
                    .appendQueryParameter("Type_User",     newUtente.getType_user())
                    .appendQueryParameter("Email",         newUtente.getEmail())
                    .appendQueryParameter("Password",      newUtente.getPassword())
                    .appendQueryParameter("ID_Ristorante", new LocalStorage(context).getData("ID_Ristorante","Integer")+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Staff.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG_STATUS").contains("1")){
                    Log.d(TAG, "sendToServer: RICEVUTO DA SERVER ->\n" + BodyJSON.toString(4));

                    return true;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);

                return false;
            }

            return false;

        }


    }


}
