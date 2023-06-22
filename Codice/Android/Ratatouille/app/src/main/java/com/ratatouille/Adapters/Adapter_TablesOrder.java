package com.ratatouille.Adapters;

import android.util.Log;
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

public class Adapter_TablesOrder extends RecyclerView.Adapter<Adapter_TablesOrder.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_TablesOrder";

    //LAYOUT
    private ViewHolder Holder;

    //FUNCTIONAL
    private final RecycleEventListener  RecycleEventListener;

    //DATA
    private final ArrayList<String>     NumberTables;

    //OTHER..
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView Card_View_Element_Tavolo;
        TextView Text_View_titoloTavolo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Tavolo  = itemView.findViewById(R.id.card_view_element_table);
            Text_View_titoloTavolo    = itemView.findViewById(R.id.text_view_name_table);
        }
    }

    public Adapter_TablesOrder(ArrayList<String> NumberTables,RecycleEventListener RecycleEventListener){
        this.NumberTables           = NumberTables;
        this.RecycleEventListener   = RecycleEventListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tavolo,parent,false);
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
        return NumberTables.size();
    }

    //LAYOUT
    private void initializeLayout(final int position){
        this.Holder.Text_View_titoloTavolo.setText(NumberTables.get(position));
    }
    private void setActions(final int position){
        this.Holder.Card_View_Element_Tavolo.setOnClickListener(view -> clickCategory(position));
    }

    //ACTIONS
    private void clickCategory(final int position){
        Log.d(TAG, "Premuto in Elenco Tavoli-------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_titoloTavolo.getText().toString());
        Log.d(TAG, " Array: "   + this.NumberTables.get(position));
        Log.d(TAG, "--------------------------------------");
        RecycleEventListener.onClickItem(NumberTables.get(position));
    }
}
