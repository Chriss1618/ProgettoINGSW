package com.ratatouille.Views.Schermate.Ordini;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.ViewLayout;
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

    public ViewLayout createView(int typeView, Manager manager)throws IllegalAccessException, InstantiationException{
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
