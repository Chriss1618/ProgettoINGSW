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
import com.ratatouille.Schermate.Ordini.Fragment_HystoryOrders;
import com.ratatouille.Schermate.Ordini.Fragment_ListOrders;
import com.ratatouille.Schermate.Ordini.Fragment_TableOrders;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_InfoProductCameriere;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_ListCategoryCameriere;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_ListProductsCameriere;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_ListTables;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_ReportOrder;
import com.ratatouille.Schermate.OrdiniCameriere.Fragment_TableInfo;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_Ordini_Cameriere {
    //SYSTEM
    private static final String TAG = "Manager_Ordini_Camerier";

    public static final int INDEX_ORDINI_CAMERIERE_LIST_TABLES      = 0;
    public static final int INDEX_ORDINI_CAMERIERE_TABLE_INFO       = 1;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_CATEGORY    = 2;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS    = 3;
    public static final int INDEX_ORDINI_CAMERIERE_INFO_PRODUCT     = 4;
    public static final int INDEX_ORDINI_CAMERIERE_RESOCONTO_ORDERS = 5;

    public static final String TAG_ORDINI_CAMERIERE_LIST_TABLES         = "listTables";
    public static final String TAG_ORDINI_CAMERIERE_TABLE_INFO          = "infoTable";
    public static final String TAG_ORDINI_CAMERIERE_LIST_CATEGORY       = "listCategory";
    public static final String TAG_ORDINI_CAMERIERE_LIST_PRODUCTS       = "listProducts";
    public static final String TAG_ORDINI_CAMERIERE_INFO_PRODUCT        = "infoProduct";
    public static final String TAG_ORDINI_CAMERIERE_RESOCONTO_ORDERS    = "resocontoOrders";

    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private final BottomBarListener       bottomBarListener;
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    public Manager_Ordini_Cameriere(Context context, android.view.View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Fragments = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        Fragments.add(new Fragment_ListTables());
        Fragments.add(new Fragment_TableInfo());
        Fragments.add(new Fragment_ListCategoryCameriere());
        Fragments.add(new Fragment_ListProductsCameriere());
        Fragments.add(new Fragment_InfoProductCameriere());
        Fragments.add(new Fragment_ReportOrder());

        onMain = INDEX_ORDINI_CAMERIERE_LIST_TABLES;
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
        showListTables();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;

        switch (index){
            case INDEX_ORDINI_CAMERIERE_LIST_TABLES:
                showListTables();
                break;
            case INDEX_ORDINI_CAMERIERE_TABLE_INFO:
                showTableInfo();
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_CATEGORY:
                showListCategory();
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS:
                showListProducts();
                break;
            case INDEX_ORDINI_CAMERIERE_INFO_PRODUCT:
                showInfoProduct();
                break;
            case INDEX_ORDINI_CAMERIERE_RESOCONTO_ORDERS:
                showResocontoOrder();
                break;
        }
    }

    //Pages
    public void showListTables      (){
        loadFragmentAsMain(TAG_ORDINI_CAMERIERE_LIST_TABLES);
    }
    public void showTableInfo       (){

    }
    public void showListCategory    (){

    }
    public void showListProducts    (){

    }
    public void showInfoProduct     (){

    }
    public void showResocontoOrder  (){

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
            case INDEX_ORDINI_CAMERIERE_LIST_TABLES:
                Fragment_ListTables listTables = (Fragment_ListTables)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_LIST_TABLES);
                Objects.requireNonNull(listTables).EndAnimations();
                break;
            case INDEX_ORDINI_CAMERIERE_TABLE_INFO:
                onMain = INDEX_ORDINI_CAMERIERE_LIST_TABLES;
                Fragment_TableInfo tableInfo = (Fragment_TableInfo)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_TABLE_INFO);
                Objects.requireNonNull(tableInfo).EndAnimations();
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_CATEGORY:
                onMain = INDEX_ORDINI_CAMERIERE_TABLE_INFO;
                Fragment_ListCategoryCameriere listCategoryCameriere = (Fragment_ListCategoryCameriere)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_LIST_CATEGORY);
                Objects.requireNonNull(listCategoryCameriere).EndAnimations();
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS:
                onMain = INDEX_ORDINI_CAMERIERE_LIST_CATEGORY;
                Fragment_ListProductsCameriere listProductsCameriere = (Fragment_ListProductsCameriere)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_LIST_PRODUCTS);
                Objects.requireNonNull(listProductsCameriere).EndAnimations();
                break;
            case INDEX_ORDINI_CAMERIERE_INFO_PRODUCT:
                onMain = INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS;
                Fragment_InfoProductCameriere infoProductCameriere = (Fragment_InfoProductCameriere)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_INFO_PRODUCT);
                Objects.requireNonNull(infoProductCameriere).EndAnimations();
                break;
            case INDEX_ORDINI_CAMERIERE_RESOCONTO_ORDERS:
                onMain = INDEX_ORDINI_CAMERIERE_TABLE_INFO;
                Fragment_ReportOrder reportOrder = (Fragment_ReportOrder)fragmentManager.findFragmentByTag(TAG_ORDINI_CAMERIERE_RESOCONTO_ORDERS);
                Objects.requireNonNull(reportOrder).EndAnimations();
                break;


        }
    }
}
