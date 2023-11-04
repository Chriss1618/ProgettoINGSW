package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

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
        int id_ristorante = (Integer) new LocalStorage(request.getManager().context).getData("ID_Ristorante", "Integer");
        if(getProductsFromServer(categoriaMenu.getID_categoria(),id_ristorante)){
            request.callBack(ListProducts);
        }else{
            request.callBack(ListProducts);
        }
    }

    protected JSONObject sendToServer(int id_category, int id_restaurant){
        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("ID_Category",id_category + "")
                .appendQueryParameter("id_restaurant",id_restaurant + "");
        final String  url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Product.php";

        return new ServerCommunication().getData( dataToSend, url);
    }
    protected boolean JSONCheckProductsExist( JSONObject BodyJSON )  {
        try{
            return BodyJSON.getString("MSG_STATUS").contains("1");

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
            return false;
        }
    }

    public boolean getProductsFromServer( int id_category, int id_restaurant ){
        JSONObject ResponseServerJSON =  sendToServer( id_category, id_restaurant );
        if( JSONCheckProductsExist( ResponseServerJSON) ){
            SaveProducts( ResponseServerJSON );
            return true;
        }
        else return false;
    }

    private void SaveProducts(JSONObject BodyJSON)  {
        try{
            ListProducts = new ArrayList<>();
            if(BodyJSON.getString("MSG_STATUS").contains("1 Products Trovati")){
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
        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }
}
