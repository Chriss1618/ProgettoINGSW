package com.ratatouille.Schermate.OrdiniCameriere;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;
import com.ratatouille.R;

import java.util.ArrayList;


public class Fragment_InfoProductCameriere extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_InfoProductCam";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_Title;
    private CardView        CardView_ProductInfo;

    //FUNCTIONAL
    private RecycleEventListener        RecycleEventListener;
    private Manager_Ordini_Cameriere    managerOrdiniCameriere;

    //DATA
    private ArrayList<String> TitleProduct;

    //OTHER...

    public Fragment_InfoProductCameriere(Manager_Ordini_Cameriere managerOrdiniCameriere) {
        this.managerOrdiniCameriere = managerOrdiniCameriere;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__info_product_cameriere, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();
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
    }

    @Override
    public void LinkLayout() {
        CardView_ProductInfo = View_fragment.findViewById(R.id.card_view_element_product);
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
        CardView_ProductInfo.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
    }
    @Override
    public void EndAnimations() {
        CardView_ProductInfo.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}