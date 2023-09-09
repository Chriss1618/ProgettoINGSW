package com.ratatouille.Views.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Fragment.Fragment_Welcome;

import maes.tech.intentanim.CustomIntent;

public class Activity_Login extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View            Fragment_View;
    MotionLayout    MotionLayout;

    //FUNCTIONAL
    private Manager ManagerLogin;

    //OTHER...

    @Override
    public void onBackPressed() {
        if(ManagerLogin.IndexOnMain == ControlMapper.INDEX_LOGIN_LOGIN){
            MotionLayout.startAnimation(Manager_Animation.getFadeOut(500));
            startActivity(new Intent(this,Activity_Login.class));
            finish();
        }else if(ManagerLogin.IndexOnMain == ControlMapper.INDEX_LOGIN_WELCOME){
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrepareLayout();

        PrepareData();

        StartAnimations();

    }


    //LAYOUT
    @Override
    public void PrepareData() {

    }
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {
        Fragment_View       = findViewById(R.id.fragment_container_view_login);
        MotionLayout        = findViewById(R.id.login_activity);
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        ManagerLogin.showMain();
    }

    @Override
    public void StartAnimations() {


    }

    @Override
    public void EndAnimations() {

    }

    @Override
    public void SetActionsOfLayout() {

    }

    //LAYOUT FUNCTION
    private void constructController(){
        ManagerLogin = new Manager(
                new SourceInfo(ControlMapper.INDEX_TYPE_MANAGER_LOGIN,ControlMapper.INDEX_TYPE_MANAGER_LOGIN),
                this,
                Fragment_View,
                getSupportFragmentManager(),
                null
        );
    }

    //ANIMATIONS
    public void fromWelcomeToLogin(){
        MotionLayout.setTransition(R.id.welcome_to_login_transition);
        MotionLayout.transitionToEnd();
    }

    public void backLoginToWelcome(){
        MotionLayout.setTransition(R.id.login_to_welcome_transition);
        MotionLayout.transitionToEnd();
        Fragment_View       = findViewById(R.id.fragment_container_view_login);
        Fragment_View.setVisibility(View.VISIBLE);

    }

    private void fromLoginToConfirm(){

    }

    private void fromConfirmToApp(){

    }




    //ACTIONS





}