package com.ratatouille.Controllers;

import java.util.HashMap;
import java.util.Map;


public class ControlMapper {

    public static class TypeUserMapper{ //TYPE USERS
        //Name
        public static final String NAME_TYPE_USER_AMMINISTRATORE    = "Amministratore";
        public static final String NAME_TYPE_USER_SUPERVISORE       = "Supervisore";
        public static final String NAME_TYPE_USER_CHEF              = "Chef";
        public static final String NAME_TYPE_USER_CAMERIERE         = "Cameriere";
        //Index
        public static final int INDEX_TYPE_CONTROLLER_AMMINISTRATORE   = 0;
        public static final int INDEX_TYPE_CONTROLLER_SUPERVISORE      = 1;
        public static final int INDEX_TYPE_CONTROLLER_CHEF             = 2;
        public static final int INDEX_TYPE_CONTROLLER_CAMERIERE        = 3;
    }

    public static class IndexManagerMapper {
        //Index
        public final static int INDEX_TYPE_MANAGER_STATS            = 0;
        public final static int INDEX_TYPE_MANAGER_STAFF            = 1;
        public final static int INDEX_TYPE_MANAGER_MENU             = 2;
        public final static int INDEX_TYPE_MANAGER_ACCOUNT          = 3;
        public final static int INDEX_TYPE_MANAGER_INVENTORY        = 4;
        public final static int INDEX_TYPE_MANAGER_ORDINI           = 5;
        public final static int INDEX_TYPE_MANAGER_ORDINI_CAMERIERE = 6;
        public final static int INDEX_TYPE_MANAGER_LOGIN            = 7;
    }

    public static class IndexViewMapper{
        //LOGIN
        public static final int INDEX_LOGIN_WELCOME            = 0;
        public static final int INDEX_LOGIN_LOGIN              = 1;
        public static final int INDEX_LOGIN_CONFIRM            = 2;

        //MENU
        public static final int INDEX_MENU_LIST_CATEGORY       = 3;
        public static final int INDEX_MENU_LIST_PRODUCTS       = 4;
        public static final int INDEX_MENU_INFO_PRODUCT        = 5;
        public static final int INDEX_MENU_NEW_PRODUCT         = 6;
        public static final int INDEX_MENU_EDIT_PRODUCT        = 7;

        //STATS
        public static final int INDEX_STATS_PRODUCTIVITY        = 8;

        //ACCOUNT
        public static final int INDEX_ACCOUNT_INFO              = 9;
        public static final int INDEX_ACCOUNT_EDIT              = 10;

        //STAFF
        public static final int INDEX_STAFF_LIST                = 11;
        public static final int INDEX_STAFF_NEW                 = 12;

        //INVENTORY
        public static final int INDEX_INVENTORY_LIST            = 13;
        public static final int INDEX_INVENTORY_NEW             = 14;
        public static final int INDEX_INVENTORY_INFO            = 15;
        public static final int INDEX_INVENTORY_EDIT            = 16;

        //ORDINI
        public static final int INDEX_ORDINI_LIST               = 17;
        public static final int INDEX_ORDINI_TABLE              = 18;
        public static final int INDEX_ORDINI_HISTORY            = 19;

        //ORDINI CAMERIERE
        public static final int INDEX_ORDINI_CAMERIERE_LIST_CAT            = 20;
        public static final int INDEX_ORDINI_CAMERIERE_LIST_TABLE          = 21;
        public static final int INDEX_ORDINI_CAMERIERE_LIST_PRODUCT        = 22;
        public static final int INDEX_ORDINI_CAMERIERE_INFO_PRODUCT        = 23;
        public static final int INDEX_ORDINI_CAMERIERE_INFO_TABLE          = 24;
        public static final int INDEX_ORDINI_CAMERIERE_INFO_REPORT_ORDER   = 25;

    }

