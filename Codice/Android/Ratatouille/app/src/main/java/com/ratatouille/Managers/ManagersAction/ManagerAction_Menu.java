package com.ratatouille.Managers.ManagersAction;

import android.os.Handler;
import android.util.Log;

import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;
import com.ratatouille.Models.Action;
import com.ratatouille.Schermate.Menu.MenuViewFactory;

import java.util.HashMap;
import java.util.Map;

public class ManagerAction_Menu {
    private static final String TAG = "ManagerAction_Menu";
    public final static int INDEX_ACTION_OPEN_LIST_PRODUCTS   = 0;
    public final static int INDEX_ACTION_ADD_CATEGORY   = 1;

    public final Map<Integer, ActionHandler> actionHandlerMap  = new HashMap<>();

    public ManagerAction_Menu() {
        initializeActionHandlers();
    }

    private interface ActionHandler{
        void handleAction(Action action);
    }

    private static class GetCategorieActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            String categoria = (String) action.getData();

            Log.d(TAG, "handleAction: GetCategorieActionHandler->"+ categoria);

            action.getManager().changeOnMain(MenuViewFactory.INDEX_MENU_LIST_PRODUCTS,categoria);
        }
    }

    private static class AddNewCategoryActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: AddNewCategoryActionHandler->");
            action.callBack();
        }
    }

    private void initializeActionHandlers() {
        actionHandlerMap.put(INDEX_ACTION_OPEN_LIST_PRODUCTS, new GetCategorieActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_CATEGORY, new AddNewCategoryActionHandler());
    }

    public void handleAction(Action action) {
        ActionHandler handler = actionHandlerMap.get(action.getActionType());
        if (handler != null) {
            handler.handleAction(action);
        }
    }



}
