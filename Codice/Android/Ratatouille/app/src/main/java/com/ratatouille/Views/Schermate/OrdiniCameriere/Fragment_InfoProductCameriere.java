package com.ratatouille.Views.Schermate.OrdiniCameriere;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsInfoEditProduct;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Fragment_InfoProductCameriere extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_InfoProductCam";

    //LAYOUT
    private View View_fragment;
    private LinearLayout LinearLayout_Ingredients;
    private TextView    Text_View_Title_Product;
    private ImageView ImageView_Product;
    private ImageView   ImageView_Back;
    private TextView    TextView_DescrizioneProduct;
    private TextView    TextView_AllergeniProduct;
    private TextView    TextView_TitleAllergeniProduct;
    private TextView    TextView_PriceProduct;
    private TextView    TextView_NameRicettaProduct;
    private TextView    TextView_DoseRicettaProduct;
    private CardView    Card_Item_Product;
    private ImageView   ImageView_Edit_Product;
    private ProgressBar ProgressBar_Loading;
    //FUNCTIONAL
    Manager manager;
    //DATA
    private com.ratatouille.Models.Entity.Product Product;

    //OTHER...


    public Fragment_InfoProductCameriere(Manager manager, int a) {
        this.manager = manager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Product = (Product) manager.getData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View_fragment = inflater.inflate(R.layout.fragment__info_product, container, false);

        PrepareLayout();
        PrepareData();

        return View_fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        sendRequest();

    }
    private void sendRequest(){
        ProgressBar_Loading.setVisibility(View.VISIBLE);
        LinearLayout_Ingredients.setVisibility(View.GONE);
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(),Product, ManagerRequestFactory.INDEX_REQUEST_RICETTARIO,(listIngredients)-> setRicettarioOnLayout((ArrayList<Ricettario>) listIngredients));
        manager.HandleRequest(request);

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
        LinearLayout_Ingredients    = View_fragment.findViewById(R.id.linear_layout_ingredients);
        Text_View_Title_Product     = View_fragment.findViewById(R.id.text_view_name_product);
        ImageView_Back              = View_fragment.findViewById(R.id.ic_back);
        Card_Item_Product           = View_fragment.findViewById(R.id.card_view_element_product);
        ImageView_Edit_Product      = View_fragment.findViewById(R.id.ic_edit_product);
        ProgressBar_Loading         = View_fragment.findViewById(R.id.progressbar_existing);
        ImageView_Product           = View_fragment.findViewById(R.id.image_view_product);
        TextView_DescrizioneProduct = View_fragment.findViewById(R.id.text_view_product_description);
        TextView_AllergeniProduct   = View_fragment.findViewById(R.id.text_view_product_allergeni);
        TextView_TitleAllergeniProduct   = View_fragment.findViewById(R.id.text_view_title_allergeni);
        TextView_PriceProduct       = View_fragment.findViewById(R.id.text_view_price);
        TextView_NameRicettaProduct = View_fragment.findViewById(R.id.text_view_product_ingredienti);
        TextView_DoseRicettaProduct = View_fragment.findViewById(R.id.text_view_product_dosi);
    }
    @Override
    public void SetActionsOfLayout() {
        ImageView_Back.setOnClickListener(view -> manager.goBack());
    }
    @Override
    public void SetDataOnLayout() {
        Text_View_Title_Product.setText(Product.getNameProduct());
        if(Product.isHasPhoto()){
            Picasso.get()
                    .load(Product.getUriImageProduct())
                    .into(ImageView_Product);
        }else{
            Picasso.get()
                    .load(EndPointer.StandardPath+ EndPointer.IMAGES_PRODUCT+ Product.getURLImageProduct())
                    .into(ImageView_Product);
        }
        ImageView_Edit_Product.setVisibility(View.GONE);

        String priceProduct = Product.getEuro() + "," + Product.getCents() + " €";

        TextView_PriceProduct           .setText(priceProduct);
        TextView_DescrizioneProduct     .setText(Product.getDescriptionProduct());
        if(Product.getAllergeniProduct().replace(" ","").equals("") || Product.getAllergeniProduct().isEmpty()){
            TextView_AllergeniProduct           .setVisibility(View.GONE);
            TextView_TitleAllergeniProduct      .setVisibility(View.GONE);
        }else{
            TextView_AllergeniProduct       .setText(Product.getAllergeniProduct());
        }
    }

    //ACTIONS

    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private void onClickEditProduct(){
        Action action = new Action(ActionsInfoEditProduct.INDEX_ACTION_OPEN_EDIT_PRODUCT, Product, this::EndAnimations);
        SendAction(action);
    }

    //FUNCTIONAL
    private void setRicettarioOnLayout(ArrayList<Ricettario> ListIngredient){

        Log.d(TAG, "setRicettarioOnLayout: Ricevutooo");
        requireActivity().runOnUiThread(()->{
            Product.setRicette(ListIngredient);

            for(Ricettario ricettario : Product.getRicette()){
                String name = TextView_NameRicettaProduct.getText().toString() + ricettario.getIngredient().getNameIngredient() + "\n" ;
                TextView_NameRicettaProduct.setText(name);

                String dose = TextView_DoseRicettaProduct.getText().toString() + ricettario.getDosi() + "\n" ;
                TextView_DoseRicettaProduct.setText(dose);
            }
            LinearLayout_Ingredients    .setVisibility(View.VISIBLE);
            ProgressBar_Loading         .setVisibility(View.GONE);

        });
    }
    //ANIMATIONS
    @Override
    public void StartAnimations(){
        Card_Item_Product       .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(300));
        ImageView_Edit_Product.startAnimation(Manager_Animation.getFadeIn(300));
    }

    @Override
    public void EndAnimations(){
        Card_Item_Product       .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        ImageView_Edit_Product.startAnimation(Manager_Animation.getFadeOut(300));
    }

}