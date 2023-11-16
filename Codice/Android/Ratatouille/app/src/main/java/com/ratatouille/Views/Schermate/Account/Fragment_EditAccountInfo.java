package com.ratatouille.Views.Schermate.Account;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsAccountInfo;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.Restaurant;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;

import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_EditAccountInfo extends Fragment implements IViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_EditAccountInf";

    //LAYOUT
    android.view.View   View_Fragment;

    EditText            EditText_NomeUtente;
    EditText            EditText_CognomeUtente;
    EditText            EditText_NomeRestaurant;
    EditText            EditText_AddressRestaurant;
    EditText            EditText_PhoneRestaurant;
    EditText            EditText_NTavoliRestaurant;
    TextView            TextView_Warning;

    LinearLayout        LinearLayout_InfoAccount;
    LinearLayout        LinearLayout_Buttons;

    CardView            CardView_Edit;
    CardView            CardView_Cancel;

    LinearLayout    LinearLayout_Dialog;
    LinearLayout    LinearLayout_DarkL;
    LinearLayout    LinearLayout_RestaurantInfo;
    //FUNCTIONAL
    private final Manager manager;
    private DialogMessage DialogCreatingStaff;
    //DATA
    private Restaurant MyRestaurant;
    Utente currentUtente;

    //OTHER...
    public Fragment_EditAccountInfo(Manager manager) {
        this.manager = manager;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUtente = new Utente();
        MyRestaurant = (Restaurant) manager.getData();
        currentUtente.setType_user((String) new LocalStorage(manager.context).getData("TypeUser","String"));
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__edit_account_info, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();
        return View_Fragment;
    }
    //DATA

    @Override
    public void PrepareData() {

    }
    //LAYOUT
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {
        LinearLayout_InfoAccount        = View_Fragment.findViewById(R.id.linear_layout_info_account);
        LinearLayout_Buttons            = View_Fragment.findViewById(R.id.linear_layout_buttons);
        LinearLayout_RestaurantInfo     = View_Fragment.findViewById(R.id.linearLayout_info_restaurant);

        EditText_NomeUtente             = View_Fragment.findViewById(R.id.edit_text_nome_persona);
        EditText_CognomeUtente          = View_Fragment.findViewById(R.id.edit_text_cognome_persona);
        EditText_NomeRestaurant         = View_Fragment.findViewById(R.id.edit_text_nome_attivita);
        EditText_AddressRestaurant      = View_Fragment.findViewById(R.id.edit_text_address);
        EditText_PhoneRestaurant        = View_Fragment.findViewById(R.id.edit_text_telefono);
        EditText_NTavoliRestaurant      = View_Fragment.findViewById(R.id.edit_text_n_tavoli);
        TextView_Warning                = View_Fragment.findViewById(R.id.warning_info);

        CardView_Edit                   = View_Fragment.findViewById(R.id.card_view_salva);
        CardView_Cancel                   = View_Fragment.findViewById(R.id.card_view_annulla);
        LinearLayout_Dialog             = View_Fragment.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL              = View_Fragment.findViewById(R.id.darkRL);
    }

    @Override
    public void SetActionsOfLayout() {
        CardView_Cancel     .setOnClickListener(view -> manager.goBack());
        CardView_Edit       .setOnClickListener(view -> onClickEdit());
    }

    @Override
    public void SetDataOnLayout() {
        String Nome         = (String) new LocalStorage(manager.context).getData("Nome","String");
        String Cognome      = (String) new LocalStorage(manager.context).getData("Cognome","String");

        EditText_NomeUtente         .setText(Nome);
        EditText_CognomeUtente      .setText(Cognome);

        if(currentUtente.getType_user().equals(ControlMapper.TypeUserMapper.NAME_TYPE_USER_AMMINISTRATORE)){

            EditText_NomeRestaurant     .setText(MyRestaurant.getName());
            EditText_AddressRestaurant  .setText(MyRestaurant.getAddress());
            EditText_PhoneRestaurant    .setText(MyRestaurant.getPhone());
            EditText_NTavoliRestaurant  .setText(MyRestaurant.getnTavoli());
        }else{
            LinearLayout_RestaurantInfo.setVisibility(View.GONE);
        }
    }

    //ACTIONS
    private void SendActions(Action action){
        manager.HandleAction(action);
    }

    private void onClickEdit(){
        if(readAllData()){
            DialogCreatingStaff = new DialogMessage();
            DialogCreatingStaff.showLoading();
            Action action = new Action(ActionsAccountInfo.INDEX_ACTION_EDIT_ACCOUNT,MyRestaurant,  (isUpdated) -> showDialog((boolean)isUpdated));
            SendActions(action);
        }
    }
    //FUNCTIONAl
    private boolean readAllData(){
        currentUtente.setNome(EditText_NomeUtente.getText().toString());
        currentUtente.setCognome(EditText_CognomeUtente.getText().toString());
        manager.setData(currentUtente);
        if(currentUtente.getType_user().equals(ControlMapper.TypeUserMapper.NAME_TYPE_USER_AMMINISTRATORE)){
            MyRestaurant.setName(EditText_NomeRestaurant.getText().toString());
            MyRestaurant.setAddress(EditText_AddressRestaurant.getText().toString());
            MyRestaurant.setPhone(EditText_PhoneRestaurant.getText().toString());
            MyRestaurant.setnTavoli(EditText_NTavoliRestaurant.getText().toString());
        }
        return checkData();
    }

    private boolean checkData(){
        boolean isOk;

        boolean isCognomeOK         = currentUtente.getCognome() != null && !currentUtente.getCognome().equals("");
        boolean isNomeOK            = currentUtente.getNome() != null && !currentUtente.getNome().equals("");
        boolean isNameRestaurantOK  = MyRestaurant.getName() != null && !MyRestaurant.getName().equals("");
        boolean isAddressOK         = MyRestaurant.getAddress() != null && !MyRestaurant.getAddress().equals("");
        boolean isPhoneOK           = MyRestaurant.getPhone() != null && !MyRestaurant.getPhone().equals("");
        boolean isNTavoliOK         = MyRestaurant.getnTavoli() != null && !MyRestaurant.getnTavoli().equals("");
        if( currentUtente.getType_user().equals(ControlMapper.TypeUserMapper.NAME_TYPE_USER_AMMINISTRATORE) ) isCognomeOK = true;
        else{
            isNameRestaurantOK = true;
            isAddressOK = true;
            isPhoneOK = true;
            isNTavoliOK = true;
        }
        isOk = showWarning(TextView_Warning,
                isCognomeOK && isNomeOK && isNameRestaurantOK && isAddressOK && isPhoneOK && isNTavoliOK );
        return isOk;
    }
    private boolean showWarning(View warning, boolean isValid){
        if(isValid) warning.setVisibility(View.GONE);
        else warning.setVisibility(View.VISIBLE);
        return isValid;
    }

    private void showDialog(boolean isOk){
        requireActivity().runOnUiThread(() -> {
            if(isOk) DialogCreatingStaff.showDialogSuccess();
            else DialogCreatingStaff.showDialogError();
        });
    }

    private class DialogMessage{
        LinearLayout LinearLayout_Error;
        LinearLayout LinearLayout_Success;
        LinearLayout LinearLayout_Loading;

        CardView CardView_Dialog_Cancel;
        private int numGiri = 0;

        public DialogMessage() {
            LinearLayout_Error      = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_error);
            LinearLayout_Success    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_accepted);
            LinearLayout_Loading    = LinearLayout_Dialog.findViewById(R.id.linear_layout_dialog_loading);

            LinearLayout_Dialog     .setVisibility(View.VISIBLE);
            LinearLayout_Error      .setVisibility(View.GONE);
            LinearLayout_Success    .setVisibility(View.GONE);
            LinearLayout_Loading    .setVisibility(View.GONE);
        }

        private void showLoading(){
            LinearLayout_Loading    .setVisibility(View.VISIBLE);
            LinearLayout_DarkL      .setVisibility(View.VISIBLE);
            LinearLayout_Loading    .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_DarkL       .startAnimation(Manager_Animation.getFadeIn(500));
            new Thread(()->rotation(1500)).start();
        }

        private void rotation(int speed){
            ImageView ImageView_Logo = LinearLayout_Loading.findViewById(R.id.image_view_logo);
            new Handler(Looper.getMainLooper()).post(() ->
                    ImageView_Logo.animate()
                            .rotationBy(speed)
                            .setDuration(5000)
                            .withEndAction(() -> {
                                int nextSpeed = (numGiri++ % 2 == 0) ? -1500 : 1500;
                                rotation(nextSpeed);
                            }));

        }
        private void showDialogError(){
            CardView_Dialog_Cancel             = LinearLayout_Error.findViewById(R.id.card_view_dialog_confirm);

            CardView_Dialog_Cancel .setOnClickListener(view -> dismissDialogError());

            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));

            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading           .setVisibility(View.GONE);
                LinearLayout_Error            .setVisibility(View.VISIBLE);
                LinearLayout_Error            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);
            hideKeyboardFrom();
        }
        private void dismissDialogError(){
            LinearLayout_Dialog.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_DarkL.startAnimation(Manager_Animation.getFadeOut(500));

            Try.run(() -> TimeUnit.MILLISECONDS.sleep(500));

            LinearLayout_Error  .setVisibility(View.GONE);
            LinearLayout_Dialog .setVisibility(View.GONE);
            LinearLayout_DarkL  .setVisibility(View.GONE);
        }
        private void showDialogSuccess(){
            LinearLayout_Loading            .startAnimation(Manager_Animation.getFadeOut(200));
            new Handler(Looper.getMainLooper()).postDelayed( ()->{
                LinearLayout_Loading            .setVisibility(View.GONE);
                LinearLayout_Success            .setVisibility(View.VISIBLE);
                LinearLayout_Success            .startAnimation(Manager_Animation.getFadeIn(300));
            },200);

            hideKeyboardFrom();
        }

    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        LinearLayout_InfoAccount        .startAnimation(Manager_Animation.getFadeIn(600));
        LinearLayout_Buttons            .startAnimation(Manager_Animation.getTranslationINfromDown(700));
    }

    @Override
    public void EndAnimations() {
        LinearLayout_InfoAccount            .startAnimation(Manager_Animation.getFadeOut(300));
        LinearLayout_Buttons                .startAnimation(Manager_Animation.getTranslationOUTtoDown(300));

    }
}