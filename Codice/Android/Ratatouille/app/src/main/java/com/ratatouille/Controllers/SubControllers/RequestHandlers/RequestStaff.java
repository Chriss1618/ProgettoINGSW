package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestStaff implements RequestHandler{
    //SYSTEM
    private static final String TAG = "RequestStaff";

    //DATA
    private ArrayList<Utente> ListStaff;
    private Request request;
    @Override
    public void handleRequest(Request request) {
        Log.d(TAG, "handleRequest: STAFF");
        this.request = request;
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Staff.php";
        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("id_ristorante", new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"");
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            ListStaff = new ArrayList<>();
            setStaff(BodyJSON);
            request.callBack(ListStaff);
            Log.d(TAG, "handleRequest: ");

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setStaff(JSONObject BodyJSON) throws JSONException {
        Log.d(TAG, "setStaff: STAFF ->\n" + BodyJSON.toString(4));
        if(BodyJSON.getString("MSG_STATUS").contains("1 Staff Trovati")){
            JSONArray ListStaffJSON = new JSONArray(BodyJSON.getString("DATA"));

            for(int i = 0 ; i<ListStaffJSON.length(); i++){
                JSONObject memberJSON = new JSONObject(ListStaffJSON.getString(i));
                int id_currUser =(Integer) new LocalStorage(request.getManager().context).getData("ID_Utente","Integer");

                if(id_currUser != Integer.parseInt(memberJSON.getString("ID_Utente")) ){
                    Utente Member = new Utente();
                    Member.setId_utente( Integer.parseInt(memberJSON.getString("ID_Utente")) );
                    Member.setNome(memberJSON.getString("Nome"));
                    Member.setCognome(memberJSON.getString("Cognome"));
                    Member.setEmail(memberJSON.getString("Email"));
                    Member.setPassword(memberJSON.getString("Password"));
                    Member.setType_user(memberJSON.getString("Type_User"));
                    Member.setToken(memberJSON.getString("Token"));

                    ListStaff.add(Member);
                }

            }

            Log.d(TAG, "setRicettario: Numero Staff (Escluso te stesso)->" + ListStaff.size());
        }
    }
}
