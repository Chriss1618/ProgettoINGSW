package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class Activity_ChooseRole extends AppCompatActivity implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    private MotionLayout    motionLayout;

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
        motionLayout = findViewById(R.id.loading_activity);
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
        finish();
    }

    private boolean AuthenticateUser(){
        return new LocalStorage(this).getData("Token","String") != null;
    }

    private void startApp(){
        Log.d(TAG, " -> User Logged in! <- ");
        if(motionLayout != null ) closeLoading();

        Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));
        Intent intent = new Intent(this, Activity_HomePage.class);
        startActivity(intent);
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
        Log.d(TAG, "closeLoading: STARTING");
        motionLayout.setTransition(R.id.start_app_transition);
        motionLayout.transitionToEnd();
        Log.d(TAG, "closeLoading: FINISHED");
    }
}