package com.ratatouille.Views.Schermate.Inventario;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

import com.google.android.material.internal.EdgeToEdgeUtils;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsNewIngredient;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_NewProductInventory extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewProductInve";

    //LAYOUT
    android.view.View View_Fragment;
    CardView        CardView_Product;
    LinearLayout    LinearLayout_Buttons;
    LinearLayout    LinearLayout_Dialog;
    LinearLayout    LinearLayout_DarkL;

    TextView        TextView_Title;
    EditText        EditText_NameIngredient;
    EditText        EditText_Euro;
    EditText        EditText_Cents;
    EditText        EditText_Description;
    EditText        EditText_Size;
    EditText        EditText_NumberInInventory;

    TextView        TextView_Kg;
    TextView        TextView_g;
    TextView        TextView_Mg;
    TextView        TextView_L;
    TextView        TextView_Cl;
    TextView        TextView_Ml;

    TextView        TextView_WarningName;
    TextView        TextView_WarningPrice;
    TextView        TextView_WarningGrandezza;

    TextView   TextView_MeasureSelected = null;

    ImageView ImageView_ChangePhoto;
    ImageView ImageView_IngredientImage;

    CardView CardView_Salva;
    CardView CardView_Cancel;

    //FUNCTIONAL
    private Manager manager;
    private ActivityResultLauncher<Intent> resultLauncher;

    //DATA
    private Ingredient Ingredient;

    //OTHER...

    public Fragment_NewProductInventory(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrepareReceiveFromGallery();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__new_product_inventory, container, false);
        PrepareData();

        PrepareLayout();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        Ingredient = new Ingredient();
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

        TextView_Title          = View_Fragment.findViewById(R.id.text_view_title_product);
        CardView_Product        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons    = View_Fragment.findViewById(R.id.linear_layout_buttons);

        EditText_NameIngredient         = View_Fragment.findViewById(R.id.edit_text_new_nome_inventory);
        EditText_Euro                   = View_Fragment.findViewById(R.id.edit_text_new_euro);
        EditText_Cents                  = View_Fragment.findViewById(R.id.edit_text_new_centesimi);
        EditText_Description            = View_Fragment.findViewById(R.id.edit_text_descrizione);
        EditText_Size                   = View_Fragment.findViewById(R.id.edit_text_new_grandezza);
        EditText_NumberInInventory      = View_Fragment.findViewById(R.id.edit_text_new_qta);

        TextView_Kg     = View_Fragment.findViewById(R.id.text_view_kg);
        TextView_g      = View_Fragment.findViewById(R.id.text_view_gr);
        TextView_Mg     = View_Fragment.findViewById(R.id.text_view_mr);
        TextView_L      = View_Fragment.findViewById(R.id.text_view_l);
        TextView_Cl     = View_Fragment.findViewById(R.id.text_view_cl);
        TextView_Ml     = View_Fragment.findViewById(R.id.text_view_ml);

        TextView_WarningName        = View_Fragment.findViewById(R.id.warning_NameIngredient);
        TextView_WarningGrandezza   = View_Fragment.findViewById(R.id.warning_Misura);

        ImageView_ChangePhoto = View_Fragment.findViewById(R.id.image_view_chose_photo);
        ImageView_IngredientImage = View_Fragment.findViewById(R.id.image_view_Ingredient);

        CardView_Salva = View_Fragment.findViewById(R.id.card_view_salva);
        CardView_Cancel = View_Fragment.findViewById(R.id.card_view_annulla);

    }

    @Override
    public void SetActionsOfLayout() {
        TextView_Kg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_g  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Mg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_L  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Cl .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Ml .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );

        ImageView_ChangePhoto   .setOnClickListener( view -> onClickChoosePhoto());
        CardView_Salva          .setOnClickListener( view -> onClickSalva());
        CardView_Cancel         .setOnClickListener( view -> onClickCancel());

    }

    @Override
    public void SetDataOnLayout() {

    }

    //ACTION
    private void sendAction(Action action){
        manager.HandleAction(action);
    }
    private void onMeasureSelected(TextView selected){
        SaveSelected(selected);
        setSelected();
    }
    private void onClickChoosePhoto(){
        Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void onClickSalva(){
        if(getAllInputs()){
            Action action = new Action(ActionsNewIngredient.INDEX_ACTION_CREATE_INGREDIENT,Ingredient,(isOk)->showDialog( (boolean)isOk) );
            sendAction(action);
        }
    }

    private void onClickCancel(){

        Action action = new Action(ActionsNewIngredient.INDEX_ACTION_CANCEL,Ingredient);
        sendAction(action);

    }

    //FUNCTIONAL
    private boolean getAllInputs(){
        Ingredient.setID_Ristorante( (Integer) new LocalStorage(manager.context).getData("ID_Ristorante","Integer"));
        Ingredient.setNameIngredient(EditText_NameIngredient.getText().toString());
        Ingredient.setDescription(EditText_Description.getText().toString());
        String price = EditText_Euro.getText().toString() + "." +  EditText_Cents.getText().toString();
        if(price.equals(".")) price = "0";
        Ingredient.setPriceIngredient(price);
        if(TextView_MeasureSelected != null) Ingredient.setMeasureType(TextView_MeasureSelected.getText().toString());

        if(EditText_Size.getText().toString().equals("")) Ingredient.setSizeIngredient(0);
        else Ingredient.setSizeIngredient(Integer.parseInt(EditText_Size.getText().toString()));

        if(EditText_NumberInInventory.getText().toString().equals("")) Ingredient.setQtaIngredient(0);
        else Ingredient.setQtaIngredient(Integer.parseInt(EditText_NumberInInventory.getText().toString()));

        return checkIngredient();
    }
    private boolean checkIngredient(){
        boolean isOk;
        isOk = showWarning(TextView_WarningName,
                ( Ingredient.getNameIngredient().length() >  3 ) );

        isOk &= showWarning(TextView_WarningGrandezza,
                ( TextView_MeasureSelected != null && Ingredient.getSizeIngredient() > 0) );


        return isOk;
    }
    private boolean showWarning(View warning, boolean isValid){
        if(isValid) warning.setVisibility(View.GONE);
        else warning.setVisibility(View.VISIBLE);
        return isValid;
    }

    private void PrepareReceiveFromGallery(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                if(result.getData() != null) {
                    Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
                    Uri imgUri = result.getData().getData();
                    ImageView_IngredientImage.setImageURI(imgUri);

                    Ingredient.setUriImageIngredient(imgUri);
                    Ingredient.setHasPhoto(true);
                }
            }catch (Exception e) {
                Log.e(TAG, "setResultLauncher: No ImageSelected",e );
            }
        });
    }

    private void SaveSelected(TextView selected){
        TextView_MeasureSelected = selected;
    }
    private void setSelected(){
            if(TextView_MeasureSelected.getText().toString().equals("Kg")) TextView_Kg.setBackgroundResource(R.drawable.background_mesure_left_selected);
            else TextView_Kg.setBackgroundResource(R.drawable.background_mesure_left);
            if(TextView_MeasureSelected.getText().toString().equals("gr")) TextView_g.setBackgroundResource(R.drawable.background_mesure_center_selected);
            else TextView_g.setBackgroundResource(R.drawable.background_mesure_center);
            if(TextView_MeasureSelected.getText().toString().equals("mg"))  TextView_Mg.setBackgroundResource(R.drawable.background_mesure_right_selected);
            else TextView_Mg.setBackgroundResource(R.drawable.background_mesure_right);

            if(TextView_MeasureSelected.getText().toString().equals("L"))  TextView_L.setBackgroundResource(R.drawable.background_mesure_left_selected);
            else TextView_L.setBackgroundResource(R.drawable.background_mesure_left);
            if(TextView_MeasureSelected.getText().toString().equals("cl")) TextView_Cl.setBackgroundResource(R.drawable.background_mesure_center_selected);
            else TextView_Cl.setBackgroundResource(R.drawable.background_mesure_center);
            if(TextView_MeasureSelected.getText().toString().equals("ml")) TextView_Ml.setBackgroundResource(R.drawable.background_mesure_right_selected);
            else TextView_Ml.setBackgroundResource(R.drawable.background_mesure_right);
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

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        TextView_Title          .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));
        LinearLayout_Buttons    .startAnimation(Manager_Animation.getTranslationINfromDown(600));
    }
    @Override
    public void EndAnimations() {
        TextView_Title          .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        CardView_Product        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        LinearLayout_Buttons    .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));

        EditText_NameIngredient.getText().clear();
        EditText_Euro.getText().clear();
        EditText_Cents.getText().clear();
        EditText_Size.getText().clear();
        EditText_NumberInInventory.getText().clear();

    }
}