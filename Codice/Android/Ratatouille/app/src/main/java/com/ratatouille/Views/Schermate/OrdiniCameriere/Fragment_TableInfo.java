package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Product;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_TableInfo extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_TableInfo";

    //LAYOUT
    private android.view.View View_fragment;
    private TextView        Text_View_Title;
    private CardView        CardView_ActivateTable;
    private LinearLayout    LinearLayout_Total;
    private RecyclerView    Recycler_Products;
    private CardView        CardView_AggiungiOrdine;
    //FUNCTIONAL
    private RecycleEventListener        RecycleEventListener;
    private Manager manager;
    //DATA
    private ArrayList<String>   TitleProducts;

    //OTHER...

    public Fragment_TableInfo(Manager manager, int a) {
        this.manager = manager;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__table_info, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
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
        Text_View_Title         = View_fragment.findViewById(R.id.text_view_title);
        CardView_ActivateTable  = View_fragment.findViewById(R.id.card_view_element_sala);
        Recycler_Products       = View_fragment.findViewById(R.id.recycler_table_orders);
        CardView_AggiungiOrdine = View_fragment.findViewById(R.id.card_view_aggiungi);
        LinearLayout_Total      = View_fragment.findViewById(R.id.linear_layout_total);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
        CardView_AggiungiOrdine.setOnClickListener(view -> onClickAddOrder());
    }
    @Override
    public void SetDataOnLayout() {
        initListProductsRV();
    }
    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        Adapter_Product adapter_product = new Adapter_Product(getContext(),TitleProducts, RecycleEventListener,false);
        Recycler_Products.setAdapter(adapter_product);
    }
    //ACTIONS
    private void onClickProduct(Object Product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+Product);
        EndAnimations();
    }
    private void onClickAddOrder(){
        Log.d(TAG, "PreparerData: Hai premuto Aggiungi Ordine");
        EndAnimations();
    }
    //FUNCTIONAL
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        Text_View_Title         .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        CardView_ActivateTable  .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationINfromDown(600));
        LinearLayout_Total      .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }

    @Override
    public void EndAnimations() {
        Text_View_Title         .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_ActivateTable  .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));
        LinearLayout_Total      .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
}