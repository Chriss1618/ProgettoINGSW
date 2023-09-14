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
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsStaff;
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
        //LOGIN
        classMap.put(ControlMapper.INDEX_LOGIN_WELCOME,     ActionsLogin.class);
        classMap.put(ControlMapper.INDEX_LOGIN_LOGIN,       ActionsLogin.class);
        classMap.put(ControlMapper.INDEX_LOGIN_CONFIRM,     ActionsLogin.class);
        //MENU
        classMap.put(ControlMapper.INDEX_MENU_LIST_CATEGORY,    ActionsListCategory.class);
        classMap.put(ControlMapper.INDEX_MENU_LIST_PRODUCTS,    ActionsListProducts.class);
        classMap.put(ControlMapper.INDEX_MENU_NEW_PRODUCT,      ActionsNewProduct.class);
        classMap.put(ControlMapper.INDEX_MENU_INFO_PRODUCT,     ActionsInfoEditProduct.class);
        classMap.put(ControlMapper.INDEX_MENU_EDIT_PRODUCT,     ActionsInfoEditProduct.class);
        //ACCOUNT
        classMap.put(ControlMapper.INDEX_ACCOUNT_INFO,          ActionsAccountInfo.class);
        classMap.put(ControlMapper.INDEX_ACCOUNT_EDIT,          ActionsAccountInfo.class);

        //STATS
//        classMap.put(ControlMapper.INDEX_STATS_PRODUCTIVITY,  MenuViewFactory.class);
        //INVENTORY
        classMap.put(ControlMapper.INDEX_INVENTORY_LIST,    ActionsListInventory.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_NEW,     ActionsNewIngredient.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_INFO, ActionsInfoEditIngredient.class);
        classMap.put(ControlMapper.INDEX_INVENTORY_EDIT, ActionsInfoEditIngredient.class);
        //STAFF
        classMap.put(ControlMapper.INDEX_STAFF_LIST, ActionsStaff.class);
        classMap.put(ControlMapper.INDEX_STAFF_NEW, ActionsStaff.class);

        //MENUWAITER
        classMap.put(ControlMapper.INDEX_ORDINI_CAMERIERE_LIST_TABLE, ActionsMenuWaiter.class);

    }

    public void handleAction(Action action){
        try{
             Objects.requireNonNull( classMap.get(action.getSourceInfo().getIndex_TypeView()) )
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
