package com.ratatouille.Schermate.Login.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.R;
import com.ratatouille.Schermate.Activity_Amministratore;
import com.ratatouille.Schermate.Activity_ChooseRole;

public class Fragment_ConfirmPassword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //SYSTEM
    private static final String TAG = "Fragment_ConfirmPasswor";

    //LAYOUT
    View        Fragment_View;
    Button      Button_Save;

    //FUNCTIONAL
    private Controller_Login Manager_Login;

    //OTHER..

    public Fragment_ConfirmPassword() {
    }

    public static Fragment_ConfirmPassword newInstance(String param1, String param2) {
        Fragment_ConfirmPassword fragment = new Fragment_ConfirmPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Fragment_View =  inflater.inflate(R.layout.fragment__confirm_password, container, false);

        PrepareData();

        PrepareLayout();

        animateIN();

        return Fragment_View;
    }

    //LAYOUT
    private void PrepareData() {

    }
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Button_Save = Fragment_View.findViewById(R.id.button_save);
    }
    private void SetDataOnLayout() {


    }
    private void SetActionsOfLayout() {
        Button_Save.setOnClickListener(View ->startApp());
    }

    //ANIMATIONS
    private void animateIN(){
        Fragment_View.startAnimation( Manager_Animation.getTranslateAnimatioINfromRight(500));
    }

    //ACTIONS
    private void startApp(){
        Intent intent = new Intent(getContext(), Activity_ChooseRole.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }

    //FUNCTIONAL
}