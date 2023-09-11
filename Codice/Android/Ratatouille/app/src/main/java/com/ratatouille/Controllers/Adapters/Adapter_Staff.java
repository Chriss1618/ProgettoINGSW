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

import com.ratatouille.Controllers.Adapters.Holders.ViewHolder_IngredientProduct;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;

import java.util.ArrayList;

public class Adapter_Staff extends RecyclerView.Adapter<Adapter_Staff.ViewHolder>{
    //SYSTEM
    private static final String TAG = "Adapter_Staff";

    //LAYOUT
    private ArrayList<Utente>         NameStuffMembers;
    private final ArrayList<Utente>         NameStuffMembersFiltered;
    private final RecycleEventListener RecycleEventListener;
    private ViewHolder                      Holder;
    private ArrayList<ViewHolder>           Holders;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView        Card_View_Element_Product;
        TextView        Text_View_Name_Staff;
        TextView        Text_View_Ruolo_Staff;
        ImageView       Image_View_State_green;
        ImageView       Image_View_Role;
        ImageView       Image_View_State_red;
        ImageView       Image_View_delete_element;
        public Utente          Stuff;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Card_View_Element_Product   = itemView.findViewById(R.id.card_view_element_staff);
            Text_View_Name_Staff        = itemView.findViewById(R.id.text_view_name_staff);
            Text_View_Ruolo_Staff       = itemView.findViewById(R.id.text_view_ruolo_staff);
            Image_View_State_green      = itemView.findViewById(R.id.ic_state_green);
            Image_View_State_red        = itemView.findViewById(R.id.ic_state_red);
            Image_View_delete_element   = itemView.findViewById(R.id.ic_delete_on_element);
            Image_View_Role             = itemView.findViewById(R.id.image_view_account);
        }
    }

    public Adapter_Staff(ArrayList<Utente> ListStaff, RecycleEventListener RecycleEventListener){
        this.NameStuffMembers           = new ArrayList<>(ListStaff) ;
        this.NameStuffMembersFiltered   = new ArrayList<>(ListStaff) ;


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
        this.Holders.get(position).Stuff = NameStuffMembers.get(position);
        this.Holders.get(position).Text_View_Name_Staff.setText(NameStuffMembers.get(position).getNome() + " " + NameStuffMembers.get(position).getCognome());
        this.Holders.get(position).Text_View_Ruolo_Staff.setText(NameStuffMembers.get(position).getType_user());
        switch (NameStuffMembers.get(position).getType_user()){

            case ControlMapper.INDEX_TYPE_USER_CAMERIERE :  this.Holders.get(position).Image_View_Role.setImageResource(R.drawable.ic_table_white);
            break;
            case ControlMapper.INDEX_TYPE_USER_CHEF:        this.Holders.get(position).Image_View_Role.setImageResource(R.drawable.ic_staff_chef_white);
            break;
            case ControlMapper.INDEX_TYPE_USER_SUPERVISORE:  this.Holders.get(position).Image_View_Role.setImageResource(R.drawable.ic_staff_supervisore_white);
            break;

        }

        if(NameStuffMembers.get(position).getToken().equals("NO_TOKEN")){
            this.Holders.get(position).Image_View_State_green.setImageResource(R.drawable.ic_state_neautral);
            this.Holders.get(position).Image_View_State_red.setImageResource(R.drawable.ic_state_red);


        }else{
            this.Holders.get(position).Image_View_State_green.setImageResource(R.drawable.ic_state_green);
            this.Holders.get(position).Image_View_State_red.setImageResource(R.drawable.ic_state_neautral);
        }
    }

    private void setActions( final int position){
        this.Holder.Card_View_Element_Product.setOnClickListener(view -> clickProduct(position));
        this.Holders.get(position).Image_View_delete_element.setOnClickListener(view -> clickDeleteStaff(position));
    }

    //ACTIONS
    private void clickDeleteStaff(int position){
        Log.d(TAG, "clickDeleteStaff: "+this.Holders.get(position).Text_View_Name_Staff.getText().toString());
        RecycleEventListener.onClickItemOption(NameStuffMembers.get(position).getEmail(),NameStuffMembers.get(position).getId_utente());
    }
    private void clickProduct( final int position){
        Log.d(TAG, "Premuto in Product--------------------");
        Log.d(TAG, " Holder: "  + this.Holder.Text_View_Name_Staff.getText().toString());
        Log.d(TAG, " Array: "   + this.NameStuffMembers.get(position));
        Log.d(TAG, "--------------------------------------");

        RecycleEventListener.onClickItem(NameStuffMembers.get(position));
    }

    public void filterList(String filteredUser){
        if (filteredUser.isEmpty()) {
            NameStuffMembers = new ArrayList<>(NameStuffMembersFiltered);
        } else {
            NameStuffMembers.clear();
            for (Utente item : NameStuffMembersFiltered) {
                if (item.getNome().toLowerCase().contains(filteredUser.toLowerCase())
                    || item.getCognome().toLowerCase().contains(filteredUser.toLowerCase())
                ) {
                    NameStuffMembers.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void removeItem(int id_staff) {
        Log.d(TAG, "removeItem: Inizio");
        for (Utente utente : NameStuffMembers) {
            if(utente.getId_utente() == id_staff){
                NameStuffMembers.remove(utente);
                removeFromHolders(id_staff);
                break;
            }
        }

    }

    public void removeFromHolders(int id_staff) {
        for(ViewHolder holder : Holders){
            if(holder.Stuff.getId_utente() == id_staff){
                Holders.remove(holder);
                break;
            }
        }
        notifyDataSetChanged();
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
