package com.ratatouille.Controllers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Controllers.Adapters.Holders.ViewHolder__IngredientProduct;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_IngredientProduct extends RecyclerView.Adapter<ViewHolder__IngredientProduct> {
    //SYSTEM
    private static final String TAG = "Adapter_IngredientProdu";
    //DATA
    private ArrayList<Ricettario> Ricettario;

    Context context;

    public Adapter_IngredientProduct(ArrayList<Ricettario> ricette, Context context) {
        this.Ricettario = ricette;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder__IngredientProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingrediente,parent,false);
        return new ViewHolder__IngredientProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder__IngredientProduct holder, int position) {
        SetDataOnLayout(holder, position);
        SetActions(holder, position);
    }
    private void SetDataOnLayout(@NonNull ViewHolder__IngredientProduct holder, int position){
    }
    private void SetActions(@NonNull ViewHolder__IngredientProduct holder, int position){
    }


    @Override
    public int getItemCount() {
        return 0 ;
    }
}
