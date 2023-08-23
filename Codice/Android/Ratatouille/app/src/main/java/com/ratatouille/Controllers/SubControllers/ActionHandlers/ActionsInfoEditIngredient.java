package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;

import java.util.HashMap;

public class ActionsInfoEditIngredient extends ActionsViewHandler {
    //SYSTEM
    private static final String TAG = "ActionsNewIngredient";

    //FUNCTIONAL
    public final static int INDEX_ACTION_EDIT_INGREDIENT        = 0;
    public final static int INDEX_ACTION_CANCEL                 = 1;

    public ActionsInfoEditIngredient() {
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_EDIT_INGREDIENT,      new EditIngredient_ActionHandler());
    }

    private static class EditIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> CANCEL NEW INGREDIENT");
            action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_EDIT,action.getData());
        }
    }
}
