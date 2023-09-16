package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestTableInfo implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestTableInfo";

    //DATA
    private     Tavolo tavolo;
    private     Ordine ordine;
    protected   ArrayList<Product> ListProducts;

    @Override
    public void handleRequest(Request request) {
        ordine = new Ordine();
        ordine.setTavolo((Tavolo) request.getData());
        getOrdineTavoloFromServer(request);
    }
    private void getOrdineTavoloFromServer(Request request){
        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("Id_Tavolo",ordine.getTavolo().getId_Tavolo()+"" );
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/TableInfo.php";
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url );
            ListProducts = new ArrayList<>();
            if( BodyJSON != null ) setOrdineTavolo( BodyJSON );
            //TimeUnit.SECONDS.sleep(1);
            request.callBack(ordine);

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setOrdineTavolo(JSONObject BodyJSON) throws org.json.JSONException{
        if(!BodyJSON.getString("MSG_STATUS").contains("0 Nessuna Ordine")) {
            JSONObject tavoloInfoJSON = new JSONObject(BodyJSON.getString("DATA"));

            JSONArray ProductJSON = new JSONArray(tavoloInfoJSON.getString("ListaProdottiOrdinati"));

            for(int i = 0 ; i<ProductJSON.length(); i++){
                JSONObject productJSON = new JSONObject(ProductJSON.getString(i));
                Product product = new Product();

                product.setID_product(      Integer.parseInt(   productJSON.getString("ID_Prodotto")));
                product.setID_category(     Integer.parseInt(   productJSON.getString("ID_CategoryMenu")));
                product.setNameProduct(                         productJSON.getString("NameProdotto"));
                product.setPriceProduct(    Float.parseFloat(   productJSON.getString("PriceProdotto")));
                product.setDescriptionProduct(                  productJSON.getString("Description"));
                product.setAllergeniProduct(                    productJSON.getString("Allergeni"));
                product.setSendToKitchen(                       productJSON.getString("isSendToKitchen").equals("1"));
                product.setURLImageProduct(                     productJSON.getString("PhotoURL"));

                ListProducts.add(product);
            }

            ordine.getTavolo().setProdottiOrdinati(ListProducts);
            ordine.setPrezzoTotale( tavoloInfoJSON.getString("PrezzoTotale"));
            ordine.setId_Ordine( tavoloInfoJSON.getString("ID_Ordine"));
        }
    }
}
