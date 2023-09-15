package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsMenuWaiter extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsMenuWaiter";

    public final static int INDEX_ACTION_SHOW_TABLE         = 0;
    public final static int INDEX_ACTION_SHOW_MENU_WAITER   = 1;
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS = 2;
    public final static int INDEX_ACTION_OPEN_INFO_PRODUCT  = 3;
    public final static int INDEX_ACTION_SEND_TO_KITCHEN    = 4;
    public final static int INDEX_ACTION_CREATE_ORDER       = 5;

    public ActionsMenuWaiter(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_TABLE,           new ShowTable_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_MENU_WAITER,     new ShowMenuWaiter_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_INFO_PRODUCT,    new ShowInfoProduct_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SEND_TO_KITCHEN,      new SendKitchen_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CREATE_ORDER,         new CreateOrder_ActionHandler());
    }
    private static class ShowTable_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_INFO_TABLE,action.getData());
        }
    }
    private static class ShowMenuWaiter_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT,action.getData());
        }
    }

    private static class OpenListProducts_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            CategoriaMenu categoria = (CategoriaMenu) action.getData();
            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria.getNomeCategoria());

            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_PRODUCT,categoria);
        }
    }

    private static class ShowInfoProduct_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,action.getData());
        }
    }
    
    private static class SendKitchen_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: Sending to Kitchen");
            ArrayList<Product> ListProducts = (ArrayList<Product>) action.getData();
            Log.d(TAG, "Senbding: "+ ListProducts.size()+" products");
            for(Product product : ListProducts){
                Log.d(TAG, "handleAction: "+ product.getNameProduct());
            }


            Try.run(() -> TimeUnit.MILLISECONDS.sleep(1500));
            action.callBack(true);
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
            action.getManager().goBack();
            if(action.getManager().IndexOnMain == ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT) action.getManager().goBack();
        }

    }

    private static class CreateOrder_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Tavolo tavolo = (Tavolo) action.getData();
            Log.d(TAG, "handleAction: Ordine da creare su id_Tavolo->"+tavolo.getId_Tavolo());
            Log.d(TAG, "handleAction: Numero Tavolo->"+tavolo.getN_Tavolo());
        }

//        private boolean CreateOrderToServer(Tavolo tavolo){
//
//            Uri.Builder dataToSend = new Uri.Builder()
//                    .appendQueryParameter("id_ordine",  tavolo.getId_Tavolo()+"")
//                    .appendQueryParameter("id_ingredient",    ingredient.getID_Ingredient()+"");
//            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/Ingredient.php";
//
//            try {
//                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
//                if( BodyJSON != null ){
//                    String Msg = BodyJSON.getString("MSG");
//                    if(Msg.contains("Failed Deleting")) return false;
//                }else{
//                    Log.d(TAG, "sendDeleteIngredientToServer: false");
//                    return false;
//                }
//            }catch (Exception e){
//                Log.e(TAG, "getDataFromServer: ",e);
//            }
//            Log.d(TAG, "sendDeleteIngredientToServer: true");
//            return true;
//        }
    }
}
