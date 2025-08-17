package com.apicollabdev.odk.collabdev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContributionDTO {
    private Long idProjet;
    private Long idFonctionnalite;
    private Long idContributeur;
    private String titre;
    private String description;
    private String type;   // "Lien" ou "Fichier"
    private String urlCode;
    private String contenu;

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public Long getIdContributeur() {
        return idContributeur;
    }

    public void setIdContributeur(Long idContributeur) {
        this.idContributeur = idContributeur;
    }



    public void setUrlCode(String urlCode) {
        this.urlCode = urlCode;
    }

    public Long getIdFonctionnalite() {
        return idFonctionnalite;
    }

    public void setIdFonctionnalite(Long idFonctionnalite) {
        this.idFonctionnalite = idFonctionnalite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
