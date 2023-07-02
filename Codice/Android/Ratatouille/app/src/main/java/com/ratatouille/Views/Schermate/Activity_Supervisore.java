package com.ratatouille.Views.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Supervisore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Supervisore extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_Supervisore";

    private final static int TAB_SUPERVISORE_INDEX_INVENTARIO   = 0;
    private final static int TAB_SUPERVISORE_INDEX_MENU         = 1;
    private final static int TAB_SUPERVISORE_INDEX_ACCOUNT      = 2;

    //LAYOUT
    AnimatedBottomBar   Bottom_Bar_Supervisore;

    //FUNCTIONS
    BottomBarListener       bottomBarListener;
    Controller_Supervisore  controller_supervisor;

    //OTHER...

    @Override
    public void onBackPressed() {
        int numberOfBackStack = getSupportFragmentManager().getBackStackEntryCount();
        callBackStackAfterAnimation(numberOfBackStack);
    }

    private void callBackStackAfterAnimation(int numberOfBackStack){
        if (numberOfBackStack > 0) {
            controller_supervisor.callEndAnimationOfFragment();
            final Handler handler = new Handler();
            handler.postDelayed(()-> getSupportFragmentManager().popBackStack(),300);//dopo 300 millisecondi
        }else{
            super.onBackPressed();
        }
    }
    private void clearBackStackPackage(){
        for(int j  = getSupportFragmentManager().getBackStackEntryCount() ; j >0; j-- ){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__supervisore);

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
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        Bottom_Bar_Supervisore = findViewById(R.id.bottom_bar_sup);
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        controller_supervisor.showMain();
    }
    @Override
    public void SetActionsOfLayout() {
        setListener();
        setBottomBar();
    }

    private void setListener(){
        bottomBarListener.setHideBottomBarListener(this::hideBottomBar);
        bottomBarListener.setShowBottomBarListener(this::showBottomBar);
    }

    //BottomBar
    private void setBottomBar(){
        Bottom_Bar_Supervisore.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
        controller_supervisor.callEndAnimationOfFragment();
        controller_supervisor.resetMainPackage();

        final Handler handler = new Handler();
        handler.postDelayed(()-> changeTAB(indexTab) ,300);
    }
    private void changeTAB(int indexTab){
        clearBackStackPackage();
        switch (indexTab){
            case TAB_SUPERVISORE_INDEX_INVENTARIO:  controller_supervisor.showINVENTORY();
                break;
            case TAB_SUPERVISORE_INDEX_MENU:        controller_supervisor.showMENU();
                break;
            case TAB_SUPERVISORE_INDEX_ACCOUNT:     controller_supervisor.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_supervisor = new Controller_Supervisore(
                this,
                findViewById(R.id.fragment_container_view_supervisore),
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
        Bottom_Bar_Supervisore.startAnimation(Manager_Animation.getTranslationOUTtoDownS(500));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Supervisore.setVisibility(android.view.View.GONE),500);
    }
    public void showBottomBar(){
        Bottom_Bar_Supervisore.startAnimation(Manager_Animation.getTranslationINfromDown(300));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Supervisore.setVisibility(android.view.View.VISIBLE),300);
    }

}