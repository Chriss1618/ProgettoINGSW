package com.ratatouille.Views.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.Controller;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Listeners.BottomBarListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Amministratore extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_Amministratore";

    //LAYOUT
    AnimatedBottomBar Bottom_Bar_Amministratore;

    //FUNCTIONAL
    private String typeUser;
    private final BottomBarListener     bottomBarListener = new BottomBarListener();
    private Controller controller;
    private int typeController;

    //OTHER

    @Override
    public void finish() {
        controller = null;
        Log.d(TAG, "finish: Closing Activity Amministratore");
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if ( getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            controller.closeView();
        }else{
            super.onBackPressed();
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
        typeUser =  (String) new LocalStorage(this).getData("TypeUser","String");
    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {
        switch (typeUser){
            case ControlMapper.INDEX_TYPE_USER_AMMINISTRATORE:
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_amm);
                typeController = ControlMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE;
                break;
            case ControlMapper.INDEX_TYPE_USER_SUPERVISORE:
                typeController = ControlMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_sup);
                break;
            case ControlMapper.INDEX_TYPE_USER_CHEF:
                typeController = ControlMapper.INDEX_TYPE_CONTROLLER_CHEF;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_chef);
                break;
            case ControlMapper.INDEX_TYPE_USER_CAMERIERE:
                typeController = ControlMapper.INDEX_TYPE_CONTROLLER_CAMERIERE;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_cam);
                break;
        }
        Bottom_Bar_Amministratore.setVisibility(View.VISIBLE);
    }
    @Override
    public void SetActionsOfLayout() {
        setBottomBar();
        setListener();
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        controller.showMain();
    }

    private void setListener(){
        bottomBarListener.setHideBottomBarListener(this::hideBottomBar);
        bottomBarListener.setShowBottomBarListener(this::showBottomBar);
    }

    //BottomBar
    private void setBottomBar(){
        Bottom_Bar_Amministratore.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int from, @Nullable AnimatedBottomBar.Tab tab, int indexTo, @NonNull AnimatedBottomBar.Tab tab1) {
                Log.d(TAG, "onTabSelected: from->"+from+" to->"+indexTo);
                tabSelected( indexTo );
            }

            @Override
            public void onTabReselected(int indexTo, @NonNull AnimatedBottomBar.Tab tab) {
                Log.d(TAG, "onTabReselected: from->"+indexTo+" to->"+indexTo);
                tabSelected( indexTo );
            }
        });

    }

     private void tabSelected(int indexTab){
        disableBottomBar();
        controller.changeOnMain(indexTab);
        new Handler().postDelayed(this::enableBottomBar,600);
    }

    //FUNCTIONAL
    private void constructController() {
        controller = new Controller(this,
                findViewById(R.id.fragment_container_view_amministratore),
                getSupportFragmentManager(),
                bottomBarListener, typeController);
    }


    private void disableBottomBar(){
        for(int i = 0; i < controller.getNumberManagers(); i++)
            Bottom_Bar_Amministratore.setTabEnabledAt(i,false);
    }

    private void enableBottomBar(){
        for(int i = 0; i < controller.getNumberManagers(); i++)
            Bottom_Bar_Amministratore.setTabEnabledAt(i,true);
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


    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }
    @Override
    public void EndAnimations() {

    }

}