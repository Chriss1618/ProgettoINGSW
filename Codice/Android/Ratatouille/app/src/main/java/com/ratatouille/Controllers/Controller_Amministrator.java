package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

import java.util.ArrayList;

public class Controller_Amministrator {
    public Manager_StaffFragments      manager_staffFragments;
    public Manager_StatsFragments      manager_statsFragments;
    public Manager_MenuFragments       manager_menuFragments;
    public Manager_AccountFragments    manager_accountFragments;

    private final Context               context;
    private final android.view.View     View;
    private final FragmentManager       fragmentManager;



    public Controller_Amministrator(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;

        constructManagerSTAFF();
        constructManagerSTATS();

        manager_statsFragments.showMain();

    }

    private void constructManagerSTAFF(){
        manager_staffFragments = new Manager_StaffFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }

    private void constructManagerSTATS(){
        manager_statsFragments = new Manager_StatsFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }

    public void showSTAFF(){
        manager_staffFragments.showMain();
    }

    public void showSTATS(){
        manager_statsFragments.showMain();
    }

}
