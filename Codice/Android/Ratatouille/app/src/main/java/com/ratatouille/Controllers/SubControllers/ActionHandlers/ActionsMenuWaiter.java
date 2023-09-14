package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Events.Action.Action;

import java.util.HashMap;

public class ActionsMenuWaiter extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsMenuWaiter";

    public final static int INDEX_ACTION_SHOW_TABLE         = 0;
    public final static int INDEX_ACTION_SHOW_MENU_WAITER   = 1;
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS = 2;

    public ActionsMenuWaiter(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_TABLE,           new ShowTable_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_MENU_WAITER,     new ShowMenuWaiter_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
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
}
