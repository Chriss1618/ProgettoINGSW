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
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Adapters.Adapter_Product;
import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.RecycleEvent;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;
import com.ratatouille.Schermate.Activity_Amministratore;

import java.util.ArrayList;

public class Fragment_ListProducts extends Fragment {


    //SYSTEM
    private static final String TAG = "Fragment_ListProducts";
    private static final String CATEGORY_TAG = "category";

    //LAYOUT
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;

    //FUNCTION
    private Adapter_Product adapter_product;
    private RecycleEvent    EventListener;
    //DATA
    private ArrayList<String> TitleProducts;
    private String Category;
    private String Product;

    //OTHER...

    //LAYOUT
    View view_fragment;
    public Fragment_ListProducts() {
        // Required empty public constructor
        Category = "Nessuna Categoria";
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: here");
        EndAnimatins();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Category = getArguments().getString(CATEGORY_TAG);
        }
        Log.d(TAG, "onCreate: category passed:"+Category);

        EventListener = new RecycleEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_fragment = inflater.inflate(R.layout.fragment__list_products, container, false);

        PreparerData();
        PrepareLayout();

        return view_fragment;
    }

    //DATA
    private void PreparerData(){
        TitleProducts = new ArrayList<>();
        TitleProducts.add("Nome Prodotto");
        TitleProducts.add("Nome Prodotto");
        TitleProducts.add("Nome Prodotto");
        TitleProducts.add("Nome Prodotto");
    }

    //LAYOUT
    private void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
        StartAnimations();
    }

    private void LinkLayout() {
        Text_View_TitleCategory     = view_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Products           = view_fragment.findViewById(R.id.recycler_products);
    }

    private void SetDataOnLayout() {
        initFeaturesRV();
        Text_View_TitleCategory.setText(Category);
    }
    private void SetActionsOfLayout() {
        EventListener.setOnClickItemAdapterListener(Product -> {
            Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
            this.Product = Product;



        });
    }

    private void initFeaturesRV( ){
        adapter_product  = new Adapter_Product(getActivity(),TitleProducts,EventListener.AdapterListener);
        Recycler_Products.setAdapter(adapter_product);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
    }

    //ANIMATIONS
    private void StartAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromDown(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(300));
    }

    public void EndAnimatins(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}