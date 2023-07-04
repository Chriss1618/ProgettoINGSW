package com.ratatouille.Models.Entity;

public class CategoriaMenu {

    private String NomeCategoria;
    private int ID_categoria;

    public CategoriaMenu() {
    }

    public CategoriaMenu(String nomeCategoria, int ID_categoria) {
        NomeCategoria = nomeCategoria;
        this.ID_categoria = ID_categoria;
    }

    public String getNomeCategoria() {
        return NomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        NomeCategoria = nomeCategoria;
    }

    public int getID_categoria() {
        return ID_categoria;
    }
    public void setID_categoria(int ID_categoria) {
        this.ID_categoria = ID_categoria;
    }
}
