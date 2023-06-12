package com.ratatouille.Controllers;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.BottomBarInterfaces.BottomBarListener;
import com.ratatouille.Interfaces.Controller;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

public class Controller_Supervisore implements Controller {
    //SYSTEM
    public final static int SUPERVISORE_INDEX_INVENTARIO    = 0;
    public final static int SUPERVISORE_INDEX_MENU          = 1;
    public final static int SUPERVISORE_INDEX_ACCOUNT       = 2;

    //FUNCTIONAL
    public int                          managerOnMain;
    private final FragmentManager       fragmentManager;
    private final BottomBarListener     bottomBarListener;

    public Manager_InventoryFragments   manager_inventoryFragments;
    public Manager_MenuFragments        manager_menuFragments;
    public Manager_AccountFragments     manager_accountFragments;

    //LAYOUT
    private final Context               context;
    private final android.view.View     View;

    public Controller_Supervisore(Context context, android.view.View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;
        this.managerOnMain      = SUPERVISORE_INDEX_INVENTARIO;
        constructManagerINVENTORY();
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
        showINVENTORY();
    }

    @Override
    public void changeOnMain(int indexMain) {

    }

    @Override
    public void closeView() {

    }

    public void showINVENTORY(){
        manager_inventoryFragments.showMain();
        managerOnMain = SUPERVISORE_INDEX_INVENTARIO;
    }
    public void showMENU(){
        manager_menuFragments.showMain();
        managerOnMain = SUPERVISORE_INDEX_MENU;
    }
    public void showACCOUNT(){
        manager_accountFragments.showMain();
        managerOnMain = SUPERVISORE_INDEX_ACCOUNT;
    }

    //FUNCTIONAL
    public void resetMainPackage(){
        switch (managerOnMain){
            case SUPERVISORE_INDEX_INVENTARIO:  this.manager_inventoryFragments.onMain  = Manager_InventoryFragments.INDEX_INVENTORY_LIST_INVENTORY;
                break;
            case SUPERVISORE_INDEX_MENU:        this.manager_menuFragments.onMain       = Manager_MenuFragments.INDEX_MENU_LIST_CATEGORY;
                break;
            case SUPERVISORE_INDEX_ACCOUNT:     this.manager_accountFragments.onMain    = Manager_AccountFragments.INDEX_ACCOUNT_INFO;
                break;
        }
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        switch (managerOnMain){
            case SUPERVISORE_INDEX_INVENTARIO:
                manager_inventoryFragments  .callEndAnimationOfFragment();
                break;
            case SUPERVISORE_INDEX_MENU:
                manager_menuFragments       .callEndAnimationOfFragment();
                break;
            case SUPERVISORE_INDEX_ACCOUNT:
                manager_accountFragments    .callEndAnimationOfFragment();
                break;
        }
    }

}
