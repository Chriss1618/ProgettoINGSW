package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import com.ratatouille.Listeners.BottomBarListener;
import com.ratatouille.Interfaces.SubController;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManagerFactory {
    public final static int INDEX_TYPE_MANAGER_STATS   = 0;
    public final static int INDEX_TYPE_MANAGER_STAFF   = 1;
    public final static int INDEX_TYPE_MANAGER_MENU    = 2;
    public final static int INDEX_TYPE_MANAGER_ACCOUNT = 3;

    private static final Map<Integer, Class<? extends SubController>> classMap = new HashMap<>();
    static {
        classMap.put(INDEX_TYPE_MANAGER_STATS,      Manager_StatsFragments.class);
        classMap.put(INDEX_TYPE_MANAGER_STAFF,      Manager_StaffFragments.class);
        classMap.put(INDEX_TYPE_MANAGER_MENU,       Manager_MenuFragments.class);
        classMap.put(INDEX_TYPE_MANAGER_ACCOUNT,    Manager_AccountFragments.class);
    }

    public static SubController createSubController(int typeManager, Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) throws IllegalAccessException, InstantiationException {
        try{
            return constructSubController_NoBottomBar(typeManager, context, view, fragmentManager);
        }catch (InvocationTargetException | NoSuchMethodException e){ //No public constructor con Signature specificata per il tipo di Manager
            try{
                return constructSubController(typeManager, context, view, fragmentManager, bottomBarListener);
            }catch ( InvocationTargetException | NoSuchMethodException e1 ) { //No public constructor con Signature specificata per il tipo di Manager
                throw new IllegalArgumentException("Invalid Manager type.");
            }

        }
    }

    private static SubController constructSubController(int typeManager, Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
        return Objects.requireNonNull(classMap.get(typeManager))
                .getConstructor(Context.class, View.class, FragmentManager.class, BottomBarListener.class)
                .newInstance(context,view,fragmentManager,bottomBarListener);
    }

    private static SubController constructSubController_NoBottomBar(int typeManager, Context context, View view, FragmentManager fragmentManager) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
        return Objects.requireNonNull(classMap.get(typeManager))
                .getConstructor(Context.class, View.class,FragmentManager.class)
                .newInstance(context,view,fragmentManager);
    }
}