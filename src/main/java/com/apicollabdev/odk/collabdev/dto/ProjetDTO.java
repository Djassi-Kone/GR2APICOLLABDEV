package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

@Data
public class ProjetDTO {
    private String titre;
    private String description;
    private String statut;
    private String cahierDeCharge;
    private Long domaineId;
    private Long gestionnaireId;
    private Long ideeProjetId;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCahierDeCharge() {
        return cahierDeCharge;
    }

    public void setCahierDeCharge(String cahierDeCharge) {
        this.cahierDeCharge = cahierDeCharge;
    }

    public Long getDomaineId() {
        return domaineId;
    }

    public void setDomaineId(Long domaineId) {
        this.domaineId = domaineId;
    }

    public Long getGestionnaireId() {
        return gestionnaireId;
    }

    public void setGestionnaireId(Long gestionnaireId) {
        this.gestionnaireId = gestionnaireId;
    }

    public Long getIdeeProjetId() {
        return ideeProjetId;
    }

    public void setIdeeProjetId(Long ideeProjetId) {
        this.ideeProjetId = ideeProjetId;
    }
}

