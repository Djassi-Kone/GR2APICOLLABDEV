package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Fonctionnalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fonctionnalite")
    private int idFonctionnalite;

    private int pointFonctionnalite;
    @Enumerated(EnumType.STRING)
    private StatutProjet statutP;
    private String nomFonctionnalite;
    private String DescriptionFonctionnalite;

    @ManyToOne
    @JoinColumn(name = "projet_id",
            nullable = false)
    private Projet projet;
    @ManyToOne
    @JoinColumn(name = "gestionnaire_id",
            referencedColumnName = "id_utilisateur",
            nullable = false)
    private Gestionnaire gestionnaires;

    public int getIdFonctionnalite() {
        return idFonctionnalite;
    }

    public void setIdFonctionnalite(int idFonctionnalite) {
        this.idFonctionnalite = idFonctionnalite;
    }

    public int getPointFonctionnalite() {
        return pointFonctionnalite;
    }

    public void setPointFonctionnalite(int pointFonctionnalite) {
        this.pointFonctionnalite = pointFonctionnalite;
    }

    public StatutProjet getStatutP() {
        return statutP;
    }

    public void setStatutP(StatutProjet statutP) {
        this.statutP = statutP;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public String getNomFonctionnalite() {
        return nomFonctionnalite;
    }

    public void setNomFonctionnalite(String nomFonctionnalite) {
        this.nomFonctionnalite = nomFonctionnalite;
    }

    public String getDescriptionFonctionnalite() {
        return DescriptionFonctionnalite;
    }

    public void setDescriptionFonctionnalite(String descriptionFonctionnalite) {
        DescriptionFonctionnalite = descriptionFonctionnalite;
    }
}
