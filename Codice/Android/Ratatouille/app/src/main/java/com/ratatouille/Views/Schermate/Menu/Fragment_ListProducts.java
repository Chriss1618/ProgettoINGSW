package com.ratatouille.Views.Schermate.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.Controllers.Adapters.Adapter_Product;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListProducts;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
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
    private ImageView       ImageView_Back;
    //FUNCTIONAL
    private RecycleEventListener            RecycleEventListener;
    private final Manager                   manager;
    private Adapter_Product                 adapter_product;
    private boolean                         isDeleting;
    //DATA
    private ArrayList<String>   TitleProducts;
    private CategoriaMenu       Categoria;
    private String              Category_Name;

    //OTHER...

    public Fragment_ListProducts(Manager manager,int a) {
        this.manager = manager;
        this.Category_Name = "Nessuna Categoria";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (manager.getData() != null) {
            Categoria = (CategoriaMenu) manager.getData();
        }
        Log.d(TAG, "onCreate: category passed:"+ Categoria.getID_categoria());
        Category_Name = Categoria.getNomeCategoria();
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
        ImageView_Back              = View_fragment.findViewById(R.id.ic_back);
    }

    @Override
    public void SetDataOnLayout() {
        initListProductsRV();
        Text_View_TitleCategory.setText(Category_Name);
    }

    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener((item)-> onClickProduct( (Product)item ) );
        ImageView_AddProduct    .setOnClickListener(            view -> onClickAddProduct());
        ImageView_deleteProduct .setOnClickListener(            view -> onClickDeleteMember());
        ImageView_Back          .setOnClickListener(            view -> manager.closeView());
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
    private void onClickProduct(Product product){
        //Log.d(TAG, "PreparerData: Hai premuto l'item->"+product);
        toProductAnimations();
    }
    private void onClickAddProduct(){
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_PRODUCTS);
        Action action = new Action(ActionsListProducts.INDEX_ACTION_OPEN_NEW_PRODUCT,Categoria,manager,this::toProductAnimations,manager.getSourceInfo());
        SendAction(action);
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
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

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