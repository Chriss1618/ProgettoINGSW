package com.ratatouille.Controllers.SubControllers;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListProducts;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewProduct;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsViewHandler;
import com.ratatouille.Models.Events.Action.Action;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManagerActionFactory {
    private static final String TAG = "ManagerActionFactory";

    private static final Map<Integer, Class<? extends ActionsViewHandler>> classMap = new HashMap<>();
    static {
        //MENU
        classMap.put(ControlMapper.INDEX_MENU_LIST_CATEGORY,  ActionsListCategory.class);
        classMap.put(ControlMapper.INDEX_MENU_LIST_PRODUCTS,  ActionsListProducts.class);
        classMap.put(ControlMapper.INDEX_MENU_NEW_PRODUCT,  ActionsNewProduct.class);
//        classMap.put(ControlMapper.INDEX_MENU_NEW_PRODUCT,  MenuViewFactory.class);
//        classMap.put(ControlMapper.INDEX_MENU_EDIT_PRODUCT,  MenuViewFactory.class);

        //STATS
//        classMap.put(ControlMapper.INDEX_STATS_PRODUCTIVITY,  MenuViewFactory.class);

    }

    public void handleAction(Action action){
        try{
             Objects.requireNonNull(classMap.get(action.getSourceInfo().getIndex_TypeView()))
                    .getConstructor()
                    .newInstance().handleAction(action);
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View
            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        } catch (IllegalAccessException | InstantiationException e) {
            Log.e(TAG, "handleAction: ", e);
            throw new RuntimeException("Constructor Not Found : \n"+e);
        }
    }
}
