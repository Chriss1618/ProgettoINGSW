package com.ratatouille.Views.Schermate.Inventario;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_ProductInventory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListInventory;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

import java.util.ArrayList;
import java.util.Objects;

public class Fragment_ListInventary extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListInventary";

    //LAYOUT
    private android.view.View View_Fragment;
    private EditText        EditText_SearchInventory;
    private TextView        TextView_TitleInventory;
    private TextView        TextView_TitleExist;
    private TextView        TextView_TitleMissing;
    private RecyclerView    Recycler_Products_Exist;
    private RecyclerView    Recycler_Products_Missing;
    private ImageView       ImageView_AddIngredient;
    private ImageView       ImageView_DeleteProduct;
    private ProgressBar     ProgressBar_Existing;
    private ProgressBar     ProgressBar_Missing;
    private TextView        TextView_EmptyExisting;
    private TextView        TextView_EmptyMissing;

    //FUNCTIONAL
    private final RecycleEventListener          RecycleEventListener;
    private final Manager                       manager;
    private Adapter_ProductInventory            adapter_product_missing;
    private Adapter_ProductInventory            adapter_product_exist;
    private boolean                             isDeleting;

    //DATA
    private ArrayList<Ingredient>   TitleProducts_Exist;
    private ArrayList<Ingredient>   TitleProducts_Missing;

    //OTHER...

    public Fragment_ListInventary(Manager manager, int a) {
        this.manager = manager;
        this.RecycleEventListener                = new RecycleEventListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment =  inflater.inflate(R.layout.fragment__list_inventary, container, false);
        PrepareLayout();
        PrepareData();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        TitleProducts_Exist = new ArrayList<>();
        TitleProducts_Missing = new ArrayList<>();

        isDeleting = false;

        ProgressBar_Existing.setVisibility(View.VISIBLE);
        ProgressBar_Missing.setVisibility(View.VISIBLE);
        TextView_EmptyExisting.setVisibility(View.GONE);
        TextView_EmptyMissing.setVisibility(View.GONE);

        sendRequest();

    }
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(),null, ManagerRequestFactory.INDEX_REQUEST_INGREDIENTS,(listIngredients)-> setIngredientsOnLayout((ArrayList<Ingredient>) listIngredients));
        manager.HandleRequest(request);

    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        //SetDataOnLayout();

        StartAnimations();
    }

    @Override
    public void LinkLayout() {
        EditText_SearchInventory    = View_Fragment.findViewById(R.id.edit_text_search_ingredient);
        ProgressBar_Existing        = View_Fragment.findViewById(R.id.progressbar_existing);
        ProgressBar_Missing         = View_Fragment.findViewById(R.id.progressbarMissing);
        TextView_EmptyExisting      = View_Fragment.findViewById(R.id.text_view_empty_existing);
        TextView_EmptyMissing       = View_Fragment.findViewById(R.id.text_view_empty_missing);

        TextView_TitleInventory     = View_Fragment.findViewById(R.id.text_view_title_inventory);
        TextView_TitleExist         = View_Fragment.findViewById(R.id.text_view_exist);
        TextView_TitleMissing       = View_Fragment.findViewById(R.id.text_view_missing);
        Recycler_Products_Exist     = View_Fragment.findViewById(R.id.recycler_products_exist);
        Recycler_Products_Missing   = View_Fragment.findViewById(R.id.recycler_products_finished);
        ImageView_DeleteProduct     = View_Fragment.findViewById(R.id.ic_delete_product);
        ImageView_AddIngredient     = View_Fragment.findViewById(R.id.ic_add_ingrediente);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener   (this::onClickProduct);
        RecycleEventListener     .setOnClickItemOptionAdapterListener( this::onClickDeleteIngredient );

        ImageView_DeleteProduct .setOnClickListener              (view -> onClickDeleteIngredients());
        ImageView_AddIngredient .setOnClickListener              (view -> onCLickAddNewIngredient());

        EditText_SearchInventory     .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( adapter_product_exist != null ) adapter_product_exist.filterList(charSequence.toString());
                if ( adapter_product_missing != null ) adapter_product_missing.filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void SetDataOnLayout() {
        initListProductsExistRV();
        initListProductsMissingRV();
    }

    private void initListProductsExistRV( ){
        if(TitleProducts_Exist.size() == 0 ){
            TextView_EmptyExisting.setVisibility(View.VISIBLE);
            adapter_product_exist = null;
        }else{
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            Recycler_Products_Exist.setLayoutManager(mLayoutManager);
            Recycler_Products_Exist.setNestedScrollingEnabled(false);
            adapter_product_exist = new Adapter_ProductInventory(TitleProducts_Exist, RecycleEventListener,false);
            Recycler_Products_Exist.setAdapter(adapter_product_exist);
        }
    }

    private void initListProductsMissingRV( ){
        if(TitleProducts_Missing.size() == 0 ){
            TextView_EmptyMissing.setVisibility(View.VISIBLE);
            adapter_product_missing = null;
        }else{
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            Recycler_Products_Missing.setLayoutManager(mLayoutManager);
            Recycler_Products_Missing.setNestedScrollingEnabled(false);
            adapter_product_missing = new Adapter_ProductInventory(TitleProducts_Missing, RecycleEventListener, false);
            Recycler_Products_Missing.setAdapter(adapter_product_missing);
        }
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickProduct(Object product) {
        EndAnimations();
    }

    private void onClickDeleteIngredients(){
        if(isDeleting){
            if ( adapter_product_missing != null ) adapter_product_missing.hideDeleteIcon();
            if ( adapter_product_exist != null ) adapter_product_exist.hideDeleteIcon();
            isDeleting = false;
        }else{
            if ( adapter_product_missing != null ) adapter_product_missing.showDeleteIcon();
            if ( adapter_product_exist != null ) adapter_product_exist.showDeleteIcon();
            isDeleting = true;
        }
    }

    private void onClickDeleteIngredient(String NameIngredientToDelete,int id_ingredientToDelete){
        Ingredient IngredientToDelete = null;
        for (Ingredient ingredient: TitleProducts_Exist ) {
            if (ingredient.getID_Ingredient() == id_ingredientToDelete) {
                IngredientToDelete = ingredient;
                break;
            }
        }
        if (IngredientToDelete == null)
            for (Ingredient ingredient: TitleProducts_Missing ) {
                if (ingredient.getID_Ingredient() == id_ingredientToDelete) {
                    IngredientToDelete = ingredient;
                    break;
                }
            }

        Action action = new Action(ActionsListInventory.INDEX_ACTION_DELETE_INGREDIENT, IngredientToDelete,
                (id_ingredient)-> deleteItemFromRecycle( (Integer)id_ingredient ));
        SendAction(action);
    }

    private void onCLickAddNewIngredient(){
        Log.d(TAG, "onCLickAddNewIngredient -> PREMUTO");
        Action action = new Action(ActionsListInventory.INDEX_ACTION_SHOW_NEW_INGREDIENT, null );
        SendAction(action);
    }
    //FUNCTIONAL
    private void setIngredientsOnLayout(ArrayList<Ingredient> ListIngredient){
        for (Ingredient ingredient : ListIngredient) {
            Log.d(TAG, "setIngredientsOnLayout: ingrediente->"+ingredient.getNameIngredient());
            if(ingredient.getQtaIngredient() > 0 ) TitleProducts_Exist.add(ingredient);
            else TitleProducts_Missing.add(ingredient);
        }

        requireActivity().runOnUiThread(()->{
            initListProductsExistRV();
            initListProductsMissingRV();

            ProgressBar_Existing.setVisibility(View.GONE);
            ProgressBar_Missing.setVisibility(View.GONE);

        });
    }

    private void deleteItemFromRecycle(Integer id_ingredient){
        requireActivity().runOnUiThread(() -> {
            for(int indexItem = 0; indexItem < TitleProducts_Exist.size() ; indexItem++){
                if(TitleProducts_Exist.get(indexItem).getID_Ingredient() == id_ingredient){
                    TitleProducts_Exist.remove(indexItem);
                    adapter_product_exist.removeItem(id_ingredient);
                    break;
                }
            }

            for(int indexItem = 0; indexItem < TitleProducts_Missing.size() ; indexItem++){
                if(TitleProducts_Missing.get(indexItem).getID_Ingredient() == id_ingredient){
                    TitleProducts_Missing.remove(indexItem);
                    adapter_product_missing.removeItem(id_ingredient);
                    break;
                }
            }
        });

    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(!manager.bottomBarListener.visible){
            manager.showBottomBar();
        }
        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
    }

    @Override
    public void EndAnimations() {
        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}