package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Action.Action;

import java.util.HashMap;

public class ActionsListCategory extends ActionsViewHandler{
    private static final String TAG = "ActionsListCategory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS     = 0;
    public final static int INDEX_ACTION_SHOW_ADD_CATEGORY      = 1;

    public ActionsListCategory() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_ADD_CATEGORY,   new showAddNewCategory_ActionHandler());
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
}
