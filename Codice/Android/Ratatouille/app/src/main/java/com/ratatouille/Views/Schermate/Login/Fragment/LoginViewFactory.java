package com.ratatouille.Views.Schermate.Login.Fragment;

import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Interfaces.IViewFactory;
import com.ratatouille.Models.Interfaces.IViewLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginViewFactory implements IViewFactory {
    private static final String TAG = "LoginViewFactory";
    private static final Map<Integer, Class<? extends IViewLayout>> classMap = new HashMap<>();
    static {
        classMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_WELCOME,     Fragment_Welcome.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_LOGIN,       Fragment_Login.class);
        classMap.put(ControlMapper.IndexViewMapper.INDEX_LOGIN_CONFIRM,     Fragment_ConfirmPassword.class);
    }

    public IViewLayout createView(int typeView, Manager manager)throws IllegalAccessException, InstantiationException{
        try{
            return Objects.requireNonNull( classMap.get(typeView) )
                    .getConstructor( Manager.class )
                    .newInstance( manager );
        }catch (InvocationTargetException | NoSuchMethodException e ) { //No public constructor con Signature specificata per il tipo di View
            Log.e(TAG, "createView: ",e );
            throw new IllegalArgumentException("Invalid View type. \n"+e);
        }
    }
}