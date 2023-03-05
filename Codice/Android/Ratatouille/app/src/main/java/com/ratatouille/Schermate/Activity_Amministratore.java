package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Amministratore extends AppCompatActivity implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Activity_Amministratore";

    private final static int TAB_AMMINISTRATORE_INDEX_STATS     = 0;
    private final static int TAB_AMMINISTRATORE_INDEX_STAFF     = 1;
    private final static int TAB_AMMINISTRATORE_INDEX_MENU      = 2;
    private final static int TAB_AMMINISTRATORE_INDEX_ACCOUNT   = 3;

    //LAYOUT
    AnimatedBottomBar Bottom_Bar_Amministratore;

    //FUNCTIONAL
    Controller_Amministratore   controller_administrator;
    BottomBarListener           bottomBarListener;

    //OTHER

    @Override
    public void onBackPressed() {
        int numberOfBackStack = getSupportFragmentManager().getBackStackEntryCount();

        controller_administrator.callEndAnimationOfFragment();
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
        setContentView(R.layout.activity__amministratore);

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
        Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_amm);
    }
    @Override
    public void SetActionsOfLayout() {
        setBottomBar();
        setListener();
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        controller_administrator.showMain();
    }

    private void setListener(){
        bottomBarListener.hideBottomBarListener(this::hideBottomBar);
        bottomBarListener.showBottomBarLinstener(this::showBottomBar);
    }

    //BottomBar
    private void setBottomBar(){
        Bottom_Bar_Amministratore.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
        controller_administrator.callEndAnimationOfFragment();
        controller_administrator.resetMainPackage();

        final Handler handler = new Handler();
        handler.postDelayed(()-> changeTAB(indexTab) ,300);
    }
    private void changeTAB(int indexTab){
        clearBackStackPackage();
        switch (indexTab){
            case TAB_AMMINISTRATORE_INDEX_STATS: controller_administrator.showSTATS();
                break;
            case TAB_AMMINISTRATORE_INDEX_STAFF: controller_administrator.showSTAFF();
                break;
            case TAB_AMMINISTRATORE_INDEX_MENU: controller_administrator.showMENU();
                break;
            case TAB_AMMINISTRATORE_INDEX_ACCOUNT: controller_administrator.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_administrator = new Controller_Amministratore(
                this,
                findViewById(R.id.fragment_container_view_amministratore),
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
        Bottom_Bar_Amministratore.startAnimation(Manager_Animation.getTranslationOUTtoDownS(500));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Amministratore.setVisibility(View.GONE),500);
    }
    public void showBottomBar(){
        Bottom_Bar_Amministratore.startAnimation(Manager_Animation.getTranslationINfromDown(300));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Amministratore.setVisibility(View.VISIBLE),300);
    }

}