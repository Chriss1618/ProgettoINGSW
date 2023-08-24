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

import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Adapter_Category extends RecyclerView.Adapter<Adapter_Category.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Category";

    //LAYOUT
    private ViewHolder                      Holder;
    final private ArrayList<ViewHolder>     Holders;

    //FUNCTIONAL
    private final RecycleEventListener      RecycleEventListener;
    private Observer observer = new Observer() {
        @Override
        public void update(Observable observable, Object o) {

        }
    };

    //DATA
    private ArrayList<CategoriaMenu>    TitleCategories;
    private ArrayList<CategoriaMenu>    CategorieFiltrate;
    private boolean showDeleting;
    private boolean SearchMode = false;
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

    public Adapter_Category(ArrayList<CategoriaMenu> TitleCategories, RecycleEventListener RecycleEventListener,boolean showDeleting){
        this.TitleCategories        = new ArrayList<>(TitleCategories);
        this.RecycleEventListener   = RecycleEventListener;
        this.Holders                = new ArrayList<>();
        this.CategorieFiltrate      = new ArrayList<>(TitleCategories);
        this.showDeleting = showDeleting;
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
        initializeLayout(position,holder);

        setActions(position,holder);
    }

    //LAYOUT
    private void initializeLayout(final int position,ViewHolder holder){
        String nomeCategoria = TitleCategories.get(position).getNomeCategoria().substring(0, 1).toUpperCase();
        nomeCategoria += TitleCategories.get(position).getNomeCategoria().substring(1);
        this.Holder.Text_View_titoloCategory.setText(nomeCategoria);
        if(showDeleting){
            holder.Image_View_delete_element.setVisibility(View.VISIBLE);
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeInZoom(200));
        }
    }
    private void setActions(final int position,ViewHolder holder){
        holder.Card_View_Element_Category.setOnClickListener(view -> clickCategory(position,holder));
        holder.Image_View_delete_element.setOnClickListener(view -> clickDeleteCategory(position,holder));
    }

    //ACTIONS
    private void clickCategory(final int position, ViewHolder holder){
        Log.d(TAG, "Premuto in Category-------------------");
        Log.d(TAG, " Holder: "  + holder.Text_View_titoloCategory.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleCategories.get(position));
        Log.d(TAG, "--------------------------------------");
        RecycleEventListener.onClickItem(TitleCategories.get(position));
    }

    private void clickDeleteCategory(int position, ViewHolder holder){
        Log.d(TAG, "clickDeleteCategory: "+holder.Text_View_titoloCategory.getText().toString());
        RecycleEventListener.onClickItemOption(holder.Text_View_titoloCategory.getText().toString(),TitleCategories.get(position).getID_categoria());
    }

    public void filterList(String filteredCategory){
        if (filteredCategory.isEmpty()) {
            TitleCategories = new ArrayList<>(CategorieFiltrate);
        } else {
            TitleCategories.clear();
            for (CategoriaMenu item : CategorieFiltrate) {
                if (item.getNomeCategoria().toLowerCase().contains(filteredCategory.toLowerCase())) {
                    TitleCategories.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(CategoriaMenu categoriaMenu) {
        TitleCategories.add(categoriaMenu);
        CategorieFiltrate.add(categoriaMenu);
    }

    public void removeItem(int id_cat) {
        int index = TitleCategories.size();
        for(int i = 0; i< TitleCategories.size();i++){
            if(TitleCategories.get(i).getID_categoria() == id_cat){
                TitleCategories.remove(i);
                index = i;
                notifyItemRemoved(i);
                break;
            }
        }
        for(int i = 0; i< CategorieFiltrate.size();i++){
            if(CategorieFiltrate.get(i).getID_categoria() == id_cat){
                CategorieFiltrate.remove(i);
                break;
            }
        }

        for (int i = index; i < TitleCategories.size(); i++) {
            notifyItemChanged(i);
        }
    }

    //ANIMATIONS
    public void showDeleteIcon(){
        showDeleting = true;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.setVisibility(View.VISIBLE);
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeInZoom(200));
        }
    }

    public void hideDeleteIcon(){
        showDeleting = false;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->
                            holder.Image_View_delete_element.setVisibility(View.GONE),
                    200);
        }
    }

}
