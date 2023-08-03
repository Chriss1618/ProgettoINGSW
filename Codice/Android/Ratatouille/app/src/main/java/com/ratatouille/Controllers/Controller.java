package com.ratatouille.Controllers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.IController;
import com.ratatouille.Models.Interfaces.SubController;
import com.ratatouille.Models.Listeners.BottomBarListener;
import com.ratatouille.Models.Events.SourceInfo;

import java.util.ArrayList;

public class Controller implements IController {

    private static final String TAG = "Controller_Amministratore";
    //SYSTEM
    public final static int AMMINISTRATORE_INDEX_STATS     = 0;
    public final static int AMMINISTRATORE_INDEX_STAFF     = 1;
    public final static int AMMINISTRATORE_INDEX_MENU      = 2;
    public final static int AMMINISTRATORE_INDEX_ACCOUNT   = 3;

    //FUNCTIONAL
    public int typeController;
    static Integer[] LIST_INDEX_MANAGERS = {
    };

    private static final int            MAIN = 0;
    public int                          managerOnMain;
    private FragmentManager             fragmentManager;

    private ArrayList<SubController> Managers;

    //LAYOUT
    private Context             context;
    private android.view.View   View;
    private BottomBarListener   bottomBarListener;

    public Controller(Context context, View view, FragmentManager fragmentManager,BottomBarListener bottomBarListener , int typeController) {
        this.context            = context;
        this.View               = view;
        this.fragmentManager    = fragmentManager;
        this.bottomBarListener  = bottomBarListener;
        this.typeController     = typeController;
        Log.d(TAG, "NEW V Controller: costruttore Controller ");

        Managers = new ArrayList<>();
        LIST_INDEX_MANAGERS = ControlMapper.classControllerToManager.get(typeController);
        assert LIST_INDEX_MANAGERS != null;
        for (int indexManager : LIST_INDEX_MANAGERS ) {
            Managers.add(new Manager(
                            new SourceInfo(indexManager,typeController),
                            context,
                            view,
                            fragmentManager,
                            bottomBarListener
                    )
            );
        }

        Log.d(TAG, "FINE NEW V Controller: costruttore Controller ");
    }

    public Controller() {

    }
    public int getNumberManagers(){
        return LIST_INDEX_MANAGERS.length;
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
            showOnMain(indexMain);
        },300);
    }

    private void showOnMain(int indexMain){
        clearBackStackPackage();
        Log.d(TAG, "showOnMain: index->"+indexMain);
        managerOnMain = indexMain;
        Managers.get(managerOnMain).showMain();
    }
    @Override
    public void closeView(){
        Managers.get(managerOnMain).closeView();
    }

    private void clearBackStackPackage(){
        for(int j  = fragmentManager.getBackStackEntryCount() ; j >0; j-- ){
            fragmentManager.popBackStack();
        }
    }
}
