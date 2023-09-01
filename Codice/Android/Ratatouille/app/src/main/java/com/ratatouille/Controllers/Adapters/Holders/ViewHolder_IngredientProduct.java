package com.ratatouille.Controllers.Adapters.Holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.R;

public class ViewHolder_IngredientProduct extends RecyclerView.ViewHolder {

    public TextView TextView_Ingredient;
    public TextView TextView_Dosi;
    public ImageView ImageView_IcDelete;

    public Ricettario ricettario;

    public ViewHolder_IngredientProduct(@NonNull View itemView) {
        super(itemView);
        TextView_Ingredient     = itemView.findViewById(R.id.text_view_name_ingredient);
        TextView_Dosi           = itemView.findViewById(R.id.text_view_ingredient_dosi);
        ImageView_IcDelete      = itemView.findViewById(R.id.ic_delete_ingrediente);
    }

    public Ricettario getRicettario() {
        return ricettario;
    }

    public void setRicettario(Ricettario ricettario) {
        this.ricettario = ricettario;
    }
}
