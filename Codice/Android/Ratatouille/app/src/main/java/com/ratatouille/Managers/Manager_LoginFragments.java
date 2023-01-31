package com.ratatouille.Managers;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.ratatouille.Schermate.Login.Fragment.Fragment_ConfirmPassword;
import com.ratatouille.Schermate.Login.Fragment.Fragment_Login;

import java.util.ArrayList;

public class Manager_LoginFragments {
    //LAYOUT
    private final Context               context;
    private final ArrayList<Fragment>   Fragments;
    private final ArrayList<View>       Views;
    private final FragmentManager       fragmentManager;
    private final LinearLayout          Container_Fragments;
    private final int                   Id_View;

    //DATA
    private int onMain;

    public Manager_LoginFragments(Context mContext, LinearLayout Container_Fragments, FragmentManager fragmentManager, int Id_View, ArrayList<View> fragments_view){
        this.Fragments              = new ArrayList<>();
        this.context                = mContext;
        this.Container_Fragments    = Container_Fragments;
        this.fragmentManager        = fragmentManager;
        this.Id_View                = Id_View;

        Views = fragments_view;

        Fragments.add(new Fragment_Login());
        Fragments.add(new Fragment_ConfirmPassword());

        onMain = 0;
    }



    //LAYOUT
    public void showPage(int index,int view){
        fragmentManager.popBackStack();

        switch (view){
            case 0://SHOWING Evento_Fragment
                fragmentManager.beginTransaction()
                        .replace(Id_View, Fragments.get(index), null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case 1://SHOWING Features_Fragment
                fragmentManager.beginTransaction()
                        .replace(Id_View, Fragments.get(index), null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
        }


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
