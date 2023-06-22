package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Chef;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Chef extends AppCompatActivity implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_Chef";

    private final static int TAB_CHEF_INDEX_INVENTARIO   = 0;
    private final static int TAB_CHEF_INDEX_ORDERS       = 1;
    private final static int TAB_CHEF_INDEX_MENU         = 2;
    private final static int TAB_CHEF_INDEX_ACCOUNT      = 3;

    //LAYOUT
    AnimatedBottomBar Bottom_Bar_Chef;

    //FUNCTIONS
    Controller_Chef     controller_chef;
    BottomBarListener   bottomBarListener;

    //OTHER

    @Override
    public void onBackPressed() {
        int numberOfBackStack = getSupportFragmentManager().getBackStackEntryCount();
        callBackStackAfterAnimation(numberOfBackStack);
    }
    private void callBackStackAfterAnimation(int numberOfBackStack){
        if (numberOfBackStack > 0) {
            controller_chef.callEndAnimationOfFragment();
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
        setContentView(R.layout.activity__chef);
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
        Bottom_Bar_Chef = findViewById(R.id.bottom_bar_chef);
    }
    @Override
    public void SetActionsOfLayout() {
        setBottomBar();
        setListener();
    }
    @Override
    public void SetDataOnLayout() {
        constructController();
        controller_chef.showMain();
    }

    private void setListener(){
        bottomBarListener.setHideBottomBarListener(this::hideBottomBarr);
        bottomBarListener.setShowBottomBarListener(this::showBottomBar);
    }

    //BottomBar
    private void setBottomBar(){
        Bottom_Bar_Chef.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
        controller_chef.callEndAnimationOfFragment();
        controller_chef.resetMainPackage();

        final Handler handler = new Handler();
        handler.postDelayed(()-> changeTAB(indexTab) ,300);
    }
    private void changeTAB(int indexTab){
        clearBackStackPackage();
        switch (indexTab){
            case TAB_CHEF_INDEX_INVENTARIO: controller_chef.showINVENTORY();
                break;
            case TAB_CHEF_INDEX_ORDERS:     controller_chef.showORDINI();
                break;
            case TAB_CHEF_INDEX_MENU:       controller_chef.showMENU();
                break;
            case TAB_CHEF_INDEX_ACCOUNT:    controller_chef.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_chef = new Controller_Chef(
                this,
                findViewById(R.id.fragment_container_view_chef),
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

    public void hideBottomBarr(){
        Bottom_Bar_Chef.startAnimation(Manager_Animation.getTranslationOUTtoDownS(500));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Chef.setVisibility(android.view.View.GONE),500);
    }
    public void showBottomBar(){
        Bottom_Bar_Chef.startAnimation(Manager_Animation.getTranslationINfromDown(300));
        final Handler handler = new Handler();
        handler.postDelayed(()-> Bottom_Bar_Chef.setVisibility(android.view.View.VISIBLE),300);
    }

}