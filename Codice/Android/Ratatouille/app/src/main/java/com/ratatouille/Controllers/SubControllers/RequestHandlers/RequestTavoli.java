package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestTavoli implements RequestHandler{

    //SYSTEM
    private static final String TAG = "RequestTavoli";

    //DATA
    Request request;
    ArrayList<Tavolo>   ListTavoli;
    @Override
    public void handleRequest(Request request) {
        Log.d(TAG, "handleRequest: STAFF");
        this.request = request;
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Tavoli.php";
        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("ID_Ristorante", new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"");
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListTavoli = new ArrayList<>();
            setTavoli(BodyJSON);
            request.callBack(ListTavoli);
            Log.d(TAG, "handleRequest: ");

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setTavoli(JSONObject BodyJSON) throws JSONException {
        if(BodyJSON.getString("MSG_STATUS").contains("1 Tavoli trovati")){
            JSONArray ListStaffJSON = new JSONArray(BodyJSON.getString("DATA"));

            for(int i = 0 ; i<ListStaffJSON.length(); i++){
                JSONObject memberJSON = new JSONObject(ListStaffJSON.getString(i));

                Tavolo Tavolo = new Tavolo();
                Tavolo.setId_Tavolo( memberJSON.getString("ID_Tavolo") );
                Tavolo.setID_Restaurant(memberJSON.getString("ID_Ristorante"));
                Tavolo.setN_Tavolo(memberJSON.getString("Numero_tavolo"));
                Tavolo.setStateTavolo(memberJSON.getString("State_Tavolo").equals("0"));


                ListTavoli.add(Tavolo);
            }

            Log.d(TAG, "setRicettario: Numero Tavoli ->" + ListTavoli.size());
        }
    }
}
