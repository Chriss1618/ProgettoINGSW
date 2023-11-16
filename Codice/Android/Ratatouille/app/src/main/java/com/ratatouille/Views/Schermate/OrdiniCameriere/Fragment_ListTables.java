package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_TablesWaiter;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Tavolo;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import java.util.ArrayList;

public class Fragment_ListTables extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListTables";

    //LAYOUT
    private android.view.View   View_fragment;
    private TextView            Text_View_Title;
    private RecyclerView        Recycler_TableWaiter;

    private TextView        Text_View_Empty;
    private ProgressBar     ProgressBar;
    //FUNCTIONAL
    private RecycleEventListener        RecycleEventListener;
    private Manager                     manager;
    //DATA
    private ArrayList<Tavolo>   TablesWaiter;

    //OTHER...

    public Fragment_ListTables(Manager manager, int a) {
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
        View_fragment = inflater.inflate(R.layout.fragment__list_tables, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_fragment;
    }
    //DATA
    @Override
    public void PrepareData() {
        TablesWaiter = new ArrayList<>();
        sendRequest();
    }
    private void sendRequest(){
        ProgressBar.setVisibility(View.VISIBLE);
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_TAVOLI,
                (list)-> setTavoliOnLayout((ArrayList<Tavolo>) list));
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
        Text_View_Empty      = View_fragment.findViewById(R.id.text_view_empty);
        ProgressBar          = View_fragment.findViewById(R.id.progressbar);
        Text_View_Title      = View_fragment.findViewById(R.id.text_view_title);
        Recycler_TableWaiter = View_fragment.findViewById(R.id.recycler_tables_waiter);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener((tavolo)-> onClickTable( (Tavolo) tavolo) );
    }
    @Override
    public void SetDataOnLayout() {

    }
    private void initTablesRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        Recycler_TableWaiter.setLayoutManager(mLayoutManager);
        Recycler_TableWaiter.setNestedScrollingEnabled(false);
        Adapter_TablesWaiter adapter_tablesWaiter = new Adapter_TablesWaiter(TablesWaiter, RecycleEventListener);
        Recycler_TableWaiter.setAdapter(adapter_tablesWaiter);
        checkEmptyRecycle();
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickTable(Tavolo table){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+table);
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_SHOW_TABLE,table);
        SendAction(action);
    }

    private void setTavoliOnLayout(ArrayList<Tavolo> list){
        TablesWaiter = list;
        requireActivity().runOnUiThread(() -> {
            initTablesRV();
            ProgressBar.setVisibility(View.GONE);
        });
    }

    private void checkEmptyRecycle(){
        if(TablesWaiter.isEmpty()) {
            Text_View_Empty.setVisibility(View.VISIBLE);
            Recycler_TableWaiter.setVisibility(View.GONE);
            StartAnimationEmptyRV();
        }else{
            Text_View_Empty.setVisibility(View.GONE);
            Recycler_TableWaiter.setVisibility(View.VISIBLE);
            StartAnimationRV();
        }
    }

    //FUNCTIONAL
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        Text_View_Title.startAnimation(Manager_Animation.getTranslationINfromUp(600));
    }
    @Override
    public void EndAnimations() {
        Text_View_Title.startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_TableWaiter.startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
    private void StartAnimationRV(){
        Recycler_TableWaiter.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
    }

    private void StartAnimationEmptyRV(){
        Text_View_Empty.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
    }

}