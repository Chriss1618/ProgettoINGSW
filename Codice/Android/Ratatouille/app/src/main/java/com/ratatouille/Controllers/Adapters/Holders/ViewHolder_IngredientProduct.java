package com.ratatouille.Controllers.Adapters.Holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.R;

public class ViewHolder_IngredientProduct extends RecyclerView.ViewHolder {

    public CheckBox Checkbox_Ingredient;
    public TextView TextView_Dosi;

    public ViewHolder_IngredientProduct(@NonNull View itemView) {
        super(itemView);
        Checkbox_Ingredient     = itemView.findViewById(R.id.checkBox_name_ingredient);
        TextView_Dosi           = itemView.findViewById(R.id.text_view_ingredient_dosi);
    }

}
