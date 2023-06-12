package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Staff.Fragment_ListStaff;
import com.ratatouille.Schermate.Staff.Fragment_NewStaffMember;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_StaffFragments implements SubController {
    //SYSTEM
    private static final String TAG = "Manager_StaffFragments_CLass";

    public static final int INDEX_STAFF_LIST        = 0;
    public static final int INDEX_STAFF_NEW_MEMBER  = 1;

    public static final String TAG_STAFF_LIST           = "staffList";
    public static final String TAG_STAFF_NEW_MEMBER     = "newMember";

    //LAYOUT
    public  final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final View                  View;

    //FUNCTIONAL
    private final BottomBarListener     bottomBarListener;
    private final FragmentManager       fragmentManager;
    public int                          onMain;
    public int                          from;

    public Manager_StaffFragments(Context context, View view,FragmentManager fragmentManager,BottomBarListener bottomBarListener){
        Fragments = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        Fragments.add(new Fragment_ListStaff(this));        //0
        Fragments.add(new Fragment_NewStaffMember(this));   //1

        onMain = INDEX_STAFF_LIST;

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
        showListStaff();
    }

    @Override
    public void changeOnMain(int indexMain, String msg) {

    }

    @Override
    public void closeView() {

    }


    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;
        switch (index){
            case INDEX_STAFF_LIST:
                showMain();
                break;
            case INDEX_STAFF_NEW_MEMBER:
                showNewStaffMember();
                break;
        }
    }
    //Pages
    public void showListStaff       (){
        loadFragmentAsMain(TAG_STAFF_LIST);
    }
    public void showNewStaffMember  (){
        hideBottomBar();
        loadFragmentAsNormal(TAG_STAFF_NEW_MEMBER);
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
            case INDEX_STAFF_LIST:
                Fragment_ListStaff listStaff = (Fragment_ListStaff)fragmentManager.findFragmentByTag(TAG_STAFF_LIST);
                Objects.requireNonNull(listStaff).EndAnimations();
                break;
            case INDEX_STAFF_NEW_MEMBER:
                onMain = INDEX_STAFF_LIST;
                showBottomBar();
                Fragment_NewStaffMember newStaffMember = (Fragment_NewStaffMember)fragmentManager.findFragmentByTag(TAG_STAFF_NEW_MEMBER);
                Objects.requireNonNull(newStaffMember).EndAnimations();
                break;
        }
    }

}