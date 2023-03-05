package com.ratatouille.Schermate.Inventario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ratatouille.Adapters.Adapter_Product;
import com.ratatouille.Adapters.Adapter_ProductExist;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListInventary extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListInventary";

    //LAYOUT
    private View            View_Fragment;
    private RecyclerView    Recycler_Products_Exist;

    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;

    //DATA
    private ArrayList<String>   TitleProducts;
    //OTHER...

    public Fragment_ListInventary() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment =  inflater.inflate(R.layout.fragment__list_inventary, container, false);
        PrepareData();
        PrepareLayout();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        TitleProducts = new ArrayList<>();
        TitleProducts.add("Pizza Tonno");
        TitleProducts.add("Pizza Margherita");
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
        Recycler_Products_Exist = View_Fragment.findViewById(R.id.recycler_products_exist);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
    }



    @Override
    public void SetDataOnLayout() {
        initListProductsMissingRV();
    }

    private void initListProductsMissingRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products_Exist.setLayoutManager(mLayoutManager);
        Recycler_Products_Exist.setNestedScrollingEnabled(false);
        Adapter_ProductExist adapter_product = new Adapter_ProductExist(TitleProducts, RecycleEventListener);
        Recycler_Products_Exist.setAdapter(adapter_product);
    }

    //ACTIONS
    private void onClickProduct(String s) {
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {

    }

    @Override
    public void EndAnimations() {

    }
}