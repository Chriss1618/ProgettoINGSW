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

import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

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
    private final ArrayList<String>         TitleProducts;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String product = TitleProducts.get(fromPosition);
        TitleProducts.remove(product);
        TitleProducts.add(toPosition,product);
        notifyItemMoved(fromPosition, toPosition);
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

    public Adapter_Product(Context context,ArrayList<String> TitleProducts, RecycleEventListener RecycleEventListener,boolean isFromLeft){
        this.context                = context;
        this.TitleProducts          = TitleProducts;
        this.RecycleEventListener   = RecycleEventListener;
        this.isFromLeft             = isFromLeft;
        this.Holders                = new ArrayList<>();
        isDeleting = false;
        isOrdering = false;
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
        if (isDeleting) {
            this.Holder.Image_View_delete_element.setVisibility(View.VISIBLE);
        }
        if (isOrdering) {
            this.Holder.LinearLayout_moveItem.setVisibility(View.VISIBLE);
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
        //this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteCategory(position));
        //this.Holder.Image_View_Product.setOnClickListener(view ->moveDrag( this.Holder ));
    }
    private void moveDrag(ViewHolder holder){
        Log.d(TAG, "onTouch: Touched!");


    }
    //ACTIONS
    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Title_Product.getText().toString());
        Log.d(TAG, " Array: "   + this.TitleProducts.get(position));
        Log.d(TAG, "--------------------------------------");

        //RecycleEventListener.onClickItem(TitleProducts.get(position));
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
            holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeInZoomUp(200));
            new Handler(Looper.getMainLooper()).postDelayed( ()->holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeInZoomBackNormal(200)),200);

            int marginInDp = 64;
            float density = context.getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (marginInDp * density);

// Create layout parameters and set margins
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, marginInPixels, 0);

// Apply layout parameters to the TextView
            holder.Text_View_Title_Product.setLayoutParams(layoutParams);
        }
    }

    public void hideMoveIcon(){
        isOrdering = false;
        for (ViewHolder holder:Holders) {
            holder.LinearLayout_moveItem.startAnimation(Manager_Animation.getFadeOutZoom(200));
            final Handler handler = new Handler();
            handler.postDelayed(()->holder.LinearLayout_moveItem.setVisibility(View.GONE), 200);

// Create layout parameters and set margins
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);

// Apply layout parameters to the TextView
            holder.Text_View_Title_Product.setLayoutParams(layoutParams);
        }
    }
}
