package com.ratatouille.Views.Schermate.Inventario;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_ProductInventory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListInventory;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListInventary extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListInventary";

    //LAYOUT
    private android.view.View View_Fragment;
    private TextView        TextView_TitleInventory;
    private TextView        TextView_TitleExist;
    private TextView        TextView_TitleMissing;
    private RecyclerView    Recycler_Products_Exist;
    private RecyclerView    Recycler_Products_Missing;
    private ImageView       ImageView_AddIngredient;
    private ImageView       ImageView_DeleteProduct;

    //FUNCTIONAL
    private final RecycleEventListener          RecycleEventListener;
    private Manager                             manager;
    private Adapter_ProductInventory            adapter_product;
    private boolean                             isDeleting;

    //DATA
    private ArrayList<String>   TitleProducts_Exist;
    private ArrayList<String>   TitleProducts_Missing;

    //OTHER...

    public Fragment_ListInventary(Manager manager, int a) {
        this.manager = manager;
        this.RecycleEventListener                = new RecycleEventListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment =  inflater.inflate(R.layout.fragment__list_inventary, container, false);
        PrepareData();
        PrepareLayout();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        TitleProducts_Exist = new ArrayList<>();
        TitleProducts_Missing = new ArrayList<>();

        TitleProducts_Exist.add("Pizza Tonno");
        TitleProducts_Exist.add("Pizza Margherita");

        TitleProducts_Missing.add("Pizza Tonno");
        TitleProducts_Missing.add("Pizza Margherita");
        isDeleting = false;
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
        TextView_TitleInventory     = View_Fragment.findViewById(R.id.text_view_title_inventory);
        TextView_TitleExist         = View_Fragment.findViewById(R.id.text_view_exist);
        TextView_TitleMissing       = View_Fragment.findViewById(R.id.text_view_missing);
        Recycler_Products_Exist     = View_Fragment.findViewById(R.id.recycler_products_exist);
        Recycler_Products_Missing   = View_Fragment.findViewById(R.id.recycler_products_finished);
        ImageView_DeleteProduct     = View_Fragment.findViewById(R.id.ic_delete_product);
        ImageView_AddIngredient     = View_Fragment.findViewById(R.id.ic_add_ingrediente);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener   (this::onClickProduct);
        ImageView_DeleteProduct .setOnClickListener              (view -> onClickDeleteProduct());
        ImageView_AddIngredient .setOnClickListener              (view -> onCLickAddNewIngredient());
    }
    @Override
    public void SetDataOnLayout() {
        initListProductsExistRV();
        initListProductsMissingRV();
    }

    private void initListProductsExistRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products_Exist.setLayoutManager(mLayoutManager);
        Recycler_Products_Exist.setNestedScrollingEnabled(false);
        Adapter_ProductInventory adapter_product = new Adapter_ProductInventory(TitleProducts_Exist, RecycleEventListener);
        Recycler_Products_Exist.setAdapter(adapter_product);
    }

    private void initListProductsMissingRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products_Missing.setLayoutManager(mLayoutManager);
        Recycler_Products_Missing.setNestedScrollingEnabled(false);
        adapter_product = new Adapter_ProductInventory(TitleProducts_Missing, RecycleEventListener);
        Recycler_Products_Missing.setAdapter(adapter_product);
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickProduct(Object product) {
        EndAnimations();
    }

    private void onClickDeleteProduct(){
        if(isDeleting){
            adapter_product.hideDeleteIcon();
            isDeleting = false;
        }else{
            adapter_product.showDeleteIcon();
            isDeleting = true;
        }
    }

    private void onCLickAddNewIngredient(){
        Log.d(TAG, "onCLickAddNewIngredient -> PREMUTO");
        Action action = new Action(ActionsListInventory.INDEX_ACTION_SHOW_NEW_INGREDIENT, null );
        SendAction(action);
    }
    //FUNCTIONAL

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(!manager.bottomBarListener.visible){
            manager.showBottomBar();
        }
        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
    }

    @Override
    public void EndAnimations() {
        TextView_TitleInventory     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        TextView_TitleExist         .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        TextView_TitleMissing       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Recycler_Products_Exist     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Recycler_Products_Missing   .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
}