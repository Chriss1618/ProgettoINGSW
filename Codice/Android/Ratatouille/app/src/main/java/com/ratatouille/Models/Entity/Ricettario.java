package com.ratatouille.Models.Entity;

public class Ricettario {

    private Integer          Dosi;
    private String           TypeMeasure;
    private Ingredient       Ingredient;

    public Ricettario() {
    }

    public Ricettario(Integer grandezzaInProduct, String typeMeasure, com.ratatouille.Models.Entity.Ingredient ingredient) {
        Dosi = grandezzaInProduct;
        TypeMeasure = typeMeasure;
        Ingredient = ingredient;
    }

    public Integer getDosi() {
        return Dosi;
    }

    public void setDosi(Integer dosi) {
        Dosi = dosi;
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
