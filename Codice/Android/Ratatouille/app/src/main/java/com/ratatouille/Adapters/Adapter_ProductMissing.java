package com.ratatouille.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.R;

public class Adapter_ProductMissing {

    //SYTEM
    private static final String TAG = "Adapter_ProductMissing";

    //LAYOUT

    //DATA

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
}
