package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;

import java.util.HashMap;

public class ActionsListInventory extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsListInventory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_SHOW_NEW_INGREDIENT    = 0;
    public final static int INDEX_ACTION_REMOVE_INGREDIENT      = 1;
    public final static int INDEX_ACTION_SHOW_REMOVE_INGREDIENT = 2;
    public final static int INDEX_ACTION_SELECT_INGREDIENT      = 2;

    public ActionsListInventory(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_NEW_INGREDIENT,      new ShowNewIngredient_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_SHOW_REMOVE_INGREDIENT,   new ShowDeleteIngredient_ActionHandler());
    }

    private static class ShowNewIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW NEW INGREDIENT");
            action.getManager().hideBottomBar();
            action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_NEW,null);
        }
    }

    private static class ShowDeleteIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> SHOW DELETE INGREDIENT");


        }
    }

}
