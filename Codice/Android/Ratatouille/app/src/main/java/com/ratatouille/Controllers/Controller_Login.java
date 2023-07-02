package com.ratatouille.Controllers;

import android.content.Context;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ratatouille.Views.Schermate.Login.Fragment.Fragment_ConfirmPassword;
import com.ratatouille.Views.Schermate.Login.Fragment.Fragment_Login;
import com.ratatouille.Views.Schermate.Login.Fragment.Fragment_Welcome;

import java.util.ArrayList;

public class Controller_Login {
    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final ArrayList<View>       Views;
    private final FragmentManager       fragmentManager;

    //DATA
    private int onMain;

    public Controller_Login(Context mContext, FragmentManager fragmentManager, ArrayList<View> fragments_view){
        this.Fragments              = new ArrayList<>();
        this.context                = mContext;
        this.fragmentManager        = fragmentManager;

        Views = fragments_view;

        Fragments.add(new Fragment_Welcome());
        Fragments.add(new Fragment_Login());
        Fragments.add(new Fragment_ConfirmPassword());
        onMain = 0;
    }

    //LAYOUT
    public void showPage(int index){
        fragmentManager.popBackStack();

        fragmentManager.beginTransaction()
                .replace(Views.get(0).getId(), Fragments.get(index), null)
                .setReorderingAllowed(true)
                .commit();
    }

    public Fragment getFragment(int index){
        return Fragments.get(index);
    }
    public int getOnMain() {
        return onMain;
    }

    //ANIMATION
//    public void endAnimFragments(){
//        if(onMain == 0){
//            Views.get(0).startAnimation(new AnimationGUI(context).animGeneralGUI.fadeOUT_left);
//            changeConstraint(Views.get(1),HomeEventoCL);
//            Views.get(1).setAnimation(new AnimationGUI(context).animGeneralGUI.move_top);
//            Views.get(0).postDelayed(() -> {
//                        Views.get(0).setVisibility(View.INVISIBLE);
//                    },
//                    400
//            );
//            onMain = 1;
//        }
//    }
//
//    public void returnAnimHomeEvento(){
//        if(onMain == 1){
//            Views.get(1).setAnimation(new AnimationGUI(context).animGeneralGUI.move_bottom);
//            Views.get(0).setVisibility(View.VISIBLE);
//            Views.get(0).startAnimation(new AnimationGUI(context).animGeneralGUI.fadeIN_left);
//            backConstraint(Views.get(0),HomeEventoCL,Views.get(1));
//            onMain = 0;
//        }
//
//    }
    private void changeConstraint(View view,ConstraintLayout constraintLayout){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,0);
        constraintSet.applyTo(constraintLayout);
        constraintLayout.invalidate();
    }
    private void backConstraint(View view,ConstraintLayout constraintLayout,View view2){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view2.getId(),ConstraintSet.TOP,view.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo(constraintLayout);
        constraintLayout.invalidate();
    }

}
