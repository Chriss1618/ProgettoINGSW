package com.ratatouille.Managers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Ordini.Fragment_HystoryOrders;
import com.ratatouille.Schermate.Ordini.Fragment_ListOrders;
import com.ratatouille.Schermate.Ordini.Fragment_TableOrders;

import java.util.ArrayList;

public class Manager_Ordini {
    private static final String TAG = "Manager_Ordini";

    public static final int INDEX_ORDINI_LIST_ORDERS    = 0;
    public static final int INDEX_ORDINI_TABLE_ORDERS   = 1;
    public static final int INDEX_ORDINI_HISTORY_ORDERS = 2;

    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;

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

    public void showMain(){
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }

}
