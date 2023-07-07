package com.ratatouille.Views.Schermate.Menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcraft.jsch.SftpException;
import com.ratatouille.Models.API.Rest.EndPointer;
import com.ratatouille.Models.API.Rest.ServerCommunication;
import com.ratatouille.Models.Animation.Manager_Animation;
import com.ratatouille.Models.Entity.CategoriaMenu;
import com.ratatouille.Models.Entity.Product;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Controllers.SubControllers.Manager;
import com.ratatouille.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Fragment_NewProduct extends Fragment implements ViewLayout {
    //SYSTEM
    private static final String TAG = "Fragment_NewProduct";

    //LAYOUT
    android.view.View View_Fragment;
    LinearLayout        LinearLayout_TitleProduct;
    CardView            CardView_ProductData;
    LinearLayout        LinearLayout_Buttons;
    ImageView           ImageView_AddFromGallery;
    ImageView           ImageView_ProductImage;

    //FUNCTIONAL
    private Manager manager;
    private ActivityResultLauncher<Intent> resultLauncher;

    //DATA
    private CategoriaMenu Categoria;
    private Product         NewProduct;
    //OTHER...

    public Fragment_NewProduct(Manager manager_MenuFragments,int a) {
        this.manager = manager_MenuFragments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(manager.getData() != null){
            Categoria = (CategoriaMenu) manager.getData();
            PrepareReceiveFromGallery();
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View_Fragment = inflater.inflate(R.layout.fragment__new_product, container, false);


        PrepareData();
        PrepareLayout();

        StartAnimations();

        return View_Fragment;
    }

    //DATA
    @Override
    public void PrepareData() {
        NewProduct = new Product();
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
        LinearLayout_TitleProduct   = View_Fragment.findViewById(R.id.toolbar_title_product);
        CardView_ProductData        = View_Fragment.findViewById(R.id.card_view_element_product);
        LinearLayout_Buttons        = View_Fragment.findViewById(R.id.linear_layout_buttons);
        ImageView_AddFromGallery    = View_Fragment.findViewById(R.id.image_view_add_from_gallery);
        ImageView_ProductImage      = View_Fragment.findViewById(R.id.image_view_product);
    }

    @Override
    public void SetActionsOfLayout() {
            ImageView_AddFromGallery        .setOnClickListener(view -> onClickAddFromGallery());
    }
    @Override
    public void SetDataOnLayout() {

    }

    //ACTIONS ************************
    private void sendAction(Action action){
        manager.HandleAction(action);
    }

    private void onClickAddFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }


    //*******************************

    //FUNCTIONAL *********************
    private void PrepareReceiveFromGallery(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                if(result.getData() != null) {
                    Uri imgUri = result.getData().getData();
                    ImageView_ProductImage.setImageURI(imgUri);
                    NewProduct.setUriImageProduct(imgUri);
                    sendPhoto();
                }
            }catch (Exception e) {
                Log.e(TAG, "setResultLauncher: No ImageSelected",e );
            }
        });
    }

    public Bitmap getBitmapFromUri(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPhoto(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapProduct = getBitmapFromUri(NewProduct.getUriImageProduct());
        bitmapProduct.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String  base64ImageProduct = Base64.encodeToString(bytes,Base64.DEFAULT);
        sendNewProductToServer(base64ImageProduct);

    }
    private void sendNewProductToServer(String image){
        Uri.Builder dataToSend = new Uri.Builder()
                .appendQueryParameter("image", image);
        String url = EndPointer.StandardPath + EndPointer.VERSION_ENDPOINT + EndPointer.INSERT + "/Product.php";

        try {
            JSONArray Msg = new ServerCommunication().getData( dataToSend, url);
            if( Msg != null ){
                Log.d(TAG, "sendNewProductToServer: MSG");
            }else{
                Log.d(TAG, "sendNewProductToServer: false");
            }
        }catch (Exception e){
            Log.e(TAG, "getDataFromServer: ",e);
        }
        Log.d(TAG, "sendNewProductToServer: true");
    }
    //********************************
    //ANIMATIONS
    @Override
    public void StartAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationINfromUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioINfromRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationINfromDown(500));
    }
    @Override
    public void EndAnimations() {
        LinearLayout_TitleProduct   .startAnimation(Manager_Animation.getTranslationOUTtoUp(500));
        CardView_ProductData        .startAnimation(Manager_Animation.getTranslateAnimatioOUTtoRight(500));
        LinearLayout_Buttons        .startAnimation(Manager_Animation.getTranslationOUTtoDown(500));
    }
}