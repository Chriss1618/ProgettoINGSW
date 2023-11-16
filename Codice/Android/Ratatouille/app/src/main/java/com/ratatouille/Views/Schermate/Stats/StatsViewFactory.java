package com.ratatouille.Views.Schermate.Stats;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatsViewFactory implements IViewFactory {
    private static final String TAG = "StatsViewFactory";

    private static final Map<Integer, Class<? extends IViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.IndexViewMapper.INDEX_STATS_PRODUCTIVITY,  Fragment_Stats.class);
    }

    public IViewLayout createView(int typeView, Manager manager)throws IllegalAccessException, InstantiationException{
        try{
            return Objects.requireNonNull(classMap.get(typeView))
                    .getConstructor(Manager.class,int.class)
                    .newInstance(manager,2);
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View
            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        }
    }
}