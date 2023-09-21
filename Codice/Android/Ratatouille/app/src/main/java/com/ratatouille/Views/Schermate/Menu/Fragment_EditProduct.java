package com.ratatouille.Views.Schermate.Menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.ratatouille.Controllers.Adapters.Adapter_IngredientProduct;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewProduct;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;


public class Fragment_EditProduct extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_EditProduct";

    //LAYOUT
    private View                View_Fragment;
    private LinearLayout        LinearLayout_TitleProduct;
    private CardView            CardView_ProductData;
    private LinearLayout        LinearLayout_Buttons;
    private ImageView           ImageView_AddFromGallery;

    private CardView            CardView_EditProduct;
    private CardView            CardView_Cancel;

    private LinearLayout        LinearLayout_Dialog;
    private LinearLayout        LinearLayout_DarkL;

    //layoutProdotto
    private ImageView           ImageView_ProductImage;
    private EditText            EditText_NomeProdotto;
    private EditText            EditText_PriceProduct;
    private EditText            EditText_CentsPriceProduct;
    private EditText            EditText_DescriptionProduct;
    private EditText            EditText_Allergeni;
    private SwitchMaterial      Switch_SendToKitchen;
    private ImageView           ImageView_AddNewIngredient;
    private RecyclerView        RecycleView_IngredientProduct;
    //warnings
    private TextView            TextView_warningNomeProdotto;
    private TextView            TextView_warningPriceProduct;
    private TextView            TextView_warningDescriptionProduct;

    //FUNCTIONAL
    private Manager manager;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Adapter_IngredientProduct adapterIngredientProduct;
    private DialogMessage DialogCreatingProduct;
    //DATA
    private Product EditProduct;
    //OTHER...

    public Fragment_EditProduct(Manager manager_MenuFragments,int a) {
        this.manager = manager_MenuFragments;
    }

    public Fragment_EditProduct() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditProduct = (Product) manager.getData();
        PrepareReceiveFromGallery();
    }

    @Override
    public void onResume() {
        super.onResume();
        if( manager.IndexFrom == ControlMapper.INDEX_MENU_INFO_PRODUCT){
            Log.d(TAG, "onResume: vieni da Info Product");
            setData();
            initRecycleView();
        } else if( manager.IndexFrom == ControlMapper.INDEX_INVENTORY_LIST){
            Log.d(TAG, "onResume: Vieni da Inventario");
            setData();
            initRecycleView();
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View_Fragment = inflater.inflate(R.layout.fragment__edit_product, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();
        return View_Fragment;
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
        LinearLayout_Dialog     = View_Fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL      = View_Fragment.findViewById(R.id.darkRL);

        LinearLayout_TitleProduct   = View_Fragment.findViewById(R.id.toolbar_title_product);
        CardView_ProductData        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons        = View_Fragment.findViewById(R.id.linear_layout_buttons);
        ImageView_AddFromGallery    = View_Fragment.findViewById(R.id.image_view_add_from_gallery);
        CardView_EditProduct        = View_Fragment.findViewById(R.id.card_view_salva);
        CardView_Cancel             = View_Fragment.findViewById(R.id.card_view_annulla);

        ImageView_ProductImage      = View_Fragment.findViewById(R.id.image_view_product);
        EditText_NomeProdotto       = View_Fragment.findViewById(R.id.edit_text_nome_prodotto);
        EditText_PriceProduct       = View_Fragment.findViewById(R.id.edit_text_euro);
        EditText_CentsPriceProduct  = View_Fragment.findViewById(R.id.edit_text_centesimi);
        EditText_DescriptionProduct = View_Fragment.findViewById(R.id.edit_text_descrizione);
        EditText_Allergeni          = View_Fragment.findViewById(R.id.edit_text_allergeni);
        Switch_SendToKitchen        = View_Fragment.findViewById(R.id.switch_sendToKitchen);
        ImageView_AddNewIngredient  = View_Fragment.findViewById(R.id.ic_add_ingrediente);
        RecycleView_IngredientProduct  = View_Fragment.findViewById(R.id.recycle_view_ingredienti);

        TextView_warningNomeProdotto        = View_Fragment.findViewById(R.id.warning_NameProduct);
        TextView_warningPriceProduct        = View_Fragment.findViewById(R.id.warning_PriceProduct);
        TextView_warningDescriptionProduct  = View_Fragment.findViewById(R.id.warning_DescrizioneProduct);

    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_AddFromGallery        .setOnClickListener(view -> onClickAddFromGallery());
        CardView_EditProduct            .setOnClickListener(view -> onClickEditProduct());
        ImageView_AddNewIngredient      .setOnClickListener(view -> onClickAddNewIngredient());
        CardView_Cancel                 .setOnClickListener(view -> manager.goBack());
    }

    @Override
    public void SetDataOnLayout() {
        setData();
        initRecycleView();
    }

    public void initRecycleView(){
        adapterIngredientProduct = new Adapter_IngredientProduct(EditProduct.getRicette(),getContext());

        RecycleView_IngredientProduct.setLayoutManager( new GridLayoutManager(getActivity(), 1));
        RecycleView_IngredientProduct.setNestedScrollingEnabled(false);

        RecycleView_IngredientProduct.setAdapter(adapterIngredientProduct);
    }
    //ACTIONS
    private void sendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickAddFromGallery(){
        Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
        ReadInputNewProduct();
        manager.setData(EditProduct);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void onClickEditProduct(){
        if(getAllInputs()){
            Log.d(TAG, "onClickEditProduct: All Inputs are OK");
            DialogCreatingProduct = new DialogMessage();
            DialogCreatingProduct.showLoading();
            Action action = new Action(ActionsInfoEditProduct.INDEX_ACTION_EDIT_PRODUCT,
                    EditProduct,
                    (isInserted)->showDialog( (boolean)isInserted ));
            sendAction(action);
        }

    }

    private void onClickAddNewIngredient(){
        ReadInputNewProduct();

        Action action = new Action(ActionsInfoEditProduct.INDEX_ACTION_ADD_INGREDIENTI,EditProduct);
        sendAction(action);
    }

    //FUNCTIONAL
    private boolean getAllInputs(){
        ReadInputNewProduct();

        Log.d(TAG, "getAllInputs: New Prodotto ------------------------");
        Log.d(TAG, "getAllInputs: Nome Prodotto->"+ EditProduct.getNameProduct());
        Log.d(TAG, "getAllInputs: Price Prodotto->"+ EditProduct.getPriceProduct());
        Log.d(TAG, "getAllInputs: Description Prodotto->"+ EditProduct.getDescriptionProduct());
        Log.d(TAG, "getAllInputs: Allergeni Prodotto->"+ EditProduct.getAllergeniProduct());
        Log.d(TAG, "getAllInputs: SendToKitchen Prodotto->"+ EditProduct.isSendToKitchen());
        Log.d(TAG, "getAllInputs: --------------------------------------");

        return checkProduct();
    }
    private boolean checkProduct(){
        boolean isOk;

        isOk = showWarning(TextView_warningNomeProdotto,
                ( EditProduct.getNameProduct().length() >  3 ) );

        isOk &= showWarning(TextView_warningDescriptionProduct,
                ( EditProduct.getDescriptionProduct().length() > 3 ) );

        isOk &= showWarning(TextView_warningPriceProduct,
                ( EditProduct.getPriceProduct() != 0 ) );

        return isOk;
    }

    private boolean showWarning(View warning,boolean isValid){
        if(isValid) warning.setVisibility(View.GONE);
        else warning.setVisibility(View.VISIBLE);
        return isValid;
    }

    private void ReadInputNewProduct(){
        EditProduct.setNameProduct(EditText_NomeProdotto.getText().toString());
        String price = EditText_PriceProduct.getText().toString() + "." +  EditText_CentsPriceProduct.getText().toString();
        if(price.equals(".")) price = "0";
        EditProduct.setPriceProduct(Float.parseFloat(price));
        EditProduct.setDescriptionProduct(EditText_DescriptionProduct.getText().toString() );
        EditProduct.setAllergeniProduct(EditText_Allergeni.getText().toString() );
        EditProduct.setSendToKitchen(Switch_SendToKitchen.isChecked());
    }

    private void PrepareReceiveFromGallery(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                if(result.getData() != null) {
                    Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
                    Uri imgUri = result.getData().getData();
                    ImageView_ProductImage.setImageURI(imgUri);

                    EditProduct.setUriImageProduct(imgUri);
                }
            }catch (Exception e) {
                Log.e(TAG, "setResultLauncher: No ImageSelected",e );
            }
        });
    }

    private void setData(){

        if(EditProduct.getUriImageProduct() != null){
            Picasso.get()
                    .load(EditProduct.getUriImageProduct())
                    .into(ImageView_ProductImage);
        }else{
            Picasso.get()
                    .load(EndPointer.StandardPath+ EndPointer.IMAGES_PRODUCT+ EditProduct.getURLImageProduct())
                    .into(ImageView_ProductImage);
        }


        EditText_NomeProdotto       .setText(EditProduct.getNameProduct());

        if(EditProduct.getPriceProduct() != 0.0f){
            EditText_PriceProduct       .setText(EditProduct.getEuro());
            EditText_CentsPriceProduct  .setText(EditProduct.getCents());
        }

        EditText_DescriptionProduct .setText(EditProduct.getDescriptionProduct());
        EditText_Allergeni          .setText(EditProduct.getAllergeniProduct());
        Switch_SendToKitchen        .setChecked(EditProduct.isSendToKitchen());
    }

    private void showDialog(boolean isOk){
        requireActivity().runOnUiThread(() -> {
            if(isOk) DialogCreatingProduct.showDialogSuccess();
            else DialogCreatingProduct.showDialogError();
        });
    }

    private class DialogMessage{
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

        private void showLoading(){
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
        private void showDialogError(){
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
        private void showDialogSuccess(){
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
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationINfromDown(500));
    }
    @Override
    public void EndAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationOUTtoDown(500));
    }
}