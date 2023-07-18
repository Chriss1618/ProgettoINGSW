package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActionsNewProduct extends ActionsViewHandler{

    //ACTIONS INDEX
    public final static int INDEX_ACTION_ADD_FROM_GALLERY       = 0;
    public final static int INDEX_ACTION_ADD_INGREDIENTI        = 1;
    public final static int INDEX_ACTION_REMOVE_INGREDIENTI     = 2;
    public final static int INDEX_ACTION_CANCEL                 = 3;
    public final static int INDEX_ACTION_CREATE_PRODUCT         = 4;

    public ActionsNewProduct(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_ADD_FROM_GALLERY, new OpenGallery_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_CREATE_PRODUCT, new CreateProduct_ActionHandler());
    }

    private static class OpenGallery_ActionHandler implements ActionHandler{

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

    private static class CreateProduct_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Product NewProduct = (Product) action.getData();



        }
    }


}
