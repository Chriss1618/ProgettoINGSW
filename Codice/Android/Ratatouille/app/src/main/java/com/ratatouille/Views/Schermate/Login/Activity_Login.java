package com.ratatouille.Views.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View            Fragment_View;
    ImageView       Image_View_Logo;
    ImageView       Image_View_Logo_1;
    ImageView       Image_View_Logo_2;

    //FUNCTIONAL
    private Manager ManagerLogin;

    //OTHER...
    @Override
    public void onBackPressed() {
        if ( getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            ManagerLogin.closeView();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrepareData();

        PrepareLayout();

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
        Image_View_Logo     = findViewById(R.id.image_view_logo);
        Image_View_Logo_1   = findViewById(R.id.image_view_logo_1);
        Image_View_Logo_2   = findViewById(R.id.image_view_logo_2);
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        ManagerLogin.showMain();
    }

    @Override
    public void StartAnimations() {
        RotateLogo();
    }

    @Override
    public void EndAnimations() {
        switch (ManagerLogin.onMain){
            case ControlMapper.INDEX_LOGIN_WELCOME :
                break;
            case ControlMapper.INDEX_LOGIN_LOGIN:
                break;
            case ControlMapper.INDEX_LOGIN_CONFIRM:
                break;
        }
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
    public void MoveLogoFrom0to1(){
        Image_View_Logo.animate().rotation(180).setDuration(500).start();
        Image_View_Logo.startAnimation(Manager_Animation.getTranslateLogoUp());

        final Handler handler = new Handler();
        handler.postDelayed(()->{
            Image_View_Logo.setVisibility(View.GONE);
            Image_View_Logo_1.setVisibility(View.VISIBLE);
        },500);
    }

    public void MoveLogoFrom1to2(){
        Image_View_Logo_1.animate().rotation(180).setDuration(500).start();
        Image_View_Logo_1.startAnimation(Manager_Animation.getTranslateLogoDown());
        final Handler handler = new Handler();
        handler.postDelayed(()->{
            Image_View_Logo_1.setVisibility(View.GONE);
            Image_View_Logo_2.setVisibility(View.VISIBLE);
        },500);
    }

    public void RotateLogo(){
        Image_View_Logo.animate().rotation(360).setDuration(2000).start();
        Image_View_Logo.animate().alpha(0f).setDuration(0).start();
        Image_View_Logo.animate().alpha(1f).setDuration(500).start();
        Image_View_Logo.startAnimation(Manager_Animation.getTranslationINfromUp(700));
    }

    //ACTIONS





}