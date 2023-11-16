package com.ratatouille.Views.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.Controller;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Listeners.BottomBarListener;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Amministratore extends AppCompatActivity implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Activity_Amministratore";

    //LAYOUT
    private AnimatedBottomBar   Bottom_Bar_Amministratore;
    private LinearLayout        LinearLayout_Fragment;
    //FUNCTIONAL
    private String typeUser;
    private final BottomBarListener     bottomBarListener = new BottomBarListener();
    private Controller                  controller;
    private int typeController;

    //OTHER

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: stack size = " + getSupportFragmentManager().getBackStackEntryCount() );
        if ( getSupportFragmentManager().getBackStackEntryCount() > 1 ) {
            controller.goBack();
        }else{
            super.onBackPressed();
            finish();
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
            case ControlMapper.TypeUserMapper.NAME_TYPE_USER_AMMINISTRATORE:
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_amm);
                typeController = ControlMapper.TypeUserMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE;
                break;
            case ControlMapper.TypeUserMapper.NAME_TYPE_USER_SUPERVISORE:
                typeController = ControlMapper.TypeUserMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_sup);
                break;
            case ControlMapper.TypeUserMapper.NAME_TYPE_USER_CHEF:
                typeController = ControlMapper.TypeUserMapper.INDEX_TYPE_CONTROLLER_CHEF;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_chef);
                break;
            case ControlMapper.TypeUserMapper.NAME_TYPE_USER_CAMERIERE:
                typeController = ControlMapper.TypeUserMapper.INDEX_TYPE_CONTROLLER_CAMERIERE;
                Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_cam);
                break;
        }
        Bottom_Bar_Amministratore.setVisibility(View.VISIBLE);

        LinearLayout_Fragment = findViewById(R.id.linear_layout_container_fragment);
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
    public void hideBottomBar(){
        runOnUiThread(() -> {
            Handler handler = new Handler();
            handler.postDelayed( () ->Bottom_Bar_Amministratore.setVisibility(View.GONE), 500);
            Bottom_Bar_Amministratore.startAnimation(Manager_Animation.getTranslationOUTtoDownS(500));

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, 0);
            LinearLayout_Fragment.setLayoutParams(layoutParams);
        });
    }
    public void showBottomBar(){
        runOnUiThread(() -> {
            Bottom_Bar_Amministratore.startAnimation(Manager_Animation.getTranslationINfromDownSlower(500));
            Bottom_Bar_Amministratore.setVisibility(View.VISIBLE);

            // Set the top margin for the LinearLayout
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) LinearLayout_Fragment.getLayoutParams();
            layoutParams.bottomMargin = 64 * 3;
            LinearLayout_Fragment.setLayoutParams(layoutParams);
        });
    }
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (px / metrics.densityDpi);
    }

    private void disableBottomBar(){
        for(int i = 0; i < controller.getNumberManagers(); i++)
            Bottom_Bar_Amministratore.setTabEnabledAt(i,false);
    }
    private void enableBottomBar(){
        for(int i = 0; i < controller.getNumberManagers(); i++)
            Bottom_Bar_Amministratore.setTabEnabledAt(i,true);
    }

    //FUNCTIONAL
    private void constructController() {
        controller = new Controller(
                this,
                findViewById(R.id.fragment_container_view_amministratore),
                getSupportFragmentManager(),
                bottomBarListener,
                typeController
        );
    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }
    @Override
    public void EndAnimations() {

    }

}