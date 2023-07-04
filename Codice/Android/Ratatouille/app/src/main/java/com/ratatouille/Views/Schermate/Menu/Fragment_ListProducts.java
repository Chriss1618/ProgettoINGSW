package com.ratatouille.Views.Schermate.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Product;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListProducts extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListProducts";
    private static final String CATEGORY_TAG = "stringToPass";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_AddProduct;
    private ImageView       ImageView_deleteProduct;
    //FUNCTIONAL
    private RecycleEventListener            RecycleEventListener;
    Manager manager;
    private Adapter_Product                 adapter_product;
    private boolean                         isDeleting;
    //DATA
    private ArrayList<String>   TitleProducts;
    private String              Category_Name;

    //OTHER...

    public Fragment_ListProducts(Manager manager,int a) {
        this.manager = manager;
        this.Category_Name = "Nessuna Categoria";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Category_Name = getArguments().getString(CATEGORY_TAG);
        }
        Log.d(TAG, "onCreate: category passed:"+ Category_Name);

        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View_fragment = inflater.inflate(R.layout.fragment__list_products, container, false);

        PrepareData();
        PrepareLayout();

        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData(){
        TitleProducts = new ArrayList<>();
        TitleProducts.add("Pizza Tonno");
        TitleProducts.add("Pizza Margherita");
        TitleProducts.add("Panino al Salame");
        TitleProducts.add("Carbonara");

        isDeleting = false;
    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();

        StartAnimations();
    }

    @Override
    public void LinkLayout() {
        Text_View_TitleCategory     = View_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Products           = View_fragment.findViewById(R.id.recycler_products);
        ImageView_AddProduct        = View_fragment.findViewById(R.id.ic_add_product);
        ImageView_deleteProduct     = View_fragment.findViewById(R.id.ic_delete_product);
    }

    @Override
    public void SetDataOnLayout() {
        initListProductsRV();
        Text_View_TitleCategory.setText(Category_Name);

    }
    @Override
    public void SetActionsOfLayout() {

        RecycleEventListener    .setOnClickItemAdapterListener( this::onClickProduct );
        ImageView_AddProduct    .setOnClickListener(            view -> onClickAddProduct());
        ImageView_deleteProduct .setOnClickListener(            view -> onClickDeleteMember());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = manager.from <= manager.onMain;

        adapter_product = new Adapter_Product(TitleProducts, RecycleEventListener,isFromLeft);
        Recycler_Products.setAdapter(adapter_product);
    }

    //ACTIONS *************************************************************************
    public void onClickProduct(String Product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
        toProductAnimations();
    }
    private void onClickAddProduct(){
        toProductAnimations();
    }
    private void onClickDeleteMember(){
        if(isDeleting){
            adapter_product.hideDeleteIcon();
            isDeleting = false;
        }else{
            adapter_product.showDeleteIcon();
            isDeleting = true;
        }
    }

    //FUNCTIONAL

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(manager.from > manager.onMain){
            Log.d(TAG, "StartAnimations: Da product");
            fromProductAnimations();
        }else{
            Log.d(TAG, "StartAnimations: Da Menu");
            fromMenuAnimations();
        }
    }

    @Override
    public void EndAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
    public void fromMenuAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromDown(300));
        //Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(300));
    }

    public void toMenuAnimations(){

    }

    public void toProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
    public void fromProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromUp(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));

    }
}