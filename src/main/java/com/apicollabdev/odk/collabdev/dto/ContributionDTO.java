package com.apicollabdev.odk.collabdev.dto;

import com.apicollabdev.odk.collabdev.enums.TypeContribution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContributionDTO {
    private String titre;                   // Nom ou titre de la contribution
    private String contenu;                 // Code ou texte si type EDITEUR
    private String lien;                    // Lien GitHub ou Figma
    private MultipartFile fichier;          // Fichier si type DOCUMENT
    private TypeContribution type;          // Type de contribution
    private Long projetId;                  // ID du projet associé
    private Long contributeurId;            // ID du contributeur
    private String fonctionnaliteNom;       // Nom de la fonctionnalité si ajoutée
    private String fonctionnaliteDescription; // Description de la fonctionnalité

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public MultipartFile getFichier() {
        return fichier;
    }

    public void setFichier(MultipartFile fichier) {
        this.fichier = fichier;
    }

    public TypeContribution getType() {
        return type;
    }

    public void setType(TypeContribution type) {
        this.type = type;
    }

    public Long getProjetId() {
        return projetId;
    }

    public void setProjetId(Long projetId) {
        this.projetId = projetId;
    }

    public Long getContributeurId() {
        return contributeurId;
    }

    public void setContributeurId(Long contributeurId) {
        this.contributeurId = contributeurId;
    }

    public String getFonctionnaliteNom() {
        return fonctionnaliteNom;
    }

    public void setFonctionnaliteNom(String fonctionnaliteNom) {
        this.fonctionnaliteNom = fonctionnaliteNom;
    }

    public String getFonctionnaliteDescription() {
        return fonctionnaliteDescription;
    }

    public void setFonctionnaliteDescription(String fonctionnaliteDescription) {
        this.fonctionnaliteDescription = fonctionnaliteDescription;
    }
}
