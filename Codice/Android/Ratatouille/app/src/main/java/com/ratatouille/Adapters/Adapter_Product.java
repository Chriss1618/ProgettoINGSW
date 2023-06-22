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

public class Adapter_Product  extends RecyclerView.Adapter<Adapter_Product.ViewHolder>  {
    //SYSTEM
    private static final String TAG = "Adapter_Product";

    //LAYOUT
    private final RecycleEventListener      RecycleEventListener;
    private ViewHolder                      Holder;
    private ArrayList<ViewHolder>           Holders;

    //FUNCTIONAL
    private boolean isFromLeft;
    //DATA
    private final ArrayList<String>         TitleProducts;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView    Card_View_Element_Product;
        TextView    Text_View_Title_Product;
        TextView    Text_View_Price_Product;
        ImageView   Image_View_Product;
        ImageView   Image_View_delete_element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_product);
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Price_Product     = itemView.findViewById(R.id.text_view_price);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    public Adapter_Product(ArrayList<String> TitleProducts, RecycleEventListener RecycleEventListener,boolean isFromLeft){
        this.TitleProducts          = TitleProducts;
        this.RecycleEventListener   = RecycleEventListener;
        this.isFromLeft             = isFromLeft;
        this.Holders                = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.Holder = holder;
        this.Holders.add(holder);
        initializeLayout(position);
        setActions(position);

        if (isFromLeft) {
            this.Holder.Card_View_Element_Product.setVisibility(View.GONE);
            StartAnimations(this.Holder.Card_View_Element_Product,position);
        }
    }

    @Override
    public int getItemCount() {
        return TitleProducts.size();
    }


    //LAYOUT
    private void initializeLayout( final int position){
        this.Holder.Text_View_Title_Product.setText(TitleProducts.get(position));
    }

    private void setActions( final int position){
        this.Holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(position));
        this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteCategory(position));

    }

    //ACTIONS
    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleProducts.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.onClickItem(TitleProducts.get(position));
    }
    private void clickDeleteCategory(int position){
        Log.d(TAG, "clickDeleteCategory: "+this.Holders.get(position).Text_View_Title_Product.getText().toString());

    }

    //ANIMATIONS
    private void StartAnimations(CardView cardView,int position){

        final Handler handler = new Handler();
        handler.postDelayed(()->{
            cardView.setVisibility(View.VISIBLE);
            cardView .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(400));
        }, (position + 1 ) * 100L);

    }

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
