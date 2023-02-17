package com.ratatouille.Managers;

import android.content.Context;
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

    private final Context context;
    private final ArrayList<Fragment> Fragments;
    private final android.view.View View;
    private final FragmentManager fragmentManager;

    private int onMain;

    public Manager_AccountFragments(Context context, android.view.View view, FragmentManager fragmentManager) {
        Fragments = new ArrayList<>();

        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        Fragments.add(new Fragment_AccountInfo());
        Fragments.add(new Fragment_EditAccountInfo());

        onMain = INDEX_ACCOUNT_INFO;
    }

    public void showMain(){
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(View.getId(), Fragments.get(onMain), null)
                .setReorderingAllowed(true)
                .commit();

    }
}
