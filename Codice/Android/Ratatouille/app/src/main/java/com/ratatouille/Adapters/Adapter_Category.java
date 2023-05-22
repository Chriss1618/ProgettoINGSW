package com.ratatouille.Adapters;

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

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Models.CategoriaMenu;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_Category extends RecyclerView.Adapter<Adapter_Category.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Category";

    //LAYOUT
    private ViewHolder                      Holder;
    final private ArrayList<ViewHolder>     Holders;

    //FUNCTIONAL
    private final RecycleEventListener      RecycleEventListener;

    //DATA
    private final ArrayList<CategoriaMenu>    TitleCategories;

    //OTHER...

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Category;
        TextView        Text_View_titoloCategory;
        ImageView       Image_View_delete_element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Category  = itemView.findViewById(R.id.card_view_element_category);
            Text_View_titoloCategory    = itemView.findViewById(R.id.text_view_title_category);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    public Adapter_Category(ArrayList<CategoriaMenu> TitleCategories, RecycleEventListener RecycleEventListener){
        this.TitleCategories        = TitleCategories;
        this.RecycleEventListener   = RecycleEventListener;
        this.Holders                = new ArrayList<>();
    }

    @NonNull
    @Override
    public Adapter_Category.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return TitleCategories.size();
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_Category.ViewHolder holder, int position) {
        this.Holder = holder;
        this.Holders.add(holder);
        initializeLayout(position);

        setActions(position);
    }



    //LAYOUT
    private void initializeLayout(final int position){
        this.Holder.Text_View_titoloCategory.setText(TitleCategories.get(position).getNomeCategoria());
    }
    private void setActions(final int position){
        this.Holder.Card_View_Element_Category.setOnClickListener(view -> clickCategory(position));
        this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteCategory(position));
    }

    //ACTIONS
    private void clickCategory(final int position){
        Log.d(TAG, "Premuto in Category-------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_titoloCategory.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleCategories.get(position));
        Log.d(TAG, "--------------------------------------");
        RecycleEventListener.AdapterListener.onClickItem(TitleCategories.get(position).getNomeCategoria());
    }

    private void clickDeleteCategory(int position){
        Log.d(TAG, "clickDeleteCategory: "+this.Holders.get(position).Text_View_titoloCategory.getText().toString());

    }

    //ANIMATIONS

    public void showDeleteIcon(){
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.setVisibility(View.VISIBLE);
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeInZoom(200));
        }
    }

    public void hideDeleteIcon(){
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->
                            holder.Image_View_delete_element.setVisibility(View.GONE),
                    200);
        }
    }


}
