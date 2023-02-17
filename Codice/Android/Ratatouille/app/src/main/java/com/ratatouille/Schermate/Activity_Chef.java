package com.ratatouille.Schermate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ratatouille.Controllers.Controller_Chef;
import com.ratatouille.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Activity_Chef extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_Chef";

    private final static int TAB_CHEF_INDEX_INVENTARIO   = 0;
    private final static int TAB_CHEF_INDEX_ORDERS       = 1;
    private final static int TAB_CHEF_INDEX_MENU         = 2;
    private final static int TAB_CHEF_INDEX_ACCOUNT      = 3;

    Controller_Chef controller_chef;
    //LAYOUT

    //FUNCTIONS

    //OTHER
    AnimatedBottomBar Bottom_Bar_Chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chef);
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
        Bottom_Bar_Chef = findViewById(R.id.bottom_bar_chef);
    }
    private void SetDataOnLayout() {
        constructController();
        controller_chef.showMain();
    }
    private void SetActionsOfLayout() {
        setBottomBar();
    }


    private void setBottomBar(){
        Bottom_Bar_Chef.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
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
            case TAB_CHEF_INDEX_INVENTARIO:
                controller_chef.showINVENTORY();
                break;
            case TAB_CHEF_INDEX_ORDERS:
                controller_chef.showORDINI();
                break;
            case TAB_CHEF_INDEX_MENU:
                controller_chef.showMENU();
                break;
            case TAB_CHEF_INDEX_ACCOUNT:
                controller_chef.showACCOUNT();
                break;
        }
    }

    //FUNCTIONAL
    private void constructController() {
        controller_chef = new Controller_Chef(
                this,
                findViewById(R.id.fragment_container_view_chef),
                getSupportFragmentManager()
        );
    }
}