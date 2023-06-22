package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ratatouille.Controllers.ControllerFactory;
import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.Controller;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Amministratore extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_Amministratore";

    //LAYOUT
    AnimatedBottomBar Bottom_Bar_Amministratore;

    //FUNCTIONAL
    private static final int            TYPE_CONTROLLER             = ControllerFactory.CONTROLLER_AMMINISTRATORE;
    private Controller                  ControllerAmministratore;
    private final BottomBarListener     bottomBarListener = new BottomBarListener();;

    //OTHER
    private boolean canChangeTab = true;

    @Override
    public void onBackPressed() {
        if ( getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            ControllerAmministratore.closeView();
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
        ControllerAmministratore.showMain();
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

        ControllerAmministratore.changeOnMain(indexTab);

        new Handler().postDelayed(this::enableBottomBar,600);
    }

    //FUNCTIONAL
    private void constructController() {
        ControllerAmministratore = ControllerFactory.createController(
            TYPE_CONTROLLER,
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

    private void disableBottomBar(){
        for(int i = 0; i < 4; i++){
            Bottom_Bar_Amministratore.setTabEnabledAt(i,false);
        }
    }

    private void enableBottomBar(){
        for(int i = 0; i < 4; i++){
            Bottom_Bar_Amministratore.setTabEnabledAt(i,true);
        }
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