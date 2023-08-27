package com.ratatouille.Models.Entity;

public class Ricettario {
    private int         GrandezzaInProduct;
    private String      TypeMeasure;
    private Ingredient  Ingredient;
    private Product     Product;

    public Ricettario() {
    }

    public int getGrandezzaInProduct() {
        return GrandezzaInProduct;
    }
    public void setGrandezzaInProduct(int grandezzaInProduct) {
        GrandezzaInProduct = grandezzaInProduct;
    }

    public String getTypeMeasure() {
        return TypeMeasure;
    }
    public void setTypeMeasure(String typeMeasure) {
        TypeMeasure = typeMeasure;
    }

    public Ingredient getIngredient() {
        return Ingredient;
    }
    public void setIngredient(com.ratatouille.Models.Entity.Ingredient ingredient) {
        Ingredient = ingredient;
    }

    public Product getProduct() {
        return Product;
    }
    public void setProduct(com.ratatouille.Models.Entity.Product product) {
        Product = product;
    }
}
