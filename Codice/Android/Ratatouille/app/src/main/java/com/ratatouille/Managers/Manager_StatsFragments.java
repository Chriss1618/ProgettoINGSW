package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Stats.Fragment_Stats;

import java.util.ArrayList;

public class Manager_StatsFragments {
    private static final String TAG = "Manager_StatsFragments_Class";

    public final static int INDEX_STAT_PRODUCTIVITY = 0;

    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;
    private final FragmentManager       fragmentManager;

    private int onMain;

    public Manager_StatsFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments               = new ArrayList<>();
        this.context            = context;
        View                    = view;
        this.fragmentManager    = fragmentManager;

        Fragments.add(new Fragment_Stats());

        onMain = INDEX_STAT_PRODUCTIVITY;

    }

    public void showPage(int indexPage){

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(indexPage), null)
                .setReorderingAllowed(true)
                .commit();

        onMain = indexPage;
    }

    public void showMain(){
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();
    }
}
