package com.ratatouille.Models.Entity;

import android.net.Uri;

public class Product {
    private int ID_product;
    private int ID_category;

    private String URLImageProduct;
    private Uri UriImageProduct;

    private String NameProduct;
    private float PriceProduct;
    private String DescriptionProduct;
    private String AllergeniProduct;
    private boolean isSendToKitchen;

    public Product() {
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
        this.URLImageProduct = URLImageProduct;
    }

    public Uri getUriImageProduct() {
        return UriImageProduct;
    }

    public void setUriImageProduct(Uri uriImageProduct) {
        UriImageProduct = uriImageProduct;
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
}
