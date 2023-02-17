package com.ratatouille.Managers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Inventario.Fragment_EditProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_InfoProductInventory;
import com.ratatouille.Schermate.Inventario.Fragment_ListInventary;
import com.ratatouille.Schermate.Inventario.Fragment_NewProductInventory;

import java.util.ArrayList;

public class Manager_InventoryFragments {
    private static final String TAG = "Manager_InventarioFragm";

    public static final int INDEX_INVENTORY_LIST_INVENTORY          = 0;
    public static final int INDEX_INVENTORY_NEW_PRODUCT_INVENTORY   = 1;
    public static final int INDEX_INVENTORY_INFO_PRODUCT_INVENTORY  = 2;
    public static final int INDEX_INVENTORY_EDIT_PRODUCT_INVENTORY  = 3;

    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;

    public Manager_InventoryFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_ListInventary());
        Fragments.add(new Fragment_NewProductInventory());
        Fragments.add(new Fragment_InfoProductInventory());
        Fragments.add(new Fragment_EditProductInventory());

        this.onMain = INDEX_INVENTORY_LIST_INVENTORY;
    }

    public void showMain(){
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }

}
