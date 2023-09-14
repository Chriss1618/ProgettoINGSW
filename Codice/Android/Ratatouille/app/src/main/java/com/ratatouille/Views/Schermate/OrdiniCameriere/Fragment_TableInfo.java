package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Product;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;

import kotlinx.coroutines.channels.Send;

public class Fragment_TableInfo extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_TableInfo";

    //LAYOUT
    private android.view.View View_fragment;
    private TextView        Text_View_Title;
    private TextView        Text_View_TavoloTitle;

    private CardView        CardView_ActivateOrder;
    private CardView        CardView_CloseOrder;
    
    private ProgressBar     Progressbar;
    private TextView        TextView_EmptyList;
    private TextView        TextView_StartOrder;
    private LinearLayout    LinearLayout_Total;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_Back;
    private CardView        CardView_AggiungiOrdine;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private Manager                 manager;
    //DATA
    private ArrayList<Product>   TitleProducts;
    private Tavolo               Tavolo;
    //OTHER...

    public Fragment_TableInfo(Manager manager, int a) {
        this.manager = manager;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tavolo = (Tavolo) manager.getData();
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
        ImageView_Back                      = View_fragment.findViewById(R.id.ic_back);
        Progressbar             = View_fragment.findViewById(R.id.progressbar);
        TextView_EmptyList      = View_fragment.findViewById(R.id.text_view_empty);
        TextView_StartOrder     = View_fragment.findViewById(R.id.text_view_start_order);

        Text_View_Title         = View_fragment.findViewById(R.id.text_view_title);
        Text_View_TavoloTitle   = View_fragment.findViewById(R.id.text_view_tavolo_title);
        CardView_CloseOrder     = View_fragment.findViewById(R.id.card_view_occupato);
        CardView_ActivateOrder  = View_fragment.findViewById(R.id.card_view_disponibile);
        Recycler_Products       = View_fragment.findViewById(R.id.recycler_table_orders);
        CardView_AggiungiOrdine = View_fragment.findViewById(R.id.card_view_aggiungi);
        LinearLayout_Total      = View_fragment.findViewById(R.id.linear_layout_total);
    }
    @Override
    public void SetActionsOfLayout() {
        CardView_AggiungiOrdine .setOnClickListener(view -> onClickAddOrder());

        CardView_ActivateOrder  .setOnClickListener(view -> onClickActivateOrder());
        CardView_CloseOrder     .setOnClickListener(view -> onClickCloseOrder());
        ImageView_Back          .setOnClickListener(view -> manager.goBack());
    }

    @Override
    public void SetDataOnLayout() {
        Text_View_Title.setText(Tavolo.getN_Tavolo());
        if(Tavolo.isStateTavolo()){
            CardView_ActivateOrder  .setVisibility(View.VISIBLE);
            CardView_CloseOrder     .setVisibility(View.GONE);
            TextView_StartOrder     .setVisibility(View.VISIBLE);
            Recycler_Products       .setVisibility(View.GONE);
            CardView_AggiungiOrdine .setVisibility(View.GONE);
        }else{
            CardView_ActivateOrder  .setVisibility(View.GONE);
            TextView_StartOrder     .setVisibility(View.GONE);
            CardView_CloseOrder     .setVisibility(View.VISIBLE);
            Recycler_Products       .setVisibility(View.VISIBLE);
            CardView_AggiungiOrdine .setVisibility(View.VISIBLE);
        }
    }
    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        Adapter_Product adapter_product = new Adapter_Product(getContext(),TitleProducts, RecycleEventListener,false);
        Recycler_Products.setAdapter(adapter_product);
    }
    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickActivateOrder(){
        switchToCloseOrder();
    }
    private void onClickCloseOrder(){
        switchToActiveOrder();
    }

    private void onClickAddOrder(){
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_SHOW_MENU_WAITER,null);
        SendAction(action);
    }
    //FUNCTIONAL

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(manager.IndexFrom < manager.IndexOnMain){
            ImageView_Back          .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600) );
        }
        TextView_StartOrder     .startAnimation(Manager_Animation.getFadeIn(600));
        Text_View_Title         .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Text_View_TavoloTitle   .startAnimation(Manager_Animation.getFadeIn(300));
        CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationINfromDown(600));
        LinearLayout_Total      .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }

    @Override
    public void EndAnimations() {
        if(manager.IndexFrom < manager.IndexOnMain){


            ImageView_Back          .startAnimation( Manager_Animation.getTranslateAnimatioOUT(300) );
        }
        Text_View_TavoloTitle   .startAnimation(Manager_Animation.getFadeOut(300));
        TextView_StartOrder     .startAnimation(Manager_Animation.getFadeOut(300));
        Text_View_Title         .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));
        LinearLayout_Total      .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }

    private void switchToActiveOrder(){
        CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(600));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationOUTtoDownS(600));
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            new Handler(Looper.getMainLooper()).postDelayed(()->{
                CardView_CloseOrder     .setVisibility(View.GONE);
                CardView_AggiungiOrdine .setVisibility(View.GONE);
            },300);

            CardView_ActivateOrder  .setVisibility(View.VISIBLE);
            TextView_StartOrder     .setVisibility(View.VISIBLE);
            CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
            TextView_StartOrder     .startAnimation(Manager_Animation.getFadeIn(600));
        },300);
    }

    private void switchToCloseOrder(){
        CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(600));
        TextView_StartOrder     .startAnimation(Manager_Animation.getFadeOut(600));
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            new Handler(Looper.getMainLooper()).postDelayed(()->{
                CardView_ActivateOrder.setVisibility(View.GONE);
                TextView_StartOrder.setVisibility(View.GONE);
            },300);
            CardView_CloseOrder     .setVisibility(View.VISIBLE);
            CardView_AggiungiOrdine .setVisibility(View.VISIBLE);
            CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
            CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationINfromDown(600));
        },300);
    }
}