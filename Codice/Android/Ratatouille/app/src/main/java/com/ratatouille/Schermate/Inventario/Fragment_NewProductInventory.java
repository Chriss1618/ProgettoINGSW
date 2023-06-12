package com.ratatouille.Schermate.Inventario;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_InventoryFragments;
import com.ratatouille.R;

public class Fragment_NewProductInventory extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewProductInve";

    //LAYOUT
    android.view.View View_Fragment;
    TextView        TextView_Title;
    CardView        CardView_Product;
    LinearLayout    LinearLayout_Buttons;

    //FUNCTIONAL
    Manager_InventoryFragments manager_inventoryFragments;
    //DATA

    //OTHER...

    public Fragment_NewProductInventory(Manager_InventoryFragments manager_inventoryFragments) {
        this.manager_inventoryFragments = manager_inventoryFragments;
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
        View_Fragment = inflater.inflate(R.layout.fragment__new_product_inventory, container, false);
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

    }
    @Override
    public void SetActionsOfLayout() {
        TextView_Title          = View_Fragment.findViewById(R.id.text_view_title_product);
        CardView_Product        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons    = View_Fragment.findViewById(R.id.linear_layout_buttons);
    }
    @Override
    public void SetDataOnLayout() {

    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title          .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        LinearLayout_Buttons    .startAnimation(Manager_Animation.getTranslationINfromDown(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title          .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        LinearLayout_Buttons    .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
    }
}