package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class Activity_ChooseRole extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    ImageView ImageView_Logo;
    LinearLayout Background;

    //DATA
    int numGiri = 0;
    String TokenUser;

    //FUNCTIONAL

    //OTHER...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        //new LocalStorage(this).DeleteAllData();
        PrepareData();

        PrepareLayout();

        StartAnimations();

    }

    //LAYOUT
    @Override
    public void PrepareData() {
        new Thread(() ->{
            Try.run(() -> TimeUnit.SECONDS.sleep(2));
            if(AuthenticateUser()) startApp(ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE);
            else startLogin();
        }).start();
    }

    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {

        ImageView_Logo          = findViewById(R.id.image_view_logo);
        Background              = findViewById(R.id.background);
    }
    @Override
    public void SetDataOnLayout() {
    }

    @Override
    public void SetActionsOfLayout() {

    }

    //FUNCTIONAL
    private void startLogin(){
        closeLoading();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(400));
        Intent intent = new Intent(this, Activity_Login.class);
        startActivity(intent);
    }

    private boolean AuthenticateUser(){
        TokenUser = (String) new LocalStorage(this).getData("Token","String");
        return TokenUser != null;
    }

    private void startApp(int typeUser){
        closeLoading();

        Intent intent = new Intent(this, Activity_Amministratore.class);
        intent.putExtra("typeUser", typeUser+"");
        startActivity(intent);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        new Thread(this::rotateAnimationLogo).start();
    }

    @Override
    public void EndAnimations() {

    }

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