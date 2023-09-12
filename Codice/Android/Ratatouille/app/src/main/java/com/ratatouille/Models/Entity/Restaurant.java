package com.ratatouille.Models.Entity;

import java.util.ArrayList;

public class Restaurant {

    private String Id_Restaurant;
    private String Name;
    private String Address;
    private String Phone;
    private String Email;
    private ArrayList<Tavolo>  ListTavoli;
    public Restaurant() {
    }

    public String getId_Restaurant() {
        return Id_Restaurant;
    }
    public void setId_Restaurant(String id_Restaurant) {
        Id_Restaurant = id_Restaurant;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
}
