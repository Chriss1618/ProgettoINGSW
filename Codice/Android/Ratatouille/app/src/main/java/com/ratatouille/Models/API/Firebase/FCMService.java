package com.ratatouille.Models.API.Firebase;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(TAG, "onMessageReceived: Ricevuto Messaggio");
        Map<String, String> data = message.getData();
        Log.d(TAG, "onMessageReceived: get Title ->" + data.get("key1"));
        Log.d(TAG, "onMessageReceived: get Title ->" + data.get("key2"));
    }

}
