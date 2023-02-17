package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini;

public class Controller_Chef {
    //SYSTEM
    public Manager_InventoryFragments   manager_inventoryFragments;
    public Manager_Ordini               manager_ordini;
    public Manager_MenuFragments        manager_menuFragments;
    public Manager_AccountFragments     manager_accountFragments;

    //FUNCTIONAL
    private final Context context;
    private final android.view.View     View;
    private final FragmentManager fragmentManager;

    public Controller_Chef(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context = context;
        View = view;
        this.fragmentManager = fragmentManager;

        constructManagerINVENTORY();
        constructManagerORDINI();
        constructManagerMENU();
        constructManagerACCOUNT();
    }

    public void showMain(){
        showORDINI();
    }

    //Costruttori Managers
    private void constructManagerINVENTORY(){
        manager_inventoryFragments = new Manager_InventoryFragments(
                this.context,
                this.View,
                this.fragmentManager
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
    public void showORDINI(){
        manager_ordini.showMain();
    }
    public void showMENU(){
        manager_menuFragments.showMain();
    }
    public void showACCOUNT(){manager_accountFragments.showMain();}
}
