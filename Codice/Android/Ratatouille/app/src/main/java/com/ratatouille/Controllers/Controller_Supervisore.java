package com.ratatouille.Controllers;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;

public class Controller_Supervisore {
    //SYSTEM
    public Manager_InventoryFragments   manager_inventoryFragments;
    public Manager_MenuFragments        manager_menuFragments;
    public Manager_AccountFragments     manager_accountFragments;

    //FUNCTIONAL
    private final Context               context;
    private final android.view.View     View;
    private final FragmentManager       fragmentManager;

    public Controller_Supervisore(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;
        constructManagerINVENTORY();
        constructManagerMENU();
        constructManagerACCOUNT();
    }
    public void showMain(){
        showINVENTORY();
    }
    //Costruttori Managers
    private void constructManagerINVENTORY(){
        manager_inventoryFragments = new Manager_InventoryFragments(
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
    public void showINVENTORY(){
        manager_inventoryFragments.showMain();
    }
    public void showMENU(){
        manager_menuFragments.showMain();
    }
    public void showACCOUNT(){manager_accountFragments.showMain();}
}
