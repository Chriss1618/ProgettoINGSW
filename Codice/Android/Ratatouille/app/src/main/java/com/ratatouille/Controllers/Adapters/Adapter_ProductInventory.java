package com.ratatouille.Controllers.Adapters;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_ProductInventory extends RecyclerView.Adapter<Adapter_ProductInventory.ViewHolder> {
    //SYTEM
    private static final String TAG = "Adapter_ProductExist";

    //LAYOUT
    private final RecycleEventListener RecycleEventListener;
    private ArrayList<ViewHolder>       Holders;
    private boolean isShowingDeleting;

    //DATA
    ArrayList<Ingredient> Ingredients;
    ArrayList<Ingredient> IngredientsFiltered;

    public Adapter_ProductInventory(ArrayList<Ingredient> Ingredients, RecycleEventListener recycleEventListener,boolean isShowingDeleting) {
        this.Ingredients            = new ArrayList<>(Ingredients);
        this.IngredientsFiltered    = new ArrayList<>(Ingredients);

        this.Holders                = new ArrayList<>();
        this.RecycleEventListener = recycleEventListener;

        this.isShowingDeleting = isShowingDeleting;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        ImageView       Image_View_Product;
        ImageView       Image_View_delete_element;
        TextView        Text_View_Title_Product;
        TextView        Text_View_Measure_Product;
        TextView        Text_View_Qta_Product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_product);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_name_product);
            Text_View_Measure_Product   = itemView.findViewById(R.id.text_view_product_mesure);
            Text_View_Qta_Product       = itemView.findViewById(R.id.text_view_qta);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_exist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Holders.add(holder);
        initializeLayout(position,holder);
        setActions(position,holder);
    }

    @Override
    public int getItemCount() {
        return Ingredients.size();
    }

    //LAYOUT
    private void initializeLayout( final int position,@NonNull ViewHolder holder){
        holder.Text_View_Title_Product.setText(Ingredients.get(position).getNameIngredient());
        holder.Text_View_Qta_Product.setText(Ingredients.get(position).getQtaIngredient()+"" );
        holder.Text_View_Measure_Product.setText(Ingredients.get(position).getSizeIngredient()+" "+ Ingredients.get(position).getMeasureType());
        Picasso.get()
                .load(EndPointer.StandardPath+"/Images/Ingredient/"+ Ingredients.get(position).getURLImageIngredient())
                .into(holder.Image_View_Product);

        if(isShowingDeleting) showDeleteIcon();
    }

    private void setActions( final int position,@NonNull ViewHolder holder){
        holder.Card_View_Element_Product.setOnClickListener(view ->onClickItem(position) );
        holder.Image_View_delete_element.setOnClickListener(view -> clickDeleteProduct(position));
    }

    //ACTIONS
    private void onClickItem(final int position){
        RecycleEventListener.onClickItem(Ingredients.get(position));
    }
    private void clickDeleteProduct(int position){
        Log.d(TAG, "clickDeleteProduct: id To DELETE ->"+Ingredients.get(position).getID_Ingredient());
        Log.d(TAG, "clickDeleteCategory: "+Holders.get(position).Text_View_Title_Product.getText().toString());
        RecycleEventListener.onClickItemOption(Holders.get(position).Text_View_Title_Product.getText().toString(),Ingredients.get(position).getID_Ingredient());
    }

    public void filterList(String filteredCategory){
        if (filteredCategory.isEmpty()) {
            Ingredients = new ArrayList<>(IngredientsFiltered);
        }else {
            Ingredients.clear();
            for (Ingredient item : IngredientsFiltered) {
                if (item.getNameIngredient().toLowerCase().contains(filteredCategory.toLowerCase())) {
                    Ingredients.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void removeItem(int id_cat) {
        int index = Ingredients.size();
        for(int i = 0; i< Ingredients.size();i++){
            if(Ingredients.get(i).getID_Ingredient() == id_cat){
                Ingredients.remove(i);
                index = i;
                notifyItemRemoved(i);
                break;
            }
        }
        for(int i = 0; i< IngredientsFiltered.size();i++){
            if(IngredientsFiltered.get(i).getID_Ingredient() == id_cat){
                IngredientsFiltered.remove(i);
                break;
            }
        }

        for (int i = index; i < Ingredients.size(); i++) {
            notifyItemChanged(i);
        }
    }

    //ANIMATIONS
    public void showDeleteIcon(){
        isShowingDeleting = true;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.setVisibility(View.VISIBLE);
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeInZoom(200));
        }
    }

    public void hideDeleteIcon(){
        isShowingDeleting = false;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->
                            holder.Image_View_delete_element.setVisibility(View.GONE),
                    200);
        }
    }
}
