package com.ratatouille.Views.Schermate.Menu;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuViewFactory implements IViewFactory {
    private static final String TAG = "MenuViewFactory";

    private static final Map<Integer, Class<? extends ViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.INDEX_MENU_LIST_CATEGORY,  Fragment_ListCategory.class);
        classMap.put(ControlMapper.INDEX_MENU_LIST_PRODUCTS,  Fragment_ListProducts.class);
        classMap.put(ControlMapper.INDEX_MENU_INFO_PRODUCT,   Fragment_InfoProduct.class);
        classMap.put(ControlMapper.INDEX_MENU_NEW_PRODUCT,    Fragment_NewProduct.class);
        classMap.put(ControlMapper.INDEX_MENU_EDIT_PRODUCT,   Fragment_EditProduct.class);
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
