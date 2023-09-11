package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Request.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class RequestRicettario implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestRicettario";

    //DATA
    ArrayList<Ricettario> ListRicettario;
    @Override
    public void handleRequest(Request request) {
        Product product = (Product) request.getData();
        Log.d(TAG, "handleRequest: Prodotto  ->"+ product.getNameProduct());

        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("ID_Product",product.getID_product() + "");
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Ricettario.php";
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListRicettario = new ArrayList<>();
            setRicettario(BodyJSON);
            request.callBack(ListRicettario);
            Log.d(TAG, "handleRequest: ");

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setRicettario(JSONObject BodyJSON) throws JSONException {
        Log.d(TAG, "setProducts: PRODUCTS ->\n" + BodyJSON.toString(4));
        if(BodyJSON.getString("MSG_STATUS").contains("1 Ricettari Trovati")){
            JSONArray ListRicettariJSON = new JSONArray(BodyJSON.getString("DATA"));

            for(int i = 0 ; i<ListRicettariJSON.length(); i++){
                JSONObject ricettarioJSON = new JSONObject(ListRicettariJSON.getString(i));
                Ricettario ricettario = new Ricettario();

                ricettario.setDosi( Integer.parseInt(ricettarioJSON.getString("Dosi").split("\\.")[0]));
                Log.d(TAG, "setRicettario: Dose salvata->"+ricettario.getDosi());
                ricettario.setTypeMeasure(ricettarioJSON.getString("UnitaMisura"));

                JSONObject IngredientJSON = new JSONObject( ricettarioJSON.getString("Ingrediente"));
                ricettario.setIngredient(new Ingredient(
                        Integer.parseInt( IngredientJSON.getString("ID_Ingrediente") ),
                        Integer.parseInt( IngredientJSON.getString("ID_Ristorante") ),
                        IngredientJSON.getString("NameIngrediente"),
                        IngredientJSON.getString("Description"),
                        IngredientJSON.getString("Price"),
                        Integer.parseInt( IngredientJSON.getString("Misura").replace(".00","") ),
                        IngredientJSON.getString("UnitaMisura"),
                        Integer.parseInt( IngredientJSON.getString("Quantita") ),
                        IngredientJSON.getString("PhotoURL")
                ));
                ListRicettario.add(ricettario);
            }
        }
    }
}
