package com.ratatouille.Adapters;

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

public class Adapter_ProductExist extends RecyclerView.Adapter<Adapter_ProductExist.ViewHolder> {
    //SYTEM
    private static final String TAG = "Adapter_ProductExist";

    //LAYOUT
    private final RecycleEventListener  RecycleEventListener;
    private ViewHolder                  Holder;

    //DATA
    ArrayList<String> TitleProducts;

    public Adapter_ProductExist(ArrayList<String> TitleProducts, RecycleEventListener recycleEventListener) {
        this.TitleProducts = TitleProducts;
        this.RecycleEventListener = recycleEventListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        ImageView       Image_View_Product;
        TextView        Text_View_Title_Product;
        TextView        Text_View_Mesure_Product;
        TextView        Text_View_Qta_Product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_product);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Mesure_Product    = itemView.findViewById(R.id.text_view_product_mesure);
            Text_View_Qta_Product       = itemView.findViewById(R.id.text_view_qta);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_exist,parent,false);
        return new Adapter_ProductExist.ViewHolder(view);
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

    }

    private void setActions( final int position){

    }
}
