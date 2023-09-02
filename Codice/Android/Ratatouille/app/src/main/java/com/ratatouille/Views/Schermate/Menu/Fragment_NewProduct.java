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
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewProduct;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Inventario.Fragment_NewProductInventory;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_NewProduct extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewProduct";

    //LAYOUT
    android.view.View   View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    CardView            CardView_ProductData;
    LinearLayout        LinearLayout_Buttons;
    ImageView           ImageView_AddFromGallery;

    CardView            CardView_AddNewProduct;
    CardView            CardView_Cancel;

    LinearLayout    LinearLayout_Dialog;
    LinearLayout    LinearLayout_DarkL;

    //layoutProdotto
    ImageView           ImageView_ProductImage;
    EditText            EditText_NomeProdotto;
    EditText            EditText_PriceProduct;
    EditText            EditText_CentsPriceProduct;
    EditText            EditText_DescriptionProduct;
    EditText            EditText_Allergeni;
    SwitchMaterial      Switch_SendToKitchen;
    ImageView           ImageView_AddNewIngredient;
    RecyclerView        RecycleView_IngredientProduct;
    //warnings
    TextView            TextView_warningNomeProdotto;
    TextView            TextView_warningPriceProduct;
    TextView            TextView_warningDescriptionProduct;

    //FUNCTIONAL
    private final Manager manager;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Adapter_IngredientProduct adapterIngredientProduct;
    private RecycleEventListener RecycleEventListener;

    //DATA
    private CategoriaMenu Categoria;
    private Product       NewProduct;
    //OTHER...

    public Fragment_NewProduct(Manager manager_MenuFragments,int a) {
        this.manager = manager_MenuFragments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( manager.getData().getClass().getSimpleName().equals("CategoriaMenu") ){
            NewProduct = new Product();
            NewProduct.setID_category( ((CategoriaMenu) manager.getData()).getID_categoria() );
            PrepareReceiveFromGallery();
        }
        RecycleEventListener    = new RecycleEventListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        if( manager.getData().getClass().getSimpleName().equals("Product") ){
            Log.d(TAG, "onResume: Product Ricevuta");
            NewProduct = (Product) manager.getData();
            Log.d(TAG, "onResume: Nome Prodotto ->"+ NewProduct.getNameProduct());
            Log.d(TAG, "onResume: Price Prodotto ->"+ NewProduct.getPriceProduct());
            Log.d(TAG, "onResume: Euro Prodotto ->"+ NewProduct.getEuro());
            Log.d(TAG, "onResume: CEnts Prodotto ->"+ NewProduct.getCents());
            for (Ricettario ricettario: NewProduct.getRicette()) {
                Log.d(TAG, "onResume: Nome Ricetta ->"+ricettario.getIngredient().getNameIngredient());
            }
            initRecycleView();
            SetDataOnLayout();
        } else if( manager.getData().getClass().getSimpleName().equals("CategoriaMenu") ){
            NewProduct = new Product();
            NewProduct.setID_category( ((CategoriaMenu) manager.getData()).getID_categoria() );
        }
    }


    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__new_product, container, false);

        String standardPhoto = "17-07-2023-16895501571000.jpg";

        PrepareData();
        PrepareLayout();
        Picasso.get()
                .load(EndPointer.StandardPath+"/Images/Product/"+standardPhoto)
                .into(ImageView_ProductImage);

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        NewProduct = new Product();

    }

    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_Dialog     = View_Fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL      = View_Fragment.findViewById(R.id.darkRL);

        LinearLayout_TitleProduct   = View_Fragment.findViewById(R.id.toolbar_title_product);
        CardView_ProductData        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons        = View_Fragment.findViewById(R.id.linear_layout_buttons);
        ImageView_AddFromGallery    = View_Fragment.findViewById(R.id.image_view_add_from_gallery);
        CardView_AddNewProduct      = View_Fragment.findViewById(R.id.card_view_aggiungi);
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
            CardView_AddNewProduct          .setOnClickListener(view -> onClickAddNewProduct());
            ImageView_AddNewIngredient      .setOnClickListener(view -> onClickAddNewIngredient());
            CardView_Cancel                 .setOnClickListener(view -> manager.goBack());
    }

    @Override
    public void SetDataOnLayout() {
        Picasso.get()
                .load(NewProduct.getUriImageProduct())
                .into(ImageView_ProductImage);

        EditText_NomeProdotto       .setText(NewProduct.getNameProduct());

        if(NewProduct.getPriceProduct() != 0.0f){
            EditText_PriceProduct       .setText(NewProduct.getEuro());
            EditText_CentsPriceProduct  .setText(NewProduct.getCents());
        }

        EditText_DescriptionProduct .setText(NewProduct.getDescriptionProduct());
        EditText_Allergeni          .setText(NewProduct.getAllergeniProduct());
        Switch_SendToKitchen        .setChecked(NewProduct.isSendToKitchen());
    }

    public void initRecycleView(){
        adapterIngredientProduct = new Adapter_IngredientProduct(NewProduct.getRicette(),getContext());

        RecycleView_IngredientProduct.setLayoutManager( new GridLayoutManager(getActivity(), 1));
        RecycleView_IngredientProduct.setNestedScrollingEnabled(false);

        RecycleView_IngredientProduct.setAdapter(adapterIngredientProduct);
    }

    //ACTIONS ************************
    private void sendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickAddFromGallery(){
        Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
        ReadInputNewProduct();
        manager.setData(NewProduct);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void onClickAddNewProduct(){
        if(getAllInputs()){
            Log.d(TAG, "onClickAddNewProduct: All Inputs are OK");
            Action action = new Action(ActionsNewProduct.INDEX_ACTION_CREATE_PRODUCT,
                    NewProduct,
                    (isInserted)->showDialog( (boolean)isInserted ));
            sendAction(action);

        }

    }

    private void onClickAddNewIngredient(){
        ReadInputNewProduct();

        Action action = new Action(ActionsNewProduct.INDEX_ACTION_ADD_INGREDIENTI,NewProduct);
        sendAction(action);
    }

    //*******************************


    //FUNCTIONAL *********************

    private void ReadInputNewProduct(){
        NewProduct.setNameProduct(EditText_NomeProdotto.getText().toString());
        String price = EditText_PriceProduct.getText().toString() + "." +  EditText_CentsPriceProduct.getText().toString();
        if(price.equals(".")) price = "0";
        NewProduct.setPriceProduct(Float.parseFloat(price));
        NewProduct.setDescriptionProduct(EditText_DescriptionProduct.getText().toString() );
        NewProduct.setAllergeniProduct(EditText_Allergeni.getText().toString() );
        NewProduct.setSendToKitchen(Switch_SendToKitchen.isChecked());
    }
    private void PrepareReceiveFromGallery(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                if(result.getData() != null) {
                    Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
                    Uri imgUri = result.getData().getData();
                    ImageView_ProductImage.setImageURI(imgUri);

                    NewProduct.setUriImageProduct(imgUri);
                }
            }catch (Exception e) {
                Log.e(TAG, "setResultLauncher: No ImageSelected",e );
            }
        });
    }


    private boolean getAllInputs(){
        ReadInputNewProduct();

        Log.d(TAG, "getAllInputs: New Prodotto ------------------------");
        Log.d(TAG, "getAllInputs: Nome Prodotto->"+ NewProduct.getNameProduct());
        Log.d(TAG, "getAllInputs: Price Prodotto->"+ NewProduct.getPriceProduct());
        Log.d(TAG, "getAllInputs: Description Prodotto->"+ NewProduct.getDescriptionProduct());
        Log.d(TAG, "getAllInputs: Allergeni Prodotto->"+ NewProduct.getAllergeniProduct());
        Log.d(TAG, "getAllInputs: SendToKitchen Prodotto->"+ NewProduct.isSendToKitchen());
        Log.d(TAG, "getAllInputs: --------------------------------------");

        return checkProduct();
    }
    private boolean checkProduct(){
        boolean isOk;

        isOk = showWarning(TextView_warningNomeProdotto,
                ( NewProduct.getNameProduct().length() >  3 ) );

        isOk &= showWarning(TextView_warningDescriptionProduct,
                ( NewProduct.getDescriptionProduct().length() > 3 ) );

        isOk &= showWarning(TextView_warningPriceProduct,
                ( NewProduct.getPriceProduct() != 0 ) );

        return isOk;
    }

    private boolean showWarning(View warning,boolean isValid){
        if(isValid) warning.setVisibility(View.GONE);
        else warning.setVisibility(View.VISIBLE);
        return isValid;
    }

    private void showDialog(boolean isOk){
        requireActivity().runOnUiThread(() -> {
            if(isOk) new DialogMessage().showDialogSuccess();
            else new DialogMessage().showDialogError();
        });
    }

    private class DialogMessage{
        LinearLayout LinearLayout_Error;
        LinearLayout LinearLayout_Success;

        CardView CardView_Dialog_Cancel;

        public DialogMessage() {
            LinearLayout_Error      = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_error);
            LinearLayout_Success    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_accepted);

            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_Error      .setVisibility(View.GONE);
            LinearLayout_Success    .setVisibility(View.GONE);
        }

        private void showDialogError(){
            CardView_Dialog_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_dialog_confirm);

            CardView_Dialog_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Error              .setVisibility(View.VISIBLE);
            LinearLayout_DarkL              .setVisibility(View.VISIBLE);

            LinearLayout_Dialog              .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL              .startAnimation(Manager_Animation.getFadeIn(500));
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
            LinearLayout_Success            .setVisibility(View.VISIBLE);
            LinearLayout_DarkL              .setVisibility(View.VISIBLE);

            LinearLayout_Dialog                .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL                  .startAnimation(Manager_Animation.getFadeIn(500));
            hideKeyboardFrom();
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }
    //********************************
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(manager.bottomBarListener.visible){
            manager.hideBottomBar();
        }
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationINfromDownSlower(500));
    }
    @Override
    public void EndAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationOUTtoDownS(300));

        EditText_NomeProdotto.getText().clear();
        EditText_PriceProduct.getText().clear();
        EditText_CentsPriceProduct.getText().clear();
        EditText_DescriptionProduct.getText().clear();
        EditText_Allergeni.getText().clear();
    }
}