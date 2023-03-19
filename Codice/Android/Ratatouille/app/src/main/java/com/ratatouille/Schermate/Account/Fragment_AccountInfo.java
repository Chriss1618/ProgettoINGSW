package com.ratatouille.Schermate.Account;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.Managers.Manager_MenuFragments;
import com.ratatouille.R;

import java.util.Set;

public class Fragment_AccountInfo extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_AccountInfo";

    //LAYOUT
    View                View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    ConstraintLayout    ConstraintLayout_ImageAccount;
    LinearLayout        LinearLayout_InfoAccount;

    ImageView           Image_View_Account;
    Button              Button_EditAccount;

    //FUNCTIONAL
    Manager_AccountFragments manager_accountFragments;

    //DATA

    //OTHER...

    public Fragment_AccountInfo(Manager_AccountFragments manager_accountFragments) {
        this.manager_accountFragments = manager_accountFragments;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        ConstraintLayout_ImageAccount   = View_Fragment.findViewById(R.id.constraint_layout_image_account);
        LinearLayout_InfoAccount        = View_Fragment.findViewById(R.id.linear_layout_info_account);

        Image_View_Account              = View_Fragment.findViewById(R.id.image_view_account);
        Button_EditAccount              = View_Fragment.findViewById(R.id.button_edit_account);
    }
    @Override
    public void SetActionsOfLayout() {
        Button_EditAccount.setOnClickListener(view -> onClickEditAccount());
    }
    @Override
    public void SetDataOnLayout() {
        Glide.with(manager_accountFragments.context)
                .load(getImage("exemple_product"))
                .circleCrop()
                .into(Image_View_Account);
    }



    public int getImage(String imageName) {
        return this.getResources().getIdentifier(imageName, "drawable", manager_accountFragments.context.getPackageName());
    }

    //ACTIONS
    private void onClickEditAccount(){
        toEditAccountAnimation();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                sendActionToManager(Manager_AccountFragments.INDEX_ACCOUNT_EDIT,"Orazio Russo"),
                300);

    }
    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.manager_accountFragments.showFragment(index,msg);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        if(manager_accountFragments.from > manager_accountFragments.onMain){
            ConstraintLayout_ImageAccount   .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getFadeIn(500));
        }else{
            LinearLayout_TitleProduct       .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            ConstraintLayout_ImageAccount   .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
            LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(500));
        }
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationINfromDown(500));
    }
    @Override
    public void EndAnimations() {
        LinearLayout_TitleProduct           .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        ConstraintLayout_ImageAccount       .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
    }

    public void toEditAccountAnimation(){
        ConstraintLayout_ImageAccount       .startAnimation(Manager_Animation.getTranslationOUTtoUp(600));
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getFadeOut(300));
        Button_EditAccount                  .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));
    }
}