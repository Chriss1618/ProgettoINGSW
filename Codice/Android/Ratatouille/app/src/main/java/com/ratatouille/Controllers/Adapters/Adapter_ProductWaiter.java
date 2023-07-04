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
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_ProductWaiter extends RecyclerView.Adapter<Adapter_ProductWaiter.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_ProductWaiter";
    private static final int INDEX_ACTION_ADD_PRODUCT = 0;
    private static final int INDEX_ACTION_REMOVE_PRODUCT = 1;
    //LAYOUT
    private final RecycleEventListener RecycleEventListener;
    private ViewHolder  Holder;
    private TextView    TextView_PlusOne;
    //FUNCTIONAL
    private boolean             isFromLeft;
    private ArrayList<Boolean>  isPlusShowing;
    private ArrayList<Integer>  plusValues;
    final Handler               handler_HidePlus = new Handler();

    //DATA
    private final ArrayList<String> TitleProducts;
    private final boolean isReport;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        TextView        Text_View_Title_Product;
        TextView        Text_View_Price_Product;
        TextView        Text_view_Option;
        TextView        Text_view_PlusOne;
        ImageView       Image_View_Product;
        CardView        Card_View_AddProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_product);
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Price_Product     = itemView.findViewById(R.id.text_view_price);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
            Card_View_AddProduct        = itemView.findViewById(R.id.card_view_add_product);
            Text_view_Option            = itemView.findViewById(R.id.text_view_option);
            Text_view_PlusOne           = itemView.findViewById(R.id.text_view_plus_one);
        }
    }

    public Adapter_ProductWaiter(ArrayList<String> TitleProducts, RecycleEventListener RecycleEventListener, boolean isFromLeft, boolean isReport){
        this.TitleProducts          = TitleProducts;
        this.RecycleEventListener   = RecycleEventListener;
        this.isFromLeft             = isFromLeft;
        this.isReport               = isReport;
        if(! isReport) initList();
    }
    private void initList(){
        isPlusShowing   = new ArrayList<>();
        plusValues      = new ArrayList<>();

        for(int i = 0; i< TitleProducts.size(); i++){ isPlusShowing.add(false); plusValues.add(0);}
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_waiter,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.Holder = holder;
        initializeLayout(holder,position);
        setActions(holder,position);

        if (isFromLeft) {
            holder.Card_View_Element_Product.setVisibility(View.GONE);
            StartAnimations(holder.Card_View_Element_Product,position);
        }
    }

    @Override
    public int getItemCount() {
        return TitleProducts.size();
    }

    //LAYOUT
    private void initializeLayout( ViewHolder Holder,final int position){
        if(isReport){
            Holder.Text_view_Option.setText("Rimuovi");
        }
        Holder.Text_View_Title_Product.setText(TitleProducts.get(position));
    }

    //ACTIONS
    private void setActions( ViewHolder Holder,final int position){
        Holder.Card_View_Element_Product   .setOnClickListener(view -> clickProduct(position));
        Holder.Card_View_AddProduct        .setOnClickListener(view -> clickAddProduct( Holder,position));
    }

    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleProducts.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.onClickItem(TitleProducts.get(position));
    }
    private void clickAddProduct(ViewHolder Holder,final int position){
//        Log.d(TAG, "Premuto in Add Product Option --------------------");
//        Log.d(TAG, " Holder: "  + Holder.Text_View_Title_Product.getText().toString());
//        Log.d(TAG, " Array: "   + this.TitleProducts.get(position));
//        Log.d(TAG, "--------------------------------------");
        if ( isReport ) {
            removeItem(Holder);
            sendOption(500,TitleProducts.get(position),INDEX_ACTION_REMOVE_PRODUCT);
        } else {
            showPlusOne(Holder.Text_view_PlusOne,position);
            sendOption(0,TitleProducts.get(position),INDEX_ACTION_ADD_PRODUCT);
        }
    }

    //FUNCTIONAL
    private void sendOption(int milliseconds,String product, int action){
        final Handler handler1 = new Handler();
        handler1.postDelayed(()->{
            RecycleEventListener.onClickItemOption(product,action);
        },milliseconds);
    }

    //ANIMATIONS
    private void StartAnimations(CardView cardView,int position){
        final Handler handler = new Handler();
        handler.postDelayed(()->{
            cardView.setVisibility(View.VISIBLE);
            cardView .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(400));
        }, (position + 1 ) * 100L);

    }

    private void showPlusOne(TextView textView_PlusOne,int position){
        Runnable runnable_HidePlus = () -> {
            TextView_PlusOne.startAnimation(Manager_Animation.getFadeOut(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->{
                isPlusShowing.set(position,false);
                plusValues.set(position,0);
                TextView_PlusOne.setVisibility(View.GONE);
            },200);
        };
        TextView_PlusOne = textView_PlusOne;

        plusValues.set(position,plusValues.get(position)+1);
        String text = "+"+plusValues.get(position);
        TextView_PlusOne.setText(text);

        if(isPlusShowing.get(position)){
            handler_HidePlus.removeCallbacksAndMessages(null);
            handler_HidePlus.postDelayed(runnable_HidePlus, 700);
        }else{
            isPlusShowing.set(position,true);
            TextView_PlusOne.setVisibility(View.VISIBLE);
            TextView_PlusOne.startAnimation(Manager_Animation.getTranslationINfromDownSlower(500));
            handler_HidePlus.postDelayed(runnable_HidePlus, 700);
        }
    }
    private void removeItem(ViewHolder Holder){
        Holder.Card_View_Element_Product.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
    }
}