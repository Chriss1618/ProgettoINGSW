package com.ratatouille.Models.Entity;

public class Utente {
    private static Utente instance;

    private int id_utente;
    private String Nome;
    private String Cognome;
    private String type_user;
    private String Email;
    private String Password;
    private String Token;
    private int id_Restaurant;

    private Utente(){

    }

    public static Utente getInstance(){
        if(instance == null) instance = new Utente();
        return instance;
    }

    public int getId_utente() {
        return id_utente;
    }
    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }
    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getType_user() {
        return type_user;
    }
    public void setType_user(String type_user) {
        this.type_user = type_user;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }

    public int getId_Restaurant() {
        return id_Restaurant;
    }
    public void setId_Restaurant(int id_Restaurant) {
        this.id_Restaurant = id_Restaurant;
    }
}
