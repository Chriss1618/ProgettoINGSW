package com.ratatouille.Schermate.Menu;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Interfaces.SubController;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.Managers.Manager_StatsFragments;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuViewFactory {
    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;
    private static final Map<Integer, Class<? extends ViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(INDEX_MENU_LIST_CATEGORY,  Fragment_ListCategory.class);
        classMap.put(INDEX_MENU_LIST_PRODUCTS,  Fragment_ListProducts.class);
        classMap.put(INDEX_MENU_INFO_PRODUCT,   Fragment_InfoProduct.class);
        classMap.put(INDEX_MENU_NEW_PRODUCT,    Fragment_NewProduct.class);
        classMap.put(INDEX_MENU_EDIT_PRODUCT,   Fragment_EditProduct.class);
    }

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
        previousIndexMapMenu.put(INDEX_MENU_LIST_PRODUCTS, INDEX_MENU_LIST_CATEGORY);
        previousIndexMapMenu.put(INDEX_MENU_INFO_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(INDEX_MENU_NEW_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(INDEX_MENU_EDIT_PRODUCT, INDEX_MENU_INFO_PRODUCT);
    }

    public static ViewLayout createView(int typeView, Manager_MenuFragments managerMenuFragments)throws IllegalAccessException, InstantiationException{
        try{
            return Objects.requireNonNull(classMap.get(typeView))
                    .getConstructor(Manager_MenuFragments.class)
                    .newInstance(managerMenuFragments);
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View
            throw new IllegalArgumentException("Invalid View type.");
        }

    }
}
