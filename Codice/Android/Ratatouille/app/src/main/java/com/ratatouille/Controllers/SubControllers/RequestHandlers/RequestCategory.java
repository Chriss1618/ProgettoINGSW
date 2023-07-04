package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class RequestCategory implements RequestHandler {

    //SYSTEM
    private static final String TAG = "RequestCategory";

    //DATA
    private ArrayList<CategoriaMenu> ListCategoryMenu;


    private void setCategories(JSONArray Msg) throws org.json.JSONException{
        if( Msg != null ){
            ListCategoryMenu = new ArrayList<>();
            for(int i = 0 ; i<Msg.length(); i++){
                JSONObject Categoria_Json = new JSONObject(Msg.getString(i));

                ListCategoryMenu.add(new CategoriaMenu(
                        Categoria_Json.getString("NomeCategoria"),
                        Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                ));
            }
        }
    }

    private ArrayList<CategoriaMenu> getCategoriesFromServer(){
        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("id_ristorante", "1");
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/CategoriaMenu.php";

        try {
            JSONArray Json_Categories = new ServerCommunication().getData( dataToSend, url);
            setCategories( Json_Categories );
        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
        return ListCategoryMenu;
    }

    @Override
    public void handleRequest(Request request) {
        getCategoriesFromServer();
        request.callBack(ListCategoryMenu);
    }
}
