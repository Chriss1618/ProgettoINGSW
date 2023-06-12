package com.ratatouille.Schermate.OrdiniCameriere;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_TablesWaiter;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListTables extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListTables";

    //LAYOUT
    private android.view.View View_fragment;
    private TextView        Text_View_Title;
    private RecyclerView    Recycler_TableWaiter;

    //FUNCTIONAL
    private RecycleEventListener        RecycleEventListener;
    private Manager_Ordini_Cameriere    managerOrdiniCameriere;

    //DATA
    private ArrayList<String>   TablesWaiter;

    //OTHER...

    public Fragment_ListTables(Manager_Ordini_Cameriere managerOrdiniCameriere) {
        this.managerOrdiniCameriere = managerOrdiniCameriere;
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
        View_fragment = inflater.inflate(R.layout.fragment__list_tables, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_fragment;
    }
    //DATA
    @Override
    public void PrepareData() {
        TablesWaiter = new ArrayList<>();
        TablesWaiter.add("1");
        TablesWaiter.add("2");
        TablesWaiter.add("3");
        TablesWaiter.add("4");
        TablesWaiter.add("5");
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
        Text_View_Title      = View_fragment.findViewById(R.id.text_view_title);
        Recycler_TableWaiter = View_fragment.findViewById(R.id.recycler_tables_waiter);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickTable);
    }
    @Override
    public void SetDataOnLayout() {
        initTablesRV();
    }
    private void initTablesRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        Recycler_TableWaiter.setLayoutManager(mLayoutManager);
        Recycler_TableWaiter.setNestedScrollingEnabled(false);
        Adapter_TablesWaiter adapter_tablesWaiter = new Adapter_TablesWaiter(TablesWaiter, RecycleEventListener);
        Recycler_TableWaiter.setAdapter(adapter_tablesWaiter);
    }

    //ACTIONS
    private void onClickTable(String table){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+table);
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_Ordini_Cameriere.INDEX_ORDINI_CAMERIERE_TABLE_INFO,table),
                300);
    }


    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.managerOrdiniCameriere.showFragment(index,msg);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        Text_View_Title.startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Recycler_TableWaiter.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
    }
    @Override
    public void EndAnimations() {
        Text_View_Title.startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_TableWaiter.startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
}