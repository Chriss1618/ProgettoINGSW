package com.ratatouille.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Interfaces.AdapterEvent;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_Category extends RecyclerView.Adapter<Adapter_Category.ViewHolder>{
    private static final String TAG = "Adapter_Category";
    private Context mContext;
    private ArrayList<String> TitleCategories;
    private AdapterEvent AdapterListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
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

    public Adapter_Category(Context context, ArrayList<String> TitleCategories, AdapterEvent AdapterListener){
        this.mContext           = context;
        this.TitleCategories    = TitleCategories;
        this.AdapterListener    = AdapterListener;
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
        initializeLayout(holder,position);
        setActioins(holder,position);

    }

    //LAYOUT
    private void initializeLayout(@NonNull ViewHolder holder,final int position){
        holder.Text_View_titoloCategory.setText(TitleCategories.get(position));
    }

    private void setActioins(@NonNull ViewHolder holder,final int position){
        holder.Card_View_Element_Category.setOnClickListener(view -> clickCategory(holder,position));
    }

    private void clickCategory(@NonNull ViewHolder holder,final int position){
        Log.d(TAG, " Holder: "+holder.Text_View_titoloCategory.getText().toString());
        Log.d(TAG, " Array: "+TitleCategories.get(position));
        Log.d(TAG, "--------------------------------------");

        //andare in list prducts
        /*((Activity_Amministratore)mContext).changeFragmentOnAmministrator(
                Controller_Amministratore.AMMINISTRATORE_INDEX_MENU,
                Manager_MenuFragments.INDEX_MENU_LIST_PRODUCTS,
                TitleCategories.get(position));*/

        AdapterListener.onClickItem(TitleCategories.get(position));
    }


}
