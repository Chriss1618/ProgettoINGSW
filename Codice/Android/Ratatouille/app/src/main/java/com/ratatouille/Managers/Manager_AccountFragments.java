package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Schermate.Account.Fragment_AccountInfo;
import com.ratatouille.Schermate.Account.Fragment_EditAccountInfo;

import java.util.ArrayList;

public class Manager_AccountFragments {
    private static final String TAG = "Manager_AccountFragment";

    public static final int INDEX_ACCOUNT_INFO  = 0;
    public static final int INDEX_ACCOUNT_EDIT  = 1;

    public static final String TAG_ACCOUNT_INFO       = "accountInfo";
    public static final String TAG_ACCOUNT_EDIT       = "accountEdit";

    public final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;
    private int from;

    public Manager_AccountFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_AccountInfo(this));
        Fragments.add(new Fragment_EditAccountInfo(this));

        onMain = INDEX_ACCOUNT_INFO;
    }

    public void showMain(){
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), TAG_ACCOUNT_INFO)
                .setReorderingAllowed(true)
                .commit();

    }

    public void showAccountEdit(String Account){
        from = onMain;
        onMain = INDEX_ACCOUNT_EDIT;

        Bundle arguments = new Bundle();
        arguments.putString("account", Account);

        Fragments.get(INDEX_ACCOUNT_EDIT).setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(INDEX_ACCOUNT_EDIT),TAG_ACCOUNT_EDIT)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void showFragment(int index,String msg){
        switch (index){
            case INDEX_ACCOUNT_INFO:
                showMain();
                break;
            case INDEX_ACCOUNT_EDIT:
                showAccountEdit(msg);
                break;

        }
    }


}
