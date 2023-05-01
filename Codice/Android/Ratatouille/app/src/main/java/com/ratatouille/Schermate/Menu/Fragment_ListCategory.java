package com.ratatouille.Schermate.Menu;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.Models.EndPoints.EndPointer;
import com.ratatouille.R;
import com.ratatouille.Schermate.Activity_Amministratore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Fragment_ListCategory extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListCategory";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Categories;
    private LinearLayout    LinearLayout_NewCategory;
    private LinearLayout    LinearLayout_BackGroundNewCategory;
    private ImageView       Image_View_AddCategory;
    private ImageView       Image_View_DeleteCategory;

    //FUNCTIONAL
    private final Manager_MenuFragments     manager_MenuFragments;
    private RecycleEventListener            RecycleEventListener;
    private Adapter_Category                adapter_category;
    private boolean                         isDeleting;
    //DATA
    private ArrayList<String> TitleCategories;
    private ArrayList<CategoriaMenu> CategorieMenu;
    //OTHERS...


    public Fragment_ListCategory(Manager_MenuFragments manager_menuFragments) {
        this.manager_MenuFragments = manager_menuFragments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecycleEventListener = new RecycleEventListener();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View_fragment = inflater.inflate(R.layout.fragment__list_category, container, false);
        PrepareData();
        PrepareLayout();

        StartAnimations();
        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData(){
        TitleCategories = new ArrayList<>();
        CategorieMenu = new ArrayList<>();
        Thread thread = new Thread(this::getDataFromServer);

        thread.start();

        while(thread.isAlive()) {}
        setData();
    }

    private void setData(){

        for(CategoriaMenu categoriaMenu : CategorieMenu)
            TitleCategories.add(categoriaMenu.getNomeCategoria());

        isDeleting = false;

    }
    private void getDataFromServer(){
        TitleCategories = new ArrayList<>();

        try {
            String url = EndPointer.StandardPath+EndPointer.VERSION_ENDPOINT+EndPointer.SELECT+"/CategoriaMenu.php";
            Log.d(TAG, "getDataFromServer: Url : "+ url);
            URL urlGetAllCategories = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlGetAllCategories.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //INVIO Risposte
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("id_ristorante", "1");

            String data = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();

            //GET RESULT
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder builder2= new StringBuilder();

            while((line = bufferedReader.readLine()) != null){
                builder2.append(line);
            }

            JSONObject json_data = new JSONObject(builder2.toString());

            Log.d(TAG, "getData: messaggio BackEnd->"+json_data );
            if(json_data.getString("status").equals("0")){
                return;
            }

            //leggi Json Se hai un successo ritorni messageid
            Log.d(TAG, "sendData: messageFromAndroid:"+json_data.getString("msg"));


            JSONArray Msg= new JSONArray(json_data.getString("msg"));

            for(int i = 0 ; i<Msg.length(); i++){
                JSONObject Categoria_Json = new JSONObject(Msg.getString(i));
                try {
                    CategorieMenu.add(new CategoriaMenu(
                            Categoria_Json.getString("NomeCategoria"),
                            Integer.parseInt( Categoria_Json.getString("ID_CategoriaMenu") )
                    ));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "getDataFromServer: numero Categorie Salvate ->"+CategorieMenu.size());


            //CHIUSURA CONNESSIONE
            bufferedReader.close();
            os.flush();
            os.close();
            conn.disconnect();

        } catch (Exception e) {
            Log.d(TAG, "getDataFromServer: Errore di Comunicazione con il BeckEnd");
            e.printStackTrace();
        }
    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();

    }

    @Override
    public void LinkLayout() {
        Text_View_TitleCategory             = View_fragment.findViewById(R.id.text_view_title_category);
        Image_View_AddCategory              = View_fragment.findViewById(R.id.ic_add_category);
        Image_View_DeleteCategory           = View_fragment.findViewById(R.id.ic_delete_category);
        Recycler_Categories                 = View_fragment.findViewById(R.id.recycler_categories);
        LinearLayout_NewCategory            = View_fragment.findViewById(R.id.linear_layout_new_category);
        LinearLayout_BackGroundNewCategory  = View_fragment.findViewById(R.id.darkRL);
    }
    @Override
    public void SetDataOnLayout() {
        initCategoryRV();
        initDialog();
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener     .setOnClickItemAdapterListener(this::onClickCategory);
        Image_View_AddCategory   .setOnClickListener(view ->onClickNewCategory());
        Image_View_DeleteCategory.setOnClickListener(view -> onClickDeleteCategory());
    }

    private void initCategoryRV(){
        adapter_category = new Adapter_Category(TitleCategories, RecycleEventListener);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);
        isDeleting = false;
    }
    private void initDialog(){

    }
    //ACTIONS
    private void onClickCategory(String Category){
        Log.d(TAG, "Ricevuto da Listener->"+Category);
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                sendActionToManager(Manager_MenuFragments.INDEX_MENU_LIST_PRODUCTS,Category),
                300);
    }

    private void onClickNewCategory(){
        showDialogNewCategory();
    }

    private void onClickDeleteCategory(){
        if(isDeleting){
            adapter_category.hideDeleteIcon();
            isDeleting = false;
        }else{
            adapter_category.showDeleteIcon();
            isDeleting = true;
        }
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.manager_MenuFragments.showFragment(index,msg);
    }

    private void showDialogNewCategory(){
        CardView CardView_Cancel    = LinearLayout_NewCategory.findViewById(R.id.card_view_annulla);
        CardView CardView_Add       = LinearLayout_NewCategory.findViewById(R.id.card_view_aggiungi);

        CardView_Cancel.setOnClickListener(view -> dismissDialogNewCategory());

        LinearLayout_NewCategory            .setVisibility(View.VISIBLE);
        LinearLayout_BackGroundNewCategory  .setVisibility(View.VISIBLE);
        LinearLayout_NewCategory.startAnimation(Manager_Animation.getTranslationINfromUp(500));
        LinearLayout_BackGroundNewCategory.startAnimation(Manager_Animation.getFadeIn(500));
    }

    private void dismissDialogNewCategory(){
        LinearLayout_NewCategory.setVisibility(View.GONE);
        LinearLayout_BackGroundNewCategory.setVisibility(View.GONE);
        LinearLayout_NewCategory.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
        LinearLayout_BackGroundNewCategory.startAnimation(Manager_Animation.getFadeOut(500));
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        Text_View_TitleCategory.setText(R.string.Menu);
        Text_View_TitleCategory     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Recycler_Categories         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
    @Override
    public void EndAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_Categories     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }


}