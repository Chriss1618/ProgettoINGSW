package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class ActionsOrdini extends ActionsViewHandler{

    //SYSTEM
    private static final String TAG = "ActionsOrdini";
    //ACTIONS INDEX
    public final static int INDEX_ACTION_SELECT_TABLE           = 0;
    public final static int INDEX_ACTION_CONFIRM_ORDERS         = 1;
    public final static int INDEX_ACTION_OPEN_HISTORY           = 2;

    public ActionsOrdini(){
        MapLocalActions();
    }
    @Override
    protected void MapLocalActions(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SELECT_TABLE,     new TableSelected_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CONFIRM_ORDERS,   new ConfirmOrders_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_HISTORY,   new ShowHistory_ActionHandler());
    }

    private static class TableSelected_ActionHandler implements IActionHandler {

        @Override
        public void handleAction(Action action) {
            ArrayList<Ordine> ListTables = (ArrayList<Ordine>) action.getData();
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_ORDINI_TABLE,ListTables);
        }

    }

    private static class ShowHistory_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            ArrayList<Ordine> ListTables = (ArrayList<Ordine>) action.getData();
            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_ORDINI_HISTORY,ListTables);
        }
    }

    private static class ConfirmOrders_ActionHandler implements IActionHandler {
        private ArrayList<Product> ListReady;
        @Override

        public void handleAction(Action action) {
            ListReady = (ArrayList<Product>) action.getData();
            if(sendNewProductToServer(action.getManager().context)){
                action.callBack(true);
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
                action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_ORDINI_LIST,null);
            }else{
                action.callBack(false);
            }
        }

        private boolean sendNewProductToServer(Context context){
            int id_utente = (Integer) new LocalStorage(context).getData("ID_Utente", "Integer");
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("nProdottiOrdinati", ListReady.size() +"")
                    .appendQueryParameter("Id_Utente", id_utente +"");
            int nIngredient = 0;

            for(Product prodottoReady : ListReady){
                dataToSend.appendQueryParameter("ID_ProdottoOrdinato" +nIngredient, prodottoReady.getId_ProdottoOrdinato() );
                dataToSend.appendQueryParameter("ID_Product" +nIngredient,            prodottoReady.getID_product()+"");
                nIngredient+=1;
            }

            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.UPDATE + "/ProductsReady.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if(BodyJSON != null){
                    Log.d(TAG, "sendToServer: RICEVUTO DA SERVER ->\n" + BodyJSON.toString(4));
                    return true;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);

                return false;
            }
            Log.d(TAG, "sendNewProductToServer: false");
            return false;

        }


    }
}
