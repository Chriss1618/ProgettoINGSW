package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class ActionsLogin extends ActionsViewHandler{
    private static final String TAG = "ActionsLogin";

    public final static int INDEX_ACTION_REGISTER_ADMIN     = 0;
    public final static int INDEX_ACTION_NORMAL_LOGIN       = 1;
    public final static int INDEX_ACTION_LOGIN              = 2;
    public final static int INDEX_ACTION_CONFIRM_LOGIN      = 3;


    public ActionsLogin(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_REGISTER_ADMIN, new RegistrarAdmin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_NORMAL_LOGIN, new NormalLogin_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGIN, new Login_ActionHandler());
    }
    private static class RegistrarAdmin_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");

        }

    }

    private static class NormalLogin_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;

            Log.d(TAG, "handleAction: NormalLogin");
            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_LOGIN,"");

        }

    }

    private static class Login_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;
            Log.d(TAG, "handleAction: RegistraADMIN");

            action.callBack();
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));//Attesa animazinoe Rotazione LOGO
            action.getManager().changeOnMain(ControlMapper.INDEX_LOGIN_CONFIRM,"");


        }

    }

}
