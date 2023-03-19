package com.ratatouille.Schermate.OrdiniCameriere;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.Managers.Manager_Ordini_Cameriere;
import com.ratatouille.R;

import java.util.ArrayList;


public class Fragment_ListCategoryCameriere extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListCategoryCameriere";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Categories;
    private LinearLayout    LinearLayout_NewCategory;
    private LinearLayout    LinearLayout_BackGroundNewCategory;
    private ImageView       Image_View_ShowResoconto;

    //FUNCTIONAL
    private RecycleEventListener RecycleEventListener;
    private Manager_Ordini_Cameriere managerOrdiniCameriere;

    //DATA
    private ArrayList<String> TitleCategories;

    //OTHER...


    public Fragment_ListCategoryCameriere(Manager_Ordini_Cameriere managerOrdiniCameriere) {
        this.managerOrdiniCameriere = managerOrdiniCameriere;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__list_category_cameriere, container, false);
        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_fragment;
    }
    //DATA

    //DATA
    @Override
    public void PrepareData(){
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
    }

    @Override
    public void LinkLayout() {
        Text_View_TitleCategory             = View_fragment.findViewById(R.id.text_view_title_category);
        Image_View_ShowResoconto            = View_fragment.findViewById(R.id.ic_resoconto);
        Recycler_Categories                 = View_fragment.findViewById(R.id.recycler_categories);
        LinearLayout_NewCategory            = View_fragment.findViewById(R.id.linear_layout_new_category);
        LinearLayout_BackGroundNewCategory  = View_fragment.findViewById(R.id.darkRL);
    }
    @Override
    public void SetDataOnLayout() {
        initCategoryRV();
        initDialog();
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener      .setOnClickItemAdapterListener(this::onClickCategory);
        Image_View_ShowResoconto  .setOnClickListener(view ->onClickShowResoconto());
    }

    private void initCategoryRV(){
        Adapter_Category adapter_category = new Adapter_Category(TitleCategories, RecycleEventListener);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);
    }
    private void initDialog(){


    }
    //ACTIONS
    private void onClickCategory(String Category){
        Log.d(TAG, "Ricevuto da Listener->"+Category);
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_Ordini_Cameriere.INDEX_ORDINI_CAMERIERE_LIST_PRODUCTS,Category),
                300);
    }

    private void onClickShowResoconto(){
        Log.d(TAG, "onClickShowResoconto");
        managerOrdiniCameriere.showBottomSheet();
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.managerOrdiniCameriere.showFragment(index,msg);
    }

    private void showDialogNewCategory(){
        CardView CardView_Cancel    = LinearLayout_NewCategory.findViewById(R.id.card_view_annulla);
        CardView CardView_Add       = LinearLayout_NewCategory.findViewById(R.id.card_view_aggiungi);

        CardView_Cancel.setOnClickListener(view -> dismissDialogNewCategory());

        LinearLayout_NewCategory            .setVisibility(View.VISIBLE);
        LinearLayout_BackGroundNewCategory  .setVisibility(View.VISIBLE);
        LinearLayout_NewCategory.startAnimation(Manager_Animation.getTranslationINfromUp(500));
        LinearLayout_BackGroundNewCategory.startAnimation(Manager_Animation.getFadeIn(500));
    }

    private void dismissDialogNewCategory(){
        LinearLayout_NewCategory.setVisibility(View.GONE);
        LinearLayout_BackGroundNewCategory.setVisibility(View.GONE);
        LinearLayout_NewCategory.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
        LinearLayout_BackGroundNewCategory.startAnimation(Manager_Animation.getFadeOut(500));
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        Text_View_TitleCategory     .setText(R.string.Menu);
        Text_View_TitleCategory     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Recycler_Categories         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }
    @Override
    public void EndAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_Categories     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }
}