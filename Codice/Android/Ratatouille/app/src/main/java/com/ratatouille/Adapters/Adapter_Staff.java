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

public class Adapter_Staff extends RecyclerView.Adapter<Adapter_Staff.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Staff";

    //LAYOUT
    private final ArrayList<String>         NameStuffMembers;
    private final RecycleEventListener      RecycleEventListener;
    private ViewHolder                      Holder;
    private ArrayList<ViewHolder>           Holders;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        TextView        Text_View_Name_Staff;
        TextView        Text_View_Ruolo_Staff;
        ImageView       Image_View_State_green;
        ImageView       Image_View_State_red;
        ImageView       Image_View_delete_element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_staff);
            Text_View_Name_Staff        = itemView.findViewById(R.id.text_view_name_staff);
            Text_View_Ruolo_Staff       = itemView.findViewById(R.id.text_view_ruolo_staff);
            Image_View_State_green      = itemView.findViewById(R.id.ic_state_green);
            Image_View_State_red        = itemView.findViewById(R.id.ic_state_red);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
        }
    }

    public Adapter_Staff(ArrayList<String> NameStuffMembers, RecycleEventListener RecycleEventListener){
        this.NameStuffMembers       = NameStuffMembers;
        this.RecycleEventListener   = RecycleEventListener;
        this.Holders                = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff,parent,false);
        return new ViewHolder(view);
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
        return NameStuffMembers.size();
    }

    //LAYOUT
    private void initializeLayout( final int position){
        this.Holder.Text_View_Name_Staff.setText(NameStuffMembers.get(position));
    }

    private void setActions( final int position){
        this.Holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(position));
        this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteStaff(position));
    }

    //ACTIONS
    private void clickDeleteStaff(int position){
        Log.d(TAG, "clickDeleteStaff: "+this.Holders.get(position).Text_View_Name_Staff.getText().toString());

    }
    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Name_Staff.getText().toString());
        Log.d(TAG, " Array: "   + this.NameStuffMembers.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.onClickItem(NameStuffMembers.get(position));
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
