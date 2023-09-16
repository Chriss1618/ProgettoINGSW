package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Looper;
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
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
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
    private TextView        Text_View_TotalePrice;
    private TextView        Text_View_TavoloTitle;

    private CardView        CardView_ActivateOrder;
    private CardView        CardView_CloseOrder;
    
    private ProgressBar     ProgressBar;
    private TextView        TextView_EmptyList;
    private TextView        TextView_StartOrder;
    private LinearLayout    LinearLayout_Total;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_Back;
    private CardView        CardView_AggiungiOrdine;

    ImageView       Image_View_State_green;
    ImageView       Image_View_State_red;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private Manager                 manager;
    //DATA
    private ArrayList<Product>   TitleProducts;
    private Tavolo               Tavolo;
    private Ordine               Ordine;
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

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData(){
        if(!Tavolo.isStateTavolo()){
            ProgressBar.setVisibility(View.VISIBLE);
            Recycler_Products.setVisibility(View.GONE);
            sendRequest();
        }
    }

    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), Tavolo, ManagerRequestFactory.INDEX_REQUEST_TAVOLO_INFO,
                (ordine)-> setProductOnLayout((Ordine) ordine));
        manager.HandleRequest(request);
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
        ImageView_Back          = View_fragment.findViewById(R.id.ic_back);
        ProgressBar             = View_fragment.findViewById(R.id.progressbar);
        TextView_EmptyList      = View_fragment.findViewById(R.id.text_view_empty);
        TextView_StartOrder     = View_fragment.findViewById(R.id.text_view_start_order);

        Image_View_State_green  = View_fragment.findViewById(R.id.ic_state_green);
        Image_View_State_red    = View_fragment.findViewById(R.id.ic_state_red);
        Text_View_Title         = View_fragment.findViewById(R.id.text_view_title);
        Text_View_TotalePrice   = View_fragment.findViewById(R.id.text_view_price);
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
            setLayoutDisponibile();
        }else{
            setLayoutOccupato();
        }
    }

    private void setLayoutOccupato(){
        CardView_ActivateOrder  .setVisibility(View.GONE);
        TextView_StartOrder     .setVisibility(View.GONE);
        CardView_CloseOrder     .setVisibility(View.VISIBLE);
        Recycler_Products       .setVisibility(View.VISIBLE);
        CardView_AggiungiOrdine .setVisibility(View.VISIBLE);

        Image_View_State_green.setImageResource(R.drawable.ic_state_neautral);
        Image_View_State_red.setImageResource(R.drawable.ic_state_red);
    }

    private void setLayoutDisponibile(){
        CardView_ActivateOrder  .setVisibility(View.VISIBLE);
        CardView_CloseOrder     .setVisibility(View.GONE);
        TextView_StartOrder     .setVisibility(View.VISIBLE);
        Recycler_Products       .setVisibility(View.GONE);
        CardView_AggiungiOrdine .setVisibility(View.GONE);

        Image_View_State_red.setImageResource(R.drawable.ic_state_neautral);
        Image_View_State_green.setImageResource(R.drawable.ic_state_green);
    }
    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        Adapter_Product adapter_product = new Adapter_Product(getContext(),TitleProducts, RecycleEventListener,false);
        Recycler_Products.setAdapter(adapter_product);
        checkEmptyRecycle();
    }
    private void clearLayout(){
        requireActivity().runOnUiThread(() -> {
            if(Ordine.getTavolo().getProdottiOrdinati() != null){
                Ordine.getTavolo().getProdottiOrdinati().clear();
                Ordine.setPrezzoTotale("0.00");
                Text_View_TotalePrice.setText(Ordine.getPrezzoTotale());
            }
            TitleProducts = new ArrayList<>();
            initListProductsRV();
        });

    }
    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickActivateOrder(){
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_CREATE_ORDER,Tavolo,this::switchToCloseOrder);
        SendAction(action);
    }

    private void onClickCloseOrder(){
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_CLOSE_TABLE,Tavolo,this::switchToActiveOrder);
        SendAction(action);
    }

    private void onClickAddOrder(){
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_SHOW_MENU_WAITER,Ordine);
        SendAction(action);
    }
    //FUNCTIONAL
    private void setProductOnLayout(Ordine ordine){
        requireActivity().runOnUiThread(() -> {
            Ordine = ordine;
            TitleProducts =  Ordine.getTavolo().getProdottiOrdinati();
            Text_View_TotalePrice.setText(Ordine.getPrezzoTotale());
            initListProductsRV();
            ProgressBar.setVisibility(View.GONE);
        });
    }

    private void checkEmptyRecycle(){
        if(TitleProducts.isEmpty()) {
            TextView_EmptyList.setVisibility(View.VISIBLE);
            Recycler_Products.setVisibility(View.GONE);
            StartAnimationEmptyCategories();
        }else{
            TextView_EmptyList.setVisibility(View.GONE);
            Recycler_Products.setVisibility(View.VISIBLE);
            StartAnimationProducts();
        }
    }
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

        if(Tavolo.isStateTavolo()){
            CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        }else{
            CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        }

        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));
        LinearLayout_Total      .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
    private void StartAnimationProducts(){
        Recycler_Products         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }

    private void StartAnimationEmptyCategories(){
        TextView_EmptyList        .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
    private void switchToActiveOrder(){
        Tavolo = (Tavolo) manager.getData();
        clearLayout();
        TextView_EmptyList.setVisibility(View.GONE);
        CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(600));
        CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationOUTtoDownS(600));
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            new Handler(Looper.getMainLooper()).postDelayed(()->{
                CardView_CloseOrder     .setVisibility(View.GONE);
                CardView_AggiungiOrdine .setVisibility(View.GONE);
                Image_View_State_red.setImageResource(R.drawable.ic_state_neautral);
                Image_View_State_green.setImageResource(R.drawable.ic_state_green);
            },300);

            CardView_ActivateOrder  .setVisibility(View.VISIBLE);
            TextView_StartOrder     .setVisibility(View.VISIBLE);
            CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
            TextView_StartOrder     .startAnimation(Manager_Animation.getFadeIn(600));
        },300);
    }

    private void switchToCloseOrder(){
        Ordine = (Ordine) manager.getData();
        Tavolo = Ordine.getTavolo();
        CardView_ActivateOrder  .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(600));
        TextView_StartOrder     .startAnimation(Manager_Animation.getFadeOut(600));
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            new Handler(Looper.getMainLooper()).postDelayed(()->{
                CardView_ActivateOrder.setVisibility(View.GONE);
                TextView_StartOrder.setVisibility(View.GONE);
                Image_View_State_green.setImageResource(R.drawable.ic_state_neautral);
                Image_View_State_red.setImageResource(R.drawable.ic_state_red);
            },300);
            CardView_CloseOrder     .setVisibility(View.VISIBLE);
            CardView_AggiungiOrdine .setVisibility(View.VISIBLE);
            CardView_CloseOrder     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
            CardView_AggiungiOrdine .startAnimation(Manager_Animation.getTranslationINfromDown(600));
        },300);
    }
}