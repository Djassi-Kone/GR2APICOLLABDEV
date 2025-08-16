package com.apicollabdev.odk.collabdev.dto;

import org.springframework.web.multipart.MultipartFile;

public class FonctionnaliteDTO {
    private int id;
    private String pointFonctionnalite;
    private String statutF;
    private Long projetId;
    private String FonctionnaliteNom;
    private MultipartFile CahierDeCharge;
    private String FonctionnaliteDescription;


    public FonctionnaliteDTO() {
    }

    public FonctionnaliteDTO(int id, String pointFonctionnalite, String statut, Long projetId, String FonctionnaliteNom, String FonctionnaliteDescription, MultipartFile CahierDeCharge) {
        this.id = id;
        this.pointFonctionnalite = pointFonctionnalite;
        this.statutF = statut;
        this.projetId = projetId;
        this.FonctionnaliteNom = FonctionnaliteNom;
        this.FonctionnaliteDescription = FonctionnaliteDescription;
        this.CahierDeCharge = CahierDeCharge;
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

    public String getStatut() {
        return statutF;
    }

    public void setStatut(String statut) {
        this.statutF = statut;
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

    public String getStatutF() {
        return statutF;
    }

    public void setStatutF(String statutF) {
        this.statutF = statutF;
    }

    public MultipartFile getCahierDeCharge() {
        return CahierDeCharge;
    }

    public void setCahierDeCharge(MultipartFile cahierDeCharge) {
        CahierDeCharge = cahierDeCharge;
    }
}
