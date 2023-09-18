package com.ratatouille.Models.API.Firebase;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.LocalStorage;

import org.json.JSONObject;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    Integer id_utente;
    String token;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken: " + token);
        id_utente = (Integer) new LocalStorage(action.getManager().context).getData("ID_Utente", "Integer");
        this.token = token;

    }
    private boolean UpdateTokenDB(){
        Uri.Builder dataToSend = new Uri.Builder()
                .appendQueryParameter("ID_Utente", id_utente+"")
                .appendQueryParameter("Token", token);


        String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/UpdateToken.php";

        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
            if( BodyJSON != null ){
                String Msg = BodyJSON.getString("MSG_STATUS");
                if(Msg.contains("1 Token Updated")) {

                }
            }else{
                Log.d(TAG, "sendDeleteIngredientToServer: false");
                return false;
            }
        }catch (Exception e){
            Log.e(TAG, "getDataFromServer: ",e);
        }
        Log.d(TAG, "sendDeleteIngredientToServer: true");
        return true;
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(TAG, "onMessageReceived: Ricevuto Messaggio");
        Map<String, String> data = message.getData();
        Log.d(TAG, "onMessageReceived: get Title ->" + data.get("key1"));
        Log.d(TAG, "onMessageReceived: get Title ->" + data.get("key2"));
    }

}
