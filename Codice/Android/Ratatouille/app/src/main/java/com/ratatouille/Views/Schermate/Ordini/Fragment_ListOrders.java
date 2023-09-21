package com.ratatouille.Views.Schermate.Ordini;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_TablesOrder;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsOrdini;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.API.Firebase.FCMService;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_ListOrders extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListOrders";

    //LAYOUT
    private android.view.View   View_Fragment;
    private TextView            TextView_Title;
    private RecyclerView        recyclerView_TablesOrders;
    private TextView            TextView_NoOrder;
    private ProgressBar         ProgressBar_LoadingProducts;

    //FUNCTIONAl
    private Manager manager;
    private RecycleEventListener    RecycleEventListener;
    private FCMService              ServiceFirebase;
    //DATA
    ArrayList<Ordine> ListOrdini;

    //OTHER...
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: Azione ricevuta da FCM");
            PrepareData();

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("getListOrder");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver);
    }
    public Fragment_ListOrders(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecycleEventListener = new RecycleEventListener();


    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment = inflater.inflate(R.layout.fragment__list_orders, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        ListOrdini = new ArrayList<>();
        sendRequest();
    }
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_ORDINI_TAVOLO,
                (list)-> setTablesOnLayout((ArrayList<Ordine>) list));
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
        TextView_Title              = View_Fragment.findViewById(R.id.text_view_title);
        recyclerView_TablesOrders   = View_Fragment.findViewById(R.id.recycler_orders);

        ProgressBar_LoadingProducts = View_Fragment.findViewById(R.id.progressbar);
        TextView_NoOrder            = View_Fragment.findViewById(R.id.text_view_empty);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener((ordine) ->onClickTable((Ordine) ordine));
    }
    @Override
    public void SetDataOnLayout() {
    }

    private void initFeaturesRV(){

        Adapter_TablesOrder adapter_tablesOrder = new Adapter_TablesOrder(ListOrdini, RecycleEventListener);
        recyclerView_TablesOrders.setAdapter(adapter_tablesOrder);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_TablesOrders.setLayoutManager(mLayoutManager);
        recyclerView_TablesOrders.setNestedScrollingEnabled(false);

        checkEmptyRecycle();
    }

    private void setTablesOnLayout(ArrayList<Ordine> list){
        requireActivity().runOnUiThread(() -> {
            ListOrdini = new ArrayList<>();
            for(Ordine ordine : list){
                for(int i = 0 ; i< ordine.getTavolo().getProdottiOrdinati().size();i++){
                    Product product = ordine.getTavolo().getProdottiOrdinati().get(i);
                    if(  product.getId_User().equals("null") ){
                        ListOrdini.add(ordine);
                        break;
                    }
                }
            }
            //ListOrdini = list;
            initFeaturesRV();
            ProgressBar_LoadingProducts.setVisibility(View.GONE);

        });
    }
    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickTable(Ordine ordine){

        manager.setDataAlternative(ordine.getTavolo().getId_Tavolo());
        Action action = new Action(ActionsOrdini.INDEX_ACTION_SELECT_TABLE, ListOrdini);
        SendAction(action);
    }
    private void onHistoryClick(){
        EndAnimations();
    }

    //FUNCTIONAL
    private void checkEmptyRecycle(){
        if(ListOrdini.isEmpty()) {
            TextView_NoOrder.setVisibility(View.VISIBLE);
            recyclerView_TablesOrders.setVisibility(View.GONE);
            StartAnimationEmptyTables();
        }else{
            TextView_NoOrder.setVisibility(View.GONE);
            recyclerView_TablesOrders.setVisibility(View.VISIBLE);
            StartAnimationTables();
        }
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationINfromUp(600));

    }
    @Override
    public void EndAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        recyclerView_TablesOrders   .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));

    }

    private void StartAnimationEmptyTables(){
        TextView_NoOrder         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }

    private void StartAnimationTables(){
        recyclerView_TablesOrders   .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
}