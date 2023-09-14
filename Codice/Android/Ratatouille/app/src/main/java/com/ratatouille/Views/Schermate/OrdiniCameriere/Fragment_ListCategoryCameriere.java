package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Category;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.R;
import java.util.ArrayList;


public class Fragment_ListCategoryCameriere extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListCategoryCameriere";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Categories;
    private ImageView       Image_View_ShowResoconto;
    private TextView        Text_View_Empty;
    private ProgressBar     ProgressBar;
    private EditText        EditText_SearchCategory;
    private ImageView       ImageView_Back;
    //FUNCTIONAL
    private RecycleEventListener RecycleEventListener;
    private Manager manager;
    private Adapter_Category        adapter_category;
    //DATA
    private ArrayList<CategoriaMenu> ListCategoryMenu;

    //OTHER...

    public Fragment_ListCategoryCameriere(Manager manager, int a) {
        this.manager = manager;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        RecycleEventListener = new RecycleEventListener();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__list_category_cameriere, container, false);
        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_fragment;
    }
    //DATA

    //DATA
    @Override
    public void PrepareData(){
        ProgressBar.setVisibility(View.VISIBLE);
        Recycler_Categories.setVisibility(View.GONE);
        sendRequest();
    }
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_CATEGORY,
                (list)-> setCategoriesOnLayout((ArrayList<CategoriaMenu>) list));
        manager.HandleRequest(request);
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
        ImageView_Back                      = View_fragment.findViewById(R.id.ic_back);
        ProgressBar                         = View_fragment.findViewById(R.id.progressbar);
        Text_View_TitleCategory             = View_fragment.findViewById(R.id.text_view_title_category);
        Image_View_ShowResoconto            = View_fragment.findViewById(R.id.ic_resoconto);
        Recycler_Categories                 = View_fragment.findViewById(R.id.recycler_categories);
        Text_View_Empty                     = View_fragment.findViewById(R.id.text_view_empty);
        EditText_SearchCategory             = View_fragment.findViewById(R.id.edit_text_name_category);
    }
    @Override
    public void SetDataOnLayout() {
    }
    @Override
    public void SetActionsOfLayout() {

        ImageView_Back              .setOnClickListener(view -> manager.goBack());
        RecycleEventListener        .setOnClickItemAdapterListener((item)-> onClickCategory( (CategoriaMenu)item ) );
        Image_View_ShowResoconto    .setOnClickListener(view ->onClickShowResoconto());
        EditText_SearchCategory     .addTextChangedListener( onChangeSearchCategory() );
    }

    private void initCategoryRV(){
        adapter_category = new Adapter_Category(ListCategoryMenu, RecycleEventListener,false);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);

        checkEmptyRecycle();
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickCategory(CategoriaMenu Category){
        Log.d(TAG, "Ricevuto da Listener->"+Category.getNomeCategoria());
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_OPEN_LIST_PRODUCTS, Category);
        SendAction(action);
    }

    private void onClickShowResoconto(){
        Log.d(TAG, "onClickShowResoconto");
        //managerOrdiniCameriere.showBottomSheet();
    }
    private TextWatcher onChangeSearchCategory(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(adapter_category != null){
                    adapter_category.filterList(charSequence.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    //FUNCTIONAL
    private void setCategoriesOnLayout(ArrayList<CategoriaMenu> list){
        ListCategoryMenu = list;
        requireActivity().runOnUiThread(() -> {
            initCategoryRV();
            ProgressBar.setVisibility(View.GONE);
        });
    }
    private void checkEmptyRecycle(){
        if(ListCategoryMenu.isEmpty()) {
            Text_View_Empty.setVisibility(View.VISIBLE);
            Recycler_Categories.setVisibility(View.GONE);
            StartAnimationEmptyCategories();
        }else{
            Text_View_Empty.setVisibility(View.GONE);
            Recycler_Categories.setVisibility(View.VISIBLE);
            StartAnimationCategories();
        }
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
    private void StartAnimationCategories(){
        Recycler_Categories         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }

    private void StartAnimationEmptyCategories(){
        Text_View_Empty         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
}