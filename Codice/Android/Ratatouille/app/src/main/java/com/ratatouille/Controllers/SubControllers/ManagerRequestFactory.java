package com.ratatouille.Controllers.SubControllers;

import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestAccount;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestCategory;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestHandler;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestIngredients;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestOrdiniTavoli;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestProducts;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestRicettario;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestStaff;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestStats;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestTableInfo;
import com.ratatouille.Controllers.SubControllers.RequestHandlers.RequestTavoli;
import com.ratatouille.Models.Events.Request.Request;
import java.util.HashMap;
import java.util.Map;

public class ManagerRequestFactory {
    //FUNCTIONAL
    public final static int INDEX_REQUEST_CATEGORY      = 0;
    public final static int INDEX_REQUEST_INGREDIENTS   = 1;
    public final static int INDEX_REQUEST_PRODUCTS      = 2;
    public final static int INDEX_REQUEST_RICETTARIO    = 3;
    public final static int INDEX_REQUEST_STAFF         = 4;
    public final static int INDEX_REQUEST_RESTAURANT    = 5;
    public final static int INDEX_REQUEST_TAVOLI        = 6;
    public final static int INDEX_REQUEST_TAVOLO_INFO   = 7;
    public final static int INDEX_REQUEST_ORDINI_TAVOLO = 8;
    public final static int INDEX_REQUEST_STATS         = 9;

    public Map<Integer, RequestHandler> requestHandlerMap ;

    public ManagerRequestFactory() {
        requestHandlerMap = new HashMap<>();
        requestHandlerMap.put(INDEX_REQUEST_CATEGORY,       new RequestCategory());
        requestHandlerMap.put(INDEX_REQUEST_INGREDIENTS,    new RequestIngredients());
        requestHandlerMap.put(INDEX_REQUEST_PRODUCTS,       new RequestProducts());
        requestHandlerMap.put(INDEX_REQUEST_RICETTARIO,     new RequestRicettario());
        requestHandlerMap.put(INDEX_REQUEST_STAFF,          new RequestStaff());
        requestHandlerMap.put(INDEX_REQUEST_RESTAURANT,     new RequestAccount());
        requestHandlerMap.put(INDEX_REQUEST_TAVOLI,         new RequestTavoli());
        requestHandlerMap.put(INDEX_REQUEST_TAVOLO_INFO,    new RequestTableInfo());
        requestHandlerMap.put(INDEX_REQUEST_ORDINI_TAVOLO,  new RequestOrdiniTavoli());
        requestHandlerMap.put(INDEX_REQUEST_STATS,          new RequestStats());

    }

    public void handleRequest(Request request) {
        RequestHandler handler = requestHandlerMap.get(request.getTypeRequest());
        if (handler != null)  handler.handleRequest(request);
    }
}
