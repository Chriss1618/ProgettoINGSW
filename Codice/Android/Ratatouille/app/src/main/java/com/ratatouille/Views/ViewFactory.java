package com.ratatouille.Views;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Views.Schermate.Account.AccountViewFactory;
import com.ratatouille.Views.Schermate.Inventario.InventarioViewFactory;
import com.ratatouille.Views.Schermate.Login.Fragment.LoginViewFactory;
import com.ratatouille.Views.Schermate.Menu.MenuViewFactory;
import com.ratatouille.Views.Schermate.Ordini.OrdiniViewFactory;
import com.ratatouille.Views.Schermate.OrdiniCameriere.OrdiniCameriereViewFactory;
import com.ratatouille.Views.Schermate.Staff.StaffViewFactory;
import com.ratatouille.Views.Schermate.Stats.StatsViewFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewFactory {
    private static final String TAG = "ViewFactory";

    private static final Map<Integer, Class<? extends IViewFactory>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_LOGIN,            LoginViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_MENU,             MenuViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_STATS,            StatsViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_STAFF,            StaffViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_ACCOUNT,          AccountViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_INVENTORY,        InventarioViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_ORDINI,           OrdiniViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_ORDINI_CAMERIERE, OrdiniCameriereViewFactory.class);
    }

    public static final Map<Integer, Integer> previousIndexMapLogin;
    static {
        previousIndexMapLogin = new HashMap<>();
        previousIndexMapLogin.put(ControlMapper.INDEX_LOGIN_LOGIN, ControlMapper.INDEX_LOGIN_WELCOME);
        previousIndexMapLogin.put(ControlMapper.INDEX_LOGIN_CONFIRM, ControlMapper.INDEX_LOGIN_LOGIN);
    }

    public static final Map<Integer, Integer> previousIndexMapInventory;
    static {
        previousIndexMapInventory = new HashMap<>();
        previousIndexMapInventory.put(ControlMapper.INDEX_INVENTORY_NEW, ControlMapper.INDEX_INVENTORY_LIST);
        previousIndexMapInventory.put(ControlMapper.INDEX_INVENTORY_INFO, ControlMapper.INDEX_INVENTORY_LIST);
        previousIndexMapInventory.put(ControlMapper.INDEX_INVENTORY_EDIT, ControlMapper.INDEX_INVENTORY_INFO);
    }

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
        previousIndexMapMenu.put(ControlMapper.INDEX_MENU_LIST_PRODUCTS, ControlMapper.INDEX_MENU_LIST_CATEGORY);
        previousIndexMapMenu.put(ControlMapper.INDEX_MENU_INFO_PRODUCT, ControlMapper.INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(ControlMapper.INDEX_MENU_NEW_PRODUCT, ControlMapper.INDEX_MENU_LIST_PRODUCTS);
        previousIndexMapMenu.put(ControlMapper.INDEX_MENU_EDIT_PRODUCT, ControlMapper.INDEX_MENU_INFO_PRODUCT);
    }

    public static final Map<Integer, Map<Integer, Integer>> PreviousIndexMapper ;
    static {
        PreviousIndexMapper = new HashMap<>();
        PreviousIndexMapper.put(ControlMapper.INDEX_TYPE_MANAGER_LOGIN,         previousIndexMapLogin);
        PreviousIndexMapper.put(ControlMapper.INDEX_TYPE_MANAGER_MENU,         previousIndexMapMenu);
        PreviousIndexMapper.put(ControlMapper.INDEX_TYPE_MANAGER_STATS,        previousIndexMapInventory);
    }


    public ViewLayout createView(int typeManager,int typeView, Manager manager)throws IllegalAccessException, InstantiationException{
        try{
            return Objects.requireNonNull(classMap.get(typeManager))
                    .getConstructor()
                    .newInstance().createView(typeView,manager);
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View
            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        }
    }


}
