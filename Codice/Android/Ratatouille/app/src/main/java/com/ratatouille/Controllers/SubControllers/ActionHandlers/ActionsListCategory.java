package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONObject;
import java.util.HashMap;

public class ActionsListCategory extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsListCategory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS     = 0;
    public final static int INDEX_ACTION_SHOW_ADD_CATEGORY      = 1;
    public final static int INDEX_ACTION_ADD_CATEGORY           = 2;
    public final static int INDEX_ACTION_REMOVE_CATEGORY        = 3;

    public ActionsListCategory(){
        MapLocalActions();
    }

    @Override
    protected void MapLocalActions(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_ADD_CATEGORY,    new showAddNewCategory_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_CATEGORY,         new AddNewCategory_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_REMOVE_CATEGORY,      new DeleteCategory_ActionHandler());
    }

    //ACTIONS HANDLED **************************************************************
    protected static class OpenListProducts_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            CategoriaMenu categoria = (CategoriaMenu) action.getData();
            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria.getNomeCategoria());

            action.getManager().changeOnMain(ControlMapper.IndexViewMapper.INDEX_MENU_LIST_PRODUCTS,categoria);
        }
    }

    protected static class showAddNewCategory_ActionHandler implements IActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: OpenPopUpNewCategoryActionHandler->");
            action.callBack();
        }
    }
    protected static class AddNewCategory_ActionHandler implements IActionHandler {
        private CategoriaMenu addedCategory;

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: AddNewCategoryActionHandler->");
            String category     = (String)action.getData();
            String id_restaurant = new LocalStorage(action.getManager().context).getData("ID_Ristorante","Integer")+"";

            if(sendNewCategoryToServer(category,id_restaurant) > 0 ){
                action.callBack(addedCategory);
                Log.d(TAG, "handleAction: Aggiunto Categoria");
            }else{
                Log.d(TAG, "handleAction: Categoria Non Aggiunto");
            }
        }

        private JSONObject sendToServer(String newCategory, String id_restaurant){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante",id_restaurant )
                    .appendQueryParameter("NameCategory",newCategory);
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/CategoriaMenu.php";

            return new ServerCommunication().getData( dataToSend, url);
        }
        private boolean JSONCheckIsInserted(JSONObject ResponseServerJSON) {
            try{
                return ResponseServerJSON.getString("MSG_STATUS").contains("1");
            }catch (Exception e){
                return false;
            }
        }

        private void getCategoryFromJSON(JSONObject BodyJSON) {
            try{
                JSONObject Categoria_Json = new JSONObject(BodyJSON.getString("DATA"));
                addedCategory = new CategoriaMenu(
                        Categoria_Json.getString("NomeCategoria"),
                        Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                );
            }catch (Exception e){
                addedCategory = null;
            }
        }

        protected int sendNewCategoryToServer( String newCategory, String id_restaurant ){
            JSONObject ResponseServerJSON = sendToServer( newCategory, id_restaurant );
            if( JSONCheckIsInserted( ResponseServerJSON ) ){
                getCategoryFromJSON( ResponseServerJSON );
                return addedCategory.getID_categoria();
            }else{
                return 0;
            }
        }


    }

    protected static class DeleteCategory_ActionHandler implements IActionHandler {

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: DeleteCategoryActionHandler->");
            int id_category = ((CategoriaMenu) action.getData()).getID_categoria();
            int id_restaurant = (int) new LocalStorage(action.getManager().context).getData("ID_Ristorante","Integer");

            if( sendDeleteCategoryToServer(id_category,id_restaurant)){
                action.callBack(id_category);
                Log.d(TAG, "handleAction: Cancelled Categoria");
            }else{
                Log.d(TAG, "handleAction: Categoria Non Cancelled");
            }
        }
        private JSONObject sendToServer(int id_category, int id_restaurant){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante", id_restaurant+"")
                    .appendQueryParameter("id_category",id_category+"");
            final String UrlDeleteCategory = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.DELETE + "/CategoriaMenu.php";

            return new ServerCommunication().getData( dataToSend, UrlDeleteCategory);
        }
        private boolean JSONCheckIsDeleted(JSONObject ResponseServerJSON)  {
            try{
                if(ResponseServerJSON == null ) return false;
                return ResponseServerJSON.getString("MSG").contains("1");
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
                return false;
            }
        }

        protected boolean sendDeleteCategoryToServer( int id_category, int id_restaurant ){
            JSONObject ResponseServerJSON = sendToServer( id_category,  id_restaurant);
            return JSONCheckIsDeleted( ResponseServerJSON );
        }

    }
}
