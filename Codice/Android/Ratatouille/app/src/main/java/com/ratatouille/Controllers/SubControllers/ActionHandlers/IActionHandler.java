package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import com.ratatouille.Models.Events.Action.Action;

public interface IActionHandler {
    void handleAction(Action action);
}
