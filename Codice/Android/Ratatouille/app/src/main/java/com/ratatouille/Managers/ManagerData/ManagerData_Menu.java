package com.ratatouille.Managers.ManagerData;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.Models.EndPoints.EndPointer;
import com.ratatouille.Models.ServerCommunication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManagerData_Menu {
    //SYSTEM
    private static final String TAG = "ManagerData_Menu";
    //LAYOUT

    //FUNCTIONAL

    //DATA
    private ArrayList<CategoriaMenu> ListCategoryMenu;

    //OTHERS...
    private void getCategoriesFromServer(){
        Uri.Builder dataToSend  = new Uri.Builder().appendQueryParameter("id_ristorante", "1");
        String      url         = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.SELECT + "/CategoriaMenu.php";

        try {
            JSONArray Json_Categories = new ServerCommunication().getData( dataToSend, url);
            setCategories( Json_Categories );
        }catch ( Exception e ){
            Log.e(TAG, "getDataFromServer: ",e);
        }
    }

    private void setCategories(JSONArray Msg) throws org.json.JSONException{
        if( Msg != null ){
            for(int i = 0 ; i<Msg.length(); i++){
                JSONObject Categoria_Json = new JSONObject(Msg.getString(i));

                ListCategoryMenu.add(new CategoriaMenu(
                        Categoria_Json.getString("NomeCategoria"),
                        Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                ));
            }
        }
    }
}
