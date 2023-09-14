package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;

import java.util.HashMap;

public class ActionsMenuWaiter extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsMenuWaiter";

    public final static int INDEX_ACTION_SHOW_TABLE   = 0;

    public ActionsMenuWaiter(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SHOW_TABLE,     new ShowTable_ActionHandler());
    }
    private static class ShowTable_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_CAMERIERE_INFO_TABLE,action.getData());
        }
    }
}
