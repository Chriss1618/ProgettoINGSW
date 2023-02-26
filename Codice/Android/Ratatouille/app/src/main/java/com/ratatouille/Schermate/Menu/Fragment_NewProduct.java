package com.ratatouille.Schermate.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;

public class Fragment_NewProduct extends Fragment {
    //SYSTEM
    private static final String TAG = "Fragment_NewProduct";

    //LAYOUT

    //FUNCTIONAL
    private final Manager_MenuFragments manager_MenuFragments;

    //DATA
    //OTHER...
    public Fragment_NewProduct(Manager_MenuFragments manager_MenuFragments) {
        this.manager_MenuFragments = manager_MenuFragments;
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
        return inflater.inflate(R.layout.fragment__new_product, container, false);
    }
}