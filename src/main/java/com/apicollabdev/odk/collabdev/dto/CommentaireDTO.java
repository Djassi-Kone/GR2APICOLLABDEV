package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

@Data
public class CommentaireDTO {

    private String auteur;

    private String contenu;

    private boolean supprime;
    private boolean modifie;

    public boolean isModifie() {
        return modifie;
    }

    public void setModifie(boolean modifie) {
        this.modifie = modifie;
    }

    public boolean isSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
