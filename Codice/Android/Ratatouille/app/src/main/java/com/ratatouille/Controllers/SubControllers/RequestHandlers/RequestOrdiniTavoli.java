package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestOrdiniTavoli extends RequestHandler {
    //SYSTEM
    private static final String TAG = "RequestOrdiniTavoli";
    //DATA
    ArrayList<Ordine> ordini;

    @Override
    public void handleRequest(Request request) {
        getRestaurantFromServer(request);
    }

    private void getRestaurantFromServer(Request request){

        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("ID_Ristorante",new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"" );
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/OrdiniTavoli.php";

        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ordini = new ArrayList<>();
            if( BodyJSON != null ) setOrders( BodyJSON );
            request.callBack(ordini);
        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setOrders(JSONObject BodyJSON) throws org.json.JSONException{
        if(BodyJSON.getString("MSG_STATUS").contains("1 Ordini trovati")) {
            JSONArray DataJSON = new JSONArray(BodyJSON.getString("DATA"));
            for( int index = 0 ; index < DataJSON.length();index++){
                JSONObject OrdineJSON = new JSONObject(DataJSON.getString(index));

                Ordine ordine = new Ordine();
                ordine.setId_Ordine(OrdineJSON.getString("ID_Ordine"));

                JSONObject TavoloJSON = new JSONObject(OrdineJSON.getString("Tavolo"));
                Tavolo tavolo = new Tavolo();

                tavolo.setId_Tavolo(TavoloJSON.getString("ID_Tavolo"));
                tavolo.setN_Tavolo(TavoloJSON.getString("Numero_tavolo"));
                JSONArray ListaProdottiJSON = new JSONArray(OrdineJSON.getString("ListaProdottiOrdinati"));
                for( int indexProdotti = 0 ; indexProdotti < ListaProdottiJSON.length();indexProdotti++){
                    JSONObject ProdottoJSON = new JSONObject(ListaProdottiJSON.getString(indexProdotti));
                    Product product = new Product();

                    product.setID_product( Integer.parseInt(ProdottoJSON.getString("ID_Prodotto")));
                    product.setID_category(Integer.parseInt(ProdottoJSON.getString("ID_CategoryMenu")));
                    product.setNameProduct(ProdottoJSON.getString("NameProdotto"));
                    product.setPriceProduct(Float.parseFloat(ProdottoJSON.getString("PriceProdotto")));
                    product.setDescriptionProduct(ProdottoJSON.getString("Description"));
                    product.setAllergeniProduct(ProdottoJSON.getString("Allergeni"));
                    product.setSendToKitchen(ProdottoJSON.getString("isSendToKitchen").equals("1"));
                    product.setURLImageProduct(ProdottoJSON.getString("PhotoURL"));
                    product.setURLImageProduct(ProdottoJSON.getString("PhotoURL"));

                    product.setId_User(   ProdottoJSON.getString("Id_User") );
                    product.setTimestampCompletamento( ProdottoJSON.getString("TimestampCompletamento") );
                    product.setId_ProdottoOrdinato(ProdottoJSON.getString("ID_ProdottoOrdinato"));
                    tavolo.getProdottiOrdinati().add(product);
                }
                ordine.setTavolo(tavolo);
                ordini.add(ordine);
            }


        }
    }
}
