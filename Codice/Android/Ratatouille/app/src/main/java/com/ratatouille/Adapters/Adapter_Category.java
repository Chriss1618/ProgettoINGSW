package com.ratatouille.Adapters;

import android.content.Context;
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
import com.ratatouille.Interfaces.RecyclerInterfaces.onClickItemAdapterListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_Category extends RecyclerView.Adapter<Adapter_Category.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Category";

    //LAYOUT
    private ViewHolder                      Holder;

    //FUNCTIONAL
    private final RecycleEventListener      RecycleEventListener;

    //DATA
    private final ArrayList<String>         TitleCategories;

    //OTHER...

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Category;
        TextView        Text_View_titoloCategory;
        ImageView       Image_View_delete_element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Category  = itemView.findViewById(R.id.card_view_element_category);
            Text_View_titoloCategory    = itemView.findViewById(R.id.text_view_title_category);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    public Adapter_Category(ArrayList<String> TitleCategories, RecycleEventListener RecycleEventListener){
        this.TitleCategories        = TitleCategories;
        this.RecycleEventListener   = RecycleEventListener;
    }

    @NonNull
    @Override
    public Adapter_Category.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return TitleCategories.size();
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_Category.ViewHolder holder, int position) {
        this.Holder = holder;
        initializeLayout(position);

        setActions(position);
    }



    //LAYOUT
    private void initializeLayout(final int position){
        this.Holder.Text_View_titoloCategory.setText(TitleCategories.get(position));
    }
    private void setActions(final int position){
        this.Holder.Card_View_Element_Category.setOnClickListener(view -> clickCategory(position));
    }

    //ACTIONS
    private void clickCategory(final int position){
        Log.d(TAG, "Premuto in Category-------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_titoloCategory.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleCategories.get(position));
        Log.d(TAG, "--------------------------------------");
        RecycleEventListener.AdapterListener.onClickItem(TitleCategories.get(position));
    }


}
