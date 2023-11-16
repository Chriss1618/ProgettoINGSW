package com.ratatouille.Models.Entity;

import java.util.ArrayList;

public class Stats {

    private ArrayList<Utente> ListStaffChef;
    private ArrayList<Product> ListOrdiniCompletati;
    private ArrayList<Utente> ShownStaffChef;

    public Stats() {
        ListStaffChef = new ArrayList<>();
        ListOrdiniCompletati = new ArrayList<>();
        ShownStaffChef = new ArrayList<>();
    }

    public ArrayList<Utente> getListStaffChef() {
        return ListStaffChef;
    }
    public void setListStaffChef(ArrayList<Utente> listStaffChef) {
        ListStaffChef = listStaffChef;
    }

    public ArrayList<Product> getListOrdiniCompletati() {
        return ListOrdiniCompletati;
    }
    public void setListOrdiniCompletati(ArrayList<Product> listOrdiniCompletati) {
        ListOrdiniCompletati = listOrdiniCompletati;
    }

    public ArrayList<Utente> getShownStaffChef() {
        return ShownStaffChef;
    }

    public void setShownStaffChef(ArrayList<Utente> shownStaffChef) {
        ShownStaffChef = shownStaffChef;
    }
}
