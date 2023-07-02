package com.ratatouille.Managers;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Views.Schermate.Ordini.Fragment_HystoryOrders;
import com.ratatouille.Views.Schermate.Ordini.Fragment_ListOrders;
import com.ratatouille.Views.Schermate.Ordini.Fragment_TableOrders;

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

        Fragments.add(new Fragment_ListOrders(this));
        Fragments.add(new Fragment_TableOrders(this));
        Fragments.add(new Fragment_HystoryOrders(this));

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
                showTableOrders(msg);
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
    public void showTableOrders     (String table){
        Bundle arguments = new Bundle();
        arguments.putString("table", table);
        Fragments.get(INDEX_ORDINI_TABLE_ORDERS).setArguments(arguments);

        loadFragmentAsNormal(TAG_ORDINI_TABLE_ORDERS);
    }
    public void showHistoryOrders   (){
        loadFragmentAsNormal(TAG_ORDINI_HISTORY_ORDERS);
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        int temp = from;
        from = onMain;
        switch (onMain){
            case INDEX_ORDINI_LIST_ORDERS:
                Log.d(TAG, "callEndAnimationOfFragment: listOrder");
                Fragment_ListOrders listOrders = (Fragment_ListOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_LIST_ORDERS);
                Objects.requireNonNull(listOrders).EndAnimations();
                break;
            case INDEX_ORDINI_TABLE_ORDERS:
                onMain = INDEX_ORDINI_LIST_ORDERS;
                Log.d(TAG, "callEndAnimationOfFragment: tableOrders");
                Fragment_TableOrders tableOrders = (Fragment_TableOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_TABLE_ORDERS);
                Objects.requireNonNull(tableOrders).EndAnimations();
                break;
            case INDEX_ORDINI_HISTORY_ORDERS:
                onMain = temp;
                Log.d(TAG, "callEndAnimationOfFragment: historyOrder");
                Fragment_HystoryOrders historyOrders = (Fragment_HystoryOrders)fragmentManager.findFragmentByTag(TAG_ORDINI_HISTORY_ORDERS);
                Objects.requireNonNull(historyOrders).EndAnimations();
                break;
        }
    }

}
