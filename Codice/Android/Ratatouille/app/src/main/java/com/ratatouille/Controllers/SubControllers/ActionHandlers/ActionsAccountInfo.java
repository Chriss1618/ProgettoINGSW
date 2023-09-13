package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Restaurant;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.Views.Schermate.Activity_ChooseRole;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsAccountInfo extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsAccountInfo";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_EDIT_ACCOUNT      = 0;
    public final static int INDEX_ACTION_LOGOUT                 = 1;
    public final static int INDEX_ACTION_EDIT_ACCOUNT           = 2;

    public ActionsAccountInfo(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_EDIT_ACCOUNT,    new OpenEditAccount_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGOUT,               new LogOut_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_EDIT_ACCOUNT,         new EditAccount_ActionHandler());
    }

    private static class OpenEditAccount_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_ACCOUNT_EDIT,action.getData());
        }
    }

    private static class LogOut_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            new LocalStorage(action.getManager().context).DeleteAllData();
            triggerRebirth(action.getManager().context);
        }

        public static void triggerRebirth(Context context) {
            Intent intent = new Intent(context, Activity_ChooseRole.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

            Runtime.getRuntime().exit(0);
        }
    }

    private static class EditAccount_ActionHandler implements ActionHandler {

        Utente utente;
        Restaurant MyRestaurant;

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: Pressed EDIT");
            utente = (Utente) action.getManager().getData();
            MyRestaurant = (Restaurant) action.getData();
            boolean isUpdated = sendRestaurantToServer(action.getManager().context);
            action.callBack(isUpdated);
            if (isUpdated) {
                new LocalStorage(action.getManager().context).putData("Cognome",utente.getCognome());
                new LocalStorage(action.getManager().context).putData("Nome",utente.getNome());
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1500));
                action.getManager().goBack();
            }
        }

        private boolean sendRestaurantToServer(Context context) {
            int id_restaurant = (Integer) new LocalStorage(context).getData("ID_Ristorante", "Integer");
            int id_utente = (Integer) new LocalStorage(context).getData("ID_Utente", "Integer");
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_Restaurant",      id_restaurant + "")
                    .appendQueryParameter("NameRistorante",     MyRestaurant.getName())
                    .appendQueryParameter("AddressRistorante",  MyRestaurant.getAddress())
                    .appendQueryParameter("Phone",              MyRestaurant.getPhone())
                    .appendQueryParameter("NrTavoli",           MyRestaurant.getnTavoli())
                    .appendQueryParameter("Nome",               utente.getNome())
                    .appendQueryParameter("Cognome",            utente.getCognome())
                    .appendQueryParameter("ID_Utente",          id_utente + "");

            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.UPDATE + "/Restaurant.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData(dataToSend, url);
                if (BodyJSON != null && BodyJSON.getString("MSG_STATUS").contains("1")) {
                    Log.d(TAG, "sendToServer: RICEVUTO DA SERVER ->\n" + BodyJSON.toString(4));
                    return true;
                }

            } catch (Exception e) {
                Log.e(TAG, "getDataFromServer: ", e);

                return false;
            }
            Log.d(TAG, "sendRestaurantToServer: false");
            return false;
        }
    }
}
