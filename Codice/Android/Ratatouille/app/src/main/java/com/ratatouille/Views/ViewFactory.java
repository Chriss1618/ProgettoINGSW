package com.ratatouille.Views;

import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Interfaces.IViewFactory;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Views.Schermate.Menu.MenuViewFactory;
import com.ratatouille.Views.Schermate.Stats.StatsViewFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewFactory {
    private static final String TAG = "ViewFactory";

    private static final Map<Integer, Class<? extends IViewFactory>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_MENU,  MenuViewFactory.class);
        classMap.put(ControlMapper.INDEX_TYPE_MANAGER_STATS,  StatsViewFactory.class);
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
