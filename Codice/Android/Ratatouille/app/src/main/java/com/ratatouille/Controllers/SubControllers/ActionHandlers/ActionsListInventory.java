package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONObject;

import java.util.HashMap;

public class ActionsListInventory extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsListInventory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_SHOW_NEW_INGREDIENT    = 0;
    public final static int INDEX_ACTION_DELETE_INGREDIENT      = 1;
    public final static int INDEX_ACTION_SHOW_REMOVE_INGREDIENT = 2;
    public final static int INDEX_ACTION_SELECT_INGREDIENT      = 3;
    public final static int INDEX_ACTION_ADD_TO_PRODUCT         = 4;

    public ActionsListInventory(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_NEW_INGREDIENT,      new ShowNewIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_REMOVE_INGREDIENT,   new ShowDeleteIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_DELETE_INGREDIENT,        new DeleteIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SELECT_INGREDIENT,        new SelectedIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_TO_PRODUCT,           new AddToProduct_ActionHandler());
    }

    private static class ShowNewIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW NEW INGREDIENT");
            action.getManager().hideBottomBar();
            action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_NEW,null);
        }
    }

    private static class ShowDeleteIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW DELETE INGREDIENT");
        }
    }

    protected static class DeleteIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Ingredient ingredient = (Ingredient) action.getData();
            if( sendDeleteIngredientToServer(ingredient.getID_Ristorante(), ingredient.getID_Ingredient() ) ){
                action.callBack(ingredient.getID_Ingredient());
            }
        }

        public boolean sendDeleteIngredientToServer(int id_ristorante, int  id_ingredient){
            Log.d(TAG, "sendDeleteIngredientToServer: idIngredient = "+id_ingredient);
            Log.d(TAG, "sendDeleteIngredientToServer: idRestaurant = "+id_ristorante);
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante",  id_ristorante+"")
                    .appendQueryParameter("id_ingredient",    id_ingredient+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/Ingredient.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG");
                    if(Msg.contains("Failed Deleting")) return false;
                }else{
                    Log.d(TAG, "sendDeleteIngredientToServer: false");
                    return false;
                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendDeleteIngredientToServer: true");
            return true;
        }
    }

    private static class SelectedIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SELECTED INGREDIENT");
            Ingredient ingredient = (Ingredient) action.getData();
            action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_INFO,ingredient);
        }
    }

    private static class AddToProduct_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW NEW INGREDIENT");

        }
    }
}
