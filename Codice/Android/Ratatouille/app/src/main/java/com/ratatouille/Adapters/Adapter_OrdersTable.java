package com.ratatouille.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_OrdersTable extends RecyclerView.Adapter<Adapter_OrdersTable.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_OrdersTable";

    //LAYOUT
    private ViewHolder Holder;

    //FUNCTIONAL
    private final RecycleEventListener RecycleEventListener;

    //DATA
    private final ArrayList<String> OrdersTable;
    //OTHER..
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox CheckBox_Order;
        TextView Text_View_Qta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CheckBox_Order  = itemView.findViewById(R.id.checkBox_order);
            Text_View_Qta    = itemView.findViewById(R.id.text_view_product_qta);
        }
    }

    public Adapter_OrdersTable(ArrayList<String> OrdersTable,RecycleEventListener recycleEventListener) {
        this.RecycleEventListener   = recycleEventListener;
        this.OrdersTable            = OrdersTable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_table,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.Holder = holder;
        initializeLayout(position);
        setActions(position);
    }

    @Override
    public int getItemCount() {
        return OrdersTable.size();
    }

    //LAYOUT
    private void initializeLayout(final int position){
        this.Holder.CheckBox_Order.setText(OrdersTable.get(position));
    }
    private void setActions(final int position){
        this.Holder.CheckBox_Order.setOnCheckedChangeListener((compoundButton, isChecked) -> checkOrder(isChecked,position));
    }

    //ACTIONS
    private void checkOrder(boolean isChecked,final int position){
        Log.d(TAG, "Premuto in Elenco Tavoli-------------------");
        Log.d(TAG, " isChecked  : "  + isChecked);
        Log.d(TAG, " Array      : "   + this.OrdersTable.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.AdapterCheckListener.onCheckItem(OrdersTable.get(position),isChecked);
    }

}
