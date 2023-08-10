package com.ratatouille.Views.Schermate.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsAccountInfo;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;

public class Fragment_AccountInfo extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_AccountInfo";

    //LAYOUT
    android.view.View View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    LinearLayout        LinearLayout_InfoAccount;

    Button              Button_EditAccount;
    ImageView           ImageView_Logout;
    //FUNCTIONAL
    private final Manager manager;

    //DATA

    //OTHER...

    public Fragment_AccountInfo(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__account_info, container, false);
        //new LocalStorage(manager.context).DeleteAllData();
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
        LinearLayout_TitleProduct       = View_Fragment.findViewById(R.id.toolbar_menu_account);
        LinearLayout_InfoAccount        = View_Fragment.findViewById(R.id.linear_layout_info_account);

        Button_EditAccount              = View_Fragment.findViewById(R.id.button_edit_account);
        ImageView_Logout                = View_Fragment.findViewById(R.id.ic_logout);
    }
    @Override
    public void SetActionsOfLayout() {
        Button_EditAccount.setOnClickListener(view -> onClickEditAccount());
        ImageView_Logout.setOnClickListener(view -> onClickLogOut());
    }
    @Override
    public void SetDataOnLayout() {

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

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(manager.IndexFrom > manager.IndexOnMain){
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getFadeIn(500));
        }else{
            LinearLayout_TitleProduct       .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        }
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