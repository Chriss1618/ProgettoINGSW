package com.ratatouille.Models.Entity;

import java.util.ArrayList;

public class Ricettario {

    private Integer          GrandezzaInProduct;
    private String           TypeMeasure;
    private Ingredient       Ingredient;

    public Ricettario() {
    }

    public Ricettario(Integer grandezzaInProduct, String typeMeasure, com.ratatouille.Models.Entity.Ingredient ingredient) {
        GrandezzaInProduct = grandezzaInProduct;
        TypeMeasure = typeMeasure;
        Ingredient = ingredient;
    }

    public Integer getGrandezzaInProduct() {
        return GrandezzaInProduct;
    }

    public void setGrandezzaInProduct(Integer grandezzaInProduct) {
        GrandezzaInProduct = grandezzaInProduct;
    }

    public String getTypeMeasure() {
        return TypeMeasure;
    }

    public void setTypeMeasure(String typeMeasure) {
        TypeMeasure = typeMeasure;
    }

    public com.ratatouille.Models.Entity.Ingredient getIngredient() {
        return Ingredient;
    }

    public void setIngredient(com.ratatouille.Models.Entity.Ingredient ingredient) {
        Ingredient = ingredient;
    }
}
