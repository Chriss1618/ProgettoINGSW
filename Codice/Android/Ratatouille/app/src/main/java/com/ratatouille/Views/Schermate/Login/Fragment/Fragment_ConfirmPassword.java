package com.ratatouille.Views.Schermate.Login.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Activity_ChooseRole;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

public class Fragment_ConfirmPassword extends Fragment implements IViewLayout {

    //SYSTEM
    private static final String TAG = "Fragment_ConfirmPasswor";

    //LAYOUT
    private View        Fragment_View;
    private TextView    TextView_WelcomeText;

    private LinearLayout        Background;
    private LinearLayout        LinearLayout_Confirm;
    private LinearLayout        LinearLayout_message;
    private LinearLayout        LinearLayout_message2;
    private ConstraintLayout    ConstraintLayout_message3;
    private Button              Button_Save;

    //FUNCTIONAL
    private final Manager manager;

    //DATA
    private String Rule;
    //OTHER...

    public Fragment_ConfirmPassword(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Fragment_View =  inflater.inflate(R.layout.fragment__confirm_password, container, false);

        PrepareData();

        PrepareLayout();

        StartAnimations();

        return Fragment_View;
    }

    //LAYOUT
    @Override
    public void PrepareData() {
        Rule = (String) new LocalStorage(manager.context).getData("TypeUser","String");
    }
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        Button_Save                 = Fragment_View.findViewById(R.id.button_save);
        LinearLayout_Confirm        = Fragment_View.findViewById(R.id.linear_layout_confirm_password);
        TextView_WelcomeText        = Fragment_View.findViewById(R.id.text_view_user_welcome);
        Background                  = Fragment_View.findViewById(R.id.background);

        Log.d(TAG, "LinkLayout: Rule ->"+Rule);

        switch (Rule){
            case "Supervisore":
                TextView_WelcomeText.setText(R.string.supervisore_welcome);
                LinearLayout_message2       = Fragment_View.findViewById(R.id.welcome2);
                ConstraintLayout_message3   = Fragment_View.findViewById(R.id.welcome3);
                break;
            case "Chef":
                TextView_WelcomeText.setText(R.string.chef_welcome);
                LinearLayout_message        = Fragment_View.findViewById(R.id.chef_welcome1);
                LinearLayout_message2       = Fragment_View.findViewById(R.id.chef_welcome2);
                ConstraintLayout_message3   = Fragment_View.findViewById(R.id.chef_welcome3);
                break;
            case "Cameriere":
                TextView_WelcomeText.setText(R.string.cameriere_welcome);
                LinearLayout_message        = Fragment_View.findViewById(R.id.cameriere_welcome1);
                LinearLayout_message2       = Fragment_View.findViewById(R.id.cameriere_welcome2);
                ConstraintLayout_message3   = Fragment_View.findViewById(R.id.cameriere_welcome3);
                break;
            default:
                TextView_WelcomeText.setText(R.string.welcome_admin);
                LinearLayout_message        = Fragment_View.findViewById(R.id.welcome1);
                LinearLayout_message2       = Fragment_View.findViewById(R.id.welcome2);
                ConstraintLayout_message3   = Fragment_View.findViewById(R.id.welcome3);
        }
    }

    @Override
    public void SetDataOnLayout() {
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.IndexViewMapper.INDEX_LOGIN_CONFIRM);
    }

    @Override
    public void StartAnimations() {
        animateIN();
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
        Button_Save.startAnimation( Manager_Animation.getTranslationINfromDown(500));
        int time = 300;
        if(!Rule.equals("Supervisore")){
            final Handler handler = new Handler();
            handler.postDelayed(()->{
                LinearLayout_message.setVisibility(View.VISIBLE);
                LinearLayout_message.startAnimation( Manager_Animation.getTranslateAnimatioINfromRight(700));
            },time);

            time += 600;
        }


        final Handler handler2 = new Handler();
        handler2.postDelayed(()->{
            LinearLayout_message2.setVisibility(View.VISIBLE);
            LinearLayout_message2.startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(700));
        },time);
        time += 600;
        final Handler handler3 = new Handler();
        handler3.postDelayed(()->{
            ConstraintLayout_message3.setVisibility(View.VISIBLE);
            ConstraintLayout_message3.startAnimation( Manager_Animation.getTranslateAnimatioINfromRight(700));
        },time);

    }

    //ACTIONS
    private void startApp(){
        Intent intent = new Intent(getContext(), Activity_ChooseRole.class);

        Background.setVisibility(View.VISIBLE);
        Background.startAnimation(Manager_Animation.getCircleReveal());
        Button_Save.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        ((Activity_Login) requireActivity()).fromConfirmToApp();
        final Handler handler = new Handler();
        handler.postDelayed(()->{
            Button_Save.setVisibility(View.GONE);
            requireContext().startActivity(intent);

            requireActivity().finish();
            //CustomIntent.customType(manager.context,"fadein-to-fadeout");
        },300);
    }


    //FUNCTIONAL
}