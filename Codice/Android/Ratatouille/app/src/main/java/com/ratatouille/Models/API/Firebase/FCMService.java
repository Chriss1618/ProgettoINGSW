package com.ratatouille.Models.API.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;

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

        id_utente = (Integer) new LocalStorage(getApplicationContext()).getData("ID_Utente", "Integer");
        this.token = token;
        UpdateTokenDB();

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
                    return true;
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
        String title = "Nuovo Ordine";
        String messageBody = data.get("key2");
        sendNotification(title, messageBody);
    }

    private void sendNotification(String title, String messageBody) {
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if Android version is greater than or equal to Oreo (API 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "your_channel_id";
            String channelName = "Your Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "your_channel_id")
                .setSmallIcon(R.mipmap.ic_ratatouille)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notificationManager.notify(0, notification); // You can use a unique notification ID here

        // You can also customize the notification further, add actions, etc., based on your needs.
    }

}
