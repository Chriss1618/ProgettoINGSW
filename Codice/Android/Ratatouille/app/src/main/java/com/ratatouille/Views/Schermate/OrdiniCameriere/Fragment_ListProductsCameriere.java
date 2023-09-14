package com.ratatouille.Views.Schermate.OrdiniCameriere;

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
import com.ratatouille.Controllers.Adapters.Adapter_ProductWaiter;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_ListProductsCameriere extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListProductsCa";
    private static final String CATEGORY_TAG = "category";

    //LAYOUT
    private android.view.View View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_Resoconto;
    private ProgressBar     ProgressBar_LoadingProducts;
    private TextView        TextView_NoProducts;
    private EditText        EditText_SearchProduct;
    private ImageView       ImageView_Back;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private final Manager                 manager;
    private Adapter_ProductWaiter adapter_product_waiter;

    private BottomSheetReport BottomSheetReport;
    //DATA
    private ArrayList<Product>  ListProducts;
    private CategoriaMenu       Category_Name;

    //OTHER...

    public Fragment_ListProductsCameriere(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecycleEventListener = new RecycleEventListener();
        Category_Name = (CategoriaMenu) manager.getData();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__list_products__cameriere, container, false);
        PrepareLayout();
        PrepareData();

        StartAnimations();
        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData(){
        ProgressBar_LoadingProducts .setVisibility(View.VISIBLE);
        Recycler_Products           .setVisibility(View.GONE);

        sendRequest();
    }
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), Category_Name, ManagerRequestFactory.INDEX_REQUEST_PRODUCTS,
                (list)-> setProductsOnLayout((ArrayList<Product>) list));
        manager.HandleRequest(request);
    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        BottomSheetReport = new BottomSheetReport(manager,View_fragment);
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {

        ProgressBar_LoadingProducts = View_fragment.findViewById(R.id.progressbar);
        TextView_NoProducts         = View_fragment.findViewById(R.id.text_view_empty);
        ImageView_Back              = View_fragment.findViewById(R.id.ic_back);
        Text_View_TitleCategory     = View_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Products           = View_fragment.findViewById(R.id.recycler_products);
        ImageView_Resoconto         = View_fragment.findViewById(R.id.ic_resoconto);
        EditText_SearchProduct      = View_fragment.findViewById(R.id.edit_text_search_product);
    }

    @Override
    public void SetDataOnLayout() {
        Text_View_TitleCategory.setText(Category_Name.getNomeCategoria());
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_Back      .setOnClickListener( view -> manager.goBack());
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
        RecycleEventListener.setOnClickItemOptionAdapterListener((item, id_product)->onClickAddProduct((Product)item, id_product));
        ImageView_Resoconto .setOnClickListener(view -> onClickResoconto());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = true;
        if(manager.IndexFrom > manager.IndexOnMain) isFromLeft = false;

        adapter_product_waiter = new Adapter_ProductWaiter(manager.context,ListProducts, RecycleEventListener,isFromLeft,false);
        Recycler_Products.setAdapter(adapter_product_waiter);
        checkEmptyRecycle();
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickProduct(Object product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+product);
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_OPEN_INFO_PRODUCT,product);
        SendAction(action);
    }

    private void onClickResoconto(){
        BottomSheetReport.showBottomSheet();
    }

    private void onClickAddProduct(Product Product, int action){
        Log.d(TAG, "onClickAddProduct");
        ArrayList<Product> ListProductsReport = (ArrayList<Product>) manager.getDataAlternative();
        if(ListProductsReport == null) ListProductsReport = new ArrayList<>();
        ListProductsReport.add(Product);
        manager.setDataAlternative(ListProductsReport);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        //this.managerOrdiniCameriere.showFragment(index,msg);
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
                    if ( adapter_product_waiter != null ) adapter_product_waiter.filterList(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(manager.IndexFrom > manager.IndexOnMain){
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
    }
    private void StartAnimationProducts(){
        if(manager.IndexFrom > manager.IndexOnMain){
            Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(300) );
        }
    }
    private void StartAnimationEmptyProducts(){
        TextView_NoProducts         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
    public void toProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));

    }
    public void fromProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromUp(300));

    }
}