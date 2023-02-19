package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Menu.Fragment_EditProduct;
import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListCategory;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Menu.Fragment_NewProduct;

import java.util.ArrayList;

public class Manager_MenuFragments {
    private static final String TAG = "Manager_MenuFragments";

    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;

    public Manager_MenuFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_ListCategory());
        Fragments.add(new Fragment_ListProducts());
        Fragments.add(new Fragment_InfoProduct());
        Fragments.add(new Fragment_NewProduct());
        Fragments.add(new Fragment_EditProduct());
        onMain = INDEX_MENU_LIST_CATEGORY;
    }

    public void showMain(){
        onMain = INDEX_MENU_LIST_CATEGORY;
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }

    public void showListProducts(String category){
        Bundle arguments = new Bundle();
        arguments.putString("category", category);

        Fragments.get(INDEX_MENU_LIST_PRODUCTS).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_LIST_PRODUCTS), null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
        onMain = INDEX_MENU_LIST_PRODUCTS;
        Log.d(TAG, "stackEntry: "+fragmentManager.getBackStackEntryCount());
    }

    public void showFragment(int index,String msg){
        switch (index){
            case INDEX_MENU_LIST_CATEGORY:
                break;
            case INDEX_MENU_LIST_PRODUCTS:
                showListProducts(msg);
                break;
            case INDEX_MENU_INFO_PRODUCT:
                break;
            case INDEX_MENU_NEW_PRODUCT:
                break;
            case INDEX_MENU_EDIT_PRODUCT:
                break;
        }
    }
}
