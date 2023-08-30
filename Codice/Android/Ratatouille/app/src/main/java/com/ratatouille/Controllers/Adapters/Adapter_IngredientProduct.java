package com.ratatouille.Controllers.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Controllers.Adapters.Holders.ViewHolder_IngredientProduct;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_IngredientProduct extends RecyclerView.Adapter<ViewHolder_IngredientProduct> {

    //SYSTEM
    private static final String TAG = "Adapter_IngredientProdu";

    //DATA
    private ArrayList<Ricettario> Ricettario;

    //FUNCTIONAl
    Context context;

    public Adapter_IngredientProduct(ArrayList<Ricettario> ricette, Context context) {
        this.Ricettario     = ricette;
        this.context        = context;
    }

    @NonNull
    @Override
    public ViewHolder_IngredientProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingrediente,parent,false);
        return new ViewHolder_IngredientProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_IngredientProduct holder, int position) {
        SetDataOnLayout( holder,  position);
        SetActions( holder,  position);
    }

    private void SetDataOnLayout(ViewHolder_IngredientProduct holder, int position){
        holder.Checkbox_Ingredient.setText( Ricettario.get(position).getIngredient().getNameIngredient() );
        String Dose = Ricettario.get(position).getDosi() + Ricettario.get(position).getTypeMeasure();
        holder.TextView_Dosi.setText(Dose);

    }

    private void SetActions(ViewHolder_IngredientProduct holder, int position){
        holder.TextView_Dosi.setOnClickListener( view -> {
            Log.d(TAG, "SetActions: Premuto la dose ->"+ holder.Checkbox_Ingredient.getText().toString());
        });
    }

    @Override
    public int getItemCount() {
        return  Ricettario.size();
    }
}
