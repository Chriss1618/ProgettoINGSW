package com.ratatouille.Views.Schermate.Staff;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsStaff;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Entity.Ricettario;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.LocalStorage;
import com.ratatouille.R;
import com.ratatouille.Views.Schermate.Menu.Fragment_NewProduct;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Fragment_NewStaffMember extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewStaffMember";
    private static final int INDEX_WAITER = 0;
    private static final int INDEX_SUPERVISORE = 1;
    private static final int INDEX_CHEF = 2;

    //LAYOUT
    android.view.View Fragment_View;
    TextView        Text_View_title_staff;
    TextView        Text_View_ScegliRuolo;
    CardView        Card_View_Sala;
    CardView        Card_View_Supervisore;
    CardView        Card_View_Chef;
    CardView        Card_View_GeneratePassword;

    CardView        Card_View_Create;
    CardView        Card_View_Cancel;
    EditText        EditText_Nome;
    EditText        EditText_Cognome;
    EditText        EditText_Email;
    LinearLayout    Linear_Layout_Dati_Membro;
    LinearLayout    Linear_Layout_Buttons;

    TextView        TextView_WarningTypeStaff;
    TextView        TextView_WarningDataStaff;

    LinearLayout    LinearLayout_Dialog;
    LinearLayout    LinearLayout_DarkL;
    //FUNCTIONAL
    //private Manager_StaffFragments manager_staffFragments;
    private Manager manager;
    private Context context;
    private DialogMessage DialogCreatingStaff;

    //DATA
    private Utente NewUtente;
    private int roleSelected = 99;

    //OTHER...

    public Fragment_NewStaffMember(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewUtente = new Utente();
        context = manager.context;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewUtente = new Utente();
        EditText_Cognome.setText("");
        EditText_Nome.setText("");
        EditText_Email.setText("");
        roleSelected = 99;
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fragment_View = inflater.inflate(R.layout.fragment__new_staff_member, container, false);
        PrepareData();
        PrepareLayout();


        StartAnimations();

        return Fragment_View;
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
        Text_View_title_staff       = Fragment_View.findViewById(R.id.text_view_title_staff);
        Text_View_ScegliRuolo       = Fragment_View.findViewById(R.id.text_view_scegli_ruolo);
        Card_View_Sala              = Fragment_View.findViewById(R.id.card_view_element_sala);
        Card_View_Supervisore       = Fragment_View.findViewById(R.id.card_view_element_supervisore);
        Card_View_Chef              = Fragment_View.findViewById(R.id.card_view_element_Shef);

        EditText_Nome               = Fragment_View.findViewById(R.id.edit_text_nome);
        EditText_Cognome            = Fragment_View.findViewById(R.id.edit_text_cognome);
        EditText_Email              = Fragment_View.findViewById(R.id.edit_text_email);
        Linear_Layout_Dati_Membro   = Fragment_View.findViewById(R.id.linear_layout_dati_dipendente);
        Linear_Layout_Buttons       = Fragment_View.findViewById(R.id.linear_layout_buttons);
        Card_View_Create            = Fragment_View.findViewById(R.id.card_view_aggiungi);
        Card_View_Cancel            = Fragment_View.findViewById(R.id.card_view_annulla);
        TextView_WarningDataStaff   = Fragment_View.findViewById(R.id.warning_data_stuff);
        TextView_WarningTypeStaff   = Fragment_View.findViewById(R.id.warning_select_type);

        LinearLayout_Dialog     = Fragment_View.findViewById(R.id.linear_layout_dialog);
        LinearLayout_DarkL      = Fragment_View.findViewById(R.id.darkRL);


    }

    @Override
    public void SetActionsOfLayout() {
        Card_View_Sala              .setOnClickListener(view -> onSelectedSala());
        Card_View_Supervisore       .setOnClickListener(view -> onSelectedSupervisore());
        Card_View_Chef              .setOnClickListener(view -> onSelectedChef());
        Card_View_Create            .setOnClickListener(view -> onClickCreate());
        Card_View_Cancel            .setOnClickListener(view -> onClickCancel());
    }

    @Override
    public void SetDataOnLayout() {

    }

    //ACTIONS
    private void onClickCreate(){

        if(readAllData()){
            DialogCreatingStaff = new DialogMessage();
            DialogCreatingStaff.showLoading();
            Action action = new Action(ActionsStaff.INDEX_ACTION_CREATE_STAFF,NewUtente, (isCreated) -> showDialog((boolean)isCreated) );
            SendAction(action);
        }
    }

    private void onClickCancel(){
        manager.goBack();
    }
    private void onSelectedSala(){
        if(roleSelected != INDEX_WAITER) {
            roleSelected = INDEX_WAITER;
            setWaiterSelected();
        }
    }
    private void onSelectedSupervisore(){
        if(roleSelected != INDEX_SUPERVISORE) {
            roleSelected = INDEX_SUPERVISORE;
            setSupervisoreSelected();
        }
    }
    private void onSelectedChef(){
        if(roleSelected != INDEX_CHEF) {
            roleSelected = INDEX_CHEF;
            setChefSelected();
        }
    }


    //FUNCTIONAL

    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private String generatePassword(){
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        for(int i = 0 ; i<10 ; i++) randomStr.append(possibleCharacters.charAt(random.nextInt(61)));

        return randomStr.toString();

    }

    private boolean readAllData(){
        NewUtente.setEmail(EditText_Email.getText().toString());
        NewUtente.setPassword(generatePassword());
        NewUtente.setNome(EditText_Nome.getText().toString());
        NewUtente.setCognome(EditText_Cognome.getText().toString());
        switch (roleSelected){
            case INDEX_WAITER :
                NewUtente.setType_user(ControlMapper.INDEX_TYPE_USER_CAMERIERE);break;
            case INDEX_SUPERVISORE:
                NewUtente.setType_user(ControlMapper.INDEX_TYPE_USER_SUPERVISORE);break;
            case INDEX_CHEF:
                NewUtente.setType_user(ControlMapper.INDEX_TYPE_USER_CHEF);break;
        }
        return checkStaff();
    }

    private boolean checkStaff(){
        boolean isOk;

        isOk = showWarning(TextView_WarningTypeStaff,
                ( roleSelected != 99 ) );

        boolean isCognomeOK = NewUtente.getCognome() != null && !NewUtente.getCognome().equals("");
        boolean isNomeOK = NewUtente.getNome() != null && !NewUtente.getNome().equals("");
        boolean isEmailOK = NewUtente.getEmail() != null && !NewUtente.getEmail().equals("");

        isOk &= showWarning(TextView_WarningDataStaff,
                 isCognomeOK && isNomeOK && isEmailOK );


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
    public void StartAnimations(){
        Text_View_title_staff.startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Text_View_ScegliRuolo.startAnimation(Manager_Animation.getFadeIn(1000));
        Card_View_Sala.startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
        Card_View_Supervisore.startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Card_View_Chef.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(600));

        Linear_Layout_Dati_Membro.startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(800));
        Linear_Layout_Buttons.startAnimation(Manager_Animation.getTranslationINfromDown(1000));
    }

    public void EndAnimations(){
        Text_View_title_staff.startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Text_View_ScegliRuolo.startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Card_View_Sala.startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
        Card_View_Supervisore.startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Card_View_Chef.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));

        Linear_Layout_Dati_Membro.startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(300));
        Linear_Layout_Buttons.startAnimation(Manager_Animation.getTranslationOUTtoDown(300));

    }

    //-SELECTED
    private void setWaiterSelected(){
        setChefUnselected();
        setSupervisoreUnselected();

        TextView textView = Card_View_Sala.findViewById(R.id.text_view_sala);
        ImageView imageView = Card_View_Sala.findViewById(R.id.image_view_table);

        Card_View_Sala.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MainColor));
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_table_white));

    }
    private void setSupervisoreSelected(){
        setChefUnselected();
        setWaiterUnselected();

        TextView textView = Card_View_Supervisore.findViewById(R.id.text_view_supervisore);
        ImageView imageView = Card_View_Supervisore.findViewById(R.id.image_view_supervisore);

        Card_View_Supervisore.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MainColor));
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_supervisore_white));
    }
    private void setChefSelected(){
        setWaiterUnselected();
        setSupervisoreUnselected();

        TextView textView = Card_View_Chef.findViewById(R.id.text_view_chef);
        ImageView imageView = Card_View_Chef.findViewById(R.id.image_view_chef);

        Card_View_Chef.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MainColor));
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_chef_white));
    }

    //-UNSELECTED
    private void setWaiterUnselected(){
        TextView textView = Card_View_Sala.findViewById(R.id.text_view_sala);
        ImageView imageView = Card_View_Sala.findViewById(R.id.image_view_table);

        Card_View_Sala.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_table));
    }
    private void setSupervisoreUnselected(){
        TextView textView = Card_View_Supervisore.findViewById(R.id.text_view_supervisore);
        ImageView imageView = Card_View_Supervisore.findViewById(R.id.image_view_supervisore);

        Card_View_Supervisore.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_supervisore));

    }
    private void setChefUnselected(){
        TextView textView = Card_View_Chef.findViewById(R.id.text_view_chef);
        ImageView imageView = Card_View_Chef.findViewById(R.id.image_view_chef);

        Card_View_Chef.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextColor(ContextCompat.getColor(context, R.color.DarkGray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_chef));
    }
}