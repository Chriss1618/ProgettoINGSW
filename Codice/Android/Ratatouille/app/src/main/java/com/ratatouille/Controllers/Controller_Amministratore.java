package com.ratatouille.Controllers;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;
import com.ratatouille.R;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;

public class Controller_Amministratore {

    //SYSTEM
    public final static int AMMINISTRATORE_INDEX_STATS     = 0;
    public final static int AMMINISTRATORE_INDEX_STAFF     = 1;
    public final static int AMMINISTRATORE_INDEX_MENU      = 2;
    public final static int AMMINISTRATORE_INDEX_ACCOUNT   = 3;

    //FUNCTIONAL
    public Manager_StaffFragments      manager_staffFragments;
    public Manager_StatsFragments      manager_statsFragments;
    public Manager_MenuFragments       manager_menuFragments;
    public Manager_AccountFragments    manager_accountFragments;
    public int managerOnMain;

    //LAYOUT
    private final Context               context;
    private final android.view.View     View;
    private final FragmentManager       fragmentManager;


    public Controller_Amministratore(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.managerOnMain = AMMINISTRATORE_INDEX_STATS;
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
        managerOnMain = AMMINISTRATORE_INDEX_STAFF;
    }
    public void showSTATS(){
        manager_statsFragments.showMain();
        managerOnMain = AMMINISTRATORE_INDEX_STATS;
    }
    public void showMENU(){
        manager_menuFragments.showMain();
        managerOnMain = AMMINISTRATORE_INDEX_MENU;
    }
    public void showACCOUNT(){
        managerOnMain = AMMINISTRATORE_INDEX_ACCOUNT;
        manager_accountFragments.showMain();
    }



    public void changeFragment(int type_manager,int index_fragment,String msg){
        switch (type_manager){
            case AMMINISTRATORE_INDEX_STATS:
                break;
            case AMMINISTRATORE_INDEX_STAFF:
                break;
            case AMMINISTRATORE_INDEX_MENU:
                setFragmentMenuToShow(index_fragment,msg);
                break;
            case AMMINISTRATORE_INDEX_ACCOUNT:
                break;
        }
    }

    public void callEndAnimationOfFragment(int numberOfBackStack){
        switch (managerOnMain){
            case AMMINISTRATORE_INDEX_STATS:
                break;
            case AMMINISTRATORE_INDEX_STAFF:
                manager_staffFragments.callEndAnimationOfFragment(numberOfBackStack);
                break;
            case AMMINISTRATORE_INDEX_MENU:
                manager_menuFragments.callEndAnimationOfFragment(numberOfBackStack);
                break;
            case AMMINISTRATORE_INDEX_ACCOUNT:
                break;

        }
    }

    private void setFragmentMenuToShow(int index,String msg){
        manager_menuFragments.showFragment(index,msg);
    }
}
