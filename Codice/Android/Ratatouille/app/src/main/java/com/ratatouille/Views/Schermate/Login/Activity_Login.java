package com.ratatouille.Views.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.R;

public class Activity_Login extends AppCompatActivity implements IViewLayout {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View            Fragment_View;
    MotionLayout    MotionLayout;
    ImageView       ImageView_Logo;

    //FUNCTIONAL
    private Manager ManagerLogin;

    //OTHER...

    @Override
    public void onBackPressed() {
        if(ManagerLogin.IndexOnMain == ControlMapper.IndexViewMapper.INDEX_LOGIN_LOGIN){
            MotionLayout.startAnimation(Manager_Animation.getFadeOut(500));
            startActivity(new Intent(this,Activity_Login.class));
            finish();
        }else if(ManagerLogin.IndexOnMain == ControlMapper.IndexViewMapper.INDEX_LOGIN_WELCOME){
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
        ImageView_Logo        = findViewById(R.id.image_view_logo);
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
                new SourceInfo(ControlMapper.IndexManagerMapper.INDEX_TYPE_MANAGER_LOGIN,ControlMapper.IndexManagerMapper.INDEX_TYPE_MANAGER_LOGIN),
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
        Log.d(TAG, "fromWelcomeToLogin: Inizia");
    }

    public void fromLoginToConfirm(){
        MotionLayout.setTransition(R.id.login_to_confirm_transition);
        MotionLayout.transitionToEnd();
        Log.d(TAG, "fromLoginToConfirm: ");
    }

    public void fromConfirmToApp(){
        MotionLayout.setTransition(R.id.toApp_transition);
        MotionLayout.transitionToEnd();
        Log.d(TAG, "fromConfirmToApp: ");

    }




    //ACTIONS





}