package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ratatouille.Adapters.Adapter_ProductWaiter;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.R;
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
    private final BottomSheetDialog     bottomSheetDialog;
    private RecyclerView                Recycler_Products;

    //FUNCTIONAL
    private Adapter_ProductWaiter       adapter_product_waiter;
    private RecycleEventListener        RecycleEventListener;
    private final BottomBarListener     bottomBarListener;
    private final FragmentManager       fragmentManager;
    public int                          onMain;
    public int                          from;

    //DATA
    private ArrayList<String> ProductsReport;

    public Manager_Ordini_Cameriere(Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Fragments = new ArrayList<>();
        ProductsReport = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        this.bottomSheetDialog  = new BottomSheetDialog(this.context, R.style.BottomSheetDialogTheme);

        Fragments.add(new Fragment_ListTables(this));
        Fragments.add(new Fragment_TableInfo(this));
        Fragments.add(new Fragment_ListCategoryCameriere(this));
        Fragments.add(new Fragment_ListProductsCameriere(this));
        Fragments.add(new Fragment_InfoProductCameriere(this));
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
                showTableInfo(msg);
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_CATEGORY:
                showListCategory();
                break;
            case INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS:
                showListProducts(msg);
                break;
            case INDEX_ORDINI_CAMERIERE_INFO_PRODUCT:
                showInfoProduct(msg);
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
    public void showTableInfo       (String table){
        Bundle arguments = new Bundle();
        arguments.putString("table", table);
        Fragments.get(INDEX_ORDINI_CAMERIERE_TABLE_INFO).setArguments(arguments);

        loadFragmentAsNormal(TAG_ORDINI_CAMERIERE_TABLE_INFO);
    }
    public void showListCategory    (){
        loadFragmentAsNormal(TAG_ORDINI_CAMERIERE_LIST_CATEGORY);
    }
    public void showListProducts    (String category){
        Bundle arguments = new Bundle();
        arguments.putString("category", category);
        Fragments.get(INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS).setArguments(arguments);

        loadFragmentAsNormal(TAG_ORDINI_CAMERIERE_LIST_PRODUCTS);
    }
    public void showInfoProduct     (String product){
        Bundle arguments = new Bundle();
        arguments.putString("product", product);
        Fragments.get(INDEX_ORDINI_CAMERIERE_INFO_PRODUCT).setArguments(arguments);
        loadFragmentAsNormal(TAG_ORDINI_CAMERIERE_INFO_PRODUCT);
    }

    public void showResocontoOrder  (){

    }

    //FUNCTIONAL
    public void hideBottomBar(){
        bottomBarListener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBar();
    }

    //BOTTOM SHEET
    public void showBottomSheet(){
        initBottomSheetDialog();
        initListProductsRV();

        bottomSheetDialog.show();
    }
    private void initBottomSheetDialog(){
        bottomSheetDialog.setContentView(LayoutInflater.from(this.context).inflate(R.layout.bottom_sheet_record_orders,
                View.findViewById(R.id.Bottom_sheet)));
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = ((BottomSheetDialog)bottomSheetDialog).getBehavior();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private void setRecycleEventListener(){
        RecycleEventListener = new RecycleEventListener();

        RecycleEventListener.setOnClickItemOptionAdapterListener(this::removeProduct);

    }
    private void initListProductsRV( ){
        setRecycleEventListener();

        Recycler_Products  = bottomSheetDialog.findViewById(R.id.recycler_products);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(true);
        boolean isFromLeft = true;

        adapter_product_waiter = new Adapter_ProductWaiter(this.ProductsReport, RecycleEventListener,false,true);
        Recycler_Products.setAdapter(adapter_product_waiter);
    }

    public void addProduct(String product){
        this.ProductsReport.add(product);
     }
    public void removeProduct(String name,int action){
         Log.d(TAG, "removeProduct: ");
         //this.ProductsReport.removeIf(name1 -> (name1.equals(name)));
        for(int i = 0;i < ProductsReport.size();i++){
            if(ProductsReport.get(i).equals(name)){
                ProductsReport.remove(i);
                Recycler_Products.invalidate();
                adapter_product_waiter.notifyDataSetChanged();
                return;
            }
        }
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        int temp = from;
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
                onMain = temp;
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