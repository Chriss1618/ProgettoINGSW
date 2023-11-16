package com.ratatouille.Views.Schermate.Ordini;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_OrderHistory;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;


public class Fragment_HystoryOrders extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_HystoryOrders";

    //LAYOUT
    android.view.View View_Fragment;
    TextView            TextView_Title;
    RecyclerView        recyclerView_OrdersTable;

    private ImageView           ImageView_Back;
    private TextView            TextView_NoOrder;
    //FUNCTIONAl
    private Manager manager;
    private RecycleEventListener    RecycleEventListener;

    int positionCurrentTable;

    //DATA
    ArrayList<Product> OrdersTable;
    ArrayList<Product> ProductsReady;
    //OTHER...

    public Fragment_HystoryOrders(Manager manager, int a) {
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
        View_Fragment = inflater.inflate(R.layout.fragment__history_orders, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        OrdersTable = new ArrayList<>();
        ArrayList<Ordine> ListOrdini = (ArrayList<Ordine>) manager.getData();
        String id_tavolo = (String) manager.getDataAlternative();

        for ( int position = 0; position < ListOrdini.size();position++) {
            Ordine ordine = ListOrdini.get(position);
            if(ordine.getTavolo().getId_Tavolo().equals(id_tavolo)){
                OrdersTable = ordine.getTavolo().getProdottiOrdinati();
                positionCurrentTable = position;
            }
        }
        initHistoryOrdersRV();

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
        ImageView_Back              = View_Fragment.findViewById(R.id.ic_back);
        TextView_Title              = View_Fragment.findViewById(R.id.text_view_title);
        recyclerView_OrdersTable    = View_Fragment.findViewById(R.id.recycler_history_orders);
        TextView_NoOrder            = View_Fragment.findViewById(R.id.text_view_empty);
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_Back          .setOnClickListener(            view -> manager.goBack());
    }
    @Override
    public void SetDataOnLayout() {

    }

    private void initHistoryOrdersRV(){
        ProductsReady = new ArrayList<>();
        for(Product product : OrdersTable){
            if(!product.getId_User().equals("null")) ProductsReady.add(product);
        }
        Adapter_OrderHistory adapter_orderHistory = new Adapter_OrderHistory(ProductsReady, RecycleEventListener);
        recyclerView_OrdersTable.setAdapter(adapter_orderHistory);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_OrdersTable.setLayoutManager(mLayoutManager);
        recyclerView_OrdersTable.setNestedScrollingEnabled(false);
        checkEmptyRecycle();
    }

    //FUNCTIONAL
    private void checkEmptyRecycle(){
        if(ProductsReady.isEmpty()) {
            TextView_NoOrder.setVisibility(View.VISIBLE);
            recyclerView_OrdersTable.setVisibility(View.GONE);
            StartAnimationEmptyOrder();
        }else{
            TextView_NoOrder.setVisibility(View.GONE);
            recyclerView_OrdersTable.setVisibility(View.VISIBLE);
            StartAnimationTables();
        }
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

    private void StartAnimationEmptyOrder(){
        TextView_NoOrder         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
    private void StartAnimationTables(){
        recyclerView_OrdersTable   .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
}