package com.ratatouille.Controllers.SubControllers;

import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestCategory;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestHandler;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestIngredients;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestProducts;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestRicettario;
import com.ratatouille.Models.Events.Request.Request;

import java.util.HashMap;
import java.util.Map;

public class ManagerRequestFactory {
    //FUNCTIONAL
    public final static int INDEX_REQUEST_CATEGORY      = 0;
    public final static int INDEX_REQUEST_INGREDIENTS   = 1;
    public final static int INDEX_REQUEST_PRODUCTS      = 2;
    public final static int INDEX_REQUEST_RICETTARIO    = 3;

    Map<Integer, RequestHandler> requestHandlerMap ;

    public ManagerRequestFactory() {
        requestHandlerMap = new HashMap<>();
        requestHandlerMap.put(INDEX_REQUEST_CATEGORY,       new RequestCategory());
        requestHandlerMap.put(INDEX_REQUEST_INGREDIENTS,    new RequestIngredients());
        requestHandlerMap.put(INDEX_REQUEST_PRODUCTS,       new RequestProducts());
        requestHandlerMap.put(INDEX_REQUEST_RICETTARIO,     new RequestRicettario());
    }

    public void handleRequest(Request request) {
        RequestHandler handler = requestHandlerMap.get(request.getTypeRequest());
        if (handler != null)  handler.handleRequest(request);
    }
}
