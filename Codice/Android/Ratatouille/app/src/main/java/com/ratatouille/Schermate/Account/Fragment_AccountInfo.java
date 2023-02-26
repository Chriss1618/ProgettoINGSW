package com.ratatouille.Schermate.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.R;

import java.util.Set;

public class Fragment_AccountInfo extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_AccountInfo";

    //LAYOUT
    View View_fragment;
    ImageView Image_View_Account;

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
        View_fragment = inflater.inflate(R.layout.fragment__account_info, container, false);
        PrepareData();

        PrepareLayout();

        return View_fragment;
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
        Image_View_Account = View_fragment.findViewById(R.id.image_view_account);
    }
    @Override
    public void SetActionsOfLayout() {

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
    //FUNCTIONAL

    //ANIMATIONS
}