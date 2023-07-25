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
    //SYSTEM
    private static final String TAG = "ActionsNewProduct";
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

            Log.d(TAG, "handleAction: Prodotto Passato->"+ NewProduct.getNameProduct());
            String image = NewProduct.getStringDataImage(action.getManager().context);
            sendNewProductToServer(image);
            //action.callBack(sendNewProductToServer(NewProduct,action.getManager().context));
        }

        private void sendNewProductToServer(String image){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("image", image);
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Product.php";

            try {
                JSONObject Msg = new ServerCommunication().getData( dataToSend, url);
                if( Msg != null ){
                    Log.d(TAG, "sendNewProductToServer: true");
                    Log.d(TAG, "sendNewProductToServer: MSG->"+Msg.toString(4));
                }else{
                    Log.d(TAG, "sendNewProductToServer: false");
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
        }

        private boolean sendNewProductToServer(Product newProduct,Context context){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("id_ristorante", newProduct.getID_category()+"")
                    .appendQueryParameter("NameProduct",newProduct.getNameProduct())
                    .appendQueryParameter("ImageProduct",newProduct.getDataFromUriProduct(context))
                    .appendQueryParameter("PrizeProduct",newProduct.getPriceProduct()+"")
                    .appendQueryParameter("DescriptionProduct",newProduct.getDescriptionProduct())
                    .appendQueryParameter("AllergeniProduct",newProduct.getAllergeniProduct())
                    .appendQueryParameter("isSendToKitchen",newProduct.isSendToKitchen()+"");

            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Product.php";

            try {
    //                JSONArray Msg = new ServerCommunication().getData( dataToSend, url);
    //                if( Msg != null ){
    //                    for(int i = 0 ; i<Msg.length(); i++){
    //                        JSONObject Response_Json = new JSONObject(Msg.getString(i));
    //                        Log.d(TAG, "sendNewProductToServer: End");
    ////                        addedCategory = new CategoriaMenu(
    ////                                Categoria_Json.getString("NomeCategoria"),
    ////                                Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
    ////                        );
    //                    }
    //                }else{
    //                    Log.d(TAG, "sendNewProductToServer: false");
    //                    return false;
    //                }
            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);
            }
            Log.d(TAG, "sendNewProductToServer: true");
            return true;

        }
    }


}
