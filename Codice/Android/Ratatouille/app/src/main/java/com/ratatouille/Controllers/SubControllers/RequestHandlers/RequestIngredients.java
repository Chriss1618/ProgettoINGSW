package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestIngredients implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestIngredients";

    //DATA
    protected ArrayList<Ingredient> ListIngredient;

    @Override
    public void handleRequest(Request request) {
        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("id_ristorante", new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"");
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Ingredients.php";
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListIngredient = new ArrayList<>();
            setIngredients(BodyJSON);

            request.callBack(ListIngredient);

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setIngredients(JSONObject BodyJSON) throws JSONException {
        Log.d(TAG, "setIngredients: DATA->\n"+BodyJSON.toString(4));
        if(!BodyJSON.getString("MSG_STATUS").contains("0 Nessun Ingredient")){
            JSONArray CategorieJSON = new JSONArray(BodyJSON.getString("DATA"));

            for(int i = 0 ; i<CategorieJSON.length(); i++){
                JSONObject categoriaJSON = new JSONObject(CategorieJSON.getString(i));
                ListIngredient.add(new Ingredient(
                        Integer.parseInt( categoriaJSON.getString("ID_Ingrediente") ),
                        Integer.parseInt( categoriaJSON.getString("ID_Ristorante") ),
                        categoriaJSON.getString("NameIngrediente"),
                        categoriaJSON.getString("Description"),
                        categoriaJSON.getString("Price"),
                        Integer.parseInt( categoriaJSON.getString("Misura").replace(".00","") ),
                        categoriaJSON.getString("UnitaMisura"),
                        Integer.parseInt( categoriaJSON.getString("Quantita") ),
                        categoriaJSON.getString("PhotoURL")
                ));
            }
        }

    }
}
