package com.apicollabdev.odk.collabdev.enums;

public enum StatutDemandeParticipation {
    EN_ATTENTE("En attente"),
    ACCEPTEE("Acceptée"),
    REFUSEE("Refusée");

    private final String libelle;

    StatutDemandeParticipation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
