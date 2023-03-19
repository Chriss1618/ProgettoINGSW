package com.ratatouille.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_TablesWaiter extends RecyclerView.Adapter<Adapter_TablesWaiter.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Product";

    //LAYOUT
    private ViewHolder                  Holder;

    //FUNCTIONAL
    private final RecycleEventListener  RecycleEventListener;

    //DATA
    private final ArrayList<String> Tables;


    //OTHER...

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        CardView_ElementTablet;
        TextView        TextView_NumberTable;
        ImageView       Image_View_State_green;
        ImageView       Image_View_State_red;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView_ElementTablet  = itemView.findViewById(R.id.card_view_element_table);
            TextView_NumberTable    = itemView.findViewById(R.id.text_view_number_table);
            Image_View_State_green          = itemView.findViewById(R.id.ic_state_green);
            Image_View_State_red          = itemView.findViewById(R.id.ic_state_red);
        }
    }

    public Adapter_TablesWaiter( ArrayList<String> tables, RecycleEventListener recycleEventListener) {
        this.RecycleEventListener = recycleEventListener;
        this.Tables = tables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_waiter,parent,false);
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
        return Tables.size();
    }

    //LAYOUT
    private void initializeLayout( final int position){
        this.Holder.TextView_NumberTable.setText(Tables.get(position));
    }

    private void setActions( final int position){
        this.Holder.CardView_ElementTablet.setOnClickListener(view -> clickTable(position));
    }

    private void clickTable( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Array: "   + this.Tables.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.AdapterListener.onClickItem(Tables.get(position));
    }

}
