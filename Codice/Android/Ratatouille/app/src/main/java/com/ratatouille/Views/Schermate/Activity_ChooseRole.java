package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Activity_ChooseRole extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    Button  Button_Amministratore;
    Button  Button_Supervisore;
    Button  Button_Chef;
    Button  Button_Cameriere;

    ImageView ImageView_Logo;
    LinearLayout Background;
    //DATA
    int numGiri = 0;
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

        new Thread(this::rotateAnimationLogo).start();

        new Thread(() ->{
            Try.run(() -> TimeUnit.SECONDS.sleep(3));
            runOnUiThread(() -> {
                ImageView_Logo.animate().alpha(0f).setDuration(300).start();
                Background.setVisibility(View.VISIBLE);
                Background.startAnimation(Manager_Animation.getCircleReveal());
            });
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(300));
            //startLogin();
            startApp(ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE);
        }).start();

    }
    private void LinkLayout() {
        Button_Amministratore   = findViewById(R.id.button_amministratore);
        Button_Supervisore      = findViewById(R.id.button_supervisore);
        Button_Chef             = findViewById(R.id.button_chef);
        Button_Cameriere        = findViewById(R.id.button_cameriere);

        ImageView_Logo          = findViewById(R.id.image_view_logo);
        Background              = findViewById(R.id.background);
    }
    private void SetDataOnLayout() {

    }

    private void SetActionsOfLayout() {
        Button_Amministratore   .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE));
        Button_Supervisore      .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE));
        Button_Chef             .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_CHEF));
//      Button_Cameriere        .setOnClickListener(view -> startApp(ControlMapper.INDEX_TYPE_CONTROLLER_CAMERIERE));
        Button_Cameriere        .setOnClickListener(view -> startLogin());
    }
    private void startLogin(){
        Intent intent = new Intent(this, Activity_Login.class);
        startActivity(intent);
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

    private boolean AuthenticateUser(){
        String TokenUser = (String) new LocalStorage(this).getData("TokenUser","String");
        if( TokenUser == null) return false;
        String url = EndPointer.StandardPath + EndPointer.
        return true;
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

    private void rotateAnimationLogo()  {
        rotation(820);
        while(true) rotation( numGiri++ % 2 == 0 ? -420 : 420 );
    }

    private void rotation(int speed){
        runOnUiThread(() -> ImageView_Logo.animate().rotation(speed).setDuration(10000).start());
        Try.run(() -> Thread.sleep(5000) );
    }
}