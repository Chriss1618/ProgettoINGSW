package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.app.Activity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_ProductWaiter;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsMenuWaiter;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Ordine;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_ListProductsCameriere extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListProductsCa";
    private static final String CATEGORY_TAG = "category";

    //LAYOUT
    private android.view.View View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Products;
    private ImageView       ImageView_Resoconto;
    private ProgressBar     ProgressBar_LoadingProducts;
    private TextView        TextView_NoProducts;
    private EditText        EditText_SearchProduct;
    private ImageView       ImageView_Back;
    private LinearLayout    LinearLayout_Dialog;
    private LinearLayout    LinearLayout_DarkL;
    //FUNCTIONAL
    private RecycleEventListener    RecycleEventListener;
    private final Manager                 manager;
    private Adapter_ProductWaiter adapter_product_waiter;
    private DialogMessage dialogMessage;
    private BottomSheetReport BottomSheetReport;
    //DATA
    private ArrayList<Product>  ListProducts;
    private CategoriaMenu       Category_Name;

    //OTHER...

    public Fragment_ListProductsCameriere(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecycleEventListener = new RecycleEventListener();
        Category_Name = ((Ordine) manager.getData()).getCategoria();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_fragment = inflater.inflate(R.layout.fragment__list_products__cameriere, container, false);
        PrepareLayout();
        PrepareData();

        StartAnimations();
        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData(){
        ProgressBar_LoadingProducts .setVisibility(View.VISIBLE);
        Recycler_Products           .setVisibility(View.GONE);

        sendRequest();
    }
    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), Category_Name, ManagerRequestFactory.INDEX_REQUEST_PRODUCTS,
                (list)-> setProductsOnLayout((ArrayList<Product>) list));
        manager.HandleRequest(request);
    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        BottomSheetReport = new BottomSheetReport(manager,View_fragment,this);
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {

        LinearLayout_Dialog                 = View_fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL                  = View_fragment.findViewById(R.id.darkRL);
        ProgressBar_LoadingProducts = View_fragment.findViewById(R.id.progressbar);
        TextView_NoProducts         = View_fragment.findViewById(R.id.text_view_empty);
        ImageView_Back              = View_fragment.findViewById(R.id.ic_back);
        Text_View_TitleCategory     = View_fragment.findViewById(R.id.text_view_title_category);
        Recycler_Products           = View_fragment.findViewById(R.id.recycler_products);
        ImageView_Resoconto         = View_fragment.findViewById(R.id.ic_resoconto);
        EditText_SearchProduct      = View_fragment.findViewById(R.id.edit_text_search_product);
    }

    @Override
    public void SetDataOnLayout() {
        Text_View_TitleCategory.setText(Category_Name.getNomeCategoria());
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_Back      .setOnClickListener( view -> manager.goBack());
        RecycleEventListener.setOnClickItemAdapterListener(this::onClickProduct);
        RecycleEventListener.setOnClickItemOptionAdapterListener((item, id_product)->onClickAddProduct((Product)item, id_product));
        ImageView_Resoconto .setOnClickListener(view -> onClickResoconto());
    }

    private void initListProductsRV( ){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_Products.setLayoutManager(mLayoutManager);
        Recycler_Products.setNestedScrollingEnabled(false);
        boolean isFromLeft = true;
        if(manager.IndexFrom > manager.IndexOnMain) isFromLeft = false;

        adapter_product_waiter = new Adapter_ProductWaiter(manager.context,ListProducts, RecycleEventListener,isFromLeft,false);
        Recycler_Products.setAdapter(adapter_product_waiter);
        checkEmptyRecycle();
    }



    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickProduct(Object product){
        Log.d(TAG, "PreparerData: Hai premuto l'item->"+product);
        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_OPEN_INFO_PRODUCT,product);
        SendAction(action);
    }

    private void onClickResoconto(){
        BottomSheetReport.showBottomSheet();
    }

    private void onClickAddProduct(Product Product, int action){
        Log.d(TAG, "onClickAddProduct");
        Ordine ordine = (Ordine) manager.getData();

        ordine.getTavolo().getProdottiDaOrdinare().add(Product);
        manager.setData(ordine);
    }

    //FUNCTIONAL

    private void checkEmptyRecycle(){
        if(ListProducts.isEmpty()) {
            TextView_NoProducts.setVisibility(View.VISIBLE);
            Recycler_Products.setVisibility(View.GONE);
            StartAnimationEmptyProducts();
        }else{
            TextView_NoProducts.setVisibility(View.GONE);
            Recycler_Products.setVisibility(View.VISIBLE);
            StartAnimationProducts();
        }
    }
    private void setProductsOnLayout(ArrayList<Product> list){
        requireActivity().runOnUiThread(() -> {
            ListProducts = list;
            initListProductsRV();
            ProgressBar_LoadingProducts.setVisibility(View.GONE);
            EditText_SearchProduct     .addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d(TAG, "onTextChanged: sta cambiando");
                    if ( adapter_product_waiter != null ) adapter_product_waiter.filterList(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });
    }
    //DIALOG
    public void SendToKitchen(){
        dialogMessage = new DialogMessage();
        dialogMessage.showLoading();

        Action action = new Action(ActionsMenuWaiter.INDEX_ACTION_SEND_TO_KITCHEN,manager.getData(),(isOk)->showDialog((Boolean) isOk));
        SendAction(action);
    }
    //DIALOG
    private void showDialog(boolean isOk){
        if(isOk) ((Ordine)manager.getData()).getTavolo().getProdottiDaOrdinare().clear();
        requireActivity().runOnUiThread(() -> {
            if(isOk) dialogMessage.showDialogSuccess();
            else dialogMessage.showDialogError();
        });
    }

    public class DialogMessage{
        LinearLayout LinearLayout_Error;
        LinearLayout LinearLayout_Success;
        LinearLayout LinearLayout_Loading;

        CardView CardView_Dialog_Cancel;
        private int numGiri = 0;

        public DialogMessage() {
            LinearLayout_Error      = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_error);
            LinearLayout_Success    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_accepted);
            LinearLayout_Loading    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_loading);

            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_Error      .setVisibility(View.GONE);
            LinearLayout_Success    .setVisibility(View.GONE);
            LinearLayout_Loading    .setVisibility(View.GONE);
        }

        public void showLoading(){
            LinearLayout_Loading    .setVisibility(View.VISIBLE);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);
            LinearLayout_Loading    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL       .startAnimation(Manager_Animation.getFadeIn(500));
            new Thread(()->rotation(1500)).start();
        }
        private void rotation(int speed){

            ImageView ImageView_Logo = LinearLayout_Loading.findViewById(R.id.image_view_logo);

            ImageView_Logo.animate()
                    .rotationBy(speed) // Use rotationBy instead of setting absolute rotation value
                    .setDuration(5000)
                    .withEndAction(() -> {
                        // This will be executed when the animation ends
                        int nextSpeed = (numGiri++ % 2 == 0) ? -1500 : 1500;
                        rotation(nextSpeed);
                    });

        }
        public void showDialogError(){
            CardView_Dialog_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_dialog_confirm);

            CardView_Dialog_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));

            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading           .setVisibility(View.GONE);
                LinearLayout_Error            .setVisibility(View.VISIBLE);
                LinearLayout_Error            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);
            hideKeyboardFrom();
        }
        private void dismissDialogError(){
            LinearLayout_Dialog.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_DarkL.startAnimation(Manager_Animation.getFadeOut(500));

            Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));

            LinearLayout_Error  .setVisibility(View.GONE);
            LinearLayout_Dialog .setVisibility(View.GONE);
            LinearLayout_DarkL  .setVisibility(View.GONE);
        }
        public void showDialogSuccess(){
            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));
            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading            .setVisibility(View.GONE);
                LinearLayout_Success            .setVisibility(View.VISIBLE);
                LinearLayout_Success            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);
            hideKeyboardFrom();
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) manager.context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.View_fragment.getWindowToken(), 0);
    }
    //END DIALOG
    //END DIALOG
    //ANIMATIONS
    @Override
    public void StartAnimations(){
        if(manager.IndexFrom > manager.IndexOnMain){
            Log.d(TAG, "StartAnimations: Da product");
            fromProductAnimations();
        }else{
            Log.d(TAG, "StartAnimations: Da Menu");
            fromMenuAnimations();
        }
    }

    @Override
    public void EndAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
        Recycler_Products       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
    }
    public void fromMenuAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromDown(300));
    }
    private void StartAnimationProducts(){
        if(manager.IndexFrom > manager.IndexOnMain){
            Recycler_Products       .startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(300) );
        }
    }
    private void StartAnimationEmptyProducts(){
        TextView_NoProducts         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(300));
    }
    public void toProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));

    }
    public void fromProductAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationINfromUp(300));

    }
}