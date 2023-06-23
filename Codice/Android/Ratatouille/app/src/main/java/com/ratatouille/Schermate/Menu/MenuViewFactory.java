package com.ratatouille.Schermate.Menu;

import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_MenuFragments;

import java.util.HashMap;
import java.util.Map;

public class MenuViewFactory {
    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
        previousIndexMapMenu.put(INDEX_MENU_LIST_PRODUCTS, INDEX_MENU_LIST_CATEGORY);
        previousIndexMapMenu.put(INDEX_MENU_INFO_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(INDEX_MENU_NEW_PRODUCT, INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(INDEX_MENU_EDIT_PRODUCT, INDEX_MENU_INFO_PRODUCT);
    }

    public static ViewLayout createView(int typeView, Manager_MenuFragments managerMenuFragments){
        switch (typeView){
            case INDEX_MENU_LIST_CATEGORY:
                return new Fragment_ListCategory(managerMenuFragments);
            case INDEX_MENU_LIST_PRODUCTS:
                return new Fragment_ListProducts(managerMenuFragments);
            case INDEX_MENU_INFO_PRODUCT:
                return new Fragment_InfoProduct(managerMenuFragments);
            case INDEX_MENU_NEW_PRODUCT:
                return new Fragment_NewProduct(managerMenuFragments);
            case INDEX_MENU_EDIT_PRODUCT:
                return new Fragment_EditProduct(managerMenuFragments);
            default: throw new IllegalArgumentException("Invalid View type.");
        }
    }
}
