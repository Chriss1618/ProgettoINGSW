package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    public int onMain;
    public int from;

    public Manager_MenuFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_ListCategory(this));
        Fragments.add(new Fragment_ListProducts(this));
        Fragments.add(new Fragment_InfoProduct(this));
        Fragments.add(new Fragment_NewProduct(this));
        Fragments.add(new Fragment_EditProduct(this));

        this.onMain = INDEX_MENU_LIST_CATEGORY;
    }

    public void showMain(){
        from = onMain;
        onMain = INDEX_MENU_LIST_CATEGORY;

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), TAG_MENU_LIST_CATEGORY)
                .setReorderingAllowed(true)
                .commit();

    }

    public void showListProducts(String category){
        from = onMain;
        onMain = INDEX_MENU_LIST_PRODUCTS;

        Bundle arguments = new Bundle();
        arguments.putString("category", category);

        Fragments.get(INDEX_MENU_LIST_PRODUCTS).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_LIST_PRODUCTS),TAG_MENU_LIST_PRODUCTS)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
    public void showInfoProduct(String product){
        from = onMain;
        onMain = INDEX_MENU_INFO_PRODUCT;

        Bundle arguments = new Bundle();
        arguments.putString("product", product);

        Fragments.get(INDEX_MENU_INFO_PRODUCT).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_INFO_PRODUCT), TAG_MENU_INFO_PRODUCT)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
    public void showEditProduct(String product){
        from = onMain;
        onMain = INDEX_MENU_EDIT_PRODUCT;

        Bundle arguments = new Bundle();
        arguments.putString("product", product);

        Fragments.get(INDEX_MENU_EDIT_PRODUCT).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_EDIT_PRODUCT), TAG_MENU_EDIT_PRODUCT)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void showAddProduct(String category){
        from = onMain;
        onMain = INDEX_MENU_NEW_PRODUCT;

        Bundle arguments = new Bundle();
        arguments.putString("category", category);

        Fragments.get(INDEX_MENU_NEW_PRODUCT).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_MENU_NEW_PRODUCT), TAG_MENU_NEW_PRODUCT)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void showFragment(int index,String msg){
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

    //ANIMATIONS
    public void callEndAnimationOfFragment(int numberOfBackStack){
        switch (numberOfBackStack){
            case INDEX_MENU_LIST_CATEGORY:
                break;
            case INDEX_MENU_LIST_PRODUCTS:
                from = INDEX_MENU_LIST_PRODUCTS;
                onMain = INDEX_MENU_LIST_CATEGORY;
                Fragment_ListProducts listProducts = (Fragment_ListProducts)fragmentManager.findFragmentByTag(TAG_MENU_LIST_PRODUCTS);
                Objects.requireNonNull(listProducts).EndAnimations();
                break;
            case 2:
                switch (onMain){
                    case INDEX_MENU_INFO_PRODUCT:
                        from = INDEX_MENU_INFO_PRODUCT;
                        onMain = INDEX_MENU_LIST_PRODUCTS;
                        Fragment_InfoProduct infoProduct = (Fragment_InfoProduct)fragmentManager.findFragmentByTag(TAG_MENU_INFO_PRODUCT);
                        Objects.requireNonNull(infoProduct).EndAnimations();
                        break;
                    case INDEX_MENU_EDIT_PRODUCT:
                        from = INDEX_MENU_EDIT_PRODUCT;
                        onMain = INDEX_MENU_INFO_PRODUCT;
                        break;
                    case INDEX_MENU_NEW_PRODUCT:
                        from = INDEX_MENU_NEW_PRODUCT;
                        onMain = INDEX_MENU_LIST_PRODUCTS;
                        break;
                }
                break;
        }
    }

}
