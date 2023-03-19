package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

public class Controller_Cameriere {
    //SYSTEM
    public final static int CAMERIERE_INDEX_MENU        = 0;
    public final static int CAMERIERE_INDEX_ACCOUNT     = 1;

    //FUNCTIONAL
    public int                         managerOnMain;
    private final FragmentManager      fragmentManager;

    public Manager_Ordini_Cameriere manager_ordini_cameriere;
    public Manager_AccountFragments manager_accountFragments;

    //FUNCTIONAL
    private final Context           context;
    private final View              View;
    private final BottomBarListener bottomBarListener;

    public Controller_Cameriere(Context context, View view, FragmentManager fragmentManager,BottomBarListener bottomBarListener) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;
        this.managerOnMain      = CAMERIERE_INDEX_MENU;

        constructManagerORDINI_CAMERIERE();
        constructManagerACCOUNT();
    }

    //Constrictor Managers
    private void constructManagerORDINI_CAMERIERE(){
        manager_ordini_cameriere = new Manager_Ordini_Cameriere(
                this.context,
                this.View,
                this.fragmentManager,
                this.bottomBarListener
        );
    }
    private void constructManagerACCOUNT(){
        manager_accountFragments = new Manager_AccountFragments(
                this.context,
                this.View,
                this.fragmentManager,
                this.bottomBarListener
        );
    }

    //Show Pages
    public void showMain(){
        showORDINI_CAMERIERE();
    }

    public void showORDINI_CAMERIERE(){
        manager_ordini_cameriere.showMain();
        managerOnMain = CAMERIERE_INDEX_MENU;
    }
    public void showACCOUNT(){
        manager_accountFragments.showMain();
        managerOnMain = CAMERIERE_INDEX_ACCOUNT;
    }

    //FUNCTIONAL
    public void resetMainPackage(){
        switch (managerOnMain){
            case CAMERIERE_INDEX_MENU:      this.manager_ordini_cameriere.onMain = Manager_StatsFragments.INDEX_STAT_PRODUCTIVITY;
                break;
            case CAMERIERE_INDEX_ACCOUNT:   this.manager_accountFragments.onMain = Manager_AccountFragments.INDEX_ACCOUNT_INFO;
                break;
        }
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        switch (managerOnMain){
            case CAMERIERE_INDEX_MENU:
                manager_ordini_cameriere.callEndAnimationOfFragment();
                break;
            case CAMERIERE_INDEX_ACCOUNT:
                manager_accountFragments.callEndAnimationOfFragment();
                break;
        }
    }

}
