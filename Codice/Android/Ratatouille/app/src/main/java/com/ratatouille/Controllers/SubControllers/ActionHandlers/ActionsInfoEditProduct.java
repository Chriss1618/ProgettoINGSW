package com.ratatouille.Controllers.SubControllers.ActionHandlers;

import android.net.Uri;
import android.util.Log;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class ActionsInfoEditProduct extends ActionsViewHandler{
    //SYSTEM
    private static final String TAG = "ActionsInfoEditProduct";

    //FUNCTIONAL
    public final static int INDEX_ACTION_OPEN_EDIT_PRODUCT      = 0;
    public final static int INDEX_ACTION_EDIT_PRODUCT           = 1;
    public final static int INDEX_ACTION_ADD_INGREDIENTI        = 2;

    public ActionsInfoEditProduct(){
        actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(INDEX_ACTION_OPEN_EDIT_PRODUCT,    new OpenEditProduct_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_EDIT_PRODUCT,         new EditProduct_ActionHandler());
        actionHandlerMap.put(INDEX_ACTION_ADD_INGREDIENTI,      new AddIngredient_ActionHandler());
    }

    //ACTIONS HANDLED **************************************************************
    private static class OpenEditProduct_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Product product = (Product) action.getData();
            Log.d(TAG, "handleAction: openEDIT->"+ product.getNameProduct());
            action.callBack();
            action.getManager().changeOnMain(ControlMapper.INDEX_MENU_EDIT_PRODUCT,product);
        }
    }

    protected static class EditProduct_ActionHandler implements ActionHandler {
        @Override
        public void handleAction(Action action) {
            Product product = (Product) action.getData();
            Log.d(TAG, "handleAction: EDIT->"+ product.getNameProduct());

            String PhotoDATA =  product.isHasPhoto()? product.getDataFromUriProduct(action.getManager().context): "NoPhoto";
            product.setDataImageProduct(PhotoDATA);
            String id_product = product.getID_product()+ "";
            boolean isUpdated = sendUpdatedProductToServer(product,id_product);
            action.callBack(isUpdated);
            Try.run(() -> TimeUnit.MILLISECONDS.sleep(1000));
            action.getManager().setData(action.getData());
            action.getManager().goBack();
        }

        protected boolean sendUpdatedProductToServer(Product newProduct, String id_product){
            Uri.Builder dataToSend = new Uri.Builder()
                    .appendQueryParameter("ID_Product",         id_product )
                    .appendQueryParameter("NameProduct",        newProduct.getNameProduct())
                    .appendQueryParameter("PhotoDATA",          newProduct.isHasPhoto()? newProduct.getDataImageProduct(): "NoPhoto")
                    .appendQueryParameter("PhotoURL",           newProduct.getURLImageProduct())
                    .appendQueryParameter("PriceProduct",       newProduct.getPriceProduct()+"")
                    .appendQueryParameter("DescriptionProduct", newProduct.getDescriptionProduct())
                    .appendQueryParameter("AllergeniProduct",   newProduct.getAllergeniProduct())
                    .appendQueryParameter("isSendToKitchen",    newProduct.isSendToKitchen()+"")
                    .appendQueryParameter("nIngredient",        newProduct.getRicette().size()+"");
            int nIngredient = 0;

            for(Ricettario ricettario : newProduct.getRicette()){
                dataToSend.appendQueryParameter("ID_Ingrediente" +nIngredient,  ricettario.getIngredient().getID_Ingredient()+"");
                dataToSend.appendQueryParameter("Dosi" +nIngredient,            ricettario.getDosi()+"");
                dataToSend.appendQueryParameter("TypeMeasure" +nIngredient,     ricettario.getTypeMeasure()+"");
                nIngredient+=1;
            }
            String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.UPDATE + "/Product.php";
            try {
                JSONObject BodyJSON = new ServerCommunication().getData( dataToSend, url);
                if(BodyJSON.getString("MSG_STATUS").contains("1")){
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
