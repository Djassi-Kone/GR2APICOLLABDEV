package com.apicollabdev.odk.collabdev.dto;

public class FonctionnaliteDTO {
    private int id;
    private String pointFonctionnalite;
    private String statut;
    private Long projetId;
    private String FonctionnaliteNom;
    private String FonctionnaliteDescription;


    public FonctionnaliteDTO() {
    }

    public FonctionnaliteDTO(int id, String pointFonctionnalite, String statut, Long projetId, String FonctionnaliteNom, String FonctionnaliteDescription) {
        this.id = id;
        this.pointFonctionnalite = pointFonctionnalite;
        this.statut = statut;
        this.projetId = projetId;
        this.FonctionnaliteNom = FonctionnaliteNom;
        this.FonctionnaliteDescription = FonctionnaliteDescription;
    }

    public int getId() {
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getProjetId() {
        return projetId;
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
