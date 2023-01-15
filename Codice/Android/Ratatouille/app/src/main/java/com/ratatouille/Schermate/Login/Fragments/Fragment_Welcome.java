package com.ratatouille.Schermate.Login.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Managers.Manager_Animation;
import com.ratatouille.Managers.Manager_LoginFragments;
import com.ratatouille.R;
import com.ratatouille.Schermate.Login.Activity_Login;

public class Fragment_Welcome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    Button          Button_Start;
    TextView        Text_View_Welcome;

    //FUNCTIONAL
    private Manager_LoginFragments Manager_Login;

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
        Background          = Fragment_View.findViewById(R.id.background);
        Button_Start        = Fragment_View.findViewById(R.id.button_start);
        Text_View_Welcome   = Fragment_View.findViewById(R.id.text_view_welcome);
    }
    private void SetDataOnLayout() {


    }
    private void SetActionsOfLayout() {
        Button_Start.setOnClickListener(View ->actionNext());
    }

    //ANIMATIONS
    private void animateIN(){
        Button_Start        .startAnimation(Manager_Animation.getTranslationINfromDown());
        Text_View_Welcome   .startAnimation(Manager_Animation.getTranslationINfromUp());
    }
    private void animateOUT(){
        Button_Start        .startAnimation(Manager_Animation.getTranslationOUTtoDown());
        Text_View_Welcome   .startAnimation(Manager_Animation.getTranslationOUTtoUp());

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