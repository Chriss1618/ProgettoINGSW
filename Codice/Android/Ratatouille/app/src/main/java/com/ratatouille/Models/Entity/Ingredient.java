package com.ratatouille.Models.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ingredient {
    private int ID_Ingredient;
    private int ID_Ristorante;

    private String NameIngredient;
    private float PriceIngredient;
    private int SizeIngredient;
    private String MeasureType;
    private int QtaIngredient;

    private String URLImageIngredient;
    private String DataImageIngredient;
    private Uri UriImageIngredient = null;

    public Ingredient() {
    }

    public int getID_Ingredient() {
        return ID_Ingredient;
    }

    public void setID_Ingredient(int ID_Ingredient) {
        this.ID_Ingredient = ID_Ingredient;
    }

    public int getID_Ristorante() {
        return ID_Ristorante;
    }

    public void setID_Ristorante(int ID_Ristorante) {
        this.ID_Ristorante = ID_Ristorante;
    }

    public String getNameIngredient() {
        return NameIngredient;
    }

    public void setNameIngredient(String nameIngredient) {
        NameIngredient = nameIngredient;
    }

    public float getPriceIngredient() {
        return PriceIngredient;
    }

    public void setPriceIngredient(float priceIngredient) {
        PriceIngredient = priceIngredient;
    }

    public int getSizeIngredient() {
        return SizeIngredient;
    }

    public void setSizeIngredient(int sizeIngredient) {
        SizeIngredient = sizeIngredient;
    }

    public String getMeasureType() {
        return MeasureType;
    }

    public void setMeasureType(String measureType) {
        MeasureType = measureType;
    }

    public int getQtaIngredient() {
        return QtaIngredient;
    }

    public void setQtaIngredient(int qtaIngredient) {
        QtaIngredient = qtaIngredient;
    }

    public String getURLImageIngredient() {
        return URLImageIngredient;
    }

    public void setURLImageIngredient(String URLImageIngredient) {
        this.URLImageIngredient = URLImageIngredient;
    }

    public String getDataImageIngredient() {
        return DataImageIngredient;
    }

    public void setDataImageIngredient(String dataImageIngredient) {
        DataImageIngredient = dataImageIngredient;
    }

    public Uri getUriImageIngredient() {
        return UriImageIngredient;
    }

    public void setUriImageIngredient(Uri uriImageIngredient) {
        UriImageIngredient = uriImageIngredient;
    }

    //FUNCTIONAL
    public String getDataFromUriProduct(Context context){
        if( UriImageIngredient == null ) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapProduct = getBitmapFromUri(this.getUriImageIngredient(),context);
        if(bitmapProduct != null){
            bitmapProduct.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray() , Base64.DEFAULT);
        }else return null;
    }
    public String getStringDataImage(Context context){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapProduct = getBitmapFromUri(this.UriImageIngredient,context);
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
}
