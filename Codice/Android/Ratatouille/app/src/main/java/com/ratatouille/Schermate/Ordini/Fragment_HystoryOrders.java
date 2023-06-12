package com.ratatouille.Schermate.Ordini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_OrderHistory;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_Ordini;
import com.ratatouille.R;

import java.util.ArrayList;


public class Fragment_HystoryOrders extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_HystoryOrders";

    //LAYOUT
    android.view.View View_Fragment;
    TextView            TextView_Title;
    RecyclerView        recyclerView_OrdersTable;

    //FUNCTIONAl
    private Manager_Ordini          manager_ordini;
    private RecycleEventListener    RecycleEventListener;

    //DATA
    ArrayList<String> OrdersTable;

    //OTHER...


    public Fragment_HystoryOrders(Manager_Ordini manager_ordini) {
        this.manager_ordini = manager_ordini;
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
        View_Fragment = inflater.inflate(R.layout.fragment__history_orders, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        OrdersTable = new ArrayList<>();

        OrdersTable.add("Cibo non Buono (3)");
        OrdersTable.add("Morte in scatola (1)");
        OrdersTable.add("Pizza Mangiabile");
        OrdersTable.add("Lamentela Cliente");

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
        recyclerView_OrdersTable    = View_Fragment.findViewById(R.id.recycler_history_orders);
    }
    @Override
    public void SetActionsOfLayout() {

    }
    @Override
    public void SetDataOnLayout() {
        initHistoryOrdersRV();
    }

    private void initHistoryOrdersRV(){
        Adapter_OrderHistory adapter_orderHistory = new Adapter_OrderHistory(OrdersTable, RecycleEventListener);
        recyclerView_OrdersTable.setAdapter(adapter_orderHistory);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_OrdersTable.setLayoutManager(mLayoutManager);
        recyclerView_OrdersTable.setNestedScrollingEnabled(false);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}