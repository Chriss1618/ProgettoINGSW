package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.Controller;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini;

public class Controller_Chef implements Controller {
    //SYSTEM
    public final static int CHEF_INDEX_INVENTORY     = 0;
    public final static int CHEF_INDEX_ORDERS       = 1;
    public final static int CHEF_INDEX_MENU         = 2;
    public final static int CHEF_INDEX_ACCOUNT      = 3;

    //FUNCTIONAL
    public int                          managerOnMain;
    private final FragmentManager       fragmentManager;

    public Manager_InventoryFragments   manager_inventoryFragments;
    public Manager_Ordini               manager_ordini;
    public Manager_MenuFragments        manager_menuFragments;
    public Manager_AccountFragments     manager_accountFragments;

    //LAYOUT
    private final Context               context;
    private final View                  View;
    private final BottomBarListener     bottomBarListener;

    public Controller_Chef(Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;
        this.managerOnMain      = CHEF_INDEX_ORDERS;

        constructManagerINVENTORY();
        constructManagerORDINI();
        constructManagerMENU();
        constructManagerACCOUNT();
    }

    //Constrictor Managers
    private void constructManagerINVENTORY(){
        manager_inventoryFragments = new Manager_InventoryFragments(
                this.context,
                this.View,
                this.fragmentManager,
                this.bottomBarListener
        );
    }
    private void constructManagerORDINI(){
        manager_ordini = new Manager_Ordini(
                this.context,
                this.View,
                this.fragmentManager
        );
    }
    private void constructManagerMENU(){
        manager_menuFragments = new Manager_MenuFragments(
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
        showORDINI();
    }

    @Override
    public void changeOnMain(int indexMain) {

    }

    @Override
    public void closeView() {

    }

    public void showINVENTORY(){
        manager_inventoryFragments.showMain();
        managerOnMain = CHEF_INDEX_INVENTORY;
    }
    public void showORDINI(){
        manager_ordini.showMain();
        managerOnMain = CHEF_INDEX_ORDERS;
    }
    public void showMENU(){
        manager_menuFragments.showMain();
        managerOnMain = CHEF_INDEX_MENU;
    }
    public void showACCOUNT(){
        manager_accountFragments.showMain();
        managerOnMain = CHEF_INDEX_ACCOUNT;
    }

    //FUNCTIONAL
    public void resetMainPackage(){
        switch (managerOnMain){
            case CHEF_INDEX_INVENTORY:   this.manager_inventoryFragments.onMain  = Manager_InventoryFragments.INDEX_INVENTORY_LIST_INVENTORY;
                break;
            case CHEF_INDEX_ORDERS:     this.manager_ordini.onMain              = Manager_Ordini.INDEX_ORDINI_LIST_ORDERS;
                break;
            case CHEF_INDEX_MENU:       this.manager_menuFragments.onMain       = Manager_MenuFragments.MAIN;
                break;
            case CHEF_INDEX_ACCOUNT:    this.manager_accountFragments.onMain    = Manager_AccountFragments.INDEX_ACCOUNT_INFO;
                break;
        }
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        switch (managerOnMain){
            case CHEF_INDEX_INVENTORY:
                manager_inventoryFragments  .callEndAnimationOfFragment();
                break;
            case CHEF_INDEX_ORDERS:
                manager_ordini              .callEndAnimationOfFragment();
                break;
            case CHEF_INDEX_MENU:
                manager_menuFragments       .callEndAnimationOfFragment();
                break;
            case CHEF_INDEX_ACCOUNT:
                manager_accountFragments    .callEndAnimationOfFragment();
                break;
        }
    }

}
