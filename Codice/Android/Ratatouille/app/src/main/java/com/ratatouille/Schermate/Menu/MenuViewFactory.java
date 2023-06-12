package com.ratatouille.Schermate.Menu;

import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_MenuFragments;

public class MenuViewFactory {
    public static final int MENU_LIST_CATEGORY       = 0;
    public static final int MENU_LIST_PRODUCTS       = 1;
    public static final int MENU_INFO_PRODUCT        = 2;
    public static final int MENU_NEW_PRODUCT         = 3;
    public static final int MENU_EDIT_PRODUCT        = 4;

    public static ViewLayout createView(int typeView, Manager_MenuFragments managerMenuFragments){
        switch (typeView){
            case MENU_LIST_CATEGORY:
                return new Fragment_ListCategory(managerMenuFragments);
            case MENU_LIST_PRODUCTS:
                return new Fragment_ListProducts(managerMenuFragments);
            case MENU_INFO_PRODUCT:
                return new Fragment_InfoProduct(managerMenuFragments);
            case MENU_NEW_PRODUCT:
                return new Fragment_NewProduct(managerMenuFragments);
            case MENU_EDIT_PRODUCT:
                return new Fragment_EditProduct(managerMenuFragments);
            default: throw new IllegalArgumentException("Invalid View type.");
        }
    }
}
