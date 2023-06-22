package com.ratatouille.Managers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.Schermate.Menu.MenuViewFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Manager_MenuFragments implements SubController {
    //SYSTEM
    private static final String TAG = "Manager_MenuFragments";


    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    public static final int MAIN = INDEX_MENU_LIST_CATEGORY;

    private static final Map<Integer, Integer> previousIndexMap;

    int[] TAG_VIEW = {MenuViewFactory.MENU_LIST_CATEGORY, MenuViewFactory.MENU_LIST_PRODUCTS,MenuViewFactory.MENU_INFO_PRODUCT, MenuViewFactory.MENU_NEW_PRODUCT, MenuViewFactory.MENU_EDIT_PRODUCT};

    String[] TAG_MENU = {"listCategory", "listProducts", "infoProduct", "newProduct", "editProduct"};

    static {
        previousIndexMap = new HashMap<>();
        previousIndexMap.put(INDEX_MENU_LIST_PRODUCTS, INDEX_MENU_LIST_CATEGORY);
        previousIndexMap.put(INDEX_MENU_INFO_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMap.put(INDEX_MENU_NEW_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMap.put(INDEX_MENU_EDIT_PRODUCT, INDEX_MENU_INFO_PRODUCT);
    }

    //LAYOUT
    private final Context               context;
    private final ArrayList<ViewLayout> Views;
    private final View                  View;

    //FUNCTIONAL
    private BottomBarListener       bottomBarListener;
    private final FragmentManager   fragmentManager;
    public int                      onMain;
    public int                      from;

    //DATA
    private ArrayList<CategoriaMenu> ListCategory;

    public Manager_MenuFragments(Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Views = new ArrayList<>();
        this.context                = context;
        this.View                   = view;
        this.fragmentManager        = fragmentManager;
        this.bottomBarListener      = bottomBarListener;

        addFragments();
    }

    private void addFragments(){
        for (int indexView : TAG_VIEW) {
            Views.add( MenuViewFactory.createView(indexView,this));
        }
    }
    //ShowPages
    public void loadFragmentAsMain(String Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(MAIN), Tag)
                .setReorderingAllowed(true)
                .commit();
    }
    public void loadFragmentAsNormal(String Tag){
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(onMain),Tag)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showMain(){
        onMain = MAIN;
        showFragment(MAIN,null);
    }

    public void getInputAction(String action){
        switch (action){

        }
    }



    @Override
    public void changeOnMain(int indexMain, String msg) {
        closeView();
        showFragment(indexMain,msg);
    }

    @Override
    public void closeView() {
        from = onMain;
        Views.get(onMain).EndAnimations();
        onMain =  Objects.requireNonNull(previousIndexMap.getOrDefault(onMain,-1));
    }

    public void showFragment(int indexFragment,String msg){
        from = onMain;
        onMain = indexFragment;
        Bundle arguments = new Bundle();
        arguments.putString("stringToPass", msg);
        ((Fragment) Views.get(indexFragment) ).setArguments(arguments);

        if( indexFragment == MAIN ) loadFragmentAsMain( TAG_MENU[indexFragment] );
        else loadFragmentAsNormal( TAG_MENU[indexFragment] );

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
        ViewLayout View = (ViewLayout)fragmentManager.findFragmentByTag(TAG_MENU[onMain]);
        Objects.requireNonNull(View).EndAnimations();

        onMain =  Objects.requireNonNull(previousIndexMap.getOrDefault(onMain,-1));
    }

}