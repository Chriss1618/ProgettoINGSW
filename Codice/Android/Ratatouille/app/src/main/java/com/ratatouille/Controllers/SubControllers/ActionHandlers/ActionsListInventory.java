package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Events.Action.Action;
import java.util.HashMap;

public class ActionsListInventory extends ActionsViewHandler{
    private static final String TAG = "ActionsListInventory";

    //FUNCTIONAL
    public final static int INDEX_ACTION_ADD_INGREDIENT         = 0;
    public final static int INDEX_ACTION_SELECTED_INGREDIENT    = 1;
    public final static int INDEX_ACTION_REMOVE_INGREDIENT      = 2;

    public ActionsListInventory(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_ADD_INGREDIENT,   new ShowAddNewIngredient_ActionHandler());
    }

    private static class ShowAddNewIngredient_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            action.callBack();
            action.getManager().hideBottomBar();
            action.getManager().changeOnMain(ControlMapper.INDEX_INVENTORY_NEW,"");
        }
    }



}
