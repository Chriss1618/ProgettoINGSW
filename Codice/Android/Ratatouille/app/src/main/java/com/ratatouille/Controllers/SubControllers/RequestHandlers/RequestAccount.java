package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Restaurant;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestAccount implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestAccount";
    //DATA
    Restaurant MyRestaurant;
    @Override
    public void handleRequest(Request request) {
        getRestaurantFromServer(request);
        request.callBack(MyRestaurant);
    }

    private void getRestaurantFromServer(Request request){

        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("ID_Ristorante",new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"" );
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Account.php";

        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            MyRestaurant = new Restaurant();
            if( BodyJSON != null ) setMyRestaurant( BodyJSON );

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }

    }

    private void setMyRestaurant(JSONObject BodyJSON) throws org.json.JSONException{
        if(!BodyJSON.getString("MSG_STATUS").contains("0 Nessuna Categoria")) {
            JSONArray CategorieJSON = new JSONArray(BodyJSON.getString("DATA"));
            for (int i = 0; i < CategorieJSON.length(); i++) {
                JSONObject categoriaJSON = new JSONObject(CategorieJSON.getString(i));
                MyRestaurant.setName(categoriaJSON.getString("NameRistorante"));
                MyRestaurant.setAddress(categoriaJSON.getString("AddressRistorante"));
                MyRestaurant.setPhone(categoriaJSON.getString("Phone"));
                MyRestaurant.setnTavoli(categoriaJSON.getString("nTavoli"));

            }
        }
    }
}
