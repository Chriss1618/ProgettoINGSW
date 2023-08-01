package com.ratatouille.Views.Schermate.Login.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

public class Fragment_Login extends Fragment implements ViewLayout {

    //SYSTEM
    private static final String TAG = "Fragment_Login";

    //LAYOUT
    View            Fragment_View;
    Button          Button_Login;
    LinearLayout    LinearLayout_Login;
    ImageView       ImageView_Logo;
    //FUNCTIONAL
    private Manager manager;

    //OTHER...

    public Fragment_Login(Manager manager, int a) {
        this.manager = manager;
    }

    public Fragment_Login() {

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
        Fragment_View = inflater.inflate(R.layout.fragment__login, container, false);

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
        LinearLayout_Login  = Fragment_View.findViewById(R.id.linear_layout_login);
        Button_Login        = Fragment_View.findViewById(R.id.button_login);
        ImageView_Logo      = Fragment_View.findViewById(R.id.image_view_logo);
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

    }

    @Override
    public void SetActionsOfLayout() {
        Button_Login.setOnClickListener(View ->onClickLogin());
    }

    //ANIMATIONS
    private void animateIN(){
        LinearLayout_Login.startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(500));
    }
    private void animateOUT(){
        requireActivity().runOnUiThread(() -> {
            LinearLayout_Login.startAnimation( Manager_Animation.getTranslateAnimatioOUTtoRight(500));
            MoveLogoFrom1to2();
        });
        //((Activity_Login)getActivity()).MoveLogoFrom1to2();
    }
    public void MoveLogoFrom1to2(){
        ImageView_Logo.animate().rotation(360).setDuration(1000).start();
        ImageView_Logo.startAnimation(Manager_Animation.getTranslateLogoDown());

    }

    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickLogin(){
        this.manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_LOGIN_LOGIN);
        Action action = new Action(ActionsLogin.INDEX_ACTION_LOGIN,"notAdmin",manager,this::animateOUT,manager.getSourceInfo());
        SendAction(action);
    }
    private void actionLogin(){
        animateOUT();
        showConfirmPassword();
    }

    //FUNCTIONAL
    private void showConfirmPassword(){
//        Thread thread = new Thread(() -> {
//            waitAbout(500);
//            ((Activity_Login)getActivity()).setViewPager(2);
//        });
//        thread.start();
    }

}