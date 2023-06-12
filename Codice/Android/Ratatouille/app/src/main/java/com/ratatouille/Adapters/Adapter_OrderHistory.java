package com.ratatouille.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_OrderHistory extends RecyclerView.Adapter<Adapter_OrderHistory.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_OrderHistory";

    //LAYOUT
    private ViewHolder Holder;

    //FUNCTIONAL
    private final RecycleEventListener RecycleEventListener;

    //DATA
    private final ArrayList<String> Orders;
    //OTHER..
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView Card_View_Element_Tavolo;
        TextView Text_View_NameOrder;
        TextView Text_View_NumeroTavolo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Tavolo    = itemView.findViewById(R.id.card_view_element_table);
            Text_View_NameOrder         = itemView.findViewById(R.id.text_view_order_completed);
            Text_View_NumeroTavolo      = itemView.findViewById(R.id.text_view_product_qta);
        }
    }

    public Adapter_OrderHistory(ArrayList<String> Orders,RecycleEventListener RecycleEventListener){
        this.Orders                 = Orders;
        this.RecycleEventListener   = RecycleEventListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order,parent,false);
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
        return Orders.size();
    }

    //LAYOUT
    private void initializeLayout(final int position){
        this.Holder.Text_View_NameOrder.setText(Orders.get(position));
    }
    private void setActions(final int position){

    }


}
