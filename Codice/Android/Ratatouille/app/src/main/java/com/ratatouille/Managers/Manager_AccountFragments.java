package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Schermate.Account.Fragment_AccountInfo;
import com.ratatouille.Schermate.Account.Fragment_EditAccountInfo;
import com.ratatouille.Schermate.Staff.Fragment_NewStaffMember;

import java.util.ArrayList;
import java.util.Objects;

public class Manager_AccountFragments {
    //SYSTEM
    private static final String TAG = "Manager_AccountFragment";

    public static final int INDEX_ACCOUNT_INFO  = 0;
    public static final int INDEX_ACCOUNT_EDIT  = 1;

    public static final String TAG_ACCOUNT_INFO       = "accountInfo";
    public static final String TAG_ACCOUNT_EDIT       = "accountEdit";

    //LAYOUT
    public final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;

    //FUNCTIONAL
    private BottomBarListener       bottomBarListener;
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    public Manager_AccountFragments(Context context, android.view.View view, FragmentManager fragmentManager,BottomBarListener bottomBarListener) {
        Fragments = new ArrayList<>();

        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        Fragments.add(new Fragment_AccountInfo(this));
        Fragments.add(new Fragment_EditAccountInfo(this));

        onMain = INDEX_ACCOUNT_INFO;
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
       showAccoutnInfo();
    }
    public void showFragment(int index,String msg){
        from = onMain;
        onMain = index;

        switch (index){
            case INDEX_ACCOUNT_INFO:
                showMain();
                break;
            case INDEX_ACCOUNT_EDIT:
                showAccountEdit(msg);
                break;

        }
    }
    //Pages
    public void showAccoutnInfo     (){
        loadFragmentAsMain(TAG_ACCOUNT_INFO);
    }
    public void showAccountEdit     (String Account){

        hideBottomBar();

        Bundle arguments = new Bundle();
        arguments.putString("account", Account);
        Fragments.get(INDEX_ACCOUNT_EDIT).setArguments(arguments);

        loadFragmentAsNormal(TAG_ACCOUNT_EDIT);
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
            case INDEX_ACCOUNT_INFO:
                Fragment_AccountInfo accountInfo = (Fragment_AccountInfo)fragmentManager.findFragmentByTag(TAG_ACCOUNT_INFO);
                Objects.requireNonNull(accountInfo).EndAnimations();
                break;
            case INDEX_ACCOUNT_EDIT:
                showBottomBar();
                onMain = INDEX_ACCOUNT_INFO;
                Fragment_EditAccountInfo editAccountInfo = (Fragment_EditAccountInfo)fragmentManager.findFragmentByTag(TAG_ACCOUNT_EDIT);
                Objects.requireNonNull(editAccountInfo).EndAnimations();
                break;
        }
    }

}