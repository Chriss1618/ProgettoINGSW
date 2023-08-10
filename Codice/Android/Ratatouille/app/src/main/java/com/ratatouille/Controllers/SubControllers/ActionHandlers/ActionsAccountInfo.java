package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.Views.Schermate.Activity_ChooseRole;
import java.util.HashMap;

public class ActionsAccountInfo extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsAccountInfo";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_EDIT_ACCOUNT      = 0;
    public final static int INDEX_ACTION_LOGOUT                 = 1;

    public ActionsAccountInfo(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_EDIT_ACCOUNT,    new OpenEditAccount_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_LOGOUT,               new LogOut_ActionHandler());
    }

    private static class OpenEditAccount_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {

        }
    }

    private static class LogOut_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            new LocalStorage(action.getManager().context).DeleteAllData();
//            Intent intent = new Intent(action.getManager().context, Activity_ChooseRole.class);
//            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            action.getManager().context.startActivity(intent);
//            ((Activity)action.getManager().context).finish();
            triggerRebirth(action.getManager().context);
        }

        public static void triggerRebirth(Context context) {
            Intent intent = new Intent(context, Activity_ChooseRole.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

            Runtime.getRuntime().exit(0);
        }
    }
}
