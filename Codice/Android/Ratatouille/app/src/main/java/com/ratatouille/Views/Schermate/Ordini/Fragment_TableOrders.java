package com.ratatouille.Views.Schermate.Ordini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_OrdersTable;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Managers.Manager_Ordini;
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
    //private Manager_Ordini          manager_ordini;
    private RecycleEventListener    RecycleEventListener;
    private Manager manager;

    //DATA
    ArrayList<String> OrdersTable;

    //OTHER...


    public Fragment_TableOrders(Manager_Ordini manager_ordini) {
        //this.manager_ordini = manager_ordini;
    }
    public Fragment_TableOrders(Manager manager, int a) {
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
        View_Fragment = inflater.inflate(R.layout.fragment__table_orders, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        OrdersTable = new ArrayList<>();

        OrdersTable.add("Pizza Mandarini");
        OrdersTable.add("Carbonara");
        OrdersTable.add("Insalata Leggera");
        OrdersTable.add("Marinara");
        OrdersTable.add("Polipetti in Salamoia");

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
        initOrdersTableRV();
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
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_Ordini.INDEX_ORDINI_HISTORY_ORDERS,""),
                300);
    }
    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        //this.manager_ordini.showFragment(index,msg);
    }


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