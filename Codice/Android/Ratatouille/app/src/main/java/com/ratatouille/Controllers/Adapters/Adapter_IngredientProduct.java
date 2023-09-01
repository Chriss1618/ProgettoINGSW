package com.ratatouille.Controllers.Adapters;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public class Adapter_IngredientProduct extends RecyclerView.Adapter<ViewHolder_IngredientProduct> {

    //SYSTEM
    private static final String TAG = "Adapter_IngredientProdu";

    //DATA
    private ArrayList<Ricettario> Ricettario;

    //LAYOUT
    private ArrayList<ViewHolder_IngredientProduct> Holders;
    //FUNCTIONAl
    Context context;

    public Adapter_IngredientProduct(ArrayList<Ricettario> ricette, Context context) {
        this.Ricettario     = ricette;
        this.context        = context;
        this.Holders        = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder_IngredientProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingrediente,parent,false);
        return new ViewHolder_IngredientProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_IngredientProduct holder, int position) {
        this.Holders.add(holder);
        SetDataOnLayout( holder,  position);
        SetActions( holder,  position);
    }

    private void SetDataOnLayout(ViewHolder_IngredientProduct holder, int position){
        holder.TextView_Ingredient.setText( Ricettario.get(position).getIngredient().getNameIngredient() );
        String Dose = Ricettario.get(position).getDosi() + Ricettario.get(position).getTypeMeasure();
        holder.TextView_Dosi.setText(Dose);
        holder.setRicettario(Ricettario.get(position));
    }

    private void SetActions(ViewHolder_IngredientProduct holder, int position){
        holder.TextView_Dosi.setOnClickListener( view -> {
            Log.d(TAG, "SetActions: Premuto la dose ->"+ holder.TextView_Ingredient.getText().toString());
        });
        holder.ImageView_IcDelete.setOnClickListener(view -> removeItem(Ricettario.get(position)));

    }

    public void removeItem(Ricettario ricettario) {
        Ricettario.remove(ricettario);
        removeFromHolders(ricettario);
    }

    public void removeFromHolders(Ricettario ricettario) {
        for(ViewHolder_IngredientProduct holder : Holders){
            if(holder.getRicettario().equals(ricettario)){
                Holders.remove(holder);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  Ricettario.size();
    }
}
