package com.ratatouille.Views.Schermate.Login.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Menu.Fragment_ListCategory;

public class Fragment_Login extends Fragment implements ViewLayout {

    //SYSTEM
    private static final String TAG = "Fragment_Login";

    //LAYOUT
    View            Fragment_View;
    Button          Button_Login;
    EditText        EditTex_Email;
    EditText        EditTex_Password;

    TextView        TextView_warningEmail;
    TextView        TextView_warningPassword;

    LinearLayout    LinearLayout_BackGroundError;
    LinearLayout    LinearLayout_Error;
    LinearLayout    LinearLayout_Login;
    ImageView       ImageView_Logo;


    //FUNCTIONAL
    private Manager manager;
    private boolean isRegistratingAdmin = false;
    //DATA
    private Utente Utente;
    private String Email;
    private String Password;
    private String Rule;

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

        Utente = new Utente();
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
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_LOGIN_LOGIN);
        if(new LocalStorage(manager.context).getData("TypeUser","String") != null) isRegistratingAdmin = true;
    }

    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_Login          = Fragment_View.findViewById(R.id.linear_layout_login);
        LinearLayout_Error    = Fragment_View.findViewById(R.id.linear_layout_new_category);

        LinearLayout_BackGroundError  = Fragment_View.findViewById(R.id.darkRL);
        EditTex_Email                       = Fragment_View.findViewById(R.id.edit_text_email);
        EditTex_Password                    = Fragment_View.findViewById(R.id.edit_text_password);
        TextView_warningEmail               = Fragment_View.findViewById(R.id.warning_Email);
        TextView_warningPassword            = Fragment_View.findViewById(R.id.warning_password);

        Button_Login        = Fragment_View.findViewById(R.id.button_login);
        ImageView_Logo      = Fragment_View.findViewById(R.id.image_view_logo);
    }
    @Override
    public void SetDataOnLayout() {


    }



    @Override
    public void SetActionsOfLayout() {
        Button_Login.setOnClickListener(View ->onClickLogin());
    }



    //ACTIONS
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickLogin(){
        if(getAllInputs()){
            if(isRegistratingAdmin){
                this.manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_LOGIN_LOGIN);
                Action action = new Action(ActionsLogin.INDEX_ACTION_LOGIN_ADMIN,Utente,manager,(flag)->getResultLogin((boolean)flag),manager.getSourceInfo());
                SendAction(action);
            }else{

                this.manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_LOGIN_LOGIN);
                Action action = new Action(ActionsLogin.INDEX_ACTION_LOGIN,Utente,manager,(flag)->getResultLogin((boolean)flag),manager.getSourceInfo());
                SendAction(action);
            }
        }
    }

    //FUNCTIONAL
    private void getResultLogin(boolean Authenticated){
        if(Authenticated) EndAnimations();
        else ShowDialogErrorLogin();

    }
    private boolean getAllInputs(){
        Utente.setEmail(EditTex_Email.getText().toString());
        Utente.setPassword(EditTex_Password.getText().toString());
        Rule = (String) new LocalStorage(manager.context).getData("Rule","String");
        if(Rule == null){
            Rule = "ToDefine";
        }
        Log.d(TAG, "getAllInputs: New User ------------------------");
        Log.d(TAG, "getAllInputs: Email ->"+ Email);
        Log.d(TAG, "getAllInputs: Password ->"+ Password);
        Log.d(TAG, "getAllInputs: Rule ->"+ Rule);
        Log.d(TAG, "getAllInputs: --------------------------------------");
//        return true;
        return checkProduct();
    }
    private boolean checkProduct(){
        boolean isOk;

        isOk = showWarning(TextView_warningEmail,
                ( Utente.getEmail().length() >  3 ) );

        isOk &= showWarning(TextView_warningPassword,
                ( Utente.getPassword().length() > 3 ) );

        return isOk;
    }

    private boolean showWarning(View warning,boolean isValid){
        if(isValid) warning.setVisibility(View.GONE);
        else warning.setVisibility(View.VISIBLE);
        return isValid;
    }

    private void ShowDialogErrorLogin(){
        requireActivity().runOnUiThread(() -> {
            DialogError dialogErrorLogin = new DialogError();
            dialogErrorLogin.showDialogError();
        });
    }

    private class DialogError{
        CardView CardView_Cancel;
        TextView TextView_Warning;

        public DialogError() {

        }

        private void showDialogError(){
            CardView_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_annulla);
            TextView_Warning            = LinearLayout_Error.findViewById(R.id.text_view_warning);

            CardView_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Error            .setVisibility(View.VISIBLE);
            LinearLayout_BackGroundError  .setVisibility(View.VISIBLE);

            LinearLayout_Error            .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_BackGroundError  .startAnimation(Manager_Animation.getFadeIn(500));
            hideKeyboardFrom();
        }


        private void dismissDialogError(){
            LinearLayout_Error.setVisibility(View.GONE);
            LinearLayout_BackGroundError.setVisibility(View.GONE);
            LinearLayout_Error.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_BackGroundError.startAnimation(Manager_Animation.getFadeOut(500));
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        animateIN();
    }

    @Override
    public void EndAnimations() {
        requireActivity().runOnUiThread(() -> {
            LinearLayout_Login.startAnimation( Manager_Animation.getTranslateAnimatioOUTtoRight(500));
            Button_Login.startAnimation( Manager_Animation.getTranslateAnimatioOUTtoRight(500));
            MoveLogoFrom1to2();
        });
    }

    private void animateIN(){
        LinearLayout_Login.startAnimation( Manager_Animation.getTranslateAnimatioINfromLeft(500));
    }


    public void MoveLogoFrom1to2(){
        ImageView_Logo.animate().rotation(360).setDuration(1000).start();
        ImageView_Logo.startAnimation(Manager_Animation.getTranslateLogoDown());

    }
}