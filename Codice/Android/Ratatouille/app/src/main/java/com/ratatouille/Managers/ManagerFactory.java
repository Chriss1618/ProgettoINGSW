package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.SubController;

public class ManagerFactory {
    public final static int MANAGER_STATS     = 0;
    public final static int MANAGER_STAFF     = 1;
    public final static int MANAGER_MENU      = 2;
    public final static int MANAGER_ACCOUNT   = 3;

    public static SubController createSubController(int typeController, Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener){
        switch (typeController){
            case MANAGER_STATS:
                return new Manager_StatsFragments(
                        context,
                        view,
                        fragmentManager
                    );
            case MANAGER_STAFF:
                return new Manager_StaffFragments(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            case MANAGER_MENU:
                return new Manager_MenuFragments(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            case MANAGER_ACCOUNT:
                return new Manager_AccountFragments(
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener
                );
            default: throw new IllegalArgumentException("Invalid product type.");
        }
    }

}
