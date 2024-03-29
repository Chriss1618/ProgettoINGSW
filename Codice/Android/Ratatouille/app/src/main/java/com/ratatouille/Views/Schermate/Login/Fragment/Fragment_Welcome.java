package com.ratatouille.Views.Schermate.Login.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;


public class Fragment_Welcome extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_Welcome";

    //LAYOUT
    private View            Fragment_View;
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

    private void animateIN(){
        requireActivity().runOnUiThread(() -> {
            Log.d(TAG, "animateIN: Executed");
            Button_Accedi.startAnimation(Manager_Animation.getTranslationINfromDown(1000));
            Button_RegistraRistorante.startAnimation(Manager_Animation.getTranslationINfromDown(1000));
            Text_View_Welcome.startAnimation(Manager_Animation.getTranslationINfromUp(1000));
        });
    }

    private void animateOUT(){
        requireActivity().runOnUiThread(() -> {
            Button_Accedi               .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
            Button_RegistraRistorante   .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
            Text_View_Welcome           .startAnimation(Manager_Animation.getTranslationOUTtoUp(1000));

            MoveLogo();
             //Attesa animazinoe Rotazione LOGO
        });

    }

    public void MoveLogo(){
        ((Activity_Login) requireActivity()).fromWelcomeToLogin();
        //animateIN();
    }

}