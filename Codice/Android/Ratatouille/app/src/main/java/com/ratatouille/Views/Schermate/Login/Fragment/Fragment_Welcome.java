package com.ratatouille.Views.Schermate.Login.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;


public class Fragment_Welcome extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_Welcome";

    //LAYOUT
    private View            Fragment_View;
    private ImageView       ImageView_Logo;
    private LinearLayout    Background;
    private Button          Button_Accedi;
    private Button          Button_RegistraRistorante;
    private TextView        Text_View_Welcome;

    //FUNCTIONAL
    private final Manager manager;

    //OTHER...

    public Fragment_Welcome(Manager manager) {
        this.manager = manager;
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
        //manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_LOGIN_WELCOME);
    }
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        ImageView_Logo              = Fragment_View.findViewById(R.id.image_view_logo);
        Background                  = Fragment_View.findViewById(R.id.background);
        Button_Accedi               = Fragment_View.findViewById(R.id.button_start);
        Text_View_Welcome           = Fragment_View.findViewById(R.id.text_view_welcome);
        Button_RegistraRistorante   = Fragment_View.findViewById(R.id.button_new_amministratore);

    }
    @Override
    public void SetDataOnLayout() {
    }

    @Override
    public void SetActionsOfLayout() {
        Button_Accedi.setOnClickListener(View ->onClickLogin());
        Button_RegistraRistorante.setOnClickListener(View ->onClickRegistra());
    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickLogin(){
        Action action = new Action(ActionsLogin.INDEX_ACTION_START_LOGIN,"notAdmin");
        SendAction(action);
    }

    private void onClickRegistra(){
        Action action = new Action(ActionsLogin.INDEX_ACTION_START_REGISTER_ADMIN,"admin");
        SendAction(action);
    }

    //FUNCTIONAL

    //ANIMATIONS
    @Override
    public void StartAnimations() {
        animateIN();
    }

    @Override
    public void EndAnimations() {
        animateOUT();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));
    }

    public void RotateLogo(){
        ImageView_Logo.animate().rotation(360).setDuration(2000).start();
        ImageView_Logo.animate().alpha(0f).setDuration(0).start();
        ImageView_Logo.animate().alpha(1f).setDuration(500).start();
        ImageView_Logo.startAnimation(Manager_Animation.getTranslationINfromUp(700));
    }
    private void animateIN(){
        Button_Accedi               .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Button_RegistraRistorante   .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Text_View_Welcome           .startAnimation(Manager_Animation.getTranslationINfromUp(1000));
        MoveLogoFrom0to1();
        RotateLogo();
    }
    private void animateOUT(){
        requireActivity().runOnUiThread(() -> {
            Button_Accedi               .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
            Button_RegistraRistorante   .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
            Text_View_Welcome           .startAnimation(Manager_Animation.getTranslationOUTtoUp(1000));

            MoveLogoFrom1to2();
            Background.setVisibility(View.VISIBLE);
            Background.startAnimation(Manager_Animation.getCircleReveal());
            Try.run(() -> {
                Button_Accedi.setVisibility(View.GONE);
                Button_RegistraRistorante.setVisibility(View.GONE);
            });
             //Attesa animazinoe Rotazione LOGO
        });

    }

    public void MoveLogoFrom0to1(){
        ImageView_Logo.animate().rotation(180).setDuration(500).start();
        ImageView_Logo.startAnimation(Manager_Animation.getTranslateLogoUp());
    }
    public void MoveLogoFrom1to2(){
        ImageView_Logo.animate().rotation(180).setDuration(1000).start();
        ImageView_Logo.startAnimation(Manager_Animation.getTranslateLogoUp());
    }

}