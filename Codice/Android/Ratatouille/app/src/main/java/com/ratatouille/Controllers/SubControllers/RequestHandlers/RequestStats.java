package com.ratatouille.Controllers.SubControllers.RequestHandlers;

import android.net.Uri;
import android.util.Log;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Stats;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.LocalStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestStats extends RequestHandler {
    //SYSTEM
    private static final String TAG = "RequestStats";

    private Stats Stats;
    @Override
    public void handleRequest(Request request) {
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/Stats.php";
        Uri.Builder dataToSend  = new Uri.Builder()
                .appendQueryParameter("id_ristorante", new LocalStorage(request.getManager().context).getData("ID_Ristorante","Integer")+"");
        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            Stats = new Stats();
            setStats(BodyJSON);
            request.callBack(Stats);

        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }
    private void setStats(JSONObject BodyJSON) throws JSONException {
        if(BodyJSON.getString("MSG_STATUS").contains("1 Stats Ottenute")){
            JSONObject DataJSON = new JSONObject(BodyJSON.getString("DATA"));
            JSONArray ListStaffJSON = new JSONArray(DataJSON.getString("Chefs"));
            for(int i = 0 ; i<ListStaffJSON.length(); i++){
                JSONObject memberJSON = new JSONObject(ListStaffJSON.getString(i));

                Utente Member = new Utente();
                Member.setId_utente( Integer.parseInt(memberJSON.getString("ID_Utente")) );
                Member.setNome(memberJSON.getString("Nome"));
                Member.setCognome(memberJSON.getString("Cognome"));
                Member.setType_user(memberJSON.getString("Type_User"));

                Stats.getListStaffChef().add(Member);

            }
            JSONArray ListOrdiniProdottiJSON = new JSONArray(DataJSON.getString("ProdottiOrdinati"));
            for(int i = 0 ; i<ListOrdiniProdottiJSON.length(); i++){
                JSONObject OrdineJSON = new JSONObject(ListOrdiniProdottiJSON.getString(i));

                Product prodotto = new Product();
                prodotto.setId_ProdottoOrdinato( OrdineJSON.getString("ID_Utente") );
                prodotto.setId_User(OrdineJSON.getString("ID_Utente"));
                prodotto.setTimestampCompletamento(OrdineJSON.getString("DataCompletamento"));

                Stats.getListOrdiniCompletati().add(prodotto);
            }

        }
    }
}
