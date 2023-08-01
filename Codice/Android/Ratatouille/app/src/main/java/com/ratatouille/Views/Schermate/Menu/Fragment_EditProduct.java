package com.ratatouille.Views.Schermate.Menu;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.R;


public class Fragment_EditProduct extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_EditProduct";

    //LAYOUT
    android.view.View View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    CardView            CardView_ProductData;
    LinearLayout        LinearLayout_Buttons;

    //FUNCTIONAL
    Manager manager;
    //DATA

    //OTHER...

    public Fragment_EditProduct(Manager manager_MenuFragments,int a) {
        this.manager = manager_MenuFragments;
    }

    public Fragment_EditProduct() {
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
        View_Fragment = inflater.inflate(R.layout.fragment__edit_product, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();
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
    }
    @Override
    public void LinkLayout() {
        LinearLayout_TitleProduct   = View_Fragment.findViewById(R.id.toolbar_title_product);
        CardView_ProductData        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons        = View_Fragment.findViewById(R.id.linear_layout_buttons);

    }
    @Override
    public void SetActionsOfLayout() {

    }

    @Override
    public void SetDataOnLayout() {

    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationINfromDown(500));
    }
    @Override
    public void EndAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationOUTtoDown(500));
    }
}