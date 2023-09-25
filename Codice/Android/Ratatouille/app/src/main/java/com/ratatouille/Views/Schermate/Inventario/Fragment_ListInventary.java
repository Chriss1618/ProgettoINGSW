package com.ratatouille.Views.Schermate.Inventario;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_ProductInventory;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListInventory;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private ImageView       ImageView_Back;
    private ImageView       ImageView_DeleteProduct;
    private ProgressBar     ProgressBar_Existing;
    private ProgressBar     ProgressBar_Missing;
    private TextView        TextView_EmptyExisting;
    private TextView        TextView_EmptyMissing;
    //dialogLayout
    private LinearLayout LinearLayout_DarkL;
    private LinearLayout LinearLayout_Dialog;

    //FUNCTIONAL
    private final RecycleEventListener          RecycleEventListener;
    private final Manager                       manager;
    private Adapter_ProductInventory            adapter_product_missing;
    private Adapter_ProductInventory            adapter_product_exist;
    private boolean                             isDeleting;
    //DATA
    private ArrayList<Ingredient>   TitleProducts_Exist;
    private ArrayList<Ingredient>   TitleProducts_Missing;
    private Ingredient              IngredientSelected;
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

        ImageView_Back              = View_Fragment.findViewById(R.id.ic_back);
        if(manager.getSourceInfo().getIndex_TypeManager() == ControlMapper.INDEX_TYPE_MANAGER_MENU)  ImageView_Back.setVisibility(View.VISIBLE);
        else ImageView_Back.setVisibility(View.GONE);

        //Dialog
        LinearLayout_DarkL      = View_Fragment.findViewById(R.id.darkRL);
        LinearLayout_Dialog     = View_Fragment.findViewById(R.id.linear_layout_add_ingredient_dialog);
    }

    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener   (this::onClickProduct);
        RecycleEventListener    .setOnClickItemOptionAdapterListener( (ingredientName,id_ingredient) -> onClickDeleteIngredient((String)ingredientName,id_ingredient) );

        ImageView_DeleteProduct .setOnClickListener              (view -> onClickDeleteIngredients());
        ImageView_AddIngredient .setOnClickListener              (view -> onCLickAddNewIngredient());
        ImageView_Back .setOnClickListener( view -> manager.goBack());


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
            TextView_EmptyExisting         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
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
            TextView_EmptyMissing         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
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
        if(manager.getSourceInfo().getIndex_TypeManager() == ControlMapper.INDEX_TYPE_MANAGER_MENU){
            IngredientSelected = (Ingredient) product;
            new DialogNewIngredient().showDialogNewIngredient();
        } else {
            Action action = new Action(ActionsListInventory.INDEX_ACTION_SELECT_INGREDIENT,product);
            SendAction(action);
        }
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

            ProgressBar_Existing        .setVisibility(View.GONE);
            ProgressBar_Missing         .setVisibility(View.GONE);
            EditText_SearchInventory     .addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d(TAG, "onTextChanged: sta cambiando");
                    if ( adapter_product_exist != null ) adapter_product_exist.filterList(charSequence.toString());
                    if ( adapter_product_missing != null ) adapter_product_missing.filterList(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
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

    private class DialogNewIngredient{
        ImageView   ImageView_Ingredient;
        TextView    TextView_IngredientName;
        EditText    EditText_GrandezzaIngredient;
        TextView    TextView_WarningGrandezza;
        TextView    TextView_Kg;
        TextView    TextView_g;
        TextView    TextView_Mg;
        TextView    TextView_L;
        TextView    TextView_Cl;
        TextView    TextView_Ml;
        TextView    TextView_MeasureSelected = null;

        CardView    CardView_Cancel;
        CardView    CardView_Add;

        public DialogNewIngredient() {

        }

        private void showDialogNewIngredient(){
            Log.d(TAG, "showDialogNewIngredient: Started");
            CardView_Cancel                 = LinearLayout_Dialog.findViewById(R.id.card_view_annulla);
            CardView_Add                    = LinearLayout_Dialog.findViewById(R.id.card_view_aggiungi);
            ImageView_Ingredient            = LinearLayout_Dialog.findViewById(R.id.image_view_Ingredient);
            TextView_IngredientName         = LinearLayout_Dialog.findViewById(R.id.text_view_name_product);
            EditText_GrandezzaIngredient    = LinearLayout_Dialog.findViewById(R.id.edit_text_new_grandezza);
            TextView_WarningGrandezza       = LinearLayout_Dialog.findViewById(R.id.warning_Misura);

            if(IngredientSelected.getURLImageIngredient().contains("https")){
                Picasso.get()
                        .load(IngredientSelected.getURLImageIngredient())
                        .into(ImageView_Ingredient);
            }else{
                Picasso.get()
                        .load(EndPointer.StandardPath+ EndPointer.IMAGES_INGREDIENT+ IngredientSelected.getURLImageIngredient())
                        .into(ImageView_Ingredient);
            }

            TextView_IngredientName.setText(IngredientSelected.getNameIngredient());

            TextView_Kg     = LinearLayout_Dialog.findViewById(R.id.text_view_kg);
            TextView_g      = LinearLayout_Dialog.findViewById(R.id.text_view_gr);
            TextView_Mg     = LinearLayout_Dialog.findViewById(R.id.text_view_mr);
            TextView_L      = LinearLayout_Dialog.findViewById(R.id.text_view_l);
            TextView_Cl     = LinearLayout_Dialog.findViewById(R.id.text_view_cl);
            TextView_Ml     = LinearLayout_Dialog.findViewById(R.id.text_view_ml);

            CardView_Cancel .setOnClickListener(view -> dismissDialogAddIngredient());
            CardView_Add    .setOnClickListener(view -> addIngredient());

            TextView_Kg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
            TextView_g  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
            TextView_Mg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
            TextView_L  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
            TextView_Cl .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
            TextView_Ml .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );

            TextView_WarningGrandezza.setVisibility(View.GONE);
            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);

            LinearLayout_Dialog     .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL      .startAnimation(Manager_Animation.getFadeIn(500));

            EditText_GrandezzaIngredient.setText("");
        }
        private void onMeasureSelected(TextView selected){
            TextView_MeasureSelected = selected;
            setSelected();
        }

        private void setSelected(){
            if(TextView_MeasureSelected.getText().toString().equals("Kg")) TextView_Kg.setBackgroundResource(R.drawable.background_mesure_left_selected);
            else TextView_Kg.setBackgroundResource(R.drawable.background_mesure_left);
            if(TextView_MeasureSelected.getText().toString().equals("gr")) TextView_g.setBackgroundResource(R.drawable.background_mesure_center_selected);
            else TextView_g.setBackgroundResource(R.drawable.background_mesure_center);
            if(TextView_MeasureSelected.getText().toString().equals("mg"))  TextView_Mg.setBackgroundResource(R.drawable.background_mesure_right_selected);
            else TextView_Mg.setBackgroundResource(R.drawable.background_mesure_right);

            if(TextView_MeasureSelected.getText().toString().equals("L"))  TextView_L.setBackgroundResource(R.drawable.background_mesure_left_selected);
            else TextView_L.setBackgroundResource(R.drawable.background_mesure_left);
            if(TextView_MeasureSelected.getText().toString().equals("cl")) TextView_Cl.setBackgroundResource(R.drawable.background_mesure_center_selected);
            else TextView_Cl.setBackgroundResource(R.drawable.background_mesure_center);
            if(TextView_MeasureSelected.getText().toString().equals("ml")) TextView_Ml.setBackgroundResource(R.drawable.background_mesure_right_selected);
            else TextView_Ml.setBackgroundResource(R.drawable.background_mesure_right);
        }
        private void addIngredient(){
            hideKeyboardFrom();
            if(EditText_GrandezzaIngredient.getText().toString().equals("") || TextView_MeasureSelected == null){
                showGrandezzaNotValid();
            }else{
                hideGrandezzaNotValid();
                Ricettario ricettario = new Ricettario();

                Product product = (Product) manager.getData();


                ricettario.setIngredient(IngredientSelected);
                ricettario.setDosi(Integer.parseInt(EditText_GrandezzaIngredient.getText().toString()));
                ricettario.setTypeMeasure(TextView_MeasureSelected.getText().toString());

                product.getRicette().add(ricettario);

                manager.setData(product);
                manager.goBack();
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private void addCategoryView(CategoriaMenu newCategory){
            requireActivity().runOnUiThread(() -> {

            });
        }
        private void showGrandezzaNotValid(){
            TextView_WarningGrandezza.setVisibility(View.VISIBLE);
        }
        private void hideGrandezzaNotValid(){
            TextView_WarningGrandezza.setVisibility(View.GONE);
        }

        private void dismissDialogAddIngredient(){
            LinearLayout_Dialog.setVisibility(View.GONE);
            LinearLayout_DarkL.setVisibility(View.GONE);
            LinearLayout_Dialog.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_DarkL.startAnimation(Manager_Animation.getFadeOut(500));
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if( manager.getSourceInfo().getIndex_TypeManager() == ControlMapper.INDEX_TYPE_MANAGER_MENU){
            ImageView_AddIngredient     .setVisibility(View.GONE);
            ImageView_DeleteProduct     .setVisibility(View.GONE);
        }else{
            if(!manager.bottomBarListener.visible){
                manager.showBottomBar();
            }
        }


        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
    }

    @Override
    public void EndAnimations() {
        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        TextView_EmptyMissing.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        TextView_EmptyExisting.startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
}