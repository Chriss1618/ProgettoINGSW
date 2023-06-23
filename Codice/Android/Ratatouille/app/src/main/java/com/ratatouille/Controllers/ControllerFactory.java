package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.Controller;

public class ControllerFactory {
    public static final int CONTROLLER_AMMINISTRATORE   = 0;
    public static final int CONTROLLER_SUPERVISORE      = 1;
    public static final int CONTROLLER_CHEF             = 2;
    public static final int CONTROLLER_CAMERIERE        = 3;

    public static Controller createController(int typeController, Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) throws IllegalAccessException, InstantiationException {
        switch (typeController){
            case CONTROLLER_AMMINISTRATORE:
                return new Controller_Amministratore(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            case CONTROLLER_SUPERVISORE:
                return new Controller_Supervisore(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            case CONTROLLER_CHEF:
                return new Controller_Chef(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            case CONTROLLER_CAMERIERE:
                return new Controller_Cameriere(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            default: throw new IllegalArgumentException("Invalid product type.");
        }
    }
}