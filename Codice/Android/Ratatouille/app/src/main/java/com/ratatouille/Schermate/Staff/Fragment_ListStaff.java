package com.ratatouille.Schermate.Staff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Adapters.Adapter_Category;
import com.ratatouille.Adapters.Adapter_Staff;
import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.GUI.Animation.Manager_Animation;
import com.ratatouille.Interfaces.LayoutContainer;
import com.ratatouille.Interfaces.RecyclerInterfaces.RecycleEventListener;
import com.ratatouille.Managers.Manager_StaffFragments;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListStaff extends Fragment implements LayoutContainer {
    //SYSTEM
    private static final String TAG = "Fragment_ListStaff";

    //LAYOUT
    private View            Fragment_View;
    private RecyclerView    Recycler_StaffMembers;
    private ImageView       Image_View_AddMember;
    private TextView        Text_View_Title_Staff;

    //FUNCTIONAL
    private final Manager_StaffFragments  manager_staffFragments;
    private RecycleEventListener    RecycleEventListener;

    //DATA
    private ArrayList<String> NameStuffMembers;

    //OTHER...

    public Fragment_ListStaff(Manager_StaffFragments manager_staffFragments) {
        this.manager_staffFragments = manager_staffFragments;
        RecycleEventListener = new RecycleEventListener();
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
        Fragment_View = inflater.inflate(R.layout.fragment_list_staff, container, false);

        PrepareData();
        PrepareLayout();

        StartAnimations();

        return Fragment_View;
    }

    //DATA
    @Override
    public void PrepareData() {
        NameStuffMembers = new ArrayList<>();
        NameStuffMembers.add("Nome Dipendente");
        NameStuffMembers.add("Nome Dipendente");
        NameStuffMembers.add("Nome Dipendente");
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
        Recycler_StaffMembers   = Fragment_View.findViewById(R.id.recycler_staff);
        Image_View_AddMember    = Fragment_View.findViewById(R.id.ic_add_staff);
        Text_View_Title_Staff   = Fragment_View.findViewById(R.id.text_view_title_staff);
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener.setOnClickItemAdapterListener( this::onClickStaffMember);
        Image_View_AddMember.setOnClickListener(            view -> onClickAddNewMember());
    }
    @Override
    public void SetDataOnLayout() {
        initFeaturesRV();
    }

    private void initFeaturesRV(){
        Adapter_Staff adapter_staff = new Adapter_Staff(NameStuffMembers, RecycleEventListener);
        Recycler_StaffMembers.setAdapter(adapter_staff);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_StaffMembers.setLayoutManager(mLayoutManager);
        Recycler_StaffMembers.setNestedScrollingEnabled(false);
    }

    //FUNCTIONAL
    private void sendActionToManager(int index,String msg){
        this.manager_staffFragments.showFragment(index,msg);
    }

    //ACTION
    private void onClickStaffMember(String StaffMember){
        Log.d(TAG, "Ricevuto da Listener->"+StaffMember);

    }

    private void onClickAddNewMember(){
        EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(()->
                        sendActionToManager(Manager_StaffFragments.INDEX_STAFF_NEW_MEMBER,""),
                300);
    }
    //ANIMATIONS
    public void StartAnimations(){
        Text_View_Title_Staff     .startAnimation(Manager_Animation.getTranslationINfromUp(600));
        Recycler_StaffMembers     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));

    }
    public void EndAnimations(){
        Text_View_Title_Staff     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_StaffMembers     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));

    }

}