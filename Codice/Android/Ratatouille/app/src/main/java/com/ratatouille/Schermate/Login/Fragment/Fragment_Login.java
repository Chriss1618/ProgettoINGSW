package com.ratatouille.Schermate.Login.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.R;
import com.ratatouille.Schermate.Login.Activity_Login;

public class Fragment_Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //SYSTEM
    private static final String TAG = "Fragment_Login";

    //LAYOUT
    View        Fragment_View;
    Button      Button_Login;

    //FUNCTIONAL
    private Controller_Login Manager_Login;

    //OTHER...


    public Fragment_Login() {

    }


    public static Fragment_Login newInstance(String param1, String param2) {
        Fragment_Login fragment = new Fragment_Login();
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
        // Inflate the layout for this fragment
        Fragment_View = inflater.inflate(R.layout.fragment__login, container, false);

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
        Button_Login = Fragment_View.findViewById(R.id.button_login);
    }
    private void SetDataOnLayout() {


    }
    private void SetActionsOfLayout() {
        Button_Login.setOnClickListener(View ->actionLogin());
    }
    //ANIMATIONS
    private void animateIN(){
        Fragment_View.startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft());
    }
    private void animateOUT(){
        Fragment_View.startAnimation( Manager_Animation.getTranslateAnimatioOUT());
        ((Activity_Login)getActivity()).MoveLogoFrom1to2();
    }


    //ACTIONS
    private void actionLogin(){
        animateOUT();
        showConfirmPassword();
    }

    //FUNCTIONAL
    private void showConfirmPassword(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                waitAbout(500);
                ((Activity_Login)getActivity()).setViewPager(2);
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