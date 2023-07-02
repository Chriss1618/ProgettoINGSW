package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.ratatouille.Listeners.BottomBarListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//FACTORY PATTERN
public class ControllerFactory {
    public static final int INDEX_TYPE_CONTROLLER_AMMINISTRATORE   = 0;
    public static final int INDEX_TYPE_CONTROLLER_SUPERVISORE      = 1;
    public static final int INDEX_TYPE_CONTROLLER_CHEF             = 2;
    public static final int INDEX_TYPE_CONTROLLER_CAMERIERE        = 3;

    private static final Map<Integer, Class<? extends Controller>> classMap = new HashMap<>();
    static {
        classMap.put(INDEX_TYPE_CONTROLLER_AMMINISTRATORE,  Controller_Amministratore.class);
        classMap.put(INDEX_TYPE_CONTROLLER_SUPERVISORE,    Controller_Supervisore.class);
        classMap.put(INDEX_TYPE_CONTROLLER_CHEF,           Controller_Chef.class);
        classMap.put(INDEX_TYPE_CONTROLLER_CAMERIERE,      Controller_Cameriere.class);
    }

    public static Controller createController(int typeController, Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) throws IllegalAccessException, InstantiationException {
        try{
            return Objects.requireNonNull(classMap.get(typeController))
                    .getConstructor(Context.class, View.class, FragmentManager.class, BottomBarListener.class)
                    .newInstance(context,view,fragmentManager,bottomBarListener);
        }catch ( InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di Controller
            throw new IllegalArgumentException("Invalid Controller type.");
        }
    }
}