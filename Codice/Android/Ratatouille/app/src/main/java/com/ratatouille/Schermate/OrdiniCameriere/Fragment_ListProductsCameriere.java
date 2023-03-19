package com.ratatouille.Schermate.OrdiniCameriere;

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
import com.ratatouille.Adapters.Adapter_ProductWaiter;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListProductsCameriere extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListProductsCa";
    private static final String CATEGORY_TAG = "category";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_Resoconto;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private Manager_Ordini_Cameriere managerOrdiniCameriere;
    //DATA
    private ArrayList<String>   TitleProducts;
    private String              Category_Name;

    //OTHER...

    public Fragment_ListProductsCameriere(Manager_Ordini_Cameriere managerOrdiniCameriere) {
        this.managerOrdiniCameriere = managerOrdiniCameriere;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Category_Name = getArguments().getString(CATEGORY_TAG);
        }
        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__list_products__cameriere, container, false);
        PrepareData();
        PrepareLayout();

        StartAnimations();
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
    }

    @Override
    public void LinkLayout() {
        Text_View_TitleCategory     = View_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Products           = View_fragment.findViewById(R.id.recycler_products);
        ImageView_Resoconto        = View_fragment.findViewById(R.id.ic_resoconto);
    }

    @Override
    public void SetDataOnLayout() {
        initListProductsRV();
        Text_View_TitleCategory.setText(Category_Name);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
        RecycleEventListener.setOnClickItemOptionAdapterListener(this::onClickAddProduct);
        ImageView_Resoconto .setOnClickListener(view -> onClickResoconto());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = true;
        if(managerOrdiniCameriere.from > managerOrdiniCameriere.onMain) isFromLeft = false;

        Adapter_ProductWaiter adapter_product_waiter = new Adapter_ProductWaiter(getContext(),TitleProducts, RecycleEventListener,isFromLeft,false);
        Recycler_Products.setAdapter(adapter_product_waiter);
    }

    //ACTIONS
    private void onClickProduct(String Product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
        toProductAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_Ordini_Cameriere.INDEX_ORDINI_CAMERIERE_INFO_PRODUCT,Product),
                300);
    }

    private void onClickResoconto(){
        managerOrdiniCameriere.showBottomSheet();
    }

    private void onClickAddProduct(String Product, int action){
        Log.d(TAG, "onClickAddProduct");
        this.managerOrdiniCameriere.addProduct(Product);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.managerOrdiniCameriere.showFragment(index,msg);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(managerOrdiniCameriere.from > managerOrdiniCameriere.onMain){
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