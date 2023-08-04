package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
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
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;
import maes.tech.intentanim.CustomIntent;

public class Activity_ChooseRole extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    ImageView ImageView_Logo;
    LinearLayout Background;
    //DATA
    int numGiri = 0;
    private Utente utente;
    String TokenUser;
    String EmailUser;
    int     IdRestaurantUser;
    //FUNCTIONAL

    //OTHER...

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        new LocalStorage(this).DeleteAllData();
        PrepareData();

        PrepareLayout();
    }

    //LAYOUT
    private void PrepareData() {
        setToken();
    }

    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();

        new Thread(this::rotateAnimationLogo).start();

        new Thread(() ->{
            Try.run(() -> TimeUnit.SECONDS.sleep(2));
            if(AuthenticateUser()) startApp(ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE);
            else startLogin();
        }).start();

    }

    private void LinkLayout() {

        ImageView_Logo          = findViewById(R.id.image_view_logo);
        Background              = findViewById(R.id.background);
    }
    private void SetDataOnLayout() {

    }
    private void SetActionsOfLayout() {

    }

    private void startLogin(){
        closeLoading();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(400));
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
        TokenUser = (String) new LocalStorage(this).getData("Token","String");
//        return true;
        if( TokenUser == null) return false;
        return true;
        //return AuthenticationUserWithServer();
    }

    private boolean AuthenticationUserWithServer(){
        getUserData();
        Uri.Builder dataToSend = new Uri.Builder()
                .appendQueryParameter("id_ristorante", IdRestaurantUser+"")
                .appendQueryParameter("EmailUser", EmailUser)
                .appendQueryParameter("TokenUser",TokenUser);
        String url = EndPointer.StandardPath + EndPointer.AUTHENTICATION;

        try {
            JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);

            if( BodyJSON != null ){
                String Response = BodyJSON.getString("MSG");

            }else{
                Log.d(TAG, "sendNewCategoryToServer: false");
                return false;
            }
        }catch (Exception e){
            Log.e(TAG, "getDataFromServer: ",e);
        }
        Log.d(TAG, "sendNewCategoryToServer: true");
        return true;
    }

    //CONNECTION
    private void getUserData(){
//        Thread thread = new Thread(this::ComunicateBackEnd);
//
//        thread.start();

        EmailUser = (String) new LocalStorage(this).getData("EmailUser","String");
        if(new LocalStorage(this).getData("IdRestaurantUser","Integer")!= null){
            IdRestaurantUser = (int) new LocalStorage(this).getData("IdRestaurantUser","Integer");
        }else{
            IdRestaurantUser = -1;
        }

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

    private void startApp(int typeUser){
        closeLoading();

        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", typeUser+"");
        startActivity(intent);
    }
    //ANIMATIONS

    private void closeLoading(){
        runOnUiThread(() -> {
            ImageView_Logo.animate().alpha(0f).setDuration(300).start();
            Background.setVisibility(View.VISIBLE);
            Background.startAnimation(Manager_Animation.getCircleReveal());
        });

    }
    private void rotateAnimationLogo()  {
        rotation(820);
        while(true) rotation( numGiri++ % 2 == 0 ? -420 : 420 );
    }

    private void rotation(int speed){
        runOnUiThread(() -> ImageView_Logo.animate().rotation(speed).setDuration(10000).start());
        Try.run(() -> Thread.sleep(5000) );
    }
}