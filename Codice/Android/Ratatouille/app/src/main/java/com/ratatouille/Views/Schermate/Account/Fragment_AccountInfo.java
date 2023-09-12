package com.ratatouille.Views.Schermate.Account;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsAccountInfo;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Restaurant;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_AccountInfo extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_AccountInfo";

    //LAYOUT
    android.view.View   View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    LinearLayout        LinearLayout_InfoAccount;

    TextView            TextView_NomeCognomeUtente;
    TextView            TextView_NomeRestaurant;
    TextView            TextView_AddressRestaurant;
    TextView            TextView_PhoneRestaurant;
    TextView            TextView_NTavoliRestaurant;

    Button              Button_EditAccount;
    ImageView           ImageView_Logout;

    private ProgressBar ProgressBar;
    //FUNCTIONAL
    private final Manager manager;

    //DATA
    private Restaurant MyRestaurant;
    //OTHER...

    public Fragment_AccountInfo(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyRestaurant = new Restaurant();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__account_info, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();
        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        ProgressBar.setVisibility(View.VISIBLE);
        LinearLayout_InfoAccount.setVisibility(View.GONE);
        sendRequest();
    }

    private void sendRequest(){
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_CATEGORY,
                (myRestaurant)-> setRestaurantOnLayout((Restaurant) myRestaurant));
        manager.HandleRequest(request);
    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_TitleProduct       = View_Fragment.findViewById(R.id.toolbar_menu_account);
        LinearLayout_InfoAccount        = View_Fragment.findViewById(R.id.linear_layout_info_account);
        ProgressBar                     = View_Fragment.findViewById(R.id.progressbar);

        TextView_NomeCognomeUtente      = View_Fragment.findViewById(R.id.text_view_nome_cognome);
        TextView_NomeRestaurant         = View_Fragment.findViewById(R.id.text_view_nome_restaruant);
        TextView_AddressRestaurant      = View_Fragment.findViewById(R.id.text_view_address);
        TextView_PhoneRestaurant        = View_Fragment.findViewById(R.id.text_view_phone);
        TextView_NTavoliRestaurant      = View_Fragment.findViewById(R.id.text_view_n_tavoli);

        Button_EditAccount              = View_Fragment.findViewById(R.id.button_edit_account);
        ImageView_Logout                = View_Fragment.findViewById(R.id.ic_logout);
    }
    @Override
    public void SetActionsOfLayout() {
        Button_EditAccount  .setOnClickListener(view -> onClickEditAccount());
        ImageView_Logout    .setOnClickListener(view -> onClickLogOut());
    }
    @Override
    public void SetDataOnLayout() {
        LinearLayout_InfoAccount.setVisibility(View.GONE);
        if(manager.IndexFrom > manager.IndexOnMain){
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getFadeIn(500));
        }else{
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        }
        String Nome         = (String) new LocalStorage(manager.context).getData("Nome","String");
        Nome      += " "+(String) new LocalStorage(manager.context).getData("Cognome","String");

        TextView_NomeCognomeUtente  .setText(Nome);
        TextView_NomeRestaurant     .setText(MyRestaurant.getName());
        TextView_AddressRestaurant  .setText(MyRestaurant.getAddress());
        TextView_PhoneRestaurant    .setText(MyRestaurant.getPhone());
        TextView_NTavoliRestaurant  .setText(MyRestaurant.get);


    }


    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickEditAccount(){
        Action action = new Action(ActionsAccountInfo.INDEX_ACTION_OPEN_EDIT_ACCOUNT,null,this::EndAnimations);
        SendAction(action);
    }
    private void onClickLogOut(){
        Action action = new Action(ActionsAccountInfo.INDEX_ACTION_LOGOUT,null,this::EndAnimations);
        SendAction(action);
    }
    //FUNCTIONAL
    private void setRestaurantOnLayout(Restaurant myRestaurant){
        MyRestaurant = myRestaurant;
        requireActivity().runOnUiThread(() -> {
            SetDataOnLayout();
            ProgressBar.setVisibility(View.GONE);
        });
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        LinearLayout_TitleProduct       .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationINfromDown(500));
    }
    @Override
    public void EndAnimations() {

        LinearLayout_TitleProduct           .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
    }

    public void toEditAccountAnimation(){
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getFadeOut(300));
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
    }
}