package com.ratatouille.Models.API.Rest;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerCommunication {
    private static final String TAG = "ServerCommunication";
    private HttpURLConnection conn;

    private Uri.Builder dataToSend ;
    private String url;
    private JSONObject jsonBODY = null;

    public JSONObject getData(Uri.Builder dataToSend, String url){
        this.dataToSend = dataToSend;
        this.url = url;

        startCommunication();

        return jsonBODY;
    }

    private void startCommunication(){
        try {

            conn = startConnectionWithServer();
            sendRequestToServer();
            JSONObject json_data = getResponseFromServer();
            conn.disconnect();

            Log.d(TAG, "getDataFromServer: response Server ->\n"+json_data.toString(4));
            Log.d(TAG, "getDataFromServer: Status -> "+json_data.getString("STATUS"));

            //Salvataggio messaggio ricevuto
            JSONArray jsonArray = new JSONArray(json_data.getString("BODY"));
            jsonBODY = new JSONObject(jsonArray.getString(0));
        } catch (Exception e) {
            Log.d(TAG, "getDataFromServer: Errore di Comunicazione con il BeckEnd");
            e.printStackTrace();
        }
    }

    private HttpURLConnection startConnectionWithServer() throws Exception {
        URL urlGetAllCategories = new URL(url);
        Log.d(TAG, "startConnectionWithServer: url-> "+url);
        HttpURLConnection conn = (HttpURLConnection) urlGetAllCategories.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }

    private void sendRequestToServer() throws Exception{
        String data = dataToSend.build().getEncodedQuery();

        Log.d(TAG, "sendRequestToServer: Sent -> "+ data);
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(data);
        writer.flush();
        os.flush();
        os.close();
    }

    private JSONObject getResponseFromServer() throws Exception{
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder builder= new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            builder.append(line);
        }
        bufferedReader.close();
        Log.d(TAG, "getResponseFromServer: recived ->"+ builder);
        return new JSONObject( builder.toString() );
    }
}
