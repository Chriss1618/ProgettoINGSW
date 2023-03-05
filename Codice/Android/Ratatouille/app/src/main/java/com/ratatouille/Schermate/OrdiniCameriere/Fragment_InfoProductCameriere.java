package com.ratatouille.Schermate.OrdiniCameriere;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.R;


public class Fragment_InfoProductCameriere extends Fragment implements LayoutContainer {

    public Fragment_InfoProductCameriere() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment__info_product_cameriere, container, false);
    }
    //DATA
    @Override
    public void PrepareData() {

    }

    //LAYOUT
    @Override
    public void PrepareLayout() {

    }

    @Override
    public void LinkLayout() {

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

    }
    @Override
    public void EndAnimations() {

    }
}