package com.ratatouille.Views.Schermate.Ordini;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Interfaces.IViewFactory;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Views.Schermate.Menu.Fragment_EditProduct;
import com.ratatouille.Views.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Views.Schermate.Menu.Fragment_ListCategory;
import com.ratatouille.Views.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Views.Schermate.Menu.Fragment_NewProduct;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrdiniViewFactory implements IViewFactory {
    private static final String TAG = "OrdiniViewFactory";

    private static final Map<Integer, Class<? extends ViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_ORDINI_LIST,   Fragment_ListOrders.class);
        classMap.put(ControlMapper.INDEX_ORDINI_TABLE,  Fragment_TableOrders.class);
        classMap.put(ControlMapper.INDEX_ORDINI_HISTORY,Fragment_HystoryOrders.class);
    }

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
        previousIndexMapMenu.put(ControlMapper.INDEX_ORDINI_LIST, ControlMapper.INDEX_ORDINI_TABLE);
        previousIndexMapMenu.put(ControlMapper.INDEX_ORDINI_HISTORY, ControlMapper.INDEX_ORDINI_TABLE);
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