    public static class ListIndexUserTabMapper{
        //PAGINATION
        static Integer [] ADMINISTRATOR_INDEX = {
                IndexManagerMapper.INDEX_TYPE_MANAGER_STATS,
                IndexManagerMapper.INDEX_TYPE_MANAGER_STAFF,
                IndexManagerMapper.INDEX_TYPE_MANAGER_MENU,
                IndexManagerMapper.INDEX_TYPE_MANAGER_ACCOUNT
        };
        static Integer [] SUPERVISORE_INDEX = {
                IndexManagerMapper.INDEX_TYPE_MANAGER_INVENTORY,
                IndexManagerMapper.INDEX_TYPE_MANAGER_MENU,
                IndexManagerMapper.INDEX_TYPE_MANAGER_ACCOUNT
        };
        static Integer [] CHEF_INDEX = {
                IndexManagerMapper.INDEX_TYPE_MANAGER_INVENTORY,
                IndexManagerMapper.INDEX_TYPE_MANAGER_ORDINI,
                IndexManagerMapper.INDEX_TYPE_MANAGER_MENU,
                IndexManagerMapper.INDEX_TYPE_MANAGER_ACCOUNT
        };
        static Integer [] CAMERIERE_INDEX = {
                IndexManagerMapper.INDEX_TYPE_MANAGER_ORDINI_CAMERIERE,
                IndexManagerMapper.INDEX_TYPE_MANAGER_ACCOUNT
        };
    }

    public static class ListIndexTabViewTabMapper{
        //VIEW_PER_PAGINATION
        static Integer [] LOGIN_INDEX = {
                IndexViewMapper.INDEX_LOGIN_WELCOME,
                IndexViewMapper.INDEX_LOGIN_LOGIN,
                IndexViewMapper.INDEX_LOGIN_CONFIRM
        };
        static Integer [] MENU_INDEX = {
                IndexViewMapper.INDEX_MENU_LIST_CATEGORY,
                IndexViewMapper.INDEX_MENU_LIST_PRODUCTS,
                IndexViewMapper.INDEX_MENU_INFO_PRODUCT,
                IndexViewMapper.INDEX_MENU_NEW_PRODUCT,
                IndexViewMapper.INDEX_MENU_EDIT_PRODUCT
        };

        static Integer [] STATS_INDEX = {
                IndexViewMapper.INDEX_STATS_PRODUCTIVITY
        };

        static Integer [] STAFF_INDEX = {
                IndexViewMapper.INDEX_STAFF_LIST,
                IndexViewMapper.INDEX_STAFF_NEW
        };

        static Integer [] ACCOUNT_INDEX = {
                IndexViewMapper.INDEX_ACCOUNT_INFO,
                IndexViewMapper.INDEX_ACCOUNT_EDIT
        };

        static Integer [] INVENTORY_INDEX = {
                IndexViewMapper.INDEX_INVENTORY_LIST,
                IndexViewMapper.INDEX_INVENTORY_NEW,
                IndexViewMapper.INDEX_INVENTORY_INFO,
                IndexViewMapper.INDEX_INVENTORY_EDIT
        };
        static Integer [] ORDINI_INDEX = {
                IndexViewMapper.INDEX_ORDINI_LIST,
                IndexViewMapper.INDEX_ORDINI_TABLE,
                IndexViewMapper.INDEX_ORDINI_HISTORY
        };
        static Integer [] ORDINI_CAMERIERE_INDEX = {
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_TABLE,
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT,
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_TABLE,
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_PRODUCT,
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,
                IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_REPORT_ORDER
        };

    }

    public static final Map<Integer, Integer[]> classControllerToManager = new HashMap<>();
    static {
        classControllerToManager.put(TypeUserMapper.INDEX_TYPE_CONTROLLER_AMMINISTRATORE,  ListIndexUserTabMapper.ADMINISTRATOR_INDEX);
        classControllerToManager.put(TypeUserMapper.INDEX_TYPE_CONTROLLER_SUPERVISORE,     ListIndexUserTabMapper.SUPERVISORE_INDEX);
        classControllerToManager.put(TypeUserMapper.INDEX_TYPE_CONTROLLER_CHEF,            ListIndexUserTabMapper.CHEF_INDEX);
        classControllerToManager.put(TypeUserMapper.INDEX_TYPE_CONTROLLER_CAMERIERE,       ListIndexUserTabMapper.CAMERIERE_INDEX);
    }

    public static final Map<Integer, Integer[]> classManagerToView = new HashMap<>();
    static {
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_LOGIN,            ListIndexTabViewTabMapper.LOGIN_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_MENU,             ListIndexTabViewTabMapper.MENU_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_STAFF,            ListIndexTabViewTabMapper.STAFF_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_STATS,            ListIndexTabViewTabMapper.STATS_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_ACCOUNT,          ListIndexTabViewTabMapper.ACCOUNT_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_INVENTORY,        ListIndexTabViewTabMapper.INVENTORY_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_ORDINI,           ListIndexTabViewTabMapper.ORDINI_INDEX);
        classManagerToView.put(IndexManagerMapper.INDEX_TYPE_MANAGER_ORDINI_CAMERIERE, ListIndexTabViewTabMapper.ORDINI_CAMERIERE_INDEX);
    }
}
