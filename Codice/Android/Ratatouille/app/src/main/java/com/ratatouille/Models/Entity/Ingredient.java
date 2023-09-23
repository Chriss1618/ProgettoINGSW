package com.ratatouille.Models.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ingredient implements Cloneable{
    private int ID_Ingredient;
    private int ID_Ristorante;

    private String NameIngredient;
    private String PriceIngredient;
    private int SizeIngredient;
    private String MeasureType;
    private int QtaIngredient;
    private String Description;

    private String URLImageIngredient;
    private String DataImageIngredient;
    private Uri UriImageIngredient = null;
    private boolean hasPhoto = false;
    private boolean hasUrl = false;

    public Ingredient() {
    }
    public Ingredient(Ingredient newIngredient) {
        this.ID_Ingredient = newIngredient.ID_Ingredient;
        this.ID_Ristorante = newIngredient.ID_Ristorante;
        this.NameIngredient = newIngredient.NameIngredient;
        this.Description = newIngredient.Description;
        this.PriceIngredient = newIngredient.PriceIngredient;
        SizeIngredient = newIngredient.SizeIngredient;
        MeasureType = newIngredient.MeasureType;
        QtaIngredient = newIngredient.QtaIngredient;
        this.URLImageIngredient = newIngredient.URLImageIngredient;
    }
    public Ingredient(int ID_Ingredient,
                      int ID_Ristorante,
                      String nameIngredient,
                      String Description,
                      String Prezzo,
                      int sizeIngredient,
                      String measureType,
                      int qtaIngredient,
                      String URLImageIngredient) {
        this.ID_Ingredient = ID_Ingredient;
        this.ID_Ristorante = ID_Ristorante;
        NameIngredient = nameIngredient;
        this.Description = Description;
        this.PriceIngredient = Prezzo;
        SizeIngredient = sizeIngredient;
        MeasureType = measureType;
        QtaIngredient = qtaIngredient;
        this.URLImageIngredient = URLImageIngredient;
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

    public String getPriceIngredient() {
        return PriceIngredient;
    }
    public void setPriceIngredient(String priceIngredient) {
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
        this.hasUrl = true;
        this.URLImageIngredient = URLImageIngredient;
    }

    public String getDataImageIngredient() {
        return DataImageIngredient;
    }
    public void setDataImageIngredient(String dataImageIngredient) {
        DataImageIngredient = dataImageIngredient;
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

    public Uri getUriImageIngredient() {
        return UriImageIngredient;
    }

    public void setUriImageIngredient(Uri uriImageIngredient) {
        UriImageIngredient = uriImageIngredient;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    @NonNull
    @Override
    public Ingredient clone() {
        try {
            return (Ingredient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
