package com.ratatouille.Views.Schermate.Inventario;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;


public class Fragment_InfoProductInventory extends Fragment implements ViewLayout {
    //SYSTEM

    //LAYOUT
    private android.view.View View_Fragment;
    private ImageView       ImageView_EditProduct;
    private CardView        CardView_Product;

    //FUNCTIONAL
    private Manager manager;
    //DATA

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
        ImageView_EditProduct   = View_Fragment.findViewById(R.id.ic_edit_product);
        CardView_Product        = View_Fragment.findViewById(R.id.card_view_element_product);
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_EditProduct.setOnClickListener(view -> onClickEditProduct());
    }
    @Override
    public void SetDataOnLayout() {

    }

    //ACTIONS
    private void onClickEditProduct(){
        EndAnimations();
    }

    //FUNCTIONAL


    //ANIMATIONS
    @Override
    public void StartAnimations() {
        ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
    }
    @Override
    public void EndAnimations() {
        ImageView_EditProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}