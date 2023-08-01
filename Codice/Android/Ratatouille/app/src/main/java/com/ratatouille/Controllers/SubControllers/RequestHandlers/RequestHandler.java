package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import com.ratatouille.Models.Events.Request.Request;

public interface RequestHandler {
    void handleRequest(Request request);
}
