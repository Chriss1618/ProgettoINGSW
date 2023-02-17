package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Supervisore;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Supervisore extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_Supervisore";

    private final static int TAB_SUPERVISORE_INDEX_INVENTARIO   = 0;
    private final static int TAB_SUPERVISORE_INDEX_MENU         = 1;
    private final static int TAB_SUPERVISORE_INDEX_ACCOUNT      = 2;

    Controller_Supervisore controller_supervisor;

    //LAYOUT

    //FUNCTIONS

    //OTHER
    AnimatedBottomBar Bottom_Bar_Supervisore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__supervisore);

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
        Bottom_Bar_Supervisore = findViewById(R.id.bottom_bar_sup);
    }
    private void SetDataOnLayout() {
        constructController();
        controller_supervisor.showMain();
    }
    private void SetActionsOfLayout() {
        setBottomBar();
    }

    private void setBottomBar(){
        Bottom_Bar_Supervisore.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
            case TAB_SUPERVISORE_INDEX_INVENTARIO:
                controller_supervisor.showINVENTORY();
                break;
            case TAB_SUPERVISORE_INDEX_MENU:
                controller_supervisor.showMENU();
                break;
            case TAB_SUPERVISORE_INDEX_ACCOUNT:
                controller_supervisor.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_supervisor = new Controller_Supervisore(
                this,
                findViewById(R.id.fragment_container_view_supervisore),
                getSupportFragmentManager()
        );
    }
}