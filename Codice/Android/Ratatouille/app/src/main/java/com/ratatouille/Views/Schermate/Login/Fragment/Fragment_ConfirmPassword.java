package com.ratatouille.Views.Schermate.Login.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Activity_ChooseRole;

public class Fragment_ConfirmPassword extends Fragment implements ViewLayout {

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
    LinearLayout LinearLayout_Confirm;

    Button      Button_Save;

    //FUNCTIONAL
    private Manager manager;

    //OTHER...

    public Fragment_ConfirmPassword(Manager manager, int a) {
        this.manager = manager;
    }

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
    @Override
    public void PrepareData() {

    }
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        Button_Save = Fragment_View.findViewById(R.id.button_save);
        LinearLayout_Confirm = Fragment_View.findViewById(R.id.linear_layout_confirm_password);

    }

    @Override
    public void SetDataOnLayout() {


    }

    @Override
    public void StartAnimations() {

    }

    @Override
    public void EndAnimations() {

    }

    @Override
    public void SetActionsOfLayout() {
        Button_Save.setOnClickListener(View ->startApp());
    }

    //ANIMATIONS
    private void animateIN(){
        LinearLayout_Confirm.startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(500));
    }

    //ACTIONS
    private void startApp(){
        Intent intent = new Intent(getContext(), Activity_ChooseRole.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }

    //FUNCTIONAL
}