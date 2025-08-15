package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

@Data
public class CoinsDTO {
    private int nombreCoins;

    private long idContributeur;       // pour savoir à qui attribuer le coin
    private long idGestionnaire;

    public int getNombreCoins() {
        return nombreCoins;
    }

    public void setNombreCoins(int nombreCoins) {
        this.nombreCoins = nombreCoins;
    }

    public long getIdContributeur() {
        return idContributeur;
    }

    public void setIdContributeur(long idContributeur) {
        this.idContributeur = idContributeur;
    }

    public long getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(long idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }
}
