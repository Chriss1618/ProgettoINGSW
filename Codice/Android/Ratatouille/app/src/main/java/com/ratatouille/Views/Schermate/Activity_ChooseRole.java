package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
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
        Button_Amministratore   .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE));
        Button_Supervisore      .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE));
        Button_Chef             .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_CHEF));
        Button_Cameriere        .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_CAMERIERE));
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
            Log.d(TAG, "getData: messaggio BackEnd->"+json_data );
            Log.d(TAG, "sendData: messageFromAndroid:"+json_data.getString("messageFromAndroid"));


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

    private void startApp(int typeUser){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", typeUser+"");
        startActivity(intent);
    }
    //ANIMATIONS

}