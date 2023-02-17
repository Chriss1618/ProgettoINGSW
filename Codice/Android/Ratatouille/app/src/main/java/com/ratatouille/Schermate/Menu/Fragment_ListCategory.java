package com.ratatouille.Schermate.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.R;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Fragment_ListCategory extends Fragment {
    //SYSTEM
    private static final String TAG = "Fragment_ListCategory";

    //LAYOUT
    private View View_layout;
    private RecyclerView Recycler_Categories;

    //FUNCTIONS
    private Adapter_Category adapter_category;

    //DATA
    private ArrayList<String> TitleCategories;
    //OTHERS...
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fragment_ListCategory() {
    }

    public static Fragment_ListCategory newInstance(String param1, String param2) {
        Fragment_ListCategory fragment = new Fragment_ListCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View_layout = inflater.inflate(R.layout.fragment__list_category, container, false);
        PreparerData();
        PrepareLayout();

        return View_layout;
    }

    //DATA
    private void PreparerData(){
        TitleCategories = new ArrayList<>();
        TitleCategories.add("Primo");
        TitleCategories.add("Secondo");
        TitleCategories.add("Antipasti");
        TitleCategories.add("Contorno");
    }
    //LAYOUT

    //LAYOUT
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Recycler_Categories = View_layout.findViewById(R.id.recycler_categories);
    }

    private void SetDataOnLayout() {
        initFeaturesRV(TitleCategories);
    }
    private void SetActionsOfLayout() {

    }

    private void initFeaturesRV( ArrayList<String> featuresEvent){
        adapter_category  = new Adapter_Category(getActivity(),featuresEvent);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);
    }


    //FUNCTIONAL

}