package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ratatouille.Adapters.Adapter_ProductWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Interfaces.ViewLayout;
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
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private Manager manager;
    //DATA
    private ArrayList<String>   TitleProducts;
    private String              Category_Name;

    //OTHER...

    public Fragment_ListProductsCameriere(Manager manager, int a) {
        this.manager = manager;
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
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        if(manager.from > manager.onMain) isFromLeft = false;

        Adapter_ProductWaiter adapter_product_waiter = new Adapter_ProductWaiter(TitleProducts, RecycleEventListener,isFromLeft,false);
        Recycler_Products.setAdapter(adapter_product_waiter);
    }

    //ACTIONS
    private void onClickProduct(String Product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
        toProductAnimations();
    }

    private void onClickResoconto(){
//        manager.showBottomSheet();
    }

    private void onClickAddProduct(String Product, int action){
        Log.d(TAG, "onClickAddProduct");
        //this.managerOrdiniCameriere.addProduct(Product);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        //this.managerOrdiniCameriere.showFragment(index,msg);
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