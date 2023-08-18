package com.ratatouille.Views.Schermate.Inventario;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Ingredient;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

public class Fragment_NewProductInventory extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewProductInve";

    //LAYOUT
    android.view.View View_Fragment;
    CardView        CardView_Product;
    LinearLayout    LinearLayout_Buttons;

    TextView        TextView_Title;
    EditText        EditText_NameIngredient;
    EditText        EditText_Euro;
    EditText        EditText_Cents;
    EditText        EditText_Size;
    EditText        EditText_NumberInInventory;

    TextView        TextView_Kg;
    TextView        TextView_g;
    TextView        TextView_Mg;
    TextView        TextView_L;
    TextView        TextView_Cl;
    TextView        TextView_Ml;

    TextView   TextView_MeasureSelected = null;

    ImageView ImageView_ChangePhoto;
    ImageView ImageView_IngredientImage;

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
        TextView_Title          = View_Fragment.findViewById(R.id.text_view_title_product);
        CardView_Product        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons    = View_Fragment.findViewById(R.id.linear_layout_buttons);

        EditText_NameIngredient         = View_Fragment.findViewById(R.id.edit_text_new_nome_inventory);
        EditText_Euro                   = View_Fragment.findViewById(R.id.edit_text_new_euro);
        EditText_Cents                  = View_Fragment.findViewById(R.id.edit_text_new_centesimi);
        EditText_Size                   = View_Fragment.findViewById(R.id.edit_text_new_grandezza);
        EditText_NumberInInventory      = View_Fragment.findViewById(R.id.edit_text_new_qta);

        TextView_Kg     = View_Fragment.findViewById(R.id.text_view_kg);
        TextView_g      = View_Fragment.findViewById(R.id.text_view_gr);
        TextView_Mg     = View_Fragment.findViewById(R.id.text_view_mr);
        TextView_L      = View_Fragment.findViewById(R.id.text_view_l);
        TextView_Cl     = View_Fragment.findViewById(R.id.text_view_cl);
        TextView_Ml     = View_Fragment.findViewById(R.id.text_view_ml);

        ImageView_ChangePhoto = View_Fragment.findViewById(R.id.image_view_chose_photo);
        ImageView_IngredientImage = View_Fragment.findViewById(R.id.image_view_Ingredient);
    }

    @Override
    public void SetActionsOfLayout() {
        TextView_Kg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_g  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Mg .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_L  .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Cl .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );
        TextView_Ml .setOnClickListener( view -> onMeasureSelected( (TextView)view ) );

        ImageView_ChangePhoto .setOnClickListener( view -> onClickChoosePhoto());
    }

    @Override
    public void SetDataOnLayout() {

    }

    //ACTION
    private void onMeasureSelected(TextView selected){
        SaveSelected(selected);
        setSelected();
    }
    private void onClickChoosePhoto(){
        Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }



    //FUNCTIONAL
    private void PrepareReceiveFromGallery(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                if(result.getData() != null) {
                    Log.d(TAG, "PrepareReceiveFromGallery: Foto Selezionata");
                    Uri imgUri = result.getData().getData();
                    ImageView_IngredientImage.setImageURI(imgUri);

                    Ingredient.setUriImageIngredient(imgUri);
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