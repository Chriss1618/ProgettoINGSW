package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Amministratore extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_Amministratore";

    private final static int TAB_AMMINISTRATORE_INDEX_STATS     = 0;
    private final static int TAB_AMMINISTRATORE_INDEX_STAFF     = 1;
    private final static int TAB_AMMINISTRATORE_INDEX_MENU      = 2;
    private final static int TAB_AMMINISTRATORE_INDEX_ACCOUNT   = 3;

    Controller_Amministratore controller_amministrator;

    //LAYOUT

    //FUNCTIONS

    //OTHER
    AnimatedBottomBar Bottom_Bar_Amministratore;

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: "+getSupportFragmentManager().getBackStackEntryCount());
        final Handler handler = new Handler();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if(getSupportFragmentManager().getBackStackEntryCount() == 1){
                FragmentManager fm = getSupportFragmentManager();
                Fragment_ListProducts fragment = (Fragment_ListProducts)fm.findFragmentById(R.id.fragment_container_view_amministratore);
                fragment.EndAnimatins();
            }
            handler.postDelayed(()->{


                getSupportFragmentManager().popBackStack();
            },300);
        }else{
            handler.postDelayed(super::onBackPressed,300);

        }



    }

    private void clearStackFragments(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__amministratore);

        PrepareData();
        PrepareLayout();

    }


    private void PrepareData() {

    }

    //LAYOUT
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Bottom_Bar_Amministratore = findViewById(R.id.bottom_bar_amm);
    }
    private void SetDataOnLayout() {
        constructController();
        controller_amministrator.showMain();
    }
    private void SetActionsOfLayout() {
        setBottomBar();
    }

    private void setBottomBar(){
        Bottom_Bar_Amministratore.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int from, @Nullable AnimatedBottomBar.Tab tab, int to, @NonNull AnimatedBottomBar.Tab tab1) {
                Log.d(TAG, "onTabSelected: from->"+from);
                Log.d(TAG, "onTabSelected: to->"+to);

                setTAB(to);
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
                Log.d(TAG, "onTabReselected: i->"+i);
                setTAB(i);
            }
        });


    }

    private void setTAB(int indexTab){
        clearStackFragments();
        switch (indexTab){
            case TAB_AMMINISTRATORE_INDEX_STATS: controller_amministrator.showSTATS();
                break;
            case TAB_AMMINISTRATORE_INDEX_STAFF: controller_amministrator.showSTAFF();
                break;
            case TAB_AMMINISTRATORE_INDEX_MENU: controller_amministrator.showMENU();
                break;
            case TAB_AMMINISTRATORE_INDEX_ACCOUNT: controller_amministrator.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_amministrator = new Controller_Amministratore(
                this,
                findViewById(R.id.fragment_container_view_amministratore),
                getSupportFragmentManager()
        );

    }

    public void changeFragmentOnAmministrator(int type_manager,int index_fragment,String msg){
        controller_amministrator.changeFragment(type_manager,index_fragment,msg);
    }

}