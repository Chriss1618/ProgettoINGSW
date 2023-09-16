package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ratatouille.Controllers.Adapters.Adapter_ProductWaiter;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class BottomSheetReport implements ViewLayout {
    private static final String TAG = "BottomSheetReport";
    //LAYOUT
    private final View          View_fragment;
    private RecyclerView        Recycler_Products;
    private CardView            CardView_ClearReport;
    private CardView            CardView_SendToKitchen;
    private TextView            TextView_Empty;

    //FUNCTIONAL
    private BottomSheetDialog           bottomSheetDialog;
    private final Manager               manager;
    private RecycleEventListener        RecycleEventListener;
    private Adapter_ProductWaiter       adapter_product_waiter;
    private Fragment_ListCategoryCameriere CallerFragment;
    private Fragment_ListProductsCameriere CallerFragment2;
    //DATA
    private ArrayList<Product>      ListProducts;

    public BottomSheetReport(Manager manager, View fragmentView,Fragment_ListCategoryCameriere callerFragment) {
        this.RecycleEventListener   = new RecycleEventListener();
        bottomSheetDialog           = new BottomSheetDialog(manager.context, R.style.BottomSheetDialogTheme);

        this.CallerFragment     = callerFragment;
        this.manager            = manager;
        this.View_fragment      = fragmentView;
        PrepareLayout();
    }

    public BottomSheetReport(Manager manager, View fragmentView,Fragment_ListProductsCameriere callerFragment) {
        this.RecycleEventListener   = new RecycleEventListener();
        bottomSheetDialog           = new BottomSheetDialog(manager.context, R.style.BottomSheetDialogTheme);
        this.CallerFragment = null;
        this.CallerFragment2     = callerFragment;
        this.manager            = manager;
        this.View_fragment      = fragmentView;
        PrepareLayout();
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
        bottomSheetDialog.setContentView(LayoutInflater.from(manager.context).inflate(R.layout.bottom_sheet_record_orders,
        View_fragment.findViewById(R.id.Bottom_sheet)));

        Recycler_Products       = bottomSheetDialog.findViewById(R.id.recycler_products);
        CardView_ClearReport    = bottomSheetDialog.findViewById(R.id.card_view_cancel);
        CardView_SendToKitchen  = bottomSheetDialog.findViewById(R.id.card_view_send_chef);
        TextView_Empty          = bottomSheetDialog.findViewById(R.id.text_view_empty);
    }

    @Override
    public void SetActionsOfLayout() {
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = bottomSheetDialog.getBehavior();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RecycleEventListener    .setOnClickItemOptionAdapterListener((product,action) -> removeProduct( (Product) product));
        CardView_SendToKitchen  .setOnClickListener(view -> onClickSendToKitchen());
        CardView_ClearReport    .setOnClickListener(view -> onClickCancel());
    }

    @Override
    public void SetDataOnLayout() {

    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(manager.context, 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(true);

        adapter_product_waiter = new Adapter_ProductWaiter(manager.context,ListProducts, RecycleEventListener,false,true);
        Recycler_Products.setAdapter(adapter_product_waiter);
        if(ListProducts.isEmpty()){
            Recycler_Products.setVisibility(View.GONE);
            TextView_Empty.setVisibility(View.VISIBLE);
        }else{
            Recycler_Products.setVisibility(View.VISIBLE);
            TextView_Empty.setVisibility(View.GONE);
        }
    }
    //DATA
    @Override
    public void PrepareData() {
        ListProducts            = (ArrayList<Product>) manager.getDataAlternative();
        if(ListProducts == null) ListProducts = new ArrayList<>();
    }
    //FUNCTIONAL
    public void showBottomSheet(){
        PrepareData();
        initListProductsRV();
        bottomSheetDialog.show();
    }

    //ACTIONS
    private void onClickSendToKitchen(){
        Log.d(TAG, "onClickSendToKitchen: Elaboro");
        if(CallerFragment != null) CallerFragment.SendToKitchen();
        else CallerFragment2.SendToKitchen();
        bottomSheetDialog.dismiss();

    }

    public void onClickCancel(){
        Log.d(TAG, "onClickCancel: Elaboro");

        ListProducts.clear();
        manager.setData(ListProducts);
        initListProductsRV();

    }

    public void removeProduct(Product name){
        Log.d(TAG, "removeProduct: Rimozione");
        ListProducts.remove(name);
        manager.setDataAlternative(ListProducts);
    }
    //ANIMATION
    @Override
    public void StartAnimations() {

    }

    @Override
    public void EndAnimations() {

    }
}