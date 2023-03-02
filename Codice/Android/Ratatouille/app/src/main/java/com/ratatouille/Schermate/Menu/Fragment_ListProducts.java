package com.ratatouille.Schermate.Menu;

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
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListProducts extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListProducts";
    private static final String CATEGORY_TAG = "category";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_AddProduct;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private Manager_MenuFragments   manager_menuFragments;
    //DATA
    private ArrayList<String>   TitleProducts;
    private String              Category_Name;

    //OTHER...

    //LAYOUT
    public Fragment_ListProducts(Manager_MenuFragments manager_menuFragments) {
        // Required empty public constructor
        this.manager_menuFragments = manager_menuFragments;
        Category_Name = "Nessuna Categoria";
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
    }

    @Override
    public void SetDataOnLayout() {
        initListProductsRV();
        Text_View_TitleCategory.setText(Category_Name);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
        ImageView_AddProduct.setOnClickListener(view -> onClickAddProduct());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        Adapter_Product adapter_product = new Adapter_Product(TitleProducts, RecycleEventListener);
        Recycler_Products.setAdapter(adapter_product);
    }

    //ACTIONS
    private void onClickProduct(String Product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
        toProductAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                sendActionToManager(Manager_MenuFragments.INDEX_MENU_INFO_PRODUCT,Product),
                300);
    }
    private void onClickAddProduct(){
        toProductAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_MenuFragments.INDEX_MENU_NEW_PRODUCT,Category_Name),
                300);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.manager_menuFragments.showFragment(index,msg);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(manager_menuFragments.from > manager_menuFragments.onMain){
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
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(300));

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