package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Cameriere;
import com.ratatouille.Controllers.Controller_Chef;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Cameriere extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_Cameriere";

    private final static int TAB_CAMERIERE_ORDINI   = 0;
    private final static int TAB_CAMERIERE_ACCOUNTT = 1;

    Controller_Cameriere controller_cameriere;
    //LAYOUT

    //FUNCTIONS

    //OTHER
    AnimatedBottomBar Bottom_Bar_cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cameriere);

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
        Bottom_Bar_cam = findViewById(R.id.bottom_bar_cam);
    }
    private void SetDataOnLayout() {
        constructController();
        controller_cameriere.showMain();
    }
    private void SetActionsOfLayout() {
        setBottomBar();
    }


    private void setBottomBar(){
        Bottom_Bar_cam.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
            case TAB_CAMERIERE_ORDINI:
                controller_cameriere.showORDINI_CAMERIERE();
                break;
            case TAB_CAMERIERE_ACCOUNTT:
                controller_cameriere.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_cameriere = new Controller_Cameriere(
                this,
                findViewById(R.id.fragment_container_view_cameriere),
                getSupportFragmentManager()
        );
    }
}