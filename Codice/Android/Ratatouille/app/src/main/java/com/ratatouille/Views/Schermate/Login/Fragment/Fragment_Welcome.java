package com.ratatouille.Views.Schermate.Login.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

public class Fragment_Welcome extends Fragment implements ViewLayout {

    //SYSTEM
    private static final String TAG = "Fragment_Welcome";

    //LAYOUT
    View            Fragment_View;
    LinearLayout    Background;
    Button          Button_Accedi;
    Button          Button_RegistraRistorante;
    TextView        Text_View_Welcome;

    //FUNCTIONAL
    private Manager manager;

    //OTHER...

    public Fragment_Welcome(Manager manager, int a) {
        this.manager = manager;
    }


    public Fragment_Welcome() {

    }

    public static Fragment_Welcome newInstance() {
        Fragment_Welcome fragment = new Fragment_Welcome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fragment_View = inflater.inflate(R.layout.fragment__welcome, container, false);

        PrepareData();
        PrepareLayout();
        StartAnimations();

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
        Background                  = Fragment_View.findViewById(R.id.background);
        Button_Accedi               = Fragment_View.findViewById(R.id.button_start);
        Text_View_Welcome           = Fragment_View.findViewById(R.id.text_view_welcome);
        Button_RegistraRistorante   = Fragment_View.findViewById(R.id.button_new_amministratore);

    }
    @Override
    public void SetDataOnLayout() {


    }

    @Override
    public void StartAnimations() {

        animateIN();
    }

    @Override
    public void EndAnimations() {
        animateOUT();
    }

    @Override
    public void SetActionsOfLayout() {
        Button_Accedi.setOnClickListener(View ->onClickLogin());

        Button_RegistraRistorante.setOnClickListener(View ->onClickRegistra());
    }

    //ANIMATIONS
    private void animateIN(){
        Button_Accedi               .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Button_RegistraRistorante   .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Text_View_Welcome           .startAnimation(Manager_Animation.getTranslationINfromUp(1000));
    }
    private void animateOUT(){
        Button_Accedi               .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
        Button_RegistraRistorante   .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
        Text_View_Welcome           .startAnimation(Manager_Animation.getTranslationOUTtoUp(1000));

        Background.setVisibility(View.VISIBLE);
        Background.startAnimation(Manager_Animation.getCircleReveal());
    }

    //ACTIONS
    private void actionNext(){

        animateOUT();
    }

    private void onClickLogin(){

    }

    private void onClickRegistra(){

    }

    //FUNCTIONAL


}