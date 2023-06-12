package com.ratatouille.Schermate.Menu;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;

public class Fragment_InfoProduct extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_InfoProduct";
    private static final String PRODUCT_TAG = "stringToPass";

    //LAYOUT
    private View        View_fragment;
    private TextView    Text_View_Title_Product;
    private CardView    Card_Item_Product;
    private ImageView   ImageView_Edit_Product;

    //FUNCTIONAL
    private final Manager_MenuFragments manager_MenuFragments;

    //DATA
    private String Product_Name;

    //OTHER...

    public Fragment_InfoProduct(Manager_MenuFragments manager_MenuFragments) {
        this.manager_MenuFragments = manager_MenuFragments;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Product_Name = getArguments().getString(PRODUCT_TAG);
        }
        Log.d(TAG, "onCreate: Product passed:"+ Product_Name);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View_fragment = inflater.inflate(R.layout.fragment__info_product, container, false);

        PrepareData();
        PrepareLayout();

        return View_fragment;
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
        Text_View_Title_Product = View_fragment.findViewById(R.id.text_view_name_product);
        Card_Item_Product       = View_fragment.findViewById(R.id.card_view_element_product);
        ImageView_Edit_Product  = View_fragment.findViewById(R.id.ic_edit_product);
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_Edit_Product.setOnClickListener(view -> onClickEditProduct());
    }
    @Override
    public void SetDataOnLayout() {
        Text_View_Title_Product.setText(Product_Name);
    }

    //ACTIONS
    private void onClickEditProduct(){
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_MenuFragments.INDEX_MENU_EDIT_PRODUCT,Product_Name),
                300);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.manager_MenuFragments.showFragment(index,msg);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        Card_Item_Product       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(300));

    }

    @Override
    public void EndAnimations(){
        Card_Item_Product       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));

    }

}