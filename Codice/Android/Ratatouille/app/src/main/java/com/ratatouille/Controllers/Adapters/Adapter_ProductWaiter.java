package com.ratatouille.Controllers.Adapters;

import android.content.Context;
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

import com.ratatouille.Controllers.Adapters.Holders.ViewHolder_IngredientProduct;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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
    private Context context;
    private boolean             isFromLeft;
    private ArrayList<Boolean>  isPlusShowing;
    private ArrayList<Integer>  plusValues;
    private ArrayList<Handler>  Listhandler_HidePlus ;
    private ArrayList<ViewHolder> ListHolders;

    //DATA
    private ArrayList<Product>         ListProducts;
    private ArrayList<Product>   ListProductsFiltered;
    private final boolean isReport;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        TextView        Text_View_Title_Product;
        TextView        Text_View_Price_Product;
        TextView        Text_view_Option;
        TextView        Text_view_PlusOne;
        ImageView       Image_View_Product;
        CardView        Card_View_AddProduct;
        Product         Product;
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

    public Adapter_ProductWaiter(Context context,ArrayList<Product> TitleProducts, RecycleEventListener RecycleEventListener, boolean isFromLeft, boolean isReport){
        this.context                = context;
        this.ListProducts           = new ArrayList<>(TitleProducts);
        this.ListProductsFiltered   = new ArrayList<>(TitleProducts);
        this.RecycleEventListener   = RecycleEventListener;
        this.isFromLeft             = isFromLeft;
        this.isReport               = isReport;
        ListHolders = new ArrayList<>();
        if(! isReport ) initList();
    }
    private void initList(){
        isPlusShowing   = new ArrayList<>();
        plusValues      = new ArrayList<>();
        Listhandler_HidePlus = new ArrayList<>();
        for(int i = 0; i< ListProducts.size(); i++){
            isPlusShowing.add(false); plusValues.add(0);
            Listhandler_HidePlus.add(new Handler());
        }
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
        ListHolders.add(holder);
        holder.Product = ListProducts.get(position);
        initializeLayout(holder,position);
        setActions(holder,position);

        if (isFromLeft) {
            holder.Card_View_Element_Product.setVisibility(View.GONE);
            StartAnimations(holder.Card_View_Element_Product,position);
        }
        if(ListProducts.get(position).getURLImageProduct().contains("https")){
            Picasso.get()
                    .load(ListProducts.get(position).getURLImageProduct())
                    .into(holder.Image_View_Product);
        }else{
            Picasso.get()
                    .load(EndPointer.StandardPath+ EndPointer.IMAGES_PRODUCT+ ListProducts.get(position).getURLImageProduct())
                    .into(holder.Image_View_Product);
        }
        String formattedValue = new DecimalFormat("0.00").format(ListProducts.get(position).getPriceProduct()) + context.getResources().getString(R.string.euro);
        holder.Text_View_Price_Product.setText(formattedValue );
    }

    @Override
    public int getItemCount() {
        return ListProducts.size();
    }

    //LAYOUT
    private void initializeLayout( ViewHolder Holder,final int position){
        if(isReport){
            Holder.Text_view_Option.setText("Rimuovi");
        }
        Holder.Text_View_Title_Product.setText(ListProducts.get(position).getNameProduct());
    }

    //ACTIONS
    private void setActions( ViewHolder Holder,final int position){
        Holder.Card_View_Element_Product   .setOnClickListener(view -> clickProduct(position));
        Holder.Card_View_AddProduct        .setOnClickListener(view -> clickAddProduct( Holder,position));
    }

    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.ListProducts.get(position));
        Log.d(TAG, "--------------------------------------");
        RecycleEventListener.onClickItem(ListProducts.get(position));
    }
    private void clickAddProduct(ViewHolder Holder,final int position){
        if ( isReport ) {
            try{

                sendOption(500,ListProducts.get(position),INDEX_ACTION_REMOVE_PRODUCT);
                removeItem(Holder.Product);
            }catch (IndexOutOfBoundsException ex){
                Log.e(TAG, "clickAddProduct: ",ex );

            }
        } else {
            showPlusOne(position);
            sendOption(0,ListProducts.get(position),INDEX_ACTION_ADD_PRODUCT);
        }
    }

    //FUNCTIONAL
    public void removeItem(Product toDelete) {

        int index = ListProducts.size();
        for(int i = 0; i< ListProducts.size();i++){
            if(ListProducts.get(i).getID_product() == toDelete.getID_product()){
                ListProducts.remove(i);
                index = i;
                notifyItemRemoved(i);
                break;
            }
        }
        for (int i = index; i < ListProducts.size(); i++) {
            notifyItemChanged(i);
        }
    }

    private void removeFromHolders(Product toDelete) {
        for(ViewHolder holder : ListHolders){
            if(holder.Product.getID_product() == toDelete.getID_product()){
                ListHolders.remove(holder);
                break;
            }
        }
        notifyDataSetChanged();
    }

    private void sendOption(int milliseconds,Product product, int action){
        final Handler handler1 = new Handler();
        handler1.postDelayed(()-> RecycleEventListener.onClickItemOption(product,action),milliseconds);
    }
    public void filterList(String filteredCategory){
        Log.d(TAG, "filterList: inside");
        if (filteredCategory.isEmpty()) {
            ListProducts = new ArrayList<>(ListProductsFiltered);
        }else {
            ListProducts.clear();
            for (Product item : ListProductsFiltered) {
                if (item.getNameProduct().toLowerCase().contains(filteredCategory.toLowerCase())) {
                    ListProducts.add(item);
                }
            }
            notifyDataSetChanged();
        }

    }
    //ANIMATIONS
    private void StartAnimations(CardView cardView,int position){
        final Handler handler = new Handler();
        handler.postDelayed(()->{
            cardView.setVisibility(View.VISIBLE);
            cardView .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(400));
        }, (position + 1 ) * 100L);

    }

    private void showPlusOne(int position){

        Runnable runnable_HidePlus = () -> {
            ListHolders.get(position).Text_view_PlusOne.startAnimation(Manager_Animation.getFadeOut(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->{
                isPlusShowing.set(position,false);
                plusValues.set(position,0);
                ListHolders.get(position).Text_view_PlusOne.setVisibility(View.GONE);
            },200);
        };

        plusValues.set(position,plusValues.get(position)+1);
        String text = "+"+plusValues.get(position);
        ListHolders.get(position).Text_view_PlusOne.setText(text);

        if(isPlusShowing.get(position)){
            Listhandler_HidePlus.get(position).removeCallbacksAndMessages(null);
            Listhandler_HidePlus.get(position).postDelayed(runnable_HidePlus, 700);
        }else{
            isPlusShowing.set(position,true);
            ListHolders.get(position).Text_view_PlusOne.setVisibility(View.VISIBLE);
            ListHolders.get(position).Text_view_PlusOne.startAnimation(Manager_Animation.getTranslationINfromDownSlower(500));
            Listhandler_HidePlus.get(position).postDelayed(runnable_HidePlus, 700);
        }
    }

    private void removeItem(ViewHolder Holder){
        Holder.Card_View_Element_Product.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
    }
}