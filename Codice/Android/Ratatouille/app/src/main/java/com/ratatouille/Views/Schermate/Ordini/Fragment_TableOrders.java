package com.ratatouille.Views.Schermate.Ordini;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_OrdersTable;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsOrdini;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class Fragment_TableOrders extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_TableOrders";

    //LAYOUT
    private android.view.View   View_Fragment;
    private TextView            TextView_Title;
    private TextView            TextView_nTable;
    private RecyclerView        recyclerView_OrdersTable;
    private CardView            CardView_CardTavolo;
    private ImageView           ImageView_Back;
    private ImageView           ImageView_HistoryOrders;
    private Button              Button_ConfirmOrders;

    private LinearLayout        LinearLayout_Dialog;
    private LinearLayout        LinearLayout_DarkL;

    //FUNCTIONAl
    private RecycleEventListener    RecycleEventListener;
    private Manager manager;
    private Adapter_OrdersTable adapter_ordersTable;
    private DialogMessage DialogCreatingProduct;
    //DATA
    ArrayList<Product> OrdersTable;
    ArrayList<Product> ProductsNotReady;
    int positionCurrentTable;
    //Others
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: Azione ricevuta da FCM");
            sendRequest();
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
        ArrayList<Ordine> ListOrdini = (ArrayList<Ordine>) manager.getData();
        String id_tavolo = (String) manager.getDataAlternative();
        for ( int position = 0; position < ListOrdini.size();position++) {
            Ordine ordine = ListOrdini.get(position);
            if(ordine.getTavolo().getId_Tavolo().equals(id_tavolo)){
                OrdersTable = ordine.getTavolo().getProdottiOrdinati();
                positionCurrentTable = position;
            }
        }
        initOrdersTableRV();
        TextView_nTable.setText(ListOrdini.get(positionCurrentTable).getTavolo().getN_Tavolo());
    }

    private void setOrdersOnLayout(ArrayList<Ordine> list){
        requireActivity().runOnUiThread(() -> {

            String id_tavolo = (String) manager.getDataAlternative();
            for ( int position = 0; position < list.size();position++) {
                Ordine ordine = list.get(position);
                if(ordine.getTavolo().getId_Tavolo().equals(id_tavolo)){
                    OrdersTable = ordine.getTavolo().getProdottiOrdinati();
                    positionCurrentTable = position;
                }
            }
            initOrdersTableRV();
            TextView_nTable.setText(list.get(positionCurrentTable).getTavolo().getN_Tavolo());

        });
    }

    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_ORDINI_TAVOLO,
                (list)-> setOrdersOnLayout((ArrayList<Ordine>) list));
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

        LinearLayout_Dialog     = View_Fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL      = View_Fragment.findViewById(R.id.darkRL);
        CardView_CardTavolo      = View_Fragment.findViewById(R.id.constraint_layout_card_table);

        ImageView_Back              = View_Fragment.findViewById(R.id.ic_back);
        TextView_Title              = View_Fragment.findViewById(R.id.text_view_title_category);
        TextView_nTable              = View_Fragment.findViewById(R.id.text_view_title);
        recyclerView_OrdersTable    = View_Fragment.findViewById(R.id.recycler_orders);
        ImageView_HistoryOrders     = View_Fragment.findViewById(R.id.ic_history_order);
        Button_ConfirmOrders        = View_Fragment.findViewById(R.id.button_confirm_orders);
    }

    @Override
    public void SetActionsOfLayout() {
        ImageView_HistoryOrders.setOnClickListener(view -> onHistoryClick());
        Button_ConfirmOrders    .setOnClickListener(view -> onConfirmOrdini());

        ImageView_Back          .setOnClickListener(            view -> manager.goBack());
    }
    @Override
    public void SetDataOnLayout() {

    }
    private void initOrdersTableRV(){
        ProductsNotReady = new ArrayList<>();
        for(Product product : OrdersTable){
            if(product.getId_User().equals("null")) ProductsNotReady.add(product);
        }

        adapter_ordersTable = new Adapter_OrdersTable(ProductsNotReady, RecycleEventListener);
        recyclerView_OrdersTable.setAdapter(adapter_ordersTable);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_OrdersTable.setLayoutManager(mLayoutManager);
        recyclerView_OrdersTable.setNestedScrollingEnabled(false);
    }
    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onHistoryClick(){

        Action action = new Action(ActionsOrdini.INDEX_ACTION_OPEN_HISTORY,manager.getData());
        SendAction(action);
    }
    private void onConfirmOrdini(){
        ArrayList<Product> listReady = adapter_ordersTable.getOrdersTableChecked();
        if(!listReady.isEmpty()){
            DialogCreatingProduct = new DialogMessage();
            DialogCreatingProduct.showLoading();
            Action action = new Action(ActionsOrdini.INDEX_ACTION_CONFIRM_ORDERS,listReady, (isOK) -> showDialog((boolean) isOK));
            SendAction(action);
        }
    }

    //FUNCTIONAL
    private void showDialog(boolean isOk){
        requireActivity().runOnUiThread(() -> {
            if(isOk) DialogCreatingProduct.showDialogSuccess();
            else DialogCreatingProduct.showDialogError();
        });
    }

    private class DialogMessage{
        LinearLayout LinearLayout_Error;
        LinearLayout LinearLayout_Success;
        LinearLayout LinearLayout_Loading;

        CardView CardView_Dialog_Cancel;
        private int numGiri = 0;

        public DialogMessage() {
            LinearLayout_Error      = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_error);
            LinearLayout_Success    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_accepted);
            LinearLayout_Loading    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_loading);

            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_Error      .setVisibility(View.GONE);
            LinearLayout_Success    .setVisibility(View.GONE);
            LinearLayout_Loading    .setVisibility(View.GONE);
        }

        private void showLoading(){
            LinearLayout_Loading    .setVisibility(View.VISIBLE);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);
            LinearLayout_Loading    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL       .startAnimation(Manager_Animation.getFadeIn(500));
            new Thread(()->rotation(1500)).start();
        }
        private void rotation(int speed){

            ImageView ImageView_Logo = LinearLayout_Loading.findViewById(R.id.image_view_logo);

            ImageView_Logo.animate()
                    .rotationBy(speed) // Use rotationBy instead of setting absolute rotation value
                    .setDuration(5000)
                    .withEndAction(() -> {
                        // This will be executed when the animation ends
                        int nextSpeed = (numGiri++ % 2 == 0) ? -1500 : 1500;
                        rotation(nextSpeed);
                    });

        }
        private void showDialogError(){
            CardView_Dialog_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_dialog_confirm);

            CardView_Dialog_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));

            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading           .setVisibility(View.GONE);
                LinearLayout_Error            .setVisibility(View.VISIBLE);
                LinearLayout_Error            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);
            hideKeyboardFrom();
        }
        private void dismissDialogError(){
            LinearLayout_Dialog.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_DarkL.startAnimation(Manager_Animation.getFadeOut(500));

            Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));

            LinearLayout_Error  .setVisibility(View.GONE);
            LinearLayout_Dialog .setVisibility(View.GONE);
            LinearLayout_DarkL  .setVisibility(View.GONE);
        }
        private void showDialogSuccess(){
            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));
            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading            .setVisibility(View.GONE);
                LinearLayout_Success            .setVisibility(View.VISIBLE);
                LinearLayout_Success            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);

            hideKeyboardFrom();
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        CardView_CardTavolo         .startAnimation(Manager_Animation.getTranslationINfromUpSlower(600));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Button_ConfirmOrders        .startAnimation(Manager_Animation.getTranslationINfromDownSlower(600));
        ImageView_Back              .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title              .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_CardTavolo         .startAnimation(Manager_Animation.getTranslationOUTtoUpSlower(600));
        recyclerView_OrdersTable    .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        ImageView_HistoryOrders     .startAnimation(Manager_Animation.getTranslationOUTtoUpSlower(300));
        Button_ConfirmOrders        .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));
        ImageView_Back              .startAnimation(Manager_Animation.getTranslateAnimatioOUT(600));

    }
}