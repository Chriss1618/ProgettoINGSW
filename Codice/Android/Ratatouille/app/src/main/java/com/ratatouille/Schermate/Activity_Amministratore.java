package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.R;

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
        switch (indexTab){
            case TAB_AMMINISTRATORE_INDEX_STATS:
                    controller_amministrator.showSTATS();
                break;
            case TAB_AMMINISTRATORE_INDEX_STAFF:
                    controller_amministrator.showSTAFF();
                break;
            case TAB_AMMINISTRATORE_INDEX_MENU:
                    controller_amministrator.showMENU();
                break;
            case TAB_AMMINISTRATORE_INDEX_ACCOUNT:
                    controller_amministrator.showACCOUNT();
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

}