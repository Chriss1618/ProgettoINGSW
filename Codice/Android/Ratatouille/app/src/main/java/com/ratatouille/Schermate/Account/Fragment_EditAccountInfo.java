package com.ratatouille.Schermate.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ratatouille.Managers.Manager_AccountFragments;
import com.ratatouille.R;

public class Fragment_EditAccountInfo extends Fragment {
    //SYSTEM
    private static final String TAG = "Fragment_EditAccountInf";

    //LAYOUT


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__edit_account_info, container, false);
    }
}