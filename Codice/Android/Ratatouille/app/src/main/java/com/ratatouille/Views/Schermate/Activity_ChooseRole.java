package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private MotionLayout motionLayout;
    private ImageView ImageView_Logo;
    private LinearLayout Background;

    //DATA
    private int numGiri = 0;

    //FUNCTIONAL

    //OTHER...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        PrepareLayout();
        PrepareData();
        StartAnimations();
    }

    //DATA
    @Override
    public void PrepareData() {
        new Thread(() ->{
            Try.run(() -> TimeUnit.SECONDS.sleep(2));
            if(AuthenticateUser()) startApp();
            else startLogin();
        }).start();
    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        motionLayout            = findViewById(R.id.loading_activity);
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
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));
        Intent intent = new Intent(this, Activity_Login.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private boolean AuthenticateUser(){
        return new LocalStorage(this).getData("Token","String") != null;
    }

    private void startApp(){
        Log.d(TAG, " -> User Logged in! <- ");
        closeLoading();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));
        Intent intent = new Intent(this, Activity_Amministratore.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }
    @Override
    public void EndAnimations() {

    }

    private void closeLoading(){
        motionLayout.setTransition(R.id.start_app_transition);
        motionLayout.transitionToEnd();
    }
}