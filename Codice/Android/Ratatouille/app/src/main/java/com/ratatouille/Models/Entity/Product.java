package com.ratatouille.Models.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Product {
    private int ID_product;
    private int ID_category;

    private String NameProduct;
    private float PriceProduct ;
    private String DescriptionProduct;
    private String AllergeniProduct;
    private boolean isSendToKitchen;

    private String  URLImageProduct;
    private String  DataImageProduct;
    private Uri     UriImageProduct = null;
    private Integer Order;

    private boolean hasPhoto;
    private boolean hasUrl;

    private String      Id_User;
    private String      TimestampCompletamento;
    private String      Id_ProdottoOrdinato;
    private ArrayList<Ricettario> Ricette;

    public Product() {
        Ricette = new ArrayList<>();
        isSendToKitchen = false;
        hasPhoto = false;
        hasUrl = false;

    }

    public int getID_product() {
        return ID_product;
    }
    public void setID_product(int ID_product) {
        this.ID_product = ID_product;
    }

    public int getID_category() {
        return ID_category;
    }
    public void setID_category(int ID_category) {
        this.ID_category = ID_category;
    }

    public String getURLImageProduct() {
        return URLImageProduct;
    }
    public void setURLImageProduct(String URLImageProduct) {
        hasUrl = true;
        this.URLImageProduct = URLImageProduct;
    }

    public Uri getUriImageProduct() {
        return UriImageProduct;
    }
    public void setUriImageProduct(Uri uriImageProduct) {
        hasPhoto = true;
        UriImageProduct = uriImageProduct;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public boolean isHasUrl() {
        return hasUrl;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public String getDataImageProduct() {
        return DataImageProduct;
    }

    public String getNameProduct() {
        return NameProduct;
    }
    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public float getPriceProduct() {

        return PriceProduct;
    }
    public void setPriceProduct(float priceProduct) {
        PriceProduct = priceProduct;
    }

    public String getDescriptionProduct() {
        return DescriptionProduct;
    }
    public void setDescriptionProduct(String descriptionProduct) {
        DescriptionProduct = descriptionProduct;
    }

    public String getAllergeniProduct() {
        return AllergeniProduct;
    }
    public void setAllergeniProduct(String allergeniProduct) {
        AllergeniProduct = allergeniProduct;
    }

    public boolean isSendToKitchen() {
        return isSendToKitchen;
    }
    public void setSendToKitchen(boolean sendToKitchen) {
        isSendToKitchen = sendToKitchen;
    }

    public ArrayList<Ricettario> getRicette() {
        return Ricette;
    }
    public void setRicette(ArrayList<Ricettario> ricette) {
        Ricette = ricette;
    }

    public Integer getOrder() {
        return Order;
    }
    public void setOrder(Integer order) {
        Order = order;
    }

    public String getId_User() {
        return Id_User;
    }
    public void setId_User(String id_User) {
        Id_User = id_User;
    }

    public String getTimestampCompletamento() {
        return TimestampCompletamento;
    }
    public void setTimestampCompletamento(String timestampCompletamento) {
        TimestampCompletamento = timestampCompletamento;
    }

    public String getId_ProdottoOrdinato() {
        return Id_ProdottoOrdinato;
    }
    public void setId_ProdottoOrdinato(String id_ProdottoOrdinato) {
        Id_ProdottoOrdinato = id_ProdottoOrdinato;
    }

    //FUNCTIONAL
    public String getDataFromUriProduct(Context context){
        if( UriImageProduct == null ) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Bitmap bitmapProduct = getBitmapFromUri(this.getUriImageProduct(),context);
        if(bitmapProduct != null){
            bitmapProduct.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray() , Base64.DEFAULT);
        }else return null;
    }
    public String getStringDataImage(Context context){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapProduct = getBitmapFromUri(this.UriImageProduct,context);
        assert bitmapProduct != null;
        bitmapProduct.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    private  Bitmap getBitmapFromUri(Uri uri, Context context) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setDataImageProduct(String dataImageProduct) {
        DataImageProduct = dataImageProduct;
    }

    public String getEuro() {
        int parteIntera = (int) PriceProduct;
        return Integer.toString(parteIntera);
    }

    public String getCents() {
        String cents = Float.toString(PriceProduct);
        int indexDot = cents.indexOf('.');

        if (indexDot != -1 && indexDot < cents.length() - 1) {
            String centss = cents.substring(indexDot + 1);
            if(centss.length() == 1) centss += "0";
            return centss;
        } else {
            return "0";
        }
    }
}
