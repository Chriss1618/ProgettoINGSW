package com.ratatouille.Views.Schermate.Menu;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Product;
import com.ratatouille.Controllers.Adapters.ProductTouchHelper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListProducts;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_ListProducts extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListProducts";
    private static final String CATEGORY_TAG = "stringToPass";

    //LAYOUT
    private View            View_fragment;
    private ConstraintLayout ConstraintLayout_SearchBox;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_AddProduct;
    private ImageView       ImageView_Order;
    private ImageView       ImageView_BackOrder;
    private ImageView       ImageView_deleteProduct;
    private ImageView       ImageView_Back;
    private Button          Button_ConfirmOrder;
    private Button          Button_CancelOrder;
    private EditText        EditText_SearchProduct;
    private TextView        TextView_NoProducts;
    private ProgressBar     ProgressBar_LoadingProducts;

    //FUNCTIONAL
    private RecycleEventListener            RecycleEventListener;
    private final Manager                   manager;
    private Adapter_Product                 adapter_product;
    private boolean                         isDeleting;
    private boolean                         isOrdering;
    //DATA
    private ArrayList<Product>   ListProducts;
    private ArrayList<Product>   ListProductsBeforeOrder;

    private CategoriaMenu        Categoria;
    private String               Category_Name;

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

        PrepareLayout();

        PrepareData();

        return View_fragment;
    }

    //DATA
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), Categoria, ManagerRequestFactory.INDEX_REQUEST_PRODUCTS,
                (list)-> setProductsOnLayout((ArrayList<Product>) list));
        manager.HandleRequest(request);
    }

    @Override
    public void PrepareData(){
        isDeleting = false;
        isOrdering = false;
        ProgressBar_LoadingProducts .setVisibility(View.VISIBLE);
        Recycler_Products           .setVisibility(View.GONE);

        sendRequest();
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
        Button_ConfirmOrder         = View_fragment.findViewById(R.id.button_confirm_orders);
        Button_CancelOrder          = View_fragment.findViewById(R.id.button_cancel_orders);
        ConstraintLayout_SearchBox  = View_fragment.findViewById(R.id.constraint_layout_search_box);


        ProgressBar_LoadingProducts = View_fragment.findViewById(R.id.progressbar);
        TextView_NoProducts         = View_fragment.findViewById(R.id.text_view_empty);

    }

    @Override
    public void SetDataOnLayout() {
        Text_View_TitleCategory.setText(Category_Name);
    }

    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener((item)-> onClickProduct( (Product)item ) );
        RecycleEventListener    .setOnClickItemOptionAdapterListener( (item, id_product)->onClickDeleteProduct( (String)item, id_product) );
        ImageView_AddProduct    .setOnClickListener(            view -> onClickAddProduct());
        ImageView_Order         .setOnClickListener(            view -> onOrderClick());
        ImageView_BackOrder     .setOnClickListener(            view -> onCancelOrder());
        ImageView_deleteProduct .setOnClickListener(            view -> onClickDeleteMember());
        ImageView_Back          .setOnClickListener(            view -> manager.goBack());
        Button_CancelOrder      .setOnClickListener(            view -> onCancelOrder());
        Button_ConfirmOrder     .setOnClickListener(            view -> onConfirmOrder());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = manager.IndexFrom <= manager.IndexOnMain;

        adapter_product = new Adapter_Product(getContext(),ListProducts, RecycleEventListener,isFromLeft);

        ItemTouchHelper.Callback callback = new ProductTouchHelper(adapter_product);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter_product.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(Recycler_Products);
        Recycler_Products.setHasFixedSize(true); // Optional, if your item sizes are fixed
        Recycler_Products.setItemViewCacheSize(ListProducts.size()); // Set the cache size to the number of items

        Recycler_Products.setAdapter(adapter_product);

        checkEmptyRecycle();
    }

    private void setProductsOnLayout(ArrayList<Product> list){
        requireActivity().runOnUiThread(() -> {
            ListProducts = list;
            initListProductsRV();
            ProgressBar_LoadingProducts.setVisibility(View.GONE);
            EditText_SearchProduct     .addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d(TAG, "onTextChanged: sta cambiando");
                    if ( adapter_product != null ) adapter_product.filterList(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });
    }
    //ACTIONS *************************************************************************
    private void onClickProduct(Product product){
        //Log.d(TAG, "PreparerData: Hai premuto l'item->"+product);
        Action action = new Action(ActionsListProducts.INDEX_ACTION_OPEN_PRODUCT,product);
        SendAction(action);
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
    private void onClickDeleteProduct(String categoryToDelete,int id_productToDelete){
        Product ProductToDelete = null;
        for (Product product: ListProducts) {
            if(  product.getID_product() == id_productToDelete)  ProductToDelete = product;
        }
        Action action = new Action(ActionsListProducts.INDEX_ACTION_DELETE_PRODUCT, ProductToDelete,
                (id_product)-> deleteItemFromRecycle( (Integer)id_product ));
        SendAction(action);
    }
    private void onOrderClick(){

        if(isOrdering){
            adapter_product.hideMoveIcon();
            ImageView_BackOrder.startAnimation(Manager_Animation.getFadeOut(300));
            Button_CancelOrder.startAnimation(Manager_Animation.getFadeOut(300));
            Button_ConfirmOrder.startAnimation(Manager_Animation.getFadeOut(300));
            ConstraintLayout_SearchBox.setVisibility(View.VISIBLE);
            ConstraintLayout_SearchBox.startAnimation(Manager_Animation.getFadeIn(500));
            new Handler(Looper.getMainLooper()).postDelayed(()->
            {
                ImageView_BackOrder.setVisibility(View.INVISIBLE);
                Button_CancelOrder.setVisibility(View.GONE);
                Button_ConfirmOrder.setVisibility(View.GONE);
            },300);
            isOrdering = false;

        }else{
            ListProductsBeforeOrder = new ArrayList<>(ListProducts);

            adapter_product.showMoveIcon();
            ImageView_BackOrder.setVisibility(View.VISIBLE);
            ImageView_BackOrder.startAnimation(Manager_Animation.getFadeIn(300));
            isOrdering = true;
            ConstraintLayout_SearchBox.startAnimation(Manager_Animation.getFadeOut(300));
            Button_CancelOrder.setVisibility(View.VISIBLE);
            Button_ConfirmOrder.setVisibility(View.VISIBLE);
            Button_CancelOrder.startAnimation(Manager_Animation.getFadeIn(500));
            Button_ConfirmOrder.startAnimation(Manager_Animation.getFadeIn(500));
            new Handler(Looper.getMainLooper()).postDelayed(()->
            {
                ConstraintLayout_SearchBox.setVisibility(View.GONE);
            },300);

        }
    }

    private void onCancelOrder(){
        setProductsOnLayout(ListProductsBeforeOrder);

        onOrderClick();
    }

    private void onConfirmOrder(){

        if(adapter_product.getListProducts() != null  && adapter_product.getListProducts().size() > 0){
            if(adapter_product.getListProducts().get(0).getOrder() != null){
                UpdateOrderProducts(adapter_product.getListProducts());
                ListProducts = new ArrayList<>(adapter_product.getListProducts());
            }
        }


        onOrderClick();
    }


    //FUNCTIONAL
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void UpdateOrderProducts(ArrayList<Product> NewListProducts){
        Action action = new Action(ActionsListProducts.INDEX_ACTION_ORDER_PRODUCTS,NewListProducts);
        SendAction(action);
    }
    private void checkEmptyRecycle(){
        if(ListProducts.isEmpty()) {
            TextView_NoProducts.setVisibility(View.VISIBLE);
            Recycler_Products.setVisibility(View.GONE);
            StartAnimationEmptyProducts();
        }else{
            TextView_NoProducts.setVisibility(View.GONE);
            Recycler_Products.setVisibility(View.VISIBLE);
            StartAnimationProducts();
        }
    }

    private void deleteItemFromRecycle(Integer id_product){
        requireActivity().runOnUiThread(() -> {
            for(int indexItem = 0; indexItem < ListProducts.size() ; indexItem++){
                if(ListProducts.get(indexItem).getID_product() == id_product){
                    ListProducts.remove(indexItem);
                    adapter_product.removeItem(id_product);
                    break;
                }
            }
        });
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
        Button_CancelOrder          .startAnimation(Manager_Animation.getFadeOut(300));
        Button_ConfirmOrder          .startAnimation(Manager_Animation.getFadeOut(300));
    }

    public void fromMenuAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationINfromDown(500) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationINfromDown(500) );
    }
    public void fromProductAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationINfromUp(300) );

        ImageView_deleteProduct .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
        ImageView_AddProduct    .startAnimation( Manager_Animation.getTranslationINfromUp(300) );
    }

    public void toMenuAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationOUTtoDown(300) );
        Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioOUTtoRight(300) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationOUTtoDown(300) );
        if(ImageView_BackOrder.getVisibility() == View.VISIBLE) ImageView_BackOrder     .startAnimation( Manager_Animation.getTranslationOUTtoDown(300));
    }

    public void toProductAnimations(){
        Text_View_TitleCategory .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioOUT(300) );
        ImageView_deleteProduct .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        ImageView_AddProduct    .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        ImageView_Order         .startAnimation( Manager_Animation.getTranslationOUTtoUp(300) );
        if(ImageView_BackOrder.getVisibility() == View.VISIBLE) ImageView_BackOrder     .startAnimation( Manager_Animation.getTranslationOUTtoUp(300));
    }

    private void StartAnimationProducts(){
        if(manager.IndexFrom > manager.IndexOnMain){
            Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(300) );
        }
    }
    private void StartAnimationEmptyProducts(){
        TextView_NoProducts         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
}