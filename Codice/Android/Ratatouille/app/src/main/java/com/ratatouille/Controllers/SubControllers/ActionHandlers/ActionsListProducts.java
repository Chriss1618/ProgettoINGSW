package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;


import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;

import java.util.HashMap;

public class ActionsListProducts extends ActionsViewHandler {
    private static final String TAG = "ActionsListProducts";

    //ACTIONS INDEX
    public final static int INDEX_ACTION_OPEN_PRODUCT           = 0;
    public final static int INDEX_ACTION_OPEN_NEW_PRODUCT       = 1;

    public ActionsListProducts() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_PRODUCT, new OpenProductInfo_ActionHandler()  );
        actionHandlerMap.put(INDEX_ACTION_OPEN_NEW_PRODUCT, new OpenNewProduct_ActionHandler()  );
    }

    //ACTIONS HANDLED **************************************************************
    private static class OpenProductInfo_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: OpenProductInfo_ActionHandler->");

        }
    }

    private static class OpenNewProduct_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction: GetCategorieActionHandler->");
            action.callBack();
            action.getManager().changeOnMain(ControlMapper.INDEX_MENU_NEW_PRODUCT, action.getData());
        }
    }

}
