package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Events.Action.Action;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionsOrdini extends ActionsViewHandler{

    //SYSTEM
    private static final String TAG = "ActionsOrdini";
    //ACTIONS INDEX
    public final static int INDEX_ACTION_SELECT_TABLE           = 0;
    public final static int INDEX_ACTION_CONFIRM_ORDERS         = 1;

    public ActionsOrdini(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_SELECT_TABLE,     new TableSelected_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CONFIRM_ORDERS,   new ConfirmOrders_ActionHandler());
    }

    private static class TableSelected_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            ArrayList<Ordine> ListTables = (ArrayList<Ordine>) action.getData();

            action.getManager().changeOnMain(ControlMapper.INDEX_ORDINI_TABLE,ListTables);

        }

    }

    private static class ConfirmOrders_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Context context = action.getManager().context;

            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(intent, 1);
            }

        }

    }
}
