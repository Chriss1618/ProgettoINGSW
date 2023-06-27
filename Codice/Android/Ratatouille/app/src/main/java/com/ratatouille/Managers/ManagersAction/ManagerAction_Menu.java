package com.ratatouille.Managers.ManagersAction;

import android.os.Handler;
import android.util.Log;

import com.ratatouille.Models.Action;
import com.ratatouille.Schermate.Menu.MenuViewFactory;

import java.util.HashMap;
import java.util.Map;

//STRATEGY PATTERN
public class ManagerAction_Menu {
    //SYSTEM
    private static final String TAG = "ManagerAction_Menu";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS     = 0;
    public final static int INDEX_ACTION_SHOW_ADD_CATEGORY      = 1;

    private final Map<Integer, ActionHandler> actionHandlerMap;

    private interface ActionHandler{
        void handleAction(Action action);
    }

    //ACTIONS HANDLED **************************************************************
    private static class OpenListProducts_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            String categoria = (String) action.getData();

            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria);

            action.getManager().changeOnMain(MenuViewFactory.INDEX_MENU_LIST_PRODUCTS,categoria);
        }
    }

    private static class showAddNewCategory_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: AddNewCategoryActionHandler->");
            action.callBack();
        }
    }
    //******************************************************************************

    public ManagerAction_Menu() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS,   new OpenListProducts_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_ADD_CATEGORY,    new showAddNewCategory_ActionHandler());
    }

    public void handleAction(Action action) {
        ActionHandler handler = actionHandlerMap.get(action.getActionType());
        if (handler != null) handler.handleAction(action);
    }

}
