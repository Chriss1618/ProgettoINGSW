package com.ratatouille.Views.Schermate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        PrepareData();

        PrepareLayout();

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
        finish();
    }

    private boolean AuthenticateUser(){
        return (String) new LocalStorage(this).getData("Token","String") != null;
    }

    private void startApp(){
        Log.d(TAG, " -> User Logged in! <- ");
        closeLoading();
        Intent intent = new Intent(this, Activity_Amministratore.class);
        startActivity(intent);
        finish();
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
        rotation(420);
    }
    private void rotation(int speed){
//        runOnUiThread(() -> ImageView_Logo.animate().rotation(speed).setDuration(5000)
//                .withEndAction( () -> rotation((numGiri++ % 2 == 0) ? -420 : 420) ).start()
//        );
        runOnUiThread(() -> ImageView_Logo.animate()
                .rotationBy(speed) // Use rotationBy instead of setting absolute rotation value
                .setDuration(5000)
                .withEndAction(() -> {
                    // This will be executed when the animation ends
                    int nextSpeed = (numGiri++ % 2 == 0) ? -420 : 420;
                    rotation(nextSpeed);
                })
                .start()
        );

    }
}