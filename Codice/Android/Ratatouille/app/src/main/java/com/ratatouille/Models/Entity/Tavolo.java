package com.ratatouille.Models.Entity;

public class Tavolo {
    private String Id_Tavolo;
    private String ID_Restaurant;
    private String N_Tavolo;
    private boolean stateTavolo;

    public Tavolo() {
    }

    public String getId_Tavolo() {
        return Id_Tavolo;
    }
    public void setId_Tavolo(String id_Tavolo) {
        Id_Tavolo = id_Tavolo;
    }

    public String getID_Restaurant() {
        return ID_Restaurant;
    }
    public void setID_Restaurant(String ID_Restaurant) {
        this.ID_Restaurant = ID_Restaurant;
    }

    public String getN_Tavolo() {
        return N_Tavolo;
    }
    public void setN_Tavolo(String n_Tavolo) {
        N_Tavolo = n_Tavolo;
    }

    public boolean isStateTavolo() {
        return stateTavolo;
    }
    public void setStateTavolo(boolean stateTavolo) {
        this.stateTavolo = stateTavolo;
    }
}