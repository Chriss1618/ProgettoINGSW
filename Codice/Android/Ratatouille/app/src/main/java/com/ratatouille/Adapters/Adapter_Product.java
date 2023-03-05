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

public class Adapter_Product  extends RecyclerView.Adapter<Adapter_Product.ViewHolder>  {
    //SYSTEM
    private static final String TAG = "Adapter_Product";

    //LAYOUT
    private final RecycleEventListener      RecycleEventListener;
    private ViewHolder                      Holder;

    //DATA
    private final ArrayList<String>         TitleProducts;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView    Card_View_Element_Product;
        TextView    Text_View_Title_Product;
        TextView    Text_View_Price_Product;
        ImageView   Image_View_Product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product  = itemView.findViewById(R.id.card_view_element_product);
            Text_View_Title_Product    = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Price_Product    = itemView.findViewById(R.id.text_view_price);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
        }
    }

    public Adapter_Product(ArrayList<String> TitleProducts, RecycleEventListener RecycleEventListener){
        this.TitleProducts          = TitleProducts;
        this.RecycleEventListener   = RecycleEventListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
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
        return TitleProducts.size();
    }


    //LAYOUT
    private void initializeLayout( final int position){
        this.Holder.Text_View_Title_Product.setText(TitleProducts.get(position));
    }

    private void setActions( final int position){
        this.Holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(position));
    }

    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleProducts.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.AdapterListener.onClickItem(TitleProducts.get(position));
    }


}
