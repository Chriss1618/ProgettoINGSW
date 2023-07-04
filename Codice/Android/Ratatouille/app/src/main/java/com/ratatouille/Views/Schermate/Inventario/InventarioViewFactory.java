package com.ratatouille.Views.Schermate.Inventario;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.ViewLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventarioViewFactory implements IViewFactory {
    private static final String TAG = "InventarioViewFactory";
    private static final Map<Integer, Class<? extends ViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_INVENTORY_LIST,    Fragment_ListInventary.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_NEW,     Fragment_NewProductInventory.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_INFO,    Fragment_InfoProductInventory.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_EDIT,    Fragment_EditProductInventory.class);
    }

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
        previousIndexMapMenu.put(ControlMapper.INDEX_INVENTORY_NEW, ControlMapper.INDEX_INVENTORY_LIST);
        previousIndexMapMenu.put(ControlMapper.INDEX_INVENTORY_INFO, ControlMapper.INDEX_INVENTORY_LIST);
        previousIndexMapMenu.put(ControlMapper.INDEX_INVENTORY_EDIT, ControlMapper.INDEX_INVENTORY_INFO);
    }

    public ViewLayout createView(int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException{
        try{
            return Objects.requireNonNull(classMap.get(typeView))
                    .getConstructor(Manager.class,int.class)
                    .newInstance(managerMenuFragments,2);
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View

            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        }
    }
}
