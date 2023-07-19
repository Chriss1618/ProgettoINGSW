package com.ratatouille.Views.Schermate.Menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ratatouille.Controllers.Adapters.Adapter_Category;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Controllers.SubControllers.ActionHandlers.ActionsListCategory;
import com.ratatouille.Controllers.SubControllers.ManagerRequestFactory;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Listeners.RecycleEventListener;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.Models.Events.Action.*;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.R;
import java.util.ArrayList;
import java.util.Objects;

public class Fragment_ListCategory extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_ListCategory";

    //LAYOUT
    private View            View_fragment;
    private TextView        Text_View_TitleCategory;
    private RecyclerView    Recycler_Categories;
    private LinearLayout    LinearLayout_NewCategory;
    private LinearLayout    LinearLayout_BackGroundNewCategory;
    private ImageView       Image_View_AddCategory;
    private ImageView       Image_View_DeleteCategory;
    private TextView        Text_View_Empty;
    private EditText        EditText_SearchCategory;
    private ProgressBar     ProgressBar;

    //FUNCTIONAL
    private final Manager           manager;
    private RecycleEventListener    RecycleEventListener;
    private Adapter_Category        adapter_category;
    private boolean                 isDeleting = false;

    //DATA
    private ArrayList<CategoriaMenu> ListCategoryMenu;

    //OTHERS...

    public Fragment_ListCategory(Manager manager,int a) {
        this.manager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecycleEventListener    = new RecycleEventListener();
        ListCategoryMenu = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View_fragment = inflater.inflate(R.layout.fragment__list_category, container, false);

        PrepareLayout();
        PrepareData();

        StartAnimations();

        return View_fragment;
    }

    //DATA *****************************************************************************
    @Override
    public void PrepareData(){
        ProgressBar.setVisibility(View.VISIBLE);
        Recycler_Categories.setVisibility(View.GONE);
        sendRequest();
    }

    private void sendRequest(){
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_CATEGORY);
        @SuppressWarnings("unchecked")
        Request request = new Request(manager.getSourceInfo(), null, ManagerRequestFactory.INDEX_REQUEST_CATEGORY,
                (list)->{
                    requireActivity().runOnUiThread(() -> {
                        ListCategoryMenu = (ArrayList<CategoriaMenu>)list;
                        initCategoryRV();
                        ProgressBar.setVisibility(View.GONE);
                        Recycler_Categories.setVisibility(View.VISIBLE);
                        StartAnimationCategories();
                    });

                });
        manager.HandleRequest(request);


    }

    //LAYOUT****************************************************************************
    @Override
    public void PrepareLayout() {
        LinkLayout();
        SetActionsOfLayout();
        SetDataOnLayout();
    }

    @Override
    public void LinkLayout() {
        ProgressBar                         = View_fragment.findViewById(R.id.progressbar);
        Text_View_TitleCategory             = View_fragment.findViewById(R.id.text_view_title_category);
        Image_View_AddCategory              = View_fragment.findViewById(R.id.ic_add_category);
        Image_View_DeleteCategory           = View_fragment.findViewById(R.id.ic_delete_category);
        Recycler_Categories                 = View_fragment.findViewById(R.id.recycler_categories);
        LinearLayout_NewCategory            = View_fragment.findViewById(R.id.linear_layout_new_category);
        LinearLayout_BackGroundNewCategory  = View_fragment.findViewById(R.id.darkRL);
        Text_View_Empty                     = View_fragment.findViewById(R.id.text_view_empty);
        EditText_SearchCategory             = View_fragment.findViewById(R.id.edit_text_name_category);
    }
    @Override
    public void SetDataOnLayout() {
    }
    @Override
    public void SetActionsOfLayout() {
        RecycleEventListener        .setOnClickItemAdapterListener( (item)-> onClickCategory( (CategoriaMenu)item ) );
        RecycleEventListener        .setOnClickItemOptionAdapterListener( this::onClickDeleteCategory );
        Image_View_AddCategory      .setOnClickListener(view -> onClickAddCategory());
        Image_View_DeleteCategory   .setOnClickListener(view -> onClickDeleteCategories());
        EditText_SearchCategory     .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter_category.filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initCategoryRV(){
        adapter_category = new Adapter_Category(ListCategoryMenu, RecycleEventListener,false);
        Recycler_Categories.setAdapter(adapter_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        Recycler_Categories.setLayoutManager(mLayoutManager);
        Recycler_Categories.setNestedScrollingEnabled(false);
        checkEmptyRecycle();

    }

    private void checkEmptyRecycle(){
        if(ListCategoryMenu.isEmpty()) {
            Text_View_Empty.setVisibility(View.VISIBLE);
            Recycler_Categories.setVisibility(View.GONE);
        }else{
            Text_View_Empty.setVisibility(View.GONE);
            Recycler_Categories.setVisibility(View.VISIBLE);
        }
    }

    //ACTIONS *************************************************************************
    private void SendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickCategory(CategoriaMenu Category){
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_CATEGORY);
        Action action = new Action(ActionsListCategory.INDEX_ACTION_OPEN_LIST_PRODUCTS, Category,manager,this::EndAnimations,manager.getSourceInfo());
        SendAction(action);
    }

    private void onClickAddCategory(){
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_CATEGORY);
        Action action = new Action(ActionsListCategory.INDEX_ACTION_SHOW_ADD_CATEGORY, null, manager, this::ShowDialogNewCategory, manager.getSourceInfo());
        SendAction(action);
    }

    private void onClickDeleteCategories(){
        if(isDeleting){
            adapter_category.hideDeleteIcon();
            isDeleting = false;
        }else{
            adapter_category.showDeleteIcon();
            isDeleting = true;
        }
    }

    private void onClickDeleteCategory(String categoryToDelete,int id_categoryToDelete){
        manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_CATEGORY);
        Action action = new Action(ActionsListCategory.INDEX_ACTION_REMOVE_CATEGORY, id_categoryToDelete,manager,
                (id_categoria)-> deleteItemFromRecycle( (Integer)id_categoria ),
                manager.getSourceInfo());
        SendAction(action);
    }

    //FUNCTIONAL *********************************************************************
    private void deleteItemFromRecycle(Integer id_category){
        for(int indexItem = 0; indexItem < ListCategoryMenu.size() ; indexItem++){
            if(ListCategoryMenu.get(indexItem).getID_categoria() == id_category){
                ListCategoryMenu.remove(indexItem);
                adapter_category.removeItem(id_category);
                break;
            }
        }
    }

    private void ShowDialogNewCategory(){
        requireActivity().runOnUiThread(() -> {

            DialogNewCategory dialogNewCategory = new DialogNewCategory();
            dialogNewCategory.showDialogNewCategory();
        });
    }

    private class DialogNewCategory{
        CardView CardView_Cancel;
        CardView CardView_Add;
        EditText EditText_NewCategoryName;
        TextView TextView_Warning;

        public DialogNewCategory() {

        }

        private void showDialogNewCategory(){
            Log.d(TAG, "showDialogNewCategory: Started");
            CardView_Cancel             = LinearLayout_NewCategory.findViewById(R.id.card_view_annulla);
            CardView_Add                = LinearLayout_NewCategory.findViewById(R.id.card_view_aggiungi);
            EditText_NewCategoryName    = LinearLayout_NewCategory.findViewById(R.id.edit_text_nome_categoria);
            TextView_Warning            = LinearLayout_NewCategory.findViewById(R.id.text_view_warning);

            CardView_Cancel .setOnClickListener(view -> dismissDialogNewCategory());
            CardView_Add    .setOnClickListener(view -> addCategory( EditText_NewCategoryName.getText().toString().replaceAll("\\s+$", "")));

            LinearLayout_NewCategory            .setVisibility(View.VISIBLE);
            LinearLayout_BackGroundNewCategory  .setVisibility(View.VISIBLE);

            LinearLayout_NewCategory            .startAnimation(Manager_Animation.getTranslationINfromUp(500));
            LinearLayout_BackGroundNewCategory  .startAnimation(Manager_Animation.getFadeIn(500));

            EditText_NewCategoryName.setText("");
        }

        private void addCategory(String newCategory){
            hideKeyboardFrom();
            if(newCategory.equals("") || newCategory.length() < 4){
                ShowNameCategoryNotValid();
            }else{
                manager.getSourceInfo().setIndex_TypeView(ControlMapper.INDEX_MENU_LIST_CATEGORY);
                Action action = new Action(ActionsListCategory.INDEX_ACTION_ADD_CATEGORY,newCategory,manager,(category)->addCategoryView( (CategoriaMenu)category ), manager.getSourceInfo());
                SendAction(action);
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private void addCategoryView(CategoriaMenu newCategory){
            ListCategoryMenu.add(newCategory);
            adapter_category.notifyDataSetChanged();
            adapter_category.addItem(newCategory);
            checkEmptyRecycle();
            showSuccessfullyAddedNewCategory();
        }

        private void ShowNameCategoryNotValid(){
            TextView_Warning.setVisibility(View.VISIBLE);
            TextView_Warning.startAnimation( Manager_Animation.getFadeIn(300));

            EditText_NewCategoryName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(EditText_NewCategoryName.getText().toString().replaceAll("\\s+$", "").length() < 4 && EditText_NewCategoryName.getText().toString().replaceAll("\\s+$", "").length() >0){
                        ShowNameCategoryNotValid();
                    }else{
                        HideNameCategoryNotValid();
                    }
                }
            });
        }

        private void HideNameCategoryNotValid(){
            TextView_Warning.setVisibility(View.INVISIBLE);
        }
        private void showSuccessfullyAddedNewCategory(){
            HideNameCategoryNotValid();
            LinearLayout LinearLayout_Accepted       = LinearLayout_NewCategory.findViewById(R.id.linear_layout_category_accepted);
            LinearLayout LinearLayout_InsertCategory = LinearLayout_NewCategory.findViewById(R.id.linear_layout_insert_category);

            LinearLayout_InsertCategory.setAnimation( Manager_Animation.getFadeOut(200));

            final Handler handler = new Handler();
            handler.postDelayed(()-> {
                LinearLayout_InsertCategory.setVisibility(View.INVISIBLE);

                new Handler().postDelayed( () ->{
                    LinearLayout_InsertCategory.setVisibility(View.GONE);
                    LinearLayout_Accepted.setVisibility(View.VISIBLE);
                    LinearLayout_Accepted.setAnimation( Manager_Animation.getFadeIn(200));
                    new Handler().postDelayed( () ->{
                        dismissDialogNewCategory();
                        new Handler().postDelayed( () ->{
                            LinearLayout_Accepted       .setVisibility(View.GONE);
                            LinearLayout_InsertCategory .setVisibility(View.VISIBLE);
                        },500);

                    },1500);

                },500);


            },200);

        }

        private void dismissDialogNewCategory(){
            LinearLayout_NewCategory.setVisibility(View.GONE);
            LinearLayout_BackGroundNewCategory.setVisibility(View.GONE);
            LinearLayout_NewCategory.startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
            LinearLayout_BackGroundNewCategory.startAnimation(Manager_Animation.getFadeOut(500));
        }

    }

    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.requireView().getWindowToken(), 0);
    }

    //ANIMATIONS
    @Override
    public void StartAnimations(){
        Text_View_TitleCategory.setText(R.string.Menu);
        Text_View_TitleCategory     .startAnimation(Manager_Animation.getTranslationINfromUp(600));

    }
    @Override
    public void EndAnimations(){
        Text_View_TitleCategory .startAnimation(Manager_Animation.getTranslationOUTtoUp(300));
        Recycler_Categories     .startAnimation(Manager_Animation.getTranslateAnimatioOUT(300));
    }

    private void StartAnimationCategories(){
        Recycler_Categories         .startAnimation(Manager_Animation.getTranslateAnimatioINfromLeft(600));
    }

    private void StartLoadingCategories(){

    }

    private void StopLoadingCategories(){

    }

}