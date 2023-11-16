package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsInfoEditIngredient extends ActionsViewHandler {
    //SYSTEM
    private static final String TAG = "ActionsNewIngredient";

    //FUNCTIONAL
    public final static int INDEX_ACTION_EDIT_INGREDIENT        = 0;
    public final static int INDEX_ACTION_UPDATE_INGREDIENT      = 1;

    public ActionsInfoEditIngredient() {
        MapLocalActions();
    }

    @Override
    protected void MapLocalActions(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_EDIT_INGREDIENT,       new EditIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_UPDATE_INGREDIENT,      new UpdateIngredient_ActionHandler());
    }

    protected static class EditIngredient_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW EDIT INGREDIENT");
            action.getManager().hideBottomBar();
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_INVENTORY_EDIT,action.getData());
        }
    }

    protected static class UpdateIngredient_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> Update INGREDIENT");

            boolean isUpdated = sendToServer(action.getData(), action.getManager().context);
            action.callBack(isUpdated);
            if(isUpdated){
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
                action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_INVENTORY_LIST,null);
            }

        }

        private boolean sendToServer(Object Data, Context context){
            Ingredient ingredient = (Ingredient) Data;

            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("NameIngredient", ingredient.getNameIngredient())
                    .appendQueryParameter("ID_Ingredient", ingredient.getID_Ingredient()+"")
                    .appendQueryParameter("id_ristorante", ingredient.getID_Ristorante()+"")
                    .appendQueryParameter("Misura", ingredient.getSizeIngredient()+"")
                    .appendQueryParameter("PhotoDATA", ingredient.isHasPhoto()? ingredient.getDataFromUriProduct(context) : "NoPhoto")
                    .appendQueryParameter("UnitaMisura", ingredient.getMeasureType())
                    .appendQueryParameter("PhotoURL", ingredient.getURLImageIngredient())
                    .appendQueryParameter("Quantita", ingredient.getQtaIngredient()+"")
                    .appendQueryParameter("Prezzo", ingredient.getPriceIngredient())
                    .appendQueryParameter("Description", ingredient.getDescription())
                    ;
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.UPDATE + "/Ingredient.php";
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
}
