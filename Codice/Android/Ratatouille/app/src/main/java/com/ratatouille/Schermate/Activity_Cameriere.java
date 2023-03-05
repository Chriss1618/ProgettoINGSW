package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ratatouille.Controllers.Controller_Cameriere;
import com.ratatouille.Controllers.Controller_Chef;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Cameriere extends AppCompatActivity implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Activity_Cameriere";

    private final static int TAB_CAMERIERE_ORDINI   = 0;
    private final static int TAB_CAMERIERE_ACCOUNT = 1;

    //LAYOUT
    AnimatedBottomBar Bottom_Bar_cam;

    //FUNCTIONS
    Controller_Cameriere    controller_cameriere;
    BottomBarListener       bottomBarListener;

    //OTHER
    @Override
    public void onBackPressed() {
        int numberOfBackStack = getSupportFragmentManager().getBackStackEntryCount();

        controller_cameriere.callEndAnimationOfFragment();
        callBackStackAfterAnimation(numberOfBackStack); //dopo 300 millisecondi
    }

    private void callBackStackAfterAnimation(int numberOfBackStack){
        final Handler handler = new Handler();
        handler.postDelayed(()-> {
            if (numberOfBackStack > 0) getSupportFragmentManager().popBackStack();
            else super.onBackPressed();
        },300);
    }
    private void clearBackStackPackage(){
        for(int j  = getSupportFragmentManager().getBackStackEntryCount() ; j >0; j-- ){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cameriere);

        PrepareData();
        PrepareLayout();
    }

    //DATA
    @Override
    public void PrepareData() {

    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        bottomBarListener = new BottomBarListener();

        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {
        Bottom_Bar_cam = findViewById(R.id.bottom_bar_cam);
    }
    @Override
    public void SetActionsOfLayout() {
        setBottomBar();
        setListener();
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        controller_cameriere.showMain();
    }

    private void setListener(){
        bottomBarListener.hideBottomBarListener(this::hideBottomBar);
        bottomBarListener.showBottomBarLinstener(this::showBottomBar);
    }

    //BottomBar
    private void setBottomBar(){
        Bottom_Bar_cam.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int from, @Nullable AnimatedBottomBar.Tab tab, int to, @NonNull AnimatedBottomBar.Tab tab1) {
                Log.d(TAG, "onTabSelected: from->"+from+" to->"+to);
                tabSelected(to);
            }

            @Override
            public void onTabReselected(int to, @NonNull AnimatedBottomBar.Tab tab) {
                Log.d(TAG, "onTabReselected: from->"+to+" to->"+to);
                tabSelected(to);
            }
        });

    }
    private void tabSelected(int indexTab){
        controller_cameriere.callEndAnimationOfFragment();
        controller_cameriere.resetMainPackage();

        final Handler handler = new Handler();
        handler.postDelayed(()-> changeTAB(indexTab) ,300);
    }
    private void changeTAB(int indexTab){
        clearBackStackPackage();
        switch (indexTab){
            case TAB_CAMERIERE_ORDINI: controller_cameriere.showORDINI_CAMERIERE();
                break;
            case TAB_CAMERIERE_ACCOUNT: controller_cameriere.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_cameriere = new Controller_Cameriere(
                this,
                findViewById(R.id.fragment_container_view_cameriere),
                getSupportFragmentManager(),
                bottomBarListener
        );
    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }
    @Override
    public void EndAnimations() {

    }

    public void hideBottomBar(){
        Bottom_Bar_cam.startAnimation(Manager_Animation.getTranslationOUTtoDownS(500));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_cam.setVisibility(View.GONE),500);
    }
    public void showBottomBar(){
        Bottom_Bar_cam.startAnimation(Manager_Animation.getTranslationINfromDown(300));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_cam.setVisibility(View.VISIBLE),300);
    }

}