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
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

public class Fragment_Welcome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //SYSTEM
    private static final String TAG = "Fragment_Welcome";

    //LAYOUT
    View            Fragment_View;
    LinearLayout    Background;
    Button          Button_Accedi;
    Button          Button_RegistraRistorante;
    TextView        Text_View_Welcome;

    //FUNCTIONAL
    private Controller_Login Manager_Login;

    //OTHER...



    public Fragment_Welcome() {
    }

    public static Fragment_Welcome newInstance(String param1, String param2) {
        Fragment_Welcome fragment = new Fragment_Welcome();
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
        Fragment_View = inflater.inflate(R.layout.fragment__welcome, container, false);

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
        Background                  = Fragment_View.findViewById(R.id.background);
        Button_Accedi               = Fragment_View.findViewById(R.id.button_start);
        Text_View_Welcome           = Fragment_View.findViewById(R.id.text_view_welcome);
        Button_RegistraRistorante   = Fragment_View.findViewById(R.id.button_new_amministratore);

    }
    private void SetDataOnLayout() {


    }

    private void SetActionsOfLayout() {
        Button_Accedi.setOnClickListener(View ->actionNext());
        Button_RegistraRistorante.setOnClickListener(View ->actionNext());
    }

    //ANIMATIONS
    private void animateIN(){
        Button_Accedi        .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Button_RegistraRistorante        .startAnimation(Manager_Animation.getTranslationINfromDown(1000));
        Text_View_Welcome   .startAnimation(Manager_Animation.getTranslationINfromUp(1000));
    }
    private void animateOUT(){
        Button_Accedi        .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
        Button_RegistraRistorante        .startAnimation(Manager_Animation.getTranslationOUTtoDown(1000));
        Text_View_Welcome   .startAnimation(Manager_Animation.getTranslationOUTtoUp(1000));

        // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
        Background.setVisibility(View.VISIBLE);
        Background.startAnimation(Manager_Animation.getCircleReveal());
        ((Activity_Login)getActivity()).MoveLogoFrom0to1();
    }

    //ACTIONS
    private void actionNext(){
        animateOUT();
        showLogin();
    }

    //FUNCTIONAL
    private void showLogin(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                waitAbout(500);
                ((Activity_Login)getActivity()).setViewPager(1);
            }
        };
        thread.start();
    }

    private void waitAbout(int time){
        try {
            synchronized (this) {
                wait(time);
            }
        } catch (InterruptedException ignored){}

    }

}