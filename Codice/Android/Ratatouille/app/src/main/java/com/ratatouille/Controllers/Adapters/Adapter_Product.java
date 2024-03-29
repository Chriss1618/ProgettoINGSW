package com.ratatouille.Controllers.Adapters;

import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ratatouille.Controllers.Adapters.Holders.ViewHolder_IngredientProduct;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Product  extends RecyclerView.Adapter<Adapter_Product.ViewHolder> implements ITouchAdapter {
    //SYSTEM
    private static final String TAG = "Adapter_Product";

    //LAYOUT
    private final RecycleEventListener      RecycleEventListener;
    private ViewHolder                      Holder;
    private ArrayList<ViewHolder>           Holders;

    //FUNCTIONAL
    private Context context;
    private boolean isFromLeft;
    private ItemTouchHelper touchHelper;
    private boolean isDeleting;
    private boolean isOrdering;

    //DATA
    private ArrayList<Product>         ListProducts;
    private final ArrayList<Product>         ListProductsFiltered;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Product product = ListProducts.get(fromPosition);
        ListProducts.remove(product);
        ListProducts.add(toPosition,product);
        notifyItemMoved(fromPosition, toPosition);
        int position = 0;
        for(Product product1 : ListProducts){
            ListProducts.get(position).setOrder(position);
            position+=1;
        }
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.touchHelper = touchHelper;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener,GestureDetector.OnGestureListener {
        CardView    Card_View_Element_Product;
        TextView    Text_View_Title_Product;
        TextView    Text_View_Price_Product;
        ImageView   Image_View_Product;
        ImageView   Image_View_delete_element;
        ImageView   Image_View_move_element;
        LinearLayout LinearLayout_moveItem;
        LinearLayout LinearLayout_moveItem2;
        GestureDetector gestureDetector;

        //DATA
        Product product;
        public void setProduct(Product product){
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }

        @SuppressLint("ClickableViewAccessibility")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gestureDetector = new GestureDetector(itemView.getContext(),this);
            itemView.setOnTouchListener(this);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_product);
            Text_View_Title_Product     = itemView.findViewById(R.id.text_view_title_product);
            Text_View_Price_Product     = itemView.findViewById(R.id.text_view_price);
            Image_View_Product          = itemView.findViewById(R.id.image_view_product);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
            Image_View_move_element     = itemView.findViewById(R.id.image_view_order_item);
            LinearLayout_moveItem       = itemView.findViewById(R.id.linear_layout_order_item);
            LinearLayout_moveItem2      = itemView.findViewById(R.id.linear_layout_order_item2);

            Image_View_move_element .setOnTouchListener(this::DragItem);
            LinearLayout_moveItem   .setOnTouchListener(this::DragItem);
            LinearLayout_moveItem2  .setOnTouchListener(this::DragItem);


        }

        private boolean DragItem(View view, MotionEvent motionEvent){
            gestureDetector.onTouchEvent(motionEvent);

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(ViewHolder.this); // Start dragging when touched

            }
            return true;
        }
        @Override
        public boolean onDown(@NonNull MotionEvent motionEvent) {
            Log.d(TAG, "ViewHolder: detected");

            
            return true;
        }

        @Override
        public void onShowPress(@NonNull MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent motionEvent) {
            touchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    public Adapter_Product(Context context, ArrayList<Product> ListProducts, RecycleEventListener RecycleEventListener, boolean isFromLeft){
        this.context                = context;
        this.ListProducts           = new ArrayList<>(ListProducts);
        this.ListProductsFiltered   = new ArrayList<>(ListProducts);
        this.RecycleEventListener   = RecycleEventListener;
        this.isFromLeft             = isFromLeft;
        this.Holders                = new ArrayList<>();
        isDeleting = false;
        isOrdering = false;
    }

    public ArrayList<Product> getListProducts() {
        return ListProducts;
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
        holder.setProduct(ListProducts.get(position));
        initializeLayout( holder,position);
        setActions( holder,position);

        if (isFromLeft) {
            this.Holder.Card_View_Element_Product.setVisibility(View.GONE);
            StartAnimations(this.Holder,position);
        }
        if (isDeleting) {
            this.Holder.Image_View_delete_element.setVisibility(View.VISIBLE);
        }
        
    }

    @Override
    public int getItemCount() {
        return ListProducts.size();
    }


    //LAYOUT
    private void initializeLayout(ViewHolder holder, final int position) {
        try {
            holder.Text_View_Title_Product.setText(ListProducts.get(position).getNameProduct());

            String formattedValue = new DecimalFormat("0.00").format(ListProducts.get(position).getPriceProduct()) + context.getResources().getString(R.string.euro);
            holder.Text_View_Price_Product.setText(formattedValue );
            if(ListProducts.get(position).getURLImageProduct().contains("https")){
                Picasso.get()
                        .load(ListProducts.get(position).getURLImageProduct())
                        .into(holder.Image_View_Product);
            }else{
                Picasso.get()
                        .load(EndPointer.StandardPath+ EndPointer.IMAGES_PRODUCT+ ListProducts.get(position).getURLImageProduct())
                        .into(holder.Image_View_Product);

            }
        }catch (Exception exception){
            Log.e(TAG, "setActions: ",exception );
        }

    }

    private void setActions(ViewHolder holder, final int position) {
        try {
            holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(position));
            this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteProduct(position,holder));

        }catch (Exception exception){
            Log.e(TAG, "setActions: ",exception );
        }


        //this.Holder.Image_View_Product.setOnClickListener(view ->moveDrag( this.Holder ));
    }


    public void removeItem(int id_cat) {
        int index = ListProducts.size();
        for(int i = 0; i< ListProducts.size();i++){
            if(ListProducts.get(i).getID_product() == id_cat){
                ListProducts.remove(i);
                index = i;
                notifyItemRemoved(i);
                break;
            }
        }
        for(int i = 0; i< ListProductsFiltered.size();i++){
            if(ListProductsFiltered.get(i).getID_product() == id_cat){
                ListProductsFiltered.remove(i);
                break;
            }
        }

        for (int i = index; i < ListProducts.size(); i++) {
            notifyItemChanged(i);
        }
    }
    public void removeFromHolders(int id_product) {
        for(ViewHolder holder : Holders){
            if(holder.getProduct().getID_product() == id_product){
                Holders.remove(holder);
                break;
            }
        }
        notifyDataSetChanged();
    }



    //ACTIONS
    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.ListProducts.get(position).getNameProduct());
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.onClickItem(ListProducts.get(position));
    }
    private void clickDeleteProduct(int position, ViewHolder holder){
        Log.d(TAG, "clickDeleteCategory: "+holder.Text_View_Title_Product.getText().toString());
        RecycleEventListener.onClickItemOption(holder.Text_View_Title_Product.getText().toString(),ListProducts.get(position).getID_product());
    }

    //FUNCTIONAL
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
    private void StartAnimations(ViewHolder holder,int position){
        final Handler handler = new Handler();
        handler.postDelayed(()->{
            holder.Card_View_Element_Product    .setVisibility(View.VISIBLE);
            holder.Card_View_Element_Product    .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(400));
            if (isOrdering) {
                new Handler(Looper.getMainLooper()).postDelayed(()->{
                    this.Holder.LinearLayout_moveItem.setVisibility(View.VISIBLE);
                    ShowMoveOne(holder);
                },2000) ;
            }
        }, (position + 1 ) * 100L);

    }

    public void showDeleteIcon(){
        isDeleting = true;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.setVisibility(View.VISIBLE);
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeInZoom(200));
        }
    }

    public void hideDeleteIcon(){
        isDeleting = false;
        for (ViewHolder holder:Holders) {
            holder.Image_View_delete_element.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->
                            holder.Image_View_delete_element.setVisibility(View.GONE),
                    200);
        }
    }

    public void showMoveIcon(){
        isOrdering = true;
        for (ViewHolder holder:Holders) {
            holder.LinearLayout_moveItem.setVisibility(View.VISIBLE);
            ShowMoveOne(holder);
        }
    }

    private void ShowMoveOne(ViewHolder holder){
        holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeInZoomUp(200));
        new Handler(Looper.getMainLooper()).postDelayed( ()->holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeInZoomBackNormal(200)),200);

        int marginInDp = 64;
        float density = context.getResources().getDisplayMetrics().density;
        int marginInPixels = (int) (marginInDp * density);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, marginInPixels, 0);
        holder.Text_View_Title_Product.setLayoutParams(layoutParams);
    }

    public void hideMoveIcon(){
        isOrdering = false;
        for (ViewHolder holder:Holders) {
            holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->holder.LinearLayout_moveItem.setVisibility(View.INVISIBLE), 200);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            holder.Text_View_Title_Product.setLayoutParams(layoutParams);
        }
    }
}
