package com.ratatouille.Views.Schermate.Login.Fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
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
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsLogin;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Login.Activity_Login;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_Login extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_Login";

    //LAYOUT
    private View            Fragment_View;
    private LinearLayout    LinearLayout_BackGroundError;
    private LinearLayout    LinearLayout_Login_step;
    private LinearLayout    LinearLayout_ConfirmPassword;
    private LinearLayout    LinearLayout_Error;
    private LinearLayout    LinearLayout_Login;

    private Button          Button_Login;
    private EditText        EditTex_Email;
    private EditText        EditTex_Password;
    private EditText        EditTex_NEWPassword;
    private EditText        EditTex_NEWConfirmPassword;
    private TextView        TextView_MsgLogin;
    private TextView        TextView_warningEmail;
    private TextView        TextView_warningPassword;
    private TextView        TextView_warningConfirmPassowrd;
    private ImageView       ImageView_Logo;

    //FUNCTIONAL
    private final Manager manager;
    private boolean isRegisteringAdmin = false;

    //DATA
    private Utente Utente;

    //OTHER...

    public Fragment_Login(Manager manager) {
        this.manager = manager;
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
        Fragment_View = inflater.inflate(R.layout.fragment__login, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return Fragment_View;
    }

    //LAYOUT
    @Override
    public void PrepareData() {
        isRegisteringAdmin = new LocalStorage(manager.context).getData("TypeUser","String").equals("Amministratore");
        Utente.setType_user((isRegisteringAdmin)?"Amministratore":"");
    }

    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_Login              = Fragment_View.findViewById(R.id.linear_layout_login);
        LinearLayout_Error              = Fragment_View.findViewById(R.id.linear_layout_error_login);

        LinearLayout_Login_step        = Fragment_View.findViewById(R.id.linear_layout_login_step);
        LinearLayout_ConfirmPassword        = Fragment_View.findViewById(R.id.linear_layout_confirm_password);

        LinearLayout_BackGroundError        = Fragment_View.findViewById(R.id.darkRL);
        EditTex_Email                       = Fragment_View.findViewById(R.id.edit_text_email);
        EditTex_Password                    = Fragment_View.findViewById(R.id.edit_text_password);

        EditTex_NEWPassword                 = Fragment_View.findViewById(R.id.edit_text_new_password);
        EditTex_NEWConfirmPassword          = Fragment_View.findViewById(R.id.edit_text_new_password_confirm);
        TextView_warningEmail               = Fragment_View.findViewById(R.id.warning_Email);
        TextView_warningPassword            = Fragment_View.findViewById(R.id.warning_password);
        TextView_MsgLogin                   = Fragment_View.findViewById(R.id.text_view_login_msg);
        TextView_warningConfirmPassowrd     = Fragment_View.findViewById(R.id.warning_new_password);
        Button_Login        = Fragment_View.findViewById(R.id.button_login);
        ImageView_Logo      = Fragment_View.findViewById(R.id.image_view_logo);
    }
    @Override
    public void SetDataOnLayout() {
        EditTex_Email.setText("");
        EditTex_Password.setText("");
        if(isRegisteringAdmin){
            TextView_MsgLogin.setText(R.string.mesg_login_admin);
        }
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
        if(Button_Login.getText().toString().equals("Conferma")){
            if(allPasswordOK()){
                Action action = new Action(ActionsLogin.INDEX_ACTION_CONFIRM_PASSWORD , EditTex_NEWPassword.getText().toString());
                SendAction(action);
            }
        }else{
            if(getAllInputs()){
                Action action = new Action(isRegisteringAdmin ? ActionsLogin.INDEX_ACTION_REGISTER_ADMIN : ActionsLogin.INDEX_ACTION_LOGIN, Utente, manager, (flag) -> getResultLogin((boolean) flag), manager.getSourceInfo());
                SendAction(action);
            }

        }

    }

    //FUNCTIONAL
    private void getResultLogin(boolean Authenticated){
        if(!Authenticated) ShowDialogErrorLogin();
        else{
            Boolean isFirsTime = (Boolean) new LocalStorage(manager.context).getData("isFirstTime","Boolean");
            isFirsTime = isFirsTime == null;
            if( isFirsTime ){
                requireActivity().runOnUiThread(() -> {
                    LinearLayout_Login_step.startAnimation(Manager_Animation.getTranslateAnimatioOUT(500));
                    new Handler(Looper.getMainLooper()).postDelayed(()->LinearLayout_Login_step.setVisibility(View.GONE),500);

                    new Handler(Looper.getMainLooper()).postDelayed(()->{
                        LinearLayout_ConfirmPassword.setVisibility(View.VISIBLE);
                        LinearLayout_ConfirmPassword.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
                    },200);
                    Button_Login.setText("Conferma");
                });
            }
        }

    }

    private boolean allPasswordOK(){
        boolean isOk;
        boolean isOKNewPassword = EditTex_NEWPassword.getText().toString().length() > 3;
        boolean isOKNewPasswordConfirm = EditTex_NEWConfirmPassword.getText().toString().length() > 3
                                    && EditTex_NEWConfirmPassword.getText().toString().equals(EditTex_NEWPassword.getText().toString());

        isOk = showWarning(TextView_warningConfirmPassowrd,
                isOKNewPassword && isOKNewPasswordConfirm  );
        return isOk;
    }
    private boolean getAllInputs(){
        Utente.setEmail(EditTex_Email.getText().toString());
        Utente.setPassword(EditTex_Password.getText().toString());

        return checkInputs();
    }
    private boolean checkInputs(){
        boolean isOk;

        isOk = showWarning(TextView_warningEmail,
                 Utente.getEmail().length() >  3  );

        isOk &= showWarning(TextView_warningPassword,
                 Utente.getPassword().length() > 3 );

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
            TextView_Warning            = LinearLayout_Error.findViewById(R.id.textview_msg_error);

            CardView_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Error            .setVisibility(View.VISIBLE);
            LinearLayout_BackGroundError  .setVisibility(View.VISIBLE);
            TextView_Warning.setText((String)manager.getData());
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
        animateOUT();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(200));
    }

    private void animateOUT(){
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
        ((Activity_Login) requireActivity()).fromLoginToConfirm();
    }
}