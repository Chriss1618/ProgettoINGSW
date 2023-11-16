package com.ratatouille.Controllers.SubControllers;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsAccountInfo;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditIngredient;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListInventory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListProducts;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewIngredient;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewProduct;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsOrdini;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsStaff;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsViewHandler;
import com.ratatouille.Models.Events.Action.Action;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManagerActionFactory {
    //SYSTEM
    private static final String TAG = "ManagerActionFactory";

    //FUNCTIONAL
    private static final Map<Integer, Class<? extends ActionsViewHandler>> IndexActionMap = new HashMap<>();
    static {
        //LOGIN
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_WELCOME,     ActionsLogin.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_LOGIN,       ActionsLogin.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_CONFIRM,     ActionsLogin.class);
        //MENU
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_MENU_LIST_CATEGORY,    ActionsListCategory.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_MENU_LIST_PRODUCTS,    ActionsListProducts.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_MENU_NEW_PRODUCT,      ActionsNewProduct.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_MENU_INFO_PRODUCT,     ActionsInfoEditProduct.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_MENU_EDIT_PRODUCT,     ActionsInfoEditProduct.class);
        //ACCOUNT
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ACCOUNT_INFO,          ActionsAccountInfo.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ACCOUNT_EDIT,          ActionsAccountInfo.class);

        //STATS
//        classMap.put(ControlMapper.INDEX_STATS_PRODUCTIVITY,  MenuViewFactory.class);
        //INVENTORY
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_INVENTORY_LIST,    ActionsListInventory.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_INVENTORY_NEW,     ActionsNewIngredient.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_INVENTORY_INFO, ActionsInfoEditIngredient.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_INVENTORY_EDIT, ActionsInfoEditIngredient.class);

        //STAFF
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_STAFF_LIST, ActionsStaff.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_STAFF_NEW, ActionsStaff.class);

        //MENU WAITER
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_TABLE,   ActionsMenuWaiter.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_INFO_TABLE,   ActionsMenuWaiter.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_CAT,     ActionsMenuWaiter.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_CAMERIERE_LIST_PRODUCT, ActionsMenuWaiter.class);

        //CHEF
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_LIST, ActionsOrdini.class);
        IndexActionMap.put(ControlMapper.IndexViewMapper.INDEX_ORDINI_TABLE, ActionsOrdini.class);

    }

    public void MapAction(Action action){
        try{
             Objects.requireNonNull( IndexActionMap.get(action.getSourceInfo().getIndex_TypeView()) )
                    .getConstructor()
                    .newInstance().handleAction( action );
        }catch (InvocationTargetException | NoSuchMethodException e ) {
            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        } catch (IllegalAccessException | InstantiationException e) {//No public constructor con Signature specificata per il tipo di View
            Log.e(TAG, "handleAction: ", e);
            throw new RuntimeException("Constructor Not Found : \n"+e);
        }
    }
}
