package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Staff.Fragment_ListStaff;
import com.ratatouille.Schermate.Staff.Fragment_NewStaffMember;

import java.util.ArrayList;

public class Manager_StaffFragments {
    private static final String TAG = "Manager_StaffFragments_CLass";

    public static final int INDEX_STAFF_LIST       = 0;
    public static final int INDEX_STAFF_NEW_MEMBER  = 1;

    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;
    private final FragmentManager       fragmentManager;

    private int onMain;

    public Manager_StaffFragments(Context context, View view,FragmentManager fragmentManager){
        Fragments = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;

        Fragments.add(new Fragment_ListStaff());        //0
        Fragments.add(new Fragment_NewStaffMember());   //1
        onMain = INDEX_STAFF_LIST;

    }

    public void showPage(int indexPage){
        fragmentManager.popBackStack();

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(indexPage), null)
                .setReorderingAllowed(true)
                .commit();

        onMain = indexPage;
    }

    public void showMain(){
        fragmentManager.popBackStack();

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }
}
