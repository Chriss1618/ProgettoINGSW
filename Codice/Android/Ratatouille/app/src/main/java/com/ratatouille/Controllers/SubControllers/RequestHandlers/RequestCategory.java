package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RequestCategory implements RequestHandler {

    //SYSTEM
    private static final String TAG = "RequestCategory";

    //DATA
    protected ArrayList<CategoriaMenu> ListCategoryMenu;

    @Override
    public void handleRequest(Request request) {
        getCategoriesFromServer(request);
    }
    private void setCategories(JSONObject BodyJSON) throws org.json.JSONException{

        //Log.d(TAG, "\nDATA_JSON ->"+CategorieJSON.toString(4));
        if(!BodyJSON.getString("MSG_STATUS").contains("0 Nessuna Categoria")) {
            JSONArray CategorieJSON = new JSONArray(BodyJSON.getString("DATA"));
            for (int i = 0; i < CategorieJSON.length(); i++) {
                JSONObject categoriaJSON = new JSONObject(CategorieJSON.getString(i));
                ListCategoryMenu.add(new CategoriaMenu(
                        categoriaJSON.getString("NomeCategoria"),
                        Integer.parseInt(categoriaJSON.getString("ID_CategoriaMenu"))
                ));
            }
        }
    }
    private void getCategoriesFromServer(Request request){

        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("id_ristorante",new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"" );
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/CategoriaMenu.php";
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListCategoryMenu = new ArrayList<>();
            if( BodyJSON != null ) setCategories( BodyJSON );
            //TimeUnit.SECONDS.sleep(1);
            request.callBack(ListCategoryMenu);

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }

    }

}
