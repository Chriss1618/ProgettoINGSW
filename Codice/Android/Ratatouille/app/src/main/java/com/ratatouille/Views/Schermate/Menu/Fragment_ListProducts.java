package com.ratatouille.Views.Schermate.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.Controllers.Adapters.Adapter_Product;
import com.ratatouille.Controllers.Adapters.ProductTouchHelper;
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
    private ImageView       ImageView_Order;
    private ImageView       ImageView_BackOrder;
    private ImageView       ImageView_deleteProduct;
    private ImageView       ImageView_Back;
    private EditText        EditText_SearchProduct;


    //FUNCTIONAL
    private RecycleEventListener            RecycleEventListener;
    private final Manager                   manager;
    private Adapter_Product                 adapter_product;
    private boolean                         isDeleting;
    private boolean                         isOrdering;
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
        TitleProducts.add("Pizza Tonno ");
        TitleProducts.add("Pizza Margherita");
        TitleProducts.add("Panino al Salame");
        TitleProducts.add("Carbonara");
        TitleProducts.add("Pizza Tonno");
        TitleProducts.add("Pizza Margherita");
        TitleProducts.add("Panino al Salame");
        TitleProducts.add("Carbonara");
        TitleProducts.add("Pizza Tonno");
        TitleProducts.add("Pizza Margherita");
        TitleProducts.add("Panino al Salame");
        TitleProducts.add("Carbonara");

        isDeleting = false;
        isOrdering = false;

        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_PRODUCTS);
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
        ImageView_Order             = View_fragment.findViewById(R.id.ic_order);
        ImageView_BackOrder         = View_fragment.findViewById(R.id.ic_back_order);
        ImageView_deleteProduct     = View_fragment.findViewById(R.id.ic_delete_product);
        ImageView_Back              = View_fragment.findViewById(R.id.ic_back);
        EditText_SearchProduct      = View_fragment.findViewById(R.id.edit_text_search_product);
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
        ImageView_Order         .setOnClickListener(            view -> onOrderClick());
        ImageView_BackOrder     .setOnClickListener(            view -> onOrderClick());
        ImageView_deleteProduct .setOnClickListener(            view -> onClickDeleteMember());
        ImageView_Back          .setOnClickListener(            view -> manager.goBack());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = manager.IndexFrom <= manager.IndexOnMain;

        adapter_product = new Adapter_Product(getContext(),TitleProducts, RecycleEventListener,isFromLeft);

        ItemTouchHelper.Callback callback = new ProductTouchHelper(adapter_product);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter_product.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(Recycler_Products);
        Recycler_Products.setHasFixedSize(true); // Optional, if your item sizes are fixed
        Recycler_Products.setItemViewCacheSize(TitleProducts.size()); // Set the cache size to the number of items

        Recycler_Products.setAdapter(adapter_product);
    }

    //ACTIONS *************************************************************************
    private void onClickProduct(Product product){
        //Log.d(TAG, "PreparerData: Hai premuto l'item->"+product);
        toProductAnimations();
    }

    private void onClickAddProduct(){
        Action action = new Action(ActionsListProducts.INDEX_ACTION_OPEN_NEW_PRODUCT,Categoria);
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

    private void onOrderClick(){
        if(isOrdering){
            adapter_product.hideMoveIcon();
            ImageView_BackOrder.startAnimation(Manager_Animation.getFadeOut(300));
            new Handler(Looper.getMainLooper()).postDelayed(()->ImageView_BackOrder.setVisibility(View.INVISIBLE),300);
            isOrdering = false;
        }else{
            adapter_product.showMoveIcon();
            ImageView_BackOrder.setVisibility(View.VISIBLE);
            ImageView_BackOrder.startAnimation(Manager_Animation.getFadeIn(300));
            isOrdering = true;
        }
    }
    //FUNCTIONAL
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(!manager.bottomBarListener.visible){
            manager.showBottomBar();
        }

        if(manager.IndexFrom > manager.IndexOnMain){
            Log.d(TAG, "StartAnimations: Da product");
            fromProductAnimations();
        }else{
            Log.d(TAG, "StartAnimations: Da Menu");
            fromMenuAnimations();
        }
        ImageView_Back          .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500) );
        EditText_SearchProduct  .startAnimation( Manager_Animation.getFadeIn(300));
    }

    @Override
    public void EndAnimations(){
        if(manager.IndexOnMain > manager.IndexFrom) toProductAnimations();
        else toMenuAnimations();
        ImageView_Back              .startAnimation( Manager_Animation.getTranslateAnimatioOUT(300) );
        EditText_SearchProduct      .startAnimation( Manager_Animation.getFadeOut(300));

    }

    public void fromMenuAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationINfromDown(500) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationINfromDown(500) );
    }
    public void fromProductAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
        Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(300) );
        ImageView_deleteProduct .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
        ImageView_AddProduct    .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
    }

    public void toMenuAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationOUTtoDown(300) );
        Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioOUTtoRight(300) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationOUTtoDown(300) );

    }

    public void toProductAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioOUT(300) );
        ImageView_deleteProduct .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        ImageView_AddProduct    .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
    }

}