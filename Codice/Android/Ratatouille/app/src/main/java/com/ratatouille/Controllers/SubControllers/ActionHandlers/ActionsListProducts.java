package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;


import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionsListProducts extends ActionsViewHandler {
    private static final String TAG = "ActionsListProducts";

    //ACTIONS INDEX
    public final static int INDEX_ACTION_OPEN_PRODUCT           = 0;
    public final static int INDEX_ACTION_OPEN_NEW_PRODUCT       = 1;
    public final static int INDEX_ACTION_DELETE_PRODUCT         = 2;
    public final static int INDEX_ACTION_ORDER_PRODUCTS         = 3;

    public ActionsListProducts() {
        MapLocalActions();
    }
    @Override
    protected void MapLocalActions(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_PRODUCT,     new OpenProductInfo_ActionHandler()  );
        actionHandlerMap.put(INDEX_ACTION_OPEN_NEW_PRODUCT, new OpenNewProduct_ActionHandler()  );
        actionHandlerMap.put(INDEX_ACTION_DELETE_PRODUCT,   new DeleteProduct_ActionHandler()  );
        actionHandlerMap.put(INDEX_ACTION_ORDER_PRODUCTS,   new OrderProducts_ActionHandler()  );
    }
    //ACTIONS HANDLED **************************************************************
    private static class OpenProductInfo_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: OpenProductInfo_ActionHandler->");
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_MENU_INFO_PRODUCT,action.getData());
        }
    }

    private static class OpenNewProduct_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: GetCategorieActionHandler->");
            action.getManager().hideBottomBar();
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_MENU_NEW_PRODUCT, action.getData());
        }
    }

    private static class DeleteProduct_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Product product = (Product) action.getData();
            if(sendDeleteProductToServer(product)){
                action.callBack(product.getID_product());
            }
        }

        protected boolean sendDeleteProductToServer(Product product){
            Log.d(TAG, "sendDeleteProductToServer: ID_product = "+product.getID_product());
            Log.d(TAG, "sendDeleteProductToServer: ID_category = "+product.getID_category());
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_Category",  product.getID_category()+"")
                    .appendQueryParameter("Id_Product",    product.getID_product()+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/Product.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG");
                    if(Msg.contains("Failed Deleting")) return false;
                }else{
                    Log.d(TAG, "sendDeleteProductToServer: false");
                    return false;
                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendDeleteProductToServer: true");
            return true;
        }
    }

    private static class OrderProducts_ActionHandler implements IActionHandler {
        @SuppressWarnings("unchecked")
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: OrderProducts_ActionHandler-> Started");
            sendUpdateOrderProductToServer((ArrayList<Product>) action.getData());
        }

        protected void sendUpdateOrderProductToServer(ArrayList<Product> ListProducts){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("nProducts",  ListProducts.size()+"");
            int index = 0;
            for (Product product : ListProducts) {
                dataToSend
                        .appendQueryParameter("Id_Product"+index,  product.getID_product()+"")
                        .appendQueryParameter("Posizione"+index,  product.getOrder()+"");
                index++;
            }

            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.UPDATE + "/OrderProducts.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("DATA");
                    //if(Msg.contains("0 Connessione DB Fallita")) return false;
                }else{
                    Log.d(TAG, "sendUpdateOrderProductToServer: false");
                    //return false;
                }
            }catch (Exception e){
                Log.e(TAG, "sendUpdateOrderProductToServer: ",e);
            }
            Log.d(TAG, "sendUpdateOrderProductToServer: true");
            //return true;
        }
    }

}
