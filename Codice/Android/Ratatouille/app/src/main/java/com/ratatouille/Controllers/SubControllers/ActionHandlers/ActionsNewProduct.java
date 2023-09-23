package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

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
        actionHandlerMap.put(INDEX_ACTION_CREATE_PRODUCT,   new CreateProduct_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_INGREDIENTI,  new AddIngredient_ActionHandler());
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
            printNewProduct(NewProduct);

            boolean isInserted = sendNewProductToServer(NewProduct,action.getManager().context);
            action.callBack(isInserted);
            if(isInserted){
                Try.run(() -> TimeUnit.MILLISECONDS.sleep(1500));
                action.getManager().goBack();
            }
        }

        private void printNewProduct(Product NewProduct){
            Log.d(TAG, "printNewProduct ----------------------------------------------- ");
            Log.d(TAG, "New Product: Name            ->" + NewProduct.getNameProduct());
            Log.d(TAG, "New Product: ID_Category     ->" + NewProduct.getID_category());
            Log.d(TAG, "New Product: List Ingredients ------------");
            for (Ricettario ricettario: NewProduct.getRicette())
                Log.d(TAG, "New Product: Ingrediente (id:"+ ricettario.getIngredient().getID_Ingredient()+")->" + ricettario.getIngredient().getNameIngredient() + " " +ricettario.getDosi() + ricettario.getTypeMeasure());
            Log.d(TAG, "New Product: END List Ingredients --------");
            Log.d(TAG, "printNewProduct ----------------------------------------------- ");
        }

        private boolean sendNewProductToServer(Product newProduct,Context context){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_category",        newProduct.getID_category()+"")
                    .appendQueryParameter("NameProduct",        newProduct.getNameProduct())
                    .appendQueryParameter("PhotoDATA",          newProduct.isHasPhoto()? newProduct.getDataFromUriProduct(context): "NoPhoto")
                    .appendQueryParameter("PhotoURL",           newProduct.isHasUrl()? newProduct.getURLImageProduct(): "NoURL")
                    .appendQueryParameter("PriceProduct",       newProduct.getPriceProduct()+"")
                    .appendQueryParameter("DescriptionProduct", newProduct.getDescriptionProduct())
                    .appendQueryParameter("AllergeniProduct",   newProduct.getAllergeniProduct())
                    .appendQueryParameter("isSendToKitchen",    newProduct.isSendToKitchen()+"")
                    .appendQueryParameter("nIngredient",    newProduct.getRicette().size()+"");
            int nIngredient = 0;
            for(Ricettario ricettario : newProduct.getRicette()){
                dataToSend.appendQueryParameter("ID_Ingrediente" +nIngredient,  ricettario.getIngredient().getID_Ingredient()+"");
                dataToSend.appendQueryParameter("Dosi" +nIngredient,            ricettario.getDosi()+"");
                dataToSend.appendQueryParameter("TypeMeasure" +nIngredient,     ricettario.getTypeMeasure()+"");
                nIngredient+=1;
            }
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Product.php";

            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if(BodyJSON != null){
                    Log.d(TAG, "sendToServer: RICEVUTO DA SERVER ->\n" + BodyJSON.toString(4));
                    return true;
                }

            }catch (Exception e){
                Log.e(TAG, "getDataFromServer: ",e);

                return false;
            }
            Log.d(TAG, "sendNewProductToServer: false");
            return false;

        }

    }
    private static class AddIngredient_ActionHandler implements ActionHandler{

        @Override
        public void handleAction(Action action) {
            Log.d(TAG, "handleAction -> Choose Ingredient");
            action.getManager().setData(action.getData());
            action.getManager().useTemporaryNewView(ControlMapper.INDEX_INVENTORY_LIST,ControlMapper.INDEX_TYPE_MANAGER_INVENTORY);
        }

    }

}
