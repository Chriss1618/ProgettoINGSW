package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

import io.vavr.control.Try;

public class ActionsListCategory extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsListCategory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS     = 0;
    public final static int INDEX_ACTION_SHOW_ADD_CATEGORY      = 1;
    public final static int INDEX_ACTION_ADD_CATEGORY           = 2;
    public final static int INDEX_ACTION_REMOVE_CATEGORY        = 3;

    public ActionsListCategory(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_ADD_CATEGORY,   new showAddNewCategory_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_CATEGORY,   new AddNewCategory_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_REMOVE_CATEGORY,   new DeleteCategory_ActionHandler());
    }

    //ACTIONS HANDLED **************************************************************
    private static class OpenListProducts_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            CategoriaMenu categoria = (CategoriaMenu) action.getData();

            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria.getNomeCategoria());
            action.callBack();
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
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante", "1")
                    .appendQueryParameter("NameCategory",newCategory);
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/CategoriaMenu.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null && BodyJSON.getString("MSG").contains("1")){

                    JSONObject Categoria_Json = new JSONObject(BodyJSON.getString("DATA"));
                    addedCategory = new CategoriaMenu(
                            Categoria_Json.getString("NomeCategoria"),
                            Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                    );
                    Log.d(TAG, "sendNewCategoryToServer: true");
                    return true;
                }else{

                    Log.d(TAG, "sendNewCategoryToServer: false");
                    return false;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }
    }
    private static class DeleteCategory_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: DeleteCategoryActionHandler->");
            int id_category = (int)action.getData();
            if(sendDeleteCategoryToServer(id_category)){
                action.callBack(id_category);
                Log.d(TAG, "handleAction: Cancelled Categoria");
            }else{
                Log.d(TAG, "handleAction: Categoria Non Cancelled");
            }
        }

        private boolean sendDeleteCategoryToServer(int id_category){
            Log.d(TAG, "sendDeleteCategoryToServer: idCategory = "+id_category);
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante", "1")
                    .appendQueryParameter("id_category",id_category+"");
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/CategoriaMenu.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if( BodyJSON != null ){
                    String Msg = BodyJSON.getString("MSG");
                    if(Msg.contains("Failed Deleting")) return false;
                }else{
                    Log.d(TAG, "sendDeleteCategoryToServer: false");
                    return false;
                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendDeleteCategoryToServer: true");
            return true;


        }
    }
}
