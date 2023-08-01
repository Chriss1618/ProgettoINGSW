package com.ratatouille.Controllers.SubControllers;

import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestCategory;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestHandler;
import com.ratatouille.Models.Events.Request.Request;

import java.util.HashMap;
import java.util.Map;

public class ManagerRequestFactory {
    //FUNCTIONAL
    public final static int INDEX_REQUEST_CATEGORY     = 0;

    Map<Integer, RequestHandler> requestHandlerMap ;

    public ManagerRequestFactory() {
        requestHandlerMap = new HashMap<>();
        requestHandlerMap.put(INDEX_REQUEST_CATEGORY,   new RequestCategory());
    }

    public void handleRequest(Request request) {
        RequestHandler handler = requestHandlerMap.get(request.getTypeRequest());
        if (handler != null)  handler.handleRequest(request);
    }
}
