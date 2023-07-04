package com.ratatouille.Views.Schermate.Staff;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.ViewLayout;
import com.ratatouille.Listeners.RecycleEventListener;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.R;

import java.util.Random;

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
    EditText        EditText_Password;
    LinearLayout    Linear_Layout_Dati_Membro;
    LinearLayout    Linear_Layout_Buttons;

    //FUNCTIONAL
    //private Manager_StaffFragments manager_staffFragments;
    private Manager manager;
    private Context context;

    //DATA
    private int roleSelected = 99;

    //OTHER...

    public Fragment_NewStaffMember(Manager_StaffFragments manager_staffFragments) {
        //this.manager_staffFragments = manager_staffFragments;
        this.context = manager_staffFragments.context;
    }
    public Fragment_NewStaffMember(Manager manager, int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        Card_View_GeneratePassword  = Fragment_View.findViewById(R.id.card_view_element_random);
        EditText_Password           = Fragment_View.findViewById(R.id.edit_text_password);
        Linear_Layout_Dati_Membro   = Fragment_View.findViewById(R.id.linear_layout_dati_dipendente);
        Linear_Layout_Buttons       = Fragment_View.findViewById(R.id.linear_layout_buttons);
    }

    @Override
    public void SetActionsOfLayout() {
        Card_View_Sala              .setOnClickListener(view -> onSelectedSala());
        Card_View_Supervisore       .setOnClickListener(view -> onSelectedSupervisore());
        Card_View_Chef              .setOnClickListener(view -> onSelectedChef());
        Card_View_GeneratePassword  .setOnClickListener(view -> onClickGeneratePassword());
    }

    @Override
    public void SetDataOnLayout() {
        EditText_Password.setText("");
    }

    //ACTIONS
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

    private void onClickGeneratePassword(){
        String password = generatePassword();
        EditText_Password.setText(password);
    }


    //FUNCTIONAL
    private String generatePassword(){
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        for(int i = 0 ; i<10 ; i++) randomStr.append(possibleCharacters.charAt(random.nextInt(61)));

        return randomStr.toString();

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
        textView.setTextColor(ContextCompat.getColor(context, R.color.Gray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_table));
    }
    private void setSupervisoreUnselected(){
        TextView textView = Card_View_Supervisore.findViewById(R.id.text_view_supervisore);
        ImageView imageView = Card_View_Supervisore.findViewById(R.id.image_view_supervisore);

        Card_View_Supervisore.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextColor(ContextCompat.getColor(context, R.color.Gray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_supervisore));

    }
    private void setChefUnselected(){
        TextView textView = Card_View_Chef.findViewById(R.id.text_view_chef);
        ImageView imageView = Card_View_Chef.findViewById(R.id.image_view_chef);

        Card_View_Chef.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextColor(ContextCompat.getColor(context, R.color.Gray));
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_staff_chef));
    }
}