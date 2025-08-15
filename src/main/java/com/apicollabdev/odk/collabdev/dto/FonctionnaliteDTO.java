package com.apicollabdev.odk.collabdev.dto;

import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;

public class FonctionnaliteDTO {
    private int id;
    private String pointFonctionnalite;
    private StatutFonctionnalite statutF;
    private Long projetId;
    private String FonctionnaliteNom;
    private String FonctionnaliteDescription;


    public FonctionnaliteDTO() {
    }

    public FonctionnaliteDTO(int id, String pointFonctionnalite, String statut, Long projetId, String FonctionnaliteNom, String FonctionnaliteDescription) {
        this.id = id;
        this.pointFonctionnalite = pointFonctionnalite;
        this.projetId = projetId;
        this.FonctionnaliteNom = FonctionnaliteNom;
        this.FonctionnaliteDescription = FonctionnaliteDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPointFonctionnalite() {
        return pointFonctionnalite;
    }

    public void setPointFonctionnalite(String pointFonctionnalite) {
        this.pointFonctionnalite = pointFonctionnalite;
    }

    public StatutFonctionnalite getStatutF() {
        return statutF;
    }

    public void setStatutF(StatutFonctionnalite statutF) {
        this.statutF = statutF;
    }

    public long getProjetId() {
        return Math.toIntExact(projetId);
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public String getFonctionnaliteDescription() {
        return FonctionnaliteDescription;
    }

    public void setFonctionnaliteDescription(String fonctionnaliteDescription) {
        FonctionnaliteDescription = fonctionnaliteDescription;
    }

    public String getFonctionnaliteNom() {
        return FonctionnaliteNom;
    }

    public void setFonctionnaliteNom(String fonctionnaliteNom) {
        FonctionnaliteNom = fonctionnaliteNom;
    }
}
