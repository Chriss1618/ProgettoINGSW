package com.ratatouille.Controllers;

import android.content.Context;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.Controller;
import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Managers.ManagerFactory;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

import java.util.ArrayList;

public class Controller_Amministratore implements Controller {
    private static final String TAG = "Controller_Amministratore";
    //SYSTEM
    public final static int AMMINISTRATORE_INDEX_STATS     = 0;
    public final static int AMMINISTRATORE_INDEX_STAFF     = 1;
    public final static int AMMINISTRATORE_INDEX_MENU      = 2;
    public final static int AMMINISTRATORE_INDEX_ACCOUNT   = 3;

    static int[] LIST_INDEX_MANAGERS = {
            ManagerFactory.INDEX_TYPE_MANAGER_STATS,
            ManagerFactory.INDEX_TYPE_MANAGER_STAFF,
            ManagerFactory.INDEX_TYPE_MANAGER_MENU,
            ManagerFactory.INDEX_TYPE_MANAGER_ACCOUNT
    };

    //FUNCTIONAL
    private static final int           MAIN =  LIST_INDEX_MANAGERS[0];
    public int                         managerOnMain;
    private final FragmentManager      fragmentManager;

    public Manager_StaffFragments       manager_staffFragments;
    public Manager_StatsFragments       manager_statsFragments;
    public Manager_MenuFragments        manager_menuFragments;
    public Manager_AccountFragments     manager_accountFragments;
    private ArrayList<SubController>    Managers;

    //LAYOUT
    private final Context               context;
    private final View                  View;
    private final BottomBarListener     bottomBarListener;

    public Controller_Amministratore(Context context, View view, FragmentManager fragmentManager,BottomBarListener bottomBarListener) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;

        Managers = new ArrayList<>();
        for (int indexManager : LIST_INDEX_MANAGERS) {
            try {
                Managers.add(ManagerFactory.createSubController(
                        indexManager,
                        context,
                        view,
                        fragmentManager,
                        bottomBarListener));
            } catch (IllegalAccessException | InstantiationException e) {
                Log.e(TAG, "Controller_Amministratore: ", e);
            }
        }

        constructManagerSTATS();
        constructManagerSTAFF();
        constructManagerMENU();
        constructManagerACCOUNT();
    }

    //Constrictor Managers
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
    @Override
    public void showMain(){
        showOnMain(MAIN);
    }

    @Override
    public void changeOnMain(int indexMain) {
        closeView();

        final Handler handler = new Handler();
        handler.postDelayed(()->{

            fragmentManager.popBackStack();
            showOnMain(indexMain);

        },300);
    }

    private void showOnMain(int indexMain){
        clearBackStackPackage();
        managerOnMain = indexMain;
        Managers.get(indexMain).showMain();
    }
    @Override
    public void closeView(){
        Managers.get(managerOnMain).closeView();

        final Handler handler = new Handler();
        handler.postDelayed(fragmentManager::popBackStack,300);
    }

    private void clearBackStackPackage(){
        for(int j  = fragmentManager.getBackStackEntryCount() ; j >0; j-- ){
            fragmentManager.popBackStack();
        }
    }
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

    //FUNCTIONAL
    public void resetMainPackage(){
        switch (managerOnMain){
            case AMMINISTRATORE_INDEX_STATS:    this.manager_statsFragments.onMain = Manager_StatsFragments.INDEX_STAT_PRODUCTIVITY;
                break;
            case AMMINISTRATORE_INDEX_STAFF:    this.manager_staffFragments.onMain = Manager_StaffFragments.INDEX_STAFF_LIST;
                break;
            case AMMINISTRATORE_INDEX_MENU:     this.manager_menuFragments.onMain = Manager_MenuFragments.MAIN;
                break;
            case AMMINISTRATORE_INDEX_ACCOUNT:  this.manager_accountFragments.onMain = Manager_AccountFragments.INDEX_ACCOUNT_INFO;
                break;
        }
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){

        switch (managerOnMain){
            case AMMINISTRATORE_INDEX_STATS:
                manager_statsFragments.callEndAnimationOfFragment();
                break;
            case AMMINISTRATORE_INDEX_STAFF:
                manager_staffFragments.callEndAnimationOfFragment();
                break;
            case AMMINISTRATORE_INDEX_MENU:
                //manager_menuFragments.callEndAnimationOfFragment();
                break;
            case AMMINISTRATORE_INDEX_ACCOUNT:
                manager_accountFragments.callEndAnimationOfFragment();
                break;
        }
    }

}
