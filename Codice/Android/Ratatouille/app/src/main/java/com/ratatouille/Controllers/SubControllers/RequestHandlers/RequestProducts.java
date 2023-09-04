package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestProducts implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestProducts";
    //DATA
    protected ArrayList<Product> ListProducts;
    @Override
    public void handleRequest(Request request) {
        CategoriaMenu categoriaMenu = (CategoriaMenu)request.getData();
        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("ID_Category",categoriaMenu.getID_categoria() + "");
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Product.php";
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListProducts = new ArrayList<>();
            setProducts(BodyJSON);

            request.callBack(ListProducts);

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setProducts(JSONObject BodyJSON) throws JSONException {
        Log.d(TAG, "setProducts: PRODUCTS ->\n" + BodyJSON.toString(4));
        if(!BodyJSON.getString("MSG_STATUS").contains("0 Nessun Ingredient")){
            JSONArray ProductJSON = new JSONArray(BodyJSON.getString("DATA"));

            for(int i = 0 ; i<ProductJSON.length(); i++){
                JSONObject productJSON = new JSONObject(ProductJSON.getString(i));
                Product product = new Product();

                product.setID_product( Integer.parseInt(productJSON.getString("ID_Prodotto")));
                product.setID_category(Integer.parseInt(productJSON.getString("ID_CategoryMenu")));
                product.setNameProduct(productJSON.getString("NameProdotto"));
                product.setPriceProduct(Float.parseFloat(productJSON.getString("PriceProdotto")));
                product.setDescriptionProduct(productJSON.getString("Description"));
                product.setAllergeniProduct(productJSON.getString("Allergeni"));
                product.setSendToKitchen(productJSON.getString("isSendToKitchen").equals("1"));
                product.setURLImageProduct(productJSON.getString("PhotoURL"));

                ListProducts.add(product);
            }
        }
    }
}
