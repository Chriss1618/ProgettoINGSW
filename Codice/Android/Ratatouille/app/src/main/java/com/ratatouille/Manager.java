package com.ratatouille;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.IViewFactory;
import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Managers.ManagersAction.ManagerAction_Menu;
import com.ratatouille.Models.Action;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.Schermate.Menu.MenuViewFactory;
import com.ratatouille.Schermate.ViewFactory;

import java.util.ArrayList;
import java.util.Objects;

public class Manager implements SubController {

    //SYSTEM
    private static final String TAG = "Manager_MenuFragments";
    public int[] LIST_INDEX_VIEW = {
    };
    public int MAIN = 0;
    int typeManager;
    //LAYOUT
    protected final Context               context;
    protected final ArrayList<ViewLayout> Views;
    protected final View                  View;

    //FUNCTIONAL
    protected final BottomBarListener     bottomBarListener;
    protected final FragmentManager       fragmentManager;
    protected final ManagerAction_Menu    ManagerAction;
    public int    onMain;
    public int    from;

    //DATA
    protected ArrayList<CategoriaMenu> ListCategory;

    public Manager(int typeManager,Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Log.d(TAG, "Manager: Costruttore");
        Views = new ArrayList<>();
        this.typeManager = typeManager;
        this.ManagerAction = new ManagerAction_Menu();

        this.context                = context;
        this.View                   = view;
        this.fragmentManager        = fragmentManager;
        this.bottomBarListener      = bottomBarListener;


        LIST_INDEX_VIEW = ControlMapper.classManagerToView.get(typeManager);

        addViews();

        Log.d(TAG, "Manager: FINE Costruttore");
    }

    private void addViews(){
        for (int indexView : LIST_INDEX_VIEW)
            try{ Views.add( new ViewFactory().createView(typeManager,indexView,this)); }
            catch (IllegalAccessException | InstantiationException e) { Log.e(TAG, "Manager_MenuFragments: ", e); }
    }

    //ShowPages
    private void loadFragmentAsMain(int Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(MAIN), String.valueOf(Tag))
                .setReorderingAllowed(true)
                .commit();
    }
    private void loadFragmentAsNormal(int Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(onMain), String.valueOf(Tag))
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showMain(){
        onMain = MAIN;
        showView(MAIN,null);
    }

    public void HandleAction(Action action){
        ManagerAction.handleAction(action);
    }

    @Override
    public void changeOnMain(int indexMain, String msg) {
        closeView();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        showView(indexMain,msg),
                300);
    }

    @Override
    public void closeView() {
        from = onMain;
        Views.get(onMain).EndAnimations();
        onMain =  Objects.requireNonNull(MenuViewFactory.previousIndexMapMenu.getOrDefault(onMain,-1));
    }

    public void showView(int indexFragment, String msg){
        from = onMain;
        onMain = indexFragment;
        Bundle arguments = new Bundle();
        arguments.putString("stringToPass", msg);
        ((Fragment) Views.get(indexFragment) ).setArguments(arguments);

        if( indexFragment == MAIN ) loadFragmentAsMain( LIST_INDEX_VIEW[indexFragment] );
        else loadFragmentAsNormal( LIST_INDEX_VIEW[indexFragment] );
    }

    //FUNCTIONAL
    public void hideBottomBar(){
        bottomBarListener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBar();
    }

    //ANIMATIONS
    public void callEndAnimationOfFragment(){
        from = onMain;
        ViewLayout View = (ViewLayout)fragmentManager.findFragmentById(LIST_INDEX_VIEW[onMain]);
        Objects.requireNonNull(View).EndAnimations();

        onMain =  Objects.requireNonNull(MenuViewFactory.previousIndexMapMenu.getOrDefault(onMain,-1));
    }


}
