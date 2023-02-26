package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Staff.Fragment_ListStaff;
import com.ratatouille.Schermate.Staff.Fragment_NewStaffMember;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_StaffFragments {
    private static final String TAG = "Manager_StaffFragments_CLass";

    public static final int INDEX_STAFF_LIST        = 0;
    public static final int INDEX_STAFF_NEW_MEMBER  = 1;

    public static final String TAG_STAFF_LIST           = "staffList";
    public static final String TAG_STAFF_NEW_MEMBER     = "newMember";


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

        Fragments.add(new Fragment_ListStaff(this));        //0
        Fragments.add(new Fragment_NewStaffMember(this));   //1

        onMain = INDEX_STAFF_LIST;

    }
    public void showMain(){
        onMain = INDEX_STAFF_LIST;
        fragmentManager.popBackStack();

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_STAFF_LIST), TAG_STAFF_LIST)
                .setReorderingAllowed(true)
                .commit();

    }

    public void showNewStaffMember(){
        onMain = INDEX_STAFF_NEW_MEMBER;

        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_STAFF_NEW_MEMBER),TAG_STAFF_NEW_MEMBER)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void showFragment(int index,String msg){
        switch (index){
            case INDEX_STAFF_LIST:
                showMain();
                break;
            case INDEX_STAFF_NEW_MEMBER:
                showNewStaffMember();
                break;
        }
    }

    public void callEndAnimationOfFragment(int numberOfBackStack){
        switch (numberOfBackStack){
            case INDEX_STAFF_LIST:
                break;
            case INDEX_STAFF_NEW_MEMBER:
                Log.d(TAG, "Manager_staff: closing New Member");
                onMain = INDEX_STAFF_LIST;
                Fragment_NewStaffMember newStaffMember = (Fragment_NewStaffMember)fragmentManager.findFragmentByTag(TAG_STAFF_NEW_MEMBER);
                Objects.requireNonNull(newStaffMember).EndAnimations();
                break;
            case 2:
                switch (onMain){

                }
                break;
        }
    }



}
