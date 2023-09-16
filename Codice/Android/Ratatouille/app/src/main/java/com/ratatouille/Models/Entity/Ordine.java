package com.ratatouille.Models.Entity;

public class Ordine {
    private Tavolo tavolo;
    private String Id_Ordine;
    private String PrezzoTotale;
    private CategoriaMenu Categoria;
    public Ordine() {
    }

    public Tavolo getTavolo() {
        return tavolo;
    }
    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    public String getId_Ordine() {
        return Id_Ordine;
    }
    public void setId_Ordine(String id_Ordine) {
        Id_Ordine = id_Ordine;
    }

    public String getPrezzoTotale() {
        return PrezzoTotale;
    }
    public void setPrezzoTotale(String prezzoTotale) {
        PrezzoTotale = prezzoTotale;
    }

    public CategoriaMenu getCategoria() {
        return Categoria;
    }
    public void setCategoria(CategoriaMenu categoria) {
        Categoria = categoria;
    }
}
