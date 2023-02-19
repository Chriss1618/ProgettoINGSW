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

import com.ratatouille.Interfaces.AdapterEvent;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_Product  extends RecyclerView.Adapter<Adapter_Product.ViewHolder>  {
    private static final String TAG = "Adapter_Product";

    //LAYOUT
    private Context             mContext;
    private ArrayList<String>   TitleProducts;
    private AdapterEvent        AdapterListener;

    public Adapter_Product(Context context, ArrayList<String> TitleProducts, AdapterEvent AdapterListener){
        this.mContext           = context;
        this.TitleProducts      = TitleProducts;
        this.AdapterListener    = AdapterListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new Adapter_Product.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initializeLayout(holder,position);
        setActioins(holder,position);
    }

    @Override
    public int getItemCount() {
        return TitleProducts.size();
    }


    //LAYOUT
    private void initializeLayout(@NonNull Adapter_Product.ViewHolder holder, final int position){
        holder.Text_View_Title_Product.setText(TitleProducts.get(position));
    }

    private void setActioins(@NonNull Adapter_Product.ViewHolder holder, final int position){
        holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(holder,position));
    }

    private void clickProduct(@NonNull Adapter_Product.ViewHolder holder, final int position){
        Log.d(TAG, " Holder: "+holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "+TitleProducts.get(position));
        Log.d(TAG, "--------------------------------------");

        //andare in list prducts
        /*((Activity_Amministratore)mContext).changeFragmentOnAmministrator(
                Controller_Amministratore.AMMINISTRATORE_INDEX_MENU,
                Manager_MenuFragments.INDEX_MENU_LIST_PRODUCTS,
                TitleCategories.get(position));*/

        AdapterListener.onClickItem(TitleProducts.get(position));
    }


}
