package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class Manager_Ordini_Cameriere {
    private static final String TAG = "Manager_Ordini_Camerier";

    public static final int INDEX_ORDINI_CAMERIERE_LIST_TABLES      = 0;
    public static final int INDEX_ORDINI_CAMERIERE_TABLE_INFO       = 1;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_CATEGORY    = 2;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS    = 3;
    public static final int INDEX_ORDINI_CAMERIERE_INFO_PRODUCT     = 4;
    public static final int INDEX_ORDINI_CAMERIERE_RESOCONTO_ORDERS = 5;

    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;

    public Manager_Ordini_Cameriere(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_ListTables());
        Fragments.add(new Fragment_TableInfo());
        Fragments.add(new Fragment_ListCategoryCameriere());
        Fragments.add(new Fragment_ListProductsCameriere());
        Fragments.add(new Fragment_InfoProductCameriere());
        Fragments.add(new Fragment_ReportOrder());

        onMain = INDEX_ORDINI_CAMERIERE_LIST_TABLES;
    }

    public void showMain(){
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }

}
