package com.ratatouille.Models;

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
    private JSONArray jsonArray = null;

    public JSONArray getData(Uri.Builder dataToSend, String url){
        this.dataToSend = dataToSend;
        this.url = url;

        Thread thread = new Thread(this::getDataFromServer);

        thread.start();
        while(thread.isAlive()) {}

        return jsonArray;
    }

    private void getDataFromServer(){
        try {
            conn = startConnectionWithServer();

            sendDataToServer();

            JSONObject json_data = getResponseFromServer() ;

            Log.d(TAG, "getDataFromServer: response Server ->"+json_data);
            //Salvataggio messaggio ricevuto
            jsonArray = new JSONArray( json_data.getString("msg") );
            if(json_data.getString("status").equals("0")){
                return ;
            }
            conn.disconnect();
        } catch (Exception e) {
            Log.d(TAG, "getDataFromServer: Errore di Comunicazione con il BeckEnd");
            e.printStackTrace();
        }
    }

    private HttpURLConnection startConnectionWithServer() throws Exception {
        URL urlGetAllCategories = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlGetAllCategories.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }

    private void sendDataToServer() throws Exception{
        String data = dataToSend.build().getEncodedQuery();
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
        return new JSONObject( builder.toString() );
    }
}
