package com.apicollabdev.odk.collabdev.dto;

import java.time.LocalDateTime;

public class IdeeProjetDTO {
    private int idIdeeProjet;
    private String titre;
   // private LocalDateTime dateCreation = LocalDateTime.now();
    private String niveau;
    private String description;

    public IdeeProjetDTO() {
    }

    public IdeeProjetDTO(int idIdeeProjet,
                         String titre,
                         LocalDateTime dateCreation,
                         String niveau,
                         String description,
                         String domaine) {
        this.idIdeeProjet = idIdeeProjet;
        this.titre = titre;
        this.niveau = niveau;
        this.description = description;
    }

    public int getIdIdeeProjet() {
        return idIdeeProjet;
    }

    public void setIdIdeeProjet(int idIdeeProjet) {
        this.idIdeeProjet = idIdeeProjet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }


    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
