package com.ratatouille.Managers;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Inventario.Fragment_EditProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_InfoProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_ListInventary;
import com.ratatouille.Schermate.Inventario.Fragment_NewProductInventory;
import com.ratatouille.Schermate.Ordini.Fragment_HystoryOrders;
import com.ratatouille.Schermate.Ordini.Fragment_ListOrders;
import com.ratatouille.Schermate.Ordini.Fragment_TableOrders;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_Ordini {
    //SYSTEM
    private static final String TAG = "Manager_Ordini";

    public static final int INDEX_ORDINI_LIST_ORDERS    = 0;
    public static final int INDEX_ORDINI_TABLE_ORDERS   = 1;
    public static final int INDEX_ORDINI_HISTORY_ORDERS = 2;

    public static final String TAG_ORDINI_LIST_ORDERS       = "listOrders";
    public static final String TAG_ORDINI_TABLE_ORDERS      = "tableOrders";
    public static final String TAG_ORDINI_HISTORY_ORDERS    = "historyOrders";

    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    public Manager_Ordini(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_ListOrders());
        Fragments.add(new Fragment_TableOrders());
        Fragments.add(new Fragment_HystoryOrders());

        onMain = INDEX_ORDINI_LIST_ORDERS;
    }

    //ShowPages
    public void loadFragmentAsMain(String Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), Tag)
                .setReorderingAllowed(true)
                .commit();
    }
    public void loadFragmentAsNormal(String Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain),Tag)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void showMain(){
        showListOrders();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;

        switch (index){
            case INDEX_ORDINI_LIST_ORDERS:
                showListOrders();
                break;
            case INDEX_ORDINI_TABLE_ORDERS:
                showTableOrders();
                break;
            case INDEX_ORDINI_HISTORY_ORDERS:
                showHistoryOrders();
                break;
        }
    }
    //Pages
    public void showListOrders      (){
        loadFragmentAsMain(TAG_ORDINI_LIST_ORDERS);
    }
    public void showTableOrders     (){

    }
    public void showHistoryOrders   (){
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        from = onMain;
        switch (onMain){
            case INDEX_ORDINI_LIST_ORDERS:
                Fragment_ListOrders listOrders = (Fragment_ListOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_LIST_ORDERS);
                Objects.requireNonNull(listOrders).EndAnimations();
                break;
            case INDEX_ORDINI_TABLE_ORDERS:
                onMain = INDEX_ORDINI_LIST_ORDERS;
                Fragment_TableOrders tableOrders = (Fragment_TableOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_TABLE_ORDERS);
                Objects.requireNonNull(tableOrders).EndAnimations();
                break;
            case INDEX_ORDINI_HISTORY_ORDERS:
                onMain = INDEX_ORDINI_LIST_ORDERS;
                Fragment_HystoryOrders hystoryOrders = (Fragment_HystoryOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_HISTORY_ORDERS);
                Objects.requireNonNull(hystoryOrders).EndAnimations();
                break;
        }
    }

}
