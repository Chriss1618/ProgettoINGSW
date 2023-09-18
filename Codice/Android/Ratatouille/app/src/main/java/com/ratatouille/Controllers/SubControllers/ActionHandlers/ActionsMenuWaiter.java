package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public final static int INDEX_ACTION_CLOSE_TABLE        = 6;

    public ActionsMenuWaiter(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_TABLE,           new ShowTable_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_MENU_WAITER,     new ShowMenuWaiter_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_INFO_PRODUCT,    new ShowInfoProduct_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SEND_TO_KITCHEN,      new SendKitchen_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CREATE_ORDER,         new CreateOrder_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CLOSE_TABLE,         new CloseOrder_ActionHandler());
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
            Ordine ordine = (Ordine) action.getManager().getData();
            ordine.setCategoria((CategoriaMenu) action.getData());
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_PRODUCT,ordine);
        }
    }

    private static class ShowInfoProduct_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            action.getManager().setDataAlternative(action.getData());
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,action.getManager().getData());
        }
    }
    
    private static class SendKitchen_ActionHandler implements ActionHandler{
        private ArrayList<Product> ListProducts ;
        private Ordine ordine;
        private Action action;
        @SuppressWarnings("unchecked")
        @Override
        public void handleAction(Action action) {
            ordine = (Ordine) action.getData();
            ListProducts = ordine.getTavolo().getProdottiDaOrdinare();
            this.action = action;

            Log.d(TAG, "handleAction: Sending to Kitchen");
            Log.d(TAG, "handleAction: id_Ordine->"+ ordine.getId_Ordine());
            Log.d(TAG, "Sending: "+ ListProducts.size()+" products");

            for(Product product : ListProducts){
                Log.d(TAG, "Aggiungo all'ordine -> "+ product.getNameProduct());
            }
            boolean isOrdered = CreateOrderToServer();
            action.callBack(isOrdered);

            if(isOrdered){
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
                if(action.getManager().IndexOnMain == ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT) action.getManager().goBack();
                else action.getManager().goBack2(ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_TABLE);
            }
        }

        private boolean CreateOrderToServer(){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Id_Ristorante", new LocalStorage(action.getManager().context).getData("ID_Ristorante","Integer")+"")
                    .appendQueryParameter("Id_Ordine", ordine.getId_Ordine())
                    .appendQueryParameter("nProducts",ListProducts.size()+ "");
            for(int i = 0 ; i < ListProducts.size(); i++){
                dataToSend.appendQueryParameter("Id_Product"+i,ListProducts.get(i).getID_product()+ "");
                dataToSend.appendQueryParameter("priceProduct"+i,ListProducts.get(i).getPriceProduct()+ "");
            }

            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/ProdottoOrdinato.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG_STATUS");
                    if(Msg.contains("1 Products Ordered")) {

                    }
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

    private static class CreateOrder_ActionHandler implements ActionHandler{
        private Ordine ordine;
        @Override
        public void handleAction(Action action) {
            ordine = (Ordine) action.getData();
            Log.d(TAG, "handleAction: Ordine da creare su id_Tavolo->"+ordine.getTavolo().getId_Tavolo());
            Log.d(TAG, "handleAction: Numero Tavolo->"+ordine.getTavolo().getN_Tavolo());
            boolean isInserted = CreateOrderToServer();
            action.getManager().setData(ordine);
            if (isInserted) action.callBack();
        }

        private boolean CreateOrderToServer(){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Id_Tavolo",  ordine.getTavolo().getId_Tavolo()+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Order.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG_STATUS");
                    if(Msg.contains("1 Order Created")) {
                        ordine.setId_Ordine(BodyJSON.getString("DATA"));
                        ordine.getTavolo().setStateTavolo(false);
                        ordine.setPrezzoTotale("0.00");
                    }
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
    private static class CloseOrder_ActionHandler implements ActionHandler{
        @Override
        public void handleAction(Action action) {
            Tavolo tavolo = (Tavolo) action.getData();
            Log.d(TAG, "handleAction: chiudere tavolo su id_Tavolo->"+tavolo.getId_Tavolo());
            Log.d(TAG, "handleAction: Numero Tavolo->"+tavolo.getN_Tavolo());
            boolean isClosed = CloseTableToServer(tavolo);
            action.getManager().setData(tavolo);
            if (isClosed) action.callBack();
        }

        private boolean CloseTableToServer(Tavolo tavolo){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("Id_Tavolo",  tavolo.getId_Tavolo()+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/OrderClose.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG_STATUS");
                    if(Msg.contains("1 Table Closed")) {
                        tavolo.setStateTavolo(true);
                        return true;
                    }
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
}
