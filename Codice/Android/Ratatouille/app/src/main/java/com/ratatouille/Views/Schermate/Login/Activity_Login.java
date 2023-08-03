package com.ratatouille.Views.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

import maes.tech.intentanim.CustomIntent;

public class Activity_Login extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View            Fragment_View;

    //FUNCTIONAL
    private Manager ManagerLogin;

    //OTHER...

    @Override
    public void onBackPressed() {
        if(ManagerLogin.onMain != ControlMapper.INDEX_LOGIN_CONFIRM){
            if ( getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
                ManagerLogin.closeView();
            }else{
                super.onBackPressed();
            }
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






    //ACTIONS





}