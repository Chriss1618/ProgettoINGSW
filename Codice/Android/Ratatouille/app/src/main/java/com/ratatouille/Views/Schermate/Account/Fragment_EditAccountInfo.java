package com.ratatouille.Views.Schermate.Account;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.R;

public class Fragment_EditAccountInfo extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_EditAccountInf";

    //LAYOUT
    android.view.View View_Fragment;
    ConstraintLayout    ConstraintLayout_ImageAccount;
    LinearLayout        LinearLayout_InfoAccount;
    LinearLayout        LinearLayout_Buttons;

    //FUNCTIONAL
    Manager_AccountFragments manager_accountFragments;

    //DATA

    //OTHER...

    public Fragment_EditAccountInfo(Manager_AccountFragments manager_accountFragments) {
        this.manager_accountFragments = manager_accountFragments;
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
        View_Fragment = inflater.inflate(R.layout.fragment__edit_account_info, container, false);

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
        ConstraintLayout_ImageAccount   = View_Fragment.findViewById(R.id.constraint_layout_image_account);
        LinearLayout_InfoAccount        = View_Fragment.findViewById(R.id.linear_layout_info_account);
        LinearLayout_Buttons            = View_Fragment.findViewById(R.id.linear_layout_buttons);
    }
    @Override
    public void SetActionsOfLayout() {

    }
    @Override
    public void SetDataOnLayout() {

    }

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        ConstraintLayout_ImageAccount   .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getFadeIn(600));
        LinearLayout_Buttons            .startAnimation(Manager_Animation.getTranslationINfromDown(700));
    }

    @Override
    public void EndAnimations() {
        ConstraintLayout_ImageAccount       .startAnimation(Manager_Animation.getTranslationOUTtoUp(600));
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getFadeOut(300));
        LinearLayout_Buttons                .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));

    }
}