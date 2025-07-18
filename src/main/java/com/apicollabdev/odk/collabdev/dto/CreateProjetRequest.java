package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
@Getter
@Setter
public class CreateProjetRequest {

    private Long idIdee;                  // L’idée source du projet
    private Long idContributeur;          // Celui qui initie la transformation
    private Long idGestionnaire;          // Celui qui va suivre le projet
    private Long idDomaine;               // Domaine d'expertise
    private String titre;                 // Facultatif : override le titre de l’idée
    private String description;           // Facultatif : override la description
    private boolean cahierDeCharge;
    private LocalDate dateCreation;       // facultatif, sinon Date.now()
    // Ajouter si besoin : budget, objectifs, impact, localisation, etc.


    public Long getIdIdee() {
        return idIdee;
    }

    public void setIdIdee(Long idIdee) {
        this.idIdee = idIdee;
    }

    public Long getIdContributeur() {
        return idContributeur;
    }

    public void setIdContributeur(Long idContributeur) {
        this.idContributeur = idContributeur;
    }

    public Long getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(Long idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }

    public Long getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(Long idDomaine) {
        this.idDomaine = idDomaine;
    }

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

    public boolean isCahierDeCharge() {
        return cahierDeCharge;
    }

    public void setCahierDeCharge(boolean cahierDeCharge) {
        this.cahierDeCharge = cahierDeCharge;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}
