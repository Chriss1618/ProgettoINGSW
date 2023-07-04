package com.ratatouille.Views.Schermate.Ordini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_TablesOrder;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_Ordini;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListOrders extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListOrders";

    //LAYOUT
    android.view.View View_Fragment;
    TextView            TextView_Title;
    RecyclerView        recyclerView_TablesOrders;
    ImageView           ImageView_HistoryOrders;

    //FUNCTIONAl
    //private Manager_Ordini          manager_ordini;
    private Manager manager;
    private RecycleEventListener    RecycleEventListener;

    //DATA
    ArrayList<String>       NumberTables;

    //OTHER...

    public Fragment_ListOrders(Manager_Ordini manager_ordini) {
        //this.manager_ordini = manager_ordini;
    }
    public Fragment_ListOrders(Manager manager, int a) {
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
        View_Fragment = inflater.inflate(R.layout.fragment__list_orders, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        NumberTables = new ArrayList<>();
        NumberTables.add("Tavolo 1");
        NumberTables.add("Tavolo 2");
        NumberTables.add("Tavolo 3");
        NumberTables.add("Tavolo 4");
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
        ImageView_HistoryOrders     = View_Fragment.findViewById(R.id.ic_history_order);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener(this::onClickTable);
        ImageView_HistoryOrders.setOnClickListener(view -> onHistoryClick());
    }
    @Override
    public void SetDataOnLayout() {
        initFeaturesRV();
    }

    private void initFeaturesRV(){
        Adapter_TablesOrder adapter_tablesOrder = new Adapter_TablesOrder(NumberTables, RecycleEventListener);
        recyclerView_TablesOrders.setAdapter(adapter_tablesOrder);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_TablesOrders.setLayoutManager(mLayoutManager);
        recyclerView_TablesOrders.setNestedScrollingEnabled(false);
    }

    //ACTIONS
    private void onClickTable(String table){
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                sendActionToManager(Manager_Ordini.INDEX_ORDINI_TABLE_ORDERS,table),
                300);
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
        recyclerView_TablesOrders   .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        recyclerView_TablesOrders   .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));

    }
}