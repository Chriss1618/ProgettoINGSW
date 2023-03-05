package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Schermate.Inventario.Fragment_EditProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_InfoProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_ListInventary;
import com.ratatouille.Schermate.Inventario.Fragment_NewProductInventory;
import com.ratatouille.Schermate.Menu.Fragment_EditProduct;
import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListCategory;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Menu.Fragment_NewProduct;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_InventoryFragments {
    //SYSTEM
    private static final String TAG = "Manager_InventarioFragm";

    public static final int INDEX_INVENTORY_LIST_INVENTORY          = 0;
    public static final int INDEX_INVENTORY_NEW_PRODUCT_INVENTORY   = 1;
    public static final int INDEX_INVENTORY_INFO_PRODUCT_INVENTORY  = 2;
    public static final int INDEX_INVENTORY_EDIT_PRODUCT_INVENTORY  = 3;

    public static final String TAG_INVENTORY_LIST           = "listInventory";
    public static final String TAG_INVENTORY_NEW_PRODUCT    = "newProductInventory";
    public static final String TAG_INVENTORY_INFO_PRODUCT   = "infoProductInventory";
    public static final String TAG_INVENTORY_EDIT_PRODUCT   = "editProductInventory";

    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private final BottomBarListener bottomBarListener;
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    public Manager_InventoryFragments(Context context, android.view.View view, FragmentManager fragmentManager,BottomBarListener bottomBarListener) {
        Fragments = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        Fragments.add(new Fragment_ListInventary());
        Fragments.add(new Fragment_NewProductInventory());
        Fragments.add(new Fragment_InfoProductInventory());
        Fragments.add(new Fragment_EditProductInventory());

        this.onMain = INDEX_INVENTORY_LIST_INVENTORY;
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
        showListInventory();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;

        switch (index){
            case INDEX_INVENTORY_LIST_INVENTORY:
                showListInventory();
                break;
            case INDEX_INVENTORY_NEW_PRODUCT_INVENTORY:
                showNewProductInventory();
                break;
            case INDEX_INVENTORY_INFO_PRODUCT_INVENTORY:
                showInfoProductInventory();
                break;
            case INDEX_INVENTORY_EDIT_PRODUCT_INVENTORY:
                showEditProductInventory();
                break;
        }
    }
    //Pages
    public void showListInventory           (){
        loadFragmentAsMain(TAG_INVENTORY_LIST);
    }
    public void showNewProductInventory     (){
    }
    public void showInfoProductInventory    (){
    }
    public void showEditProductInventory    (){
    }

    //FUNCTIONAL
    public void hideBottomBar(){
        bottomBarListener.hideBottomBarLinstener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBarLinstener.showBottomBar();
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        from = onMain;
        switch (onMain){
            case INDEX_INVENTORY_LIST_INVENTORY:
                Fragment_ListInventary listInventary = (Fragment_ListInventary)fragmentManager.findFragmentByTag(TAG_INVENTORY_LIST);
                Objects.requireNonNull(listInventary).EndAnimations();
                break;
            case INDEX_INVENTORY_NEW_PRODUCT_INVENTORY:
                onMain = INDEX_INVENTORY_LIST_INVENTORY;
                Fragment_NewProductInventory newProductInventory = (Fragment_NewProductInventory)fragmentManager.findFragmentByTag(TAG_INVENTORY_NEW_PRODUCT);
                Objects.requireNonNull(newProductInventory).EndAnimations();
                showBottomBar();
                break;
            case INDEX_INVENTORY_INFO_PRODUCT_INVENTORY:
                onMain = INDEX_INVENTORY_LIST_INVENTORY;
                Fragment_InfoProductInventory infoProductInventory = (Fragment_InfoProductInventory)fragmentManager.findFragmentByTag(TAG_INVENTORY_INFO_PRODUCT);
                Objects.requireNonNull(infoProductInventory).EndAnimations();
                break;
            case INDEX_INVENTORY_EDIT_PRODUCT_INVENTORY:
                onMain = INDEX_INVENTORY_INFO_PRODUCT_INVENTORY;
                Fragment_EditProductInventory editProductInventory = (Fragment_EditProductInventory)fragmentManager.findFragmentByTag(TAG_INVENTORY_EDIT_PRODUCT);
                Objects.requireNonNull(editProductInventory).EndAnimations();
                showBottomBar();
                break;


        }
    }


}
