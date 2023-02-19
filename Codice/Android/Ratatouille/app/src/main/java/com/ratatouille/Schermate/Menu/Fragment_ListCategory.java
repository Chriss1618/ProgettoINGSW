package com.ratatouille.Schermate.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Controllers.Controller_Amministratore;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.AdapterEvent;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecycleEvent;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;
import com.ratatouille.Schermate.Activity_Amministratore;
import com.ratatouille.Schermate.Login.Activity_Login;

import java.util.ArrayList;

public class Fragment_ListCategory extends Fragment implements LayoutContainer {


    //SYSTEM
    private static final String TAG = "Fragment_ListCategory";

    //LAYOUT
    private TextView Text_View_TitleCategory;
    private View view_fragment;
    private RecyclerView Recycler_Categories;

    //FUNCTIONS
    private Adapter_Category        adapter_category;
    private RecycleEvent            EventListener;

    //DATA
    private ArrayList<String> TitleCategories;
    private String Category_Title;

    //OTHERS...
    public Fragment_ListCategory() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventListener = new RecycleEvent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_fragment = inflater.inflate(R.layout.fragment__list_category, container, false);
        PreparerData();
        PrepareLayout();

        return view_fragment;
    }

    //DATA
    @Override
    public void PreparerData(){
            TitleCategories = new ArrayList<>();
            TitleCategories.add("Primo");
            TitleCategories.add("Secondo");
            TitleCategories.add("Antipasti");
            TitleCategories.add("Contorno");
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
        Text_View_TitleCategory = view_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Categories     = view_fragment.findViewById(R.id.recycler_categories);
    }

    @Override
    public void SetDataOnLayout() {
        initFeaturesRV();
    }

    @Override
    public void SetActionsOfLayout() {
        EventListener.setOnClickItemAdapterListener(Category -> {
            Log.d(TAG, "PreparerData: Hai premuto l'item->"+Category);
            Category_Title = Category;
            final Handler handler = new Handler();
            animateTextTitleOUT();
            handler.postDelayed(()->{
                ((Activity_Amministratore)getActivity()).changeFragmentOnAmministrator(
                        Controller_Amministratore.AMMINISTRATORE_INDEX_MENU,
                        Manager_MenuFragments.INDEX_MENU_LIST_PRODUCTS,
                        Category_Title);
            },300);



        });
    }
    private void initFeaturesRV(){
        adapter_category  = new Adapter_Category(getActivity(),TitleCategories,EventListener.AdapterListener);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);
    }
    //FUNCTIONAL

    //ANIMATIONS
    private void animateTextTitleOUT(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_Categories     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }

    private void StartAnimations(){
        Text_View_TitleCategory.setText("Menu");
        Text_View_TitleCategory     .startAnimation(Manager_Animation.getTranslationINfromUp(300));
        Recycler_Categories         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
}