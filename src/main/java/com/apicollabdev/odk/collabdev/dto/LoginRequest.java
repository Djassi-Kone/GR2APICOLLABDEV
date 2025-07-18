package com.apicollabdev.odk.collabdev.dto;

public class LoginRequest {
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String nom;
    private String password;


}
