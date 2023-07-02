package com.ratatouille.Controllers;

import java.util.HashMap;
import java.util.Map;

public class ControlMapper {

    //LIST
    // Controllers
    public static final int INDEX_TYPE_CONTROLLER_AMMINISTRATORE   = 0;
    public static final int INDEX_TYPE_CONTROLLER_SUPERVISORE      = 1;
    public static final int INDEX_TYPE_CONTROLLER_CHEF             = 2;
    public static final int INDEX_TYPE_CONTROLLER_CAMERIERE        = 3;

    //Managers
    public final static int INDEX_TYPE_MANAGER_STATS   = 0;
    public final static int INDEX_TYPE_MANAGER_STAFF   = 1;
    public final static int INDEX_TYPE_MANAGER_MENU    = 2;
    public final static int INDEX_TYPE_MANAGER_ACCOUNT = 3;

    // Views
    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    public static final int INDEX_STATS_PRODUCTIVITY        = 5;

    //PAGINAZIONI
    static int [] ADMINISTRATOR_INDEX = {
            INDEX_TYPE_MANAGER_STATS,
            INDEX_TYPE_MANAGER_STAFF,
            INDEX_TYPE_MANAGER_MENU,
            INDEX_TYPE_MANAGER_ACCOUNT
    };
    static int [] MENU_INDEX = {
            INDEX_MENU_LIST_CATEGORY,
            INDEX_MENU_LIST_PRODUCTS,
            INDEX_MENU_INFO_PRODUCT,
            INDEX_MENU_NEW_PRODUCT,
            INDEX_MENU_EDIT_PRODUCT
    };

    static int [] STATS_INDEX = {
            INDEX_STATS_PRODUCTIVITY
    };

    public static final Map<Integer, int[]> classControllerToManager = new HashMap<>();
    static {
        classControllerToManager.put(INDEX_TYPE_CONTROLLER_AMMINISTRATORE, ADMINISTRATOR_INDEX);
    }

    public static final Map<Integer, int[]> classManagerToView = new HashMap<>();
    static {
        classManagerToView.put(INDEX_TYPE_MANAGER_MENU, MENU_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_STATS, STATS_INDEX);
    }
}
