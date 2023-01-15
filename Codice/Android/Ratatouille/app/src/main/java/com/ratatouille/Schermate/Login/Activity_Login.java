package com.ratatouille.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ratatouille.Managers.Manager_Animation;
import com.ratatouille.Managers.Manager_LoginFragments;
import com.ratatouille.R;

import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View            Fragment_View;
    ImageView       Image_View_Logo;
    ImageView       Image_View_Logo_1;
    ImageView       Image_View_Logo_2;

    //FUNCTIONAL
    private Manager_LoginFragments Manager_Login;

    //OTHER...
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrepareData();

        PrepareLayout();

        Log.d(TAG, "onCreate: Hai creato la schermata");
    }


    //LAYOUT
    private void PrepareData() {

    }
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Fragment_View       = findViewById(R.id.fragment_container_view_login);
        Image_View_Logo     = findViewById(R.id.image_view_logo);
        Image_View_Logo_1   = findViewById(R.id.image_view_logo_1);
        Image_View_Logo_2   = findViewById(R.id.image_view_logo_2);
    }
    private void SetDataOnLayout() {
        setManagerFragment();
    }
    private void SetActionsOfLayout() {

    }


    //LAYOUT FUNCTION
    private void setManagerFragment(){
        ArrayList<View> Views = new ArrayList<>();
        Views.add(Fragment_View);
        Manager_Login = new Manager_LoginFragments(this,
                getSupportFragmentManager(),
                Views);
        Manager_Login.showPage(0);
        RotateLogo();
    }
    public void setViewPager(int index){
        Manager_Login.showPage(index);
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
        Image_View_Logo.animate().alpha(0f).setDuration(0).start();
        Image_View_Logo.animate().rotation(180).setDuration(1000).alpha(1f).start();
    }

    //ACTIONS
    private void onButtonLogin(){

    }




}