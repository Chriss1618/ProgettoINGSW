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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_OrdersTable;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_TableOrders extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_TableOrders";

    //LAYOUT
    android.view.View View_Fragment;
    TextView            TextView_Title;
    RecyclerView        recyclerView_OrdersTable;
    ImageView           ImageView_HistoryOrders;
    Button              Button_ConfirmOrders;

    //FUNCTIONAl
    private RecycleEventListener    RecycleEventListener;
    private Manager manager;

    //DATA
    ArrayList<Product> OrdersTable;
    int positionCurrentTable;
    //Others
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
    public Fragment_TableOrders(Manager manager, int a) {
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
        View_Fragment = inflater.inflate(R.layout.fragment__table_orders, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        ArrayList<Ordine> ListOrdini= (ArrayList<Ordine>) manager.getData();
        String id_tavolo = (String) manager.getDataAlternative();
        for ( int position = 0; position < ListOrdini.size();position++) {
            Ordine ordine = ListOrdini.get(position);
            if(ordine.getTavolo().getId_Tavolo().equals(id_tavolo)){
                OrdersTable = ordine.getTavolo().getProdottiOrdinati();
                positionCurrentTable = position;
            }
        }
        initOrdersTableRV();
        TextView_Title.setText(ListOrdini.get(positionCurrentTable).getTavolo().getN_Tavolo());
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
        recyclerView_OrdersTable    = View_Fragment.findViewById(R.id.recycler_orders);
        ImageView_HistoryOrders     = View_Fragment.findViewById(R.id.ic_history_order);
        Button_ConfirmOrders        = View_Fragment.findViewById(R.id.button_confirm_orders);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnCheckItemAdapterListener(this::onChedkItem);
        ImageView_HistoryOrders.setOnClickListener(view -> onHistoryClick());
    }
    @Override
    public void SetDataOnLayout() {

    }
    private void initOrdersTableRV(){
        Adapter_OrdersTable adapter_ordersTable = new Adapter_OrdersTable(OrdersTable, RecycleEventListener);
        recyclerView_OrdersTable.setAdapter(adapter_ordersTable);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_OrdersTable.setLayoutManager(mLayoutManager);
        recyclerView_OrdersTable.setNestedScrollingEnabled(false);
    }
    //ACTIONS
    private void onChedkItem(String name,Boolean isChecked){

    }
    private void onHistoryClick(){
        EndAnimations();
    }
    //FUNCTIONAL
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Button_ConfirmOrders        .startAnimation(Manager_Animation.getTranslationINfromDown(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Button_ConfirmOrders        .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));
    }
}