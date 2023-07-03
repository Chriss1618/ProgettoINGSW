package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Action.Action;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.Models.EndPoints.EndPointer;
import com.ratatouille.Models.ServerCommunication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActionsListCategory extends ActionsViewHandler{
    private static final String TAG = "ActionsListCategory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS     = 0;
    public final static int INDEX_ACTION_SHOW_ADD_CATEGORY      = 1;
    public final static int INDEX_ACTION_ADD_CATEGORY           = 2;
    public ActionsListCategory() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_ADD_CATEGORY,   new showAddNewCategory_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_CATEGORY,   new AddNewCategory_ActionHandler());
    }

    //ACTIONS HANDLED **************************************************************
    private static class OpenListProducts_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            String categoria = (String) action.getData();

            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria);

            action.getManager().changeOnMain(ControlMapper.INDEX_MENU_LIST_PRODUCTS,categoria);
        }
    }
    private static class showAddNewCategory_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: AddNewCategoryActionHandler->");
            action.callBack();
        }
    }
    private static class AddNewCategory_ActionHandler implements ActionHandler {
        private CategoriaMenu addedCategory;
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: AddNewCategoryActionHandler->");
            String category = (String)action.getData();
            if(sendNewCategoryToServer(category)){
                action.callBack(addedCategory);
                Log.d(TAG, "handleAction: Aggiunto Categoria");
            }else{
                Log.d(TAG, "handleAction: Categoria Non Aggiunto");
            }
        }


        private boolean sendNewCategoryToServer(String newCategory){
            Uri.Builder dataToSend = new Uri.Builder().appendQueryParameter("id_ristorante", "1")
                    .appendQueryParameter("NameCategory",newCategory);
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/CategoriaMenu.php";

            try {
                JSONArray Msg = new ServerCommunication().getData( dataToSend, url);
                if( Msg != null ){

                    for(int i = 0 ; i<Msg.length(); i++){
                        JSONObject Categoria_Json = new JSONObject(Msg.getString(i));

                        addedCategory = new CategoriaMenu(
                                Categoria_Json.getString("NomeCategoria"),
                                Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                        );
                    }

                }else{
                    Log.d(TAG, "sendNewCategoryToServer: false");
                    return false;
                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendNewCategoryToServer: true");
            return true;


        }
    }
}
