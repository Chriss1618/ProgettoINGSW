package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;

public class Controller_Cameriere {
    //SYSTEM
    public Manager_Ordini_Cameriere manager_ordini_cameriere;
    public Manager_AccountFragments manager_accountFragments;

    //FUNCTIONAL
    private final Context context;
    private final android.view.View     View;
    private final FragmentManager fragmentManager;

    public Controller_Cameriere(Context context, android.view.View view, FragmentManager fragmentManager) {
        this.context = context;
        this.View = view;
        this.fragmentManager = fragmentManager;

        constructManagerORDINI_CAMERIERE();
        constructManagerACCOUNT();
    }

    public void showMain(){
        showORDINI_CAMERIERE();
    }


    //Costruttori Managers
    private void constructManagerORDINI_CAMERIERE(){
        manager_ordini_cameriere = new Manager_Ordini_Cameriere(
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
    public void showORDINI_CAMERIERE(){
        manager_ordini_cameriere.showMain();
    }
    public void showACCOUNT(){manager_accountFragments.showMain();}
}
