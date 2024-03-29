package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.IViewLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrdiniCameriereViewFactory implements IViewFactory {
    private static final String TAG = "OrdiniCameriereViewFact";

    private static final Map<Integer, Class<? extends IViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT,         Fragment_ListCategoryCameriere.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_TABLE,       Fragment_ListTables.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_PRODUCT,     Fragment_ListProductsCameriere.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,     Fragment_InfoProductCameriere.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_TABLE,       Fragment_TableInfo.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_REPORT_ORDER,Fragment_ReportOrder.class);
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
