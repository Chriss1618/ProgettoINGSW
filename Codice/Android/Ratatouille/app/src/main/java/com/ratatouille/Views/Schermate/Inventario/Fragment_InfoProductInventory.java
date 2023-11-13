package com.ratatouille.Views.Schermate.Inventario;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditIngredient;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;


public class Fragment_InfoProductInventory extends Fragment implements ViewLayout {
    //SYSTEM

    //LAYOUT
    private android.view.View View_Fragment;
    private LinearLayout    LinearLayout_GoBack;
    private ImageView       ImageView_EditProduct;
    private ImageView       ImageView_Back;

    private ImageView       ImageView_Ingredient;
    private TextView        TextView_Ingredient;
    private TextView        TextView_Description;
    private TextView        TextView_Misura;
    private TextView        TextView_Price;
    private TextView        TextView_QtaInInventario;

    private CardView        CardView_Product;

    //FUNCTIONAL
    private Manager manager;
    //DATA
    private Ingredient Ingredient;
    //OTHER..

    public Fragment_InfoProductInventory(Manager manager, int a) {
        this.manager = manager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment = inflater.inflate(R.layout.fragment__info_product_inventory, container, false);

        PrepareData();
        PrepareLayout();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        Ingredient = (Ingredient) manager.getData();
    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();

        StartAnimations();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_GoBack   = View_Fragment.findViewById(R.id.linearlayout_go_back);
        ImageView_EditProduct   = View_Fragment.findViewById(R.id.ic_edit_product);
        ImageView_Back          = View_Fragment.findViewById(R.id.ic_back);
        CardView_Product        = View_Fragment.findViewById(R.id.card_view_element_product);

        ImageView_Ingredient        = View_Fragment.findViewById(R.id.image_view_image_ingredient);
        TextView_Ingredient         = View_Fragment.findViewById(R.id.text_view_name_ingredient);
        TextView_Description        = View_Fragment.findViewById(R.id.text_view_description_ingredient);
        TextView_Misura             = View_Fragment.findViewById(R.id.text_view_measure_ingredient);
        TextView_Price              = View_Fragment.findViewById(R.id.text_view_price_ingredient);
        TextView_QtaInInventario    = View_Fragment.findViewById(R.id.text_view_qta_ingredient);
    }

    @Override
    public void SetActionsOfLayout() {
        ImageView_EditProduct.setOnClickListener(view -> onClickEditProduct());
        ImageView_Back.setOnClickListener(view -> manager.goBack());
    }
    @Override
    public void SetDataOnLayout() {
        if(Ingredient.getURLImageIngredient().contains("https")){
            Picasso.get()
                    .load(Ingredient.getURLImageIngredient())
                    .into(ImageView_Ingredient);
        }else if(Ingredient.isHasPhoto()){
            Picasso.get()
                    .load(Ingredient.getUriImageIngredient())
                    .into(ImageView_Ingredient);
        }else {
            Picasso.get()
                    .load(EndPointer.StandardPath+"/Images/Ingredient/"+ Ingredient.getURLImageIngredient())
                    .into(ImageView_Ingredient);
        }

        TextView_Ingredient     .setText(Ingredient.getNameIngredient());
        TextView_Description    .setText(Ingredient.getDescription());
        TextView_Misura         .setText(Ingredient.getSizeIngredient() + " " + Ingredient.getMeasureType());
        TextView_Price          .setText(Ingredient.getPriceIngredient()+"€");
        TextView_QtaInInventario.setText(Ingredient.getQtaIngredient()+"");
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickEditProduct(){
        Action action = new Action(ActionsInfoEditIngredient.INDEX_ACTION_EDIT_INGREDIENT,Ingredient);
        SendAction(action);
    }

    //FUNCTIONAL


    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(!manager.bottomBarListener.visible){
            manager.showBottomBar();
        }
        if(manager.IndexFrom > manager.IndexOnMain){
            LinearLayout_GoBack     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
            ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(600));
            CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        }else{
            LinearLayout_GoBack     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
            ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(600));
            CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        }
    }

    @Override
    public void EndAnimations() {
        if(manager.IndexOnMain > manager.IndexFrom){
            LinearLayout_GoBack     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(600));
            ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
            CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        }else{
            LinearLayout_GoBack     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(600));
            ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
            CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        }

    }
}