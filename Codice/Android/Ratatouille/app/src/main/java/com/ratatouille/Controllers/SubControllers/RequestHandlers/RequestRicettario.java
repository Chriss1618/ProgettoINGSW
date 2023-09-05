package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.util.Log;

import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Request.Request;

public class RequestRicettario implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestRicettario";
    @Override
    public void handleRequest(Request request) {
        Product product = (Product) request.getData();
        Log.d(TAG, "handleRequest: Prodotto  ->"+ product.getNameProduct());
    }
}
