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

    private Uri.Builder dataToSend ;
    private String url;
    private JSONArray jsonArray;

    public JSONArray getDataFromBackEnd(Uri.Builder dataToSend, String url){
        this.dataToSend = dataToSend;
        this.url = url;
        this.jsonArray = null;
        Thread thread = new Thread(this::getDataFromServer);

        thread.start();

        while(thread.isAlive()) {}

        return jsonArray;


    }

    private void getDataFromServer(){
        try {
            Log.d(TAG, "getDataFromServer: Url : "+ url);
            URL urlGetAllCategories = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlGetAllCategories.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //INVIO Risposte
            String data = dataToSend.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();

            //GET RESULT
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder builder2= new StringBuilder();

            while((line = bufferedReader.readLine()) != null){
                builder2.append(line);
            }

            JSONObject json_data = new JSONObject(builder2.toString());

            Log.d(TAG, "getData: messaggio BackEnd->"+json_data );
            if(json_data.getString("status").equals("0")){
                return;
            }

            //leggi Json Se hai un successo ritorni messageid
            Log.d(TAG, "sendData: messageFromAndroid:"+json_data.getString("msg"));


            jsonArray = new JSONArray(json_data.getString("msg"));

            //CHIUSURA CONNESSIONE
            bufferedReader.close();
            os.flush();
            os.close();
            conn.disconnect();

        } catch (Exception e) {
            Log.d(TAG, "getDataFromServer: Errore di Comunicazione con il BeckEnd");
            e.printStackTrace();
        }
    }

}
