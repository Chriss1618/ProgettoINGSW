package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Stats.Fragment_Stats;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_StatsFragments {
    //SYSTEM
    private static final String TAG = "Manager_StatsFragments_Class";

    public final static int INDEX_STAT_PRODUCTIVITY = 0;

    public final static String TAG_STAT_PRODUCTIVITY = "productivityStat";

    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    public Manager_StatsFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.Fragments          = new ArrayList<>();
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;

        Fragments.add(new Fragment_Stats(this));

        onMain = INDEX_STAT_PRODUCTIVITY;
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
        showStats();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;
        switch (index){
            case INDEX_STAT_PRODUCTIVITY:
                showStats();
                break;
        }
    }
    //Pages
    public void showStats   (){
        loadFragmentAsMain(TAG_STAT_PRODUCTIVITY);
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        from = onMain;
        switch (onMain){
            case INDEX_STAT_PRODUCTIVITY:
                Fragment_Stats fragment_stats = (Fragment_Stats)fragmentManager.findFragmentByTag(TAG_STAT_PRODUCTIVITY);
                Objects.requireNonNull(fragment_stats).EndAnimations();
                break;
        }
    }

}
