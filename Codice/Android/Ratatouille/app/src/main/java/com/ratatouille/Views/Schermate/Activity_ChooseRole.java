package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.EndPoints.EndPointer;
import com.ratatouille.Models.ServerCommunication;
import com.ratatouille.R;

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

public class Activity_ChooseRole extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    Button  Button_Amministratore;
    Button  Button_Supervisore;
    Button  Button_Chef;
    Button  Button_Cameriere;

    //FUNCTIONAL

    //OTHER...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        PrepareData();

        PrepareLayout();

    }

    //LAYOUT
    private void PrepareData() {
        sendData();
        getData();
        setToken();
    }
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Button_Amministratore   = findViewById(R.id.button_amministratore);
        Button_Supervisore      = findViewById(R.id.button_supervisore);
        Button_Chef             = findViewById(R.id.button_chef);
        Button_Cameriere        = findViewById(R.id.button_cameriere);
    }
    private void SetDataOnLayout() {

    }

    private void SetActionsOfLayout() {
        Button_Amministratore   .setOnClickListener(view -> startAmministratore());
        Button_Supervisore      .setOnClickListener(view -> startSupervisore());
        Button_Chef             .setOnClickListener(view -> startChef());
        Button_Cameriere        .setOnClickListener(view -> startCameriere());
    }

    private void setToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (! task.isSuccessful() ) {
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }

            String token = task.getResult();
            Log.d(TAG, "FCM registration token: " + token);

            String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + "/RealTimeMessaging" + "/sendOrder.php";
            Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("token", token);
            new ServerCommunication().getData(dataToSend,url);
        });


    }
    //CONNECTION
    private void sendData(){
//        Thread thread = new Thread(this::ComunicateBackEnd);
//
//        thread.start();



    }

    private void ComunicateBackEnd(){
        try {
            URL urlGetAllProducts = new URL("http://s956013630.sito-web-online.it/App/test.php");
            HttpURLConnection conn = (HttpURLConnection) urlGetAllProducts.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //INVIO Risposte
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("nome", "porco")
                    .appendQueryParameter("cognome", "dio");

            String data = builder.build().getEncodedQuery();
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

            //leggi Json Se hai un successo ritorni messageid;
//                if (json_data.getInt("status")>0) {
            Log.d(TAG, "getData: messaggio BackEnd->"+json_data );
            Log.d(TAG, "sendData: messageFromAndroid:"+json_data.getString("messageFromAndroid"));
//                    JSONArray document= new JSONArray(json_data.getString("messageFromAndroid"));
//
//                    for(int i = 0 ; i<document.length(); i++){
//                        JSONObject singleDocument = new JSONObject(document.getString(i));
//                        try {
//                            Log.d(TAG, "getDocuments: data formatted->"+dateUpdate);
//                            newFiles.add(new FileDocument(
//                                    singleDocument.getString("document_id"),
//                                    user_id,
//                                    singleDocument.getString("plaza_id"),
//                                    singleDocument.getString("name"),
//                                    singleDocument.getString("plaza"),
//                                    singleDocument.getString("filename"),
//                                    singleDocument.getString("description"),
//                                    dateUpdate
//                            ));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }

//                }

            //CHIUSURA CONNESSIONE
            bufferedReader.close();
            os.flush();
            os.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getData(){

    }

    //ANIMATIONS
    private void startAmministratore(){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE+"");
        startActivity(intent);
    }
    private void startSupervisore(){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", ControlMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE+"");
        startActivity(intent);
    }
    private void startChef(){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", ControlMapper.INDEX_TYPE_CONTROLLER_CHEF+"");
        startActivity(intent);
    }
    private void startCameriere(){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", ControlMapper.INDEX_TYPE_CONTROLLER_CAMERIERE+"");
        startActivity(intent);
    }

}