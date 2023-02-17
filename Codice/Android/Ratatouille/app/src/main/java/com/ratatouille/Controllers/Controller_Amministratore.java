package com.ratatouille.Controllers;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

public class Controller_Amministratore {
    //SYSTEM
    public Manager_StaffFragments      manager_staffFragments;
    public Manager_StatsFragments      manager_statsFragments;
    public Manager_MenuFragments       manager_menuFragments;
    public Manager_AccountFragments    manager_accountFragments;

    //FUNCTIONAL
    private final Context               context;
    private final android.view.View     View;
    private final FragmentManager       fragmentManager;

    public Controller_Amministratore(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;

        constructManagerSTAFF();
        constructManagerSTATS();
        constructManagerMENU();
        constructManagerACCOUNT();

    }

    public void showMain(){
        showSTATS();
    }
    //Costruttori Managers
    private void constructManagerSTATS(){
        manager_statsFragments = new Manager_StatsFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }
    private void constructManagerSTAFF(){
        manager_staffFragments = new Manager_StaffFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }
    private void constructManagerMENU(){
        manager_menuFragments = new Manager_MenuFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }
    private void constructManagerACCOUNT(){
        manager_accountFragments = new Manager_AccountFragments(
                this.context,
                this.View,
                this.fragmentManager
        );
    }

    //Show Pages
    public void showSTAFF(){
        manager_staffFragments.showMain();
    }
    public void showSTATS(){
        manager_statsFragments.showMain();
    }
    public void showMENU(){
        manager_menuFragments.showMain();
    }
    public void showACCOUNT(){manager_accountFragments.showMain();}

}
