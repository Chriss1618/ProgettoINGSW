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
    public final static int INDEX_TYPE_MANAGER_STATS            = 0;
    public final static int INDEX_TYPE_MANAGER_STAFF            = 1;
    public final static int INDEX_TYPE_MANAGER_MENU             = 2;
    public final static int INDEX_TYPE_MANAGER_ACCOUNT          = 3;
    public final static int INDEX_TYPE_MANAGER_INVENTORY        = 4;
    public final static int INDEX_TYPE_MANAGER_ORDINI           = 5;
    public final static int INDEX_TYPE_MANAGER_ORDINI_CAMERIERE = 6;

    // Views
    //MENU
    public static final int INDEX_MENU_LIST_CATEGORY       = 0;
    public static final int INDEX_MENU_LIST_PRODUCTS       = 1;
    public static final int INDEX_MENU_INFO_PRODUCT        = 2;
    public static final int INDEX_MENU_NEW_PRODUCT         = 3;
    public static final int INDEX_MENU_EDIT_PRODUCT        = 4;

    //STATS
    public static final int INDEX_STATS_PRODUCTIVITY        = 5;

    //ACCOUNT
    public static final int INDEX_ACCOUNT_INFO              = 6;
    public static final int INDEX_ACCOUNT_EDIT              = 7;

    //STAFF
    public static final int INDEX_STAFF_LIST                = 8;
    public static final int INDEX_STAFF_NEW                 = 9;

    //INVENTORY
    public static final int INDEX_INVENTORY_LIST            = 10;
    public static final int INDEX_INVENTORY_NEW             = 11;
    public static final int INDEX_INVENTORY_INFO            = 12;
    public static final int INDEX_INVENTORY_EDIT            = 13;

    //ORDINI
    public static final int INDEX_ORDINI_LIST               = 14;
    public static final int INDEX_ORDINI_TABLE              = 15;
    public static final int INDEX_ORDINI_HISTORY            = 16;

    //ORDINI CAMERIERE
    public static final int INDEX_ORDINI_CAMERIERE_LIST_CAT            = 17;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_TABLE          = 18;
    public static final int INDEX_ORDINI_CAMERIERE_LIST_PRODUCT        = 19;
    public static final int INDEX_ORDINI_CAMERIERE_INFO_PRODUCT        = 20;
    public static final int INDEX_ORDINI_CAMERIERE_INFO_TABLE          = 21;
    public static final int INDEX_ORDINI_CAMERIERE_INFO_REPORT_ORDER   = 22;

    //PAGINATION
    static int [] ADMINISTRATOR_INDEX = {
            INDEX_TYPE_MANAGER_STATS,
            INDEX_TYPE_MANAGER_STAFF,
            INDEX_TYPE_MANAGER_MENU,
            INDEX_TYPE_MANAGER_ACCOUNT
    };
    static int [] SUPERVISORE_INDEX = {
            INDEX_TYPE_MANAGER_INVENTORY,
            INDEX_TYPE_MANAGER_MENU,
            INDEX_TYPE_MANAGER_ACCOUNT
    };
    static int [] CHEF_INDEX = {
            INDEX_TYPE_MANAGER_INVENTORY,
            INDEX_TYPE_MANAGER_ORDINI,
            INDEX_TYPE_MANAGER_MENU,
            INDEX_TYPE_MANAGER_ACCOUNT
    };
    static int [] CAMERIERE_INDEX = {
            INDEX_TYPE_MANAGER_ORDINI_CAMERIERE,
            INDEX_TYPE_MANAGER_ACCOUNT
    };

    //VIEW_PER_PAGINATION
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

    static int [] STAFF_INDEX = {
            INDEX_STAFF_LIST,
            INDEX_STAFF_NEW
    };

    static int [] ACCOUNT_INDEX = {
            INDEX_ACCOUNT_INFO,
            INDEX_ACCOUNT_EDIT
    };

    static int [] INVENTORY_INDEX = {
            INDEX_INVENTORY_LIST,
            INDEX_INVENTORY_NEW,
            INDEX_INVENTORY_INFO,
            INDEX_INVENTORY_EDIT
    };
    static int [] ORDINI_INDEX = {
            INDEX_ORDINI_LIST,
            INDEX_ORDINI_TABLE,
            INDEX_ORDINI_HISTORY
    };
    static int [] ORDINI_CAMERIERE_INDEX = {
            INDEX_ORDINI_CAMERIERE_LIST_CAT,
            INDEX_ORDINI_CAMERIERE_LIST_TABLE,
            INDEX_ORDINI_CAMERIERE_LIST_PRODUCT,
            INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,
            INDEX_ORDINI_CAMERIERE_INFO_TABLE,
            INDEX_ORDINI_CAMERIERE_INFO_REPORT_ORDER
    };

    public static final Map<Integer, int[]> classControllerToManager = new HashMap<>();
    static {
        classControllerToManager.put(INDEX_TYPE_CONTROLLER_AMMINISTRATORE, ADMINISTRATOR_INDEX);
        classControllerToManager.put(INDEX_TYPE_CONTROLLER_SUPERVISORE, SUPERVISORE_INDEX);
        classControllerToManager.put(INDEX_TYPE_CONTROLLER_CHEF, CHEF_INDEX);
        classControllerToManager.put(INDEX_TYPE_CONTROLLER_CAMERIERE, CAMERIERE_INDEX);
    }

    public static final Map<Integer, int[]> classManagerToView = new HashMap<>();
    static {
        classManagerToView.put(INDEX_TYPE_MANAGER_MENU, MENU_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_STAFF, STAFF_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_STATS, STATS_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_ACCOUNT, ACCOUNT_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_INVENTORY, INVENTORY_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_ORDINI, ORDINI_INDEX);
        classManagerToView.put(INDEX_TYPE_MANAGER_ORDINI_CAMERIERE, ORDINI_CAMERIERE_INDEX);
    }
}
