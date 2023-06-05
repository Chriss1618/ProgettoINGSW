package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.R;
import com.ratatouille.Schermate.Menu.Fragment_EditProduct;
import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListCategory;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Menu.Fragment_NewProduct;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_MenuFragments {
    //SYSTEM
    private static final String TAG = "Manager_MenuFragments";

    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    public static final String TAG_MENU_LIST_CATEGORY       = "listCategory";
    public static final String TAG_MENU_LIST_PRODUCTS       = "listProducts";
    public static final String TAG_MENU_INFO_PRODUCT        = "infoProduct";
    public static final String TAG_MENU_NEW_PRODUCT         = "newProduct";
    public static final String TAG_MENU_EDIT_PRODUCT        = "editProduct";

    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private BottomBarListener       bottomBarListener;
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    //DATA
    private ArrayList<CategoriaMenu> ListCategory;

    public Manager_MenuFragments(Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Fragments = new ArrayList<>();

        this.context                = context;
        this.View                   = view;
        this.fragmentManager        = fragmentManager;
        this.bottomBarListener      = bottomBarListener;

        Fragments.add(new Fragment_ListCategory(this));
        Fragments.add(new Fragment_ListProducts(this));
        Fragments.add(new Fragment_InfoProduct(this));
        Fragments.add(new Fragment_NewProduct(this));
        Fragments.add(new Fragment_EditProduct(this));

        this.onMain = INDEX_MENU_LIST_CATEGORY;
    }
    public Manager_MenuFragments(Context context, View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context                = context;
        this.View                   = view;
        this.fragmentManager        = fragmentManager;

        Fragments.add(new Fragment_ListCategory(this));
        Fragments.add(new Fragment_ListProducts(this));
        Fragments.add(new Fragment_InfoProduct(this));
        Fragments.add(new Fragment_NewProduct(this));
        Fragments.add(new Fragment_EditProduct(this));

        this.onMain = INDEX_MENU_LIST_CATEGORY;
    }
    //ShowPages
    public void loadFragmentAsMain(String Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_LIST_CATEGORY), Tag)
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
       showListCategory();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;

        switch (index){
            case INDEX_MENU_LIST_CATEGORY:
                showMain();
                break;
            case INDEX_MENU_LIST_PRODUCTS:
                showListProducts(msg);
                break;
            case INDEX_MENU_INFO_PRODUCT:
                showInfoProduct(msg);
                break;
            case INDEX_MENU_NEW_PRODUCT:
                showAddProduct(msg);
                break;
            case INDEX_MENU_EDIT_PRODUCT:
                showEditProduct(msg);
                break;
        }
    }
    //Pages
    public void showListCategory    (){
        loadFragmentAsMain(TAG_MENU_LIST_CATEGORY);
    }
    public void showListProducts    (String category){
        Bundle arguments = new Bundle();
        arguments.putString("category", category);
        Fragments.get(INDEX_MENU_LIST_PRODUCTS).setArguments(arguments);

        loadFragmentAsNormal(TAG_MENU_LIST_PRODUCTS);
    }
    public void showInfoProduct     (String product){
        Bundle arguments = new Bundle();
        arguments.putString("product", product);
        Fragments.get(INDEX_MENU_INFO_PRODUCT).setArguments(arguments);

        loadFragmentAsNormal(TAG_MENU_INFO_PRODUCT);
    }
    public void showEditProduct     (String product){
        Bundle arguments = new Bundle();
        arguments.putString("product", product);
        Fragments.get(INDEX_MENU_EDIT_PRODUCT).setArguments(arguments);

        hideBottomBar();
        loadFragmentAsNormal(TAG_MENU_EDIT_PRODUCT);
    }
    public void showAddProduct      (String category){
        Bundle arguments = new Bundle();
        arguments.putString("category", category);
        Fragments.get(INDEX_MENU_NEW_PRODUCT).setArguments(arguments);
        hideBottomBar();
        loadFragmentAsNormal(TAG_MENU_NEW_PRODUCT);
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
            case INDEX_MENU_LIST_CATEGORY:
                Fragment_ListCategory listCategory = (Fragment_ListCategory)fragmentManager.findFragmentByTag(TAG_MENU_LIST_CATEGORY);
                Objects.requireNonNull(listCategory).EndAnimations();
                break;
            case INDEX_MENU_LIST_PRODUCTS:
                onMain = INDEX_MENU_LIST_CATEGORY;
                Fragment_ListProducts listProducts = (Fragment_ListProducts)fragmentManager.findFragmentByTag(TAG_MENU_LIST_PRODUCTS);
                Objects.requireNonNull(listProducts).EndAnimations();
                break;
            case INDEX_MENU_INFO_PRODUCT:
                onMain = INDEX_MENU_LIST_PRODUCTS;
                Fragment_InfoProduct infoProduct = (Fragment_InfoProduct)fragmentManager.findFragmentByTag(TAG_MENU_INFO_PRODUCT);
                Objects.requireNonNull(infoProduct).EndAnimations();
                break;
            case INDEX_MENU_EDIT_PRODUCT:
                onMain = INDEX_MENU_INFO_PRODUCT;
                Fragment_EditProduct editProduct = (Fragment_EditProduct)fragmentManager.findFragmentByTag(TAG_MENU_EDIT_PRODUCT);
                Objects.requireNonNull(editProduct).EndAnimations();
                showBottomBar();
                break;
            case INDEX_MENU_NEW_PRODUCT:
                onMain = INDEX_MENU_LIST_PRODUCTS;
                Fragment_NewProduct newProduct = (Fragment_NewProduct)fragmentManager.findFragmentByTag(TAG_MENU_NEW_PRODUCT);
                Objects.requireNonNull(newProduct).EndAnimations();
                showBottomBar();
                break;
        }
    }

}