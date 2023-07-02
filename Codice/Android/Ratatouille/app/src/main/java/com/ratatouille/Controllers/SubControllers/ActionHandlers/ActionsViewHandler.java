package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import com.ratatouille.Models.Action.Action;

import java.util.Map;

public class ActionsViewHandler {
    Map<Integer, ActionHandler> actionHandlerMap = null;

    public void handleAction(Action action){
        ActionHandler handler = actionHandlerMap.get(action.getActionType());
        if (handler != null) handler.handleAction(action);
    }
}
