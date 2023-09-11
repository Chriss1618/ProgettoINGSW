package com.ratatouille.Views.Schermate.Staff;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Staff;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsStaff;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Utente;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.R;

import java.util.ArrayList;

public class Fragment_ListStaff extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListStaff";

    //LAYOUT
    private android.view.View   Fragment_View;
    private RecyclerView        Recycler_StaffMembers;
    private ImageView           Image_View_AddMember;
    private ImageView           Image_View_DeleteMember;
    private TextView            Text_View_Title_Staff;
    private EditText            EditText_SearchStaff;

    private TextView        Text_View_Empty;
    private ProgressBar     ProgressBar;

    //FUNCTIONAL
    private RecycleEventListener      RecycleEventListener;
    private Manager                     manager;
    private Adapter_Staff                   adapter_staff;
    private boolean                         isDeleting;
    //DATA
    private ArrayList<String> NameStuffMembers;
    private ArrayList<Utente> ListStaff;

    //OTHER...

    public Fragment_ListStaff(Manager manager, int a) {
        this.manager = manager;
        this.RecycleEventListener   = new RecycleEventListener();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListStaff = new ArrayList<>();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        Fragment_View = inflater.inflate(R.layout.fragment_list_staff, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return Fragment_View;
    }

    //DATA
    @Override
    public void PrepareData() {

        SendRequest();
        NameStuffMembers = new ArrayList<>();
        NameStuffMembers.add("Francesco Berdardo");
        NameStuffMembers.add("Mario Rossi");
        NameStuffMembers.add("Piera Negra");

        isDeleting = false;
    }
    private void SendRequest(){
        ProgressBar.setVisibility(View.VISIBLE);
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_STAFF,
                (list)-> setStaffOnLayout((ArrayList<Utente>) list));
        manager.HandleRequest(request);

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
        Image_View_DeleteMember = Fragment_View.findViewById(R.id.ic_delete_staff);
        Text_View_Title_Staff   = Fragment_View.findViewById(R.id.text_view_title_staff);
        EditText_SearchStaff    = Fragment_View.findViewById(R.id.edit_text_name_staff);
        ProgressBar             = Fragment_View.findViewById(R.id.progressbar);
        Text_View_Empty         = Fragment_View.findViewById(R.id.text_view_empty);

    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener    .setOnClickItemAdapterListener( this::onClickStaffMember);
        RecycleEventListener    .setOnClickItemOptionAdapterListener( this::onClickDeleteStaff );
        Image_View_AddMember    .setOnClickListener(            view -> onClickAddNewMember());
        Image_View_DeleteMember .setOnClickListener(            view -> onClickDeleteMember());
        EditText_SearchStaff    .addTextChangedListener( onChangeSearchStaff());
    }

    @Override
    public void SetDataOnLayout() {


    }

    private void initFeaturesRV(){
        isDeleting = false;
        adapter_staff = new Adapter_Staff(ListStaff, RecycleEventListener);
        Recycler_StaffMembers.setAdapter(adapter_staff);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        Recycler_StaffMembers.setLayoutManager(mLayoutManager);
        Recycler_StaffMembers.setNestedScrollingEnabled(false);

        checkEmptyRecycle();
    }


    //ACTION
    private void onClickStaffMember(Object StaffMember){
        Log.d(TAG, "Ricevuto da Listener->"+StaffMember);

    }

    private void onClickDeleteMember(){
        if(isDeleting){
            adapter_staff.hideDeleteIcon();
            isDeleting = false;
        }else{
            adapter_staff.showDeleteIcon();
            isDeleting = true;
        }
    }
    private void onClickAddNewMember(){
        Action action = new Action(ActionsStaff.INDEX_ACTION_ADD_STAFF,null);
        SendAction(action);
    }

    private void onClickDeleteStaff(String stuffToDelete,int id_stuffToDelete){
        Utente staffToDelete = null;
        for (Utente staff: ListStaff) {
            if(  staff.getId_utente() == id_stuffToDelete)  staffToDelete = staff;
        }
        Action action = new Action(ActionsStaff.INDEX_ACTION_REMOVE_STAFF, staffToDelete,
                (staff)-> deleteItemFromRecycle( (Utente)staff ));
        SendAction(action);
    }
    //FUNCTIONAL
    private void deleteItemFromRecycle(Utente stuffToDelete){
        requireActivity().runOnUiThread(() -> {
            for(int indexItem = 0; indexItem < ListStaff.size() ; indexItem++){
                if(ListStaff.get(indexItem).getId_utente() == stuffToDelete.getId_utente()){
                    ListStaff.remove(indexItem);
                    initFeaturesRV();
                    break;
                }
            }
        });
    }
    private void SendAction(Action action){
        manager.HandleAction(action);
    }
    private TextWatcher onChangeSearchStaff(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(adapter_staff != null){
                    adapter_staff.filterList(charSequence.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    private void checkEmptyRecycle(){
        if(ListStaff.isEmpty()) {
            Log.d(TAG, "checkEmptyRecycle: no staff");
            Text_View_Empty.setVisibility(View.VISIBLE);
            Recycler_StaffMembers.setVisibility(View.GONE);
            StartAnimationsEmptyStaff();
        }else{
            Text_View_Empty.setVisibility(View.GONE);
            Recycler_StaffMembers.setVisibility(View.VISIBLE);
            StartAnimationStaff();
        }
    }

    private void setStaffOnLayout(ArrayList<Utente> list){
        ListStaff = list;
        requireActivity().runOnUiThread(() -> {
            initFeaturesRV();
            ProgressBar.setVisibility(View.GONE);
        });
    }
    //ANIMATIONS
    public void StartAnimations(){
        Text_View_Title_Staff     .startAnimation(Manager_Animation.getTranslationINfromUp(600));


    }
    public void EndAnimations(){
        Text_View_Title_Staff     .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_StaffMembers     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));

    }

    private void StartAnimationsEmptyStaff(){
        Recycler_StaffMembers       .setVisibility(View.GONE);
        Text_View_Empty             .setVisibility(View.VISIBLE);

    }

    private void StartAnimationStaff(){
        Recycler_StaffMembers     .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }

}