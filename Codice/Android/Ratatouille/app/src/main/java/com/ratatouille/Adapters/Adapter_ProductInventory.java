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
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_ProductInventory extends RecyclerView.Adapter<Adapter_ProductInventory.ViewHolder> {
    //SYTEM
    private static final String TAG = "Adapter_ProductExist";

    //LAYOUT
    private final RecycleEventListener  RecycleEventListener;
    private ViewHolder                  Holder;
    private ArrayList<ViewHolder>       Holders;

    //DATA
    ArrayList<String> TitleProducts;

    public Adapter_ProductInventory(ArrayList<String> TitleProducts, RecycleEventListener recycleEventListener) {
        this.TitleProducts = TitleProducts;
        this.RecycleEventListener = recycleEventListener;
        this.Holders = new ArrayList<>();
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
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Measure_Product   = itemView.findViewById(R.id.text_view_product_mesure);
            Text_View_Qta_Product       = itemView.findViewById(R.id.text_view_qta);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_exist,parent,false);
        return new Adapter_ProductInventory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.Holder = holder;
        this.Holders.add(holder);
        initializeLayout(position);
        setActions(position);
    }

    @Override
    public int getItemCount() {
        return TitleProducts.size();
    }

    //LAYOUT
    private void initializeLayout( final int position){
        setActions(position);
    }

    private void setActions( final int position){
        this.Holder.Card_View_Element_Product.setOnClickListener(view ->onClickItem(position) );
        this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteProduct(position));
    }


    //ACTIONS
    private void onClickItem(final int position){
        RecycleEventListener.onClickItem(TitleProducts.get(position));
    }
    private void clickDeleteProduct(int position){
        Log.d(TAG, "clickDeleteCategory: "+this.Holders.get(position).Text_View_Title_Product.getText().toString());

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
