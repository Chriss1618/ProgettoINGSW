package com.ratatouille.Schermate.Stats;

import android.util.Log;

import com.ratatouille.ControlMapper;
import com.ratatouille.Interfaces.IViewFactory;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Manager;
import com.ratatouille.Schermate.Menu.Fragment_EditProduct;
import com.ratatouille.Schermate.Menu.Fragment_InfoProduct;
import com.ratatouille.Schermate.Menu.Fragment_ListCategory;
import com.ratatouille.Schermate.Menu.Fragment_ListProducts;
import com.ratatouille.Schermate.Menu.Fragment_NewProduct;
import com.ratatouille.Schermate.Stats.Fragment_Stats;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatsViewFactory implements IViewFactory {
    private static final String TAG = "StatsViewFactory";

    private static final Map<Integer, Class<? extends ViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_STATS_PRODUCTIVITY,  Fragment_Stats.class);
    }

    public static final Map<Integer, Integer> previousIndexMapMenu;
    static {
        previousIndexMapMenu = new HashMap<>();
    }

    public ViewLayout createView( int typeView, Manager managerMenuFragments)throws IllegalAccessException, InstantiationException{
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
