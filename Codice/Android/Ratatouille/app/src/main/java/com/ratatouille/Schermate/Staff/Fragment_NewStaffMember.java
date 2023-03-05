package com.ratatouille.Schermate.Staff;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.R;

public class Fragment_NewStaffMember extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_NewStaffMember";

    //LAYOUT
    View            Fragment_View;

    TextView        Text_View_title_staff;
    TextView        Text_View_ScegliRuolo;
    CardView        Card_View_Sala;
    CardView        Card_View_Supervisore;
    CardView        Card_View_Chef;
    LinearLayout    Linear_Layout_Dati_Membro;
    LinearLayout    Linear_Layout_Buttons;

    //FUNCTIONAL
    private Manager_StaffFragments manager_staffFragments;

    //DATA

    //OTHER...

    public Fragment_NewStaffMember(Manager_StaffFragments manager_staffFragments) {
        this.manager_staffFragments = manager_staffFragments;
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
        Linear_Layout_Dati_Membro   = Fragment_View.findViewById(R.id.linear_layout_dati_dipendente);
        Linear_Layout_Buttons       = Fragment_View.findViewById(R.id.linear_layout_buttons);
    }

    @Override
    public void SetActionsOfLayout() {

    }

    @Override
    public void SetDataOnLayout() {

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
}