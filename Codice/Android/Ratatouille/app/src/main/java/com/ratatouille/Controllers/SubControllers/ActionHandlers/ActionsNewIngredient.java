package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsNewIngredient extends ActionsViewHandler {
    //SYSTEM
    private static final String TAG = "ActionsNewIngredient";

    //FUNCTIONAL
    public final static int INDEX_ACTION_CREATE_INGREDIENT      = 0;
    public final static int INDEX_ACTION_CANCEL                 = 1;

    public ActionsNewIngredient() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_CREATE_INGREDIENT,      new CreateIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CANCEL,   new Cancel_ActionHandler());
    }

    private static class CreateIngredient_ActionHandler implements ActionHandler {
        String nome;
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> CREATE NEW INGREDIENT");
            boolean isInserted = sendToServer(action);
            action.callBack(isInserted);
            if(isInserted){
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
                action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_LIST,null);
            }
        }

        private boolean sendToServer(Action action){
            Ingredient ingredient = (Ingredient) action.getData();

            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("NameIngredient", ingredient.getNameIngredient())
                    .appendQueryParameter("id_ristorante", ingredient.getID_Ristorante()+"")
                    .appendQueryParameter("Misura", ingredient.getSizeIngredient()+"")
                    .appendQueryParameter("PhotoDATA", ingredient.isHasPhoto()? ingredient.getDataFromUriProduct(action.getManager().context): "NoPhoto")
                    .appendQueryParameter("UnitaMisura", ingredient.getMeasureType())
                    .appendQueryParameter("Quantita", ingredient.getQtaIngredient()+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Ingredient.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG_STATUS").contains("1")){
                    Log.d(TAG, "sendToServer: RICEVUTO DA SERVER ->\n" + BodyJSON.toString(4));

                    return true;
                }else{

                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }
    }

    private static class Cancel_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> CANCEL NEW INGREDIENT");

        }
    }
}
