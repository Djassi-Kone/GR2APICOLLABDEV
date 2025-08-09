package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
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
    private StatutFonctionnalite statutF;
    private String nomFonctionnalite;
    private String DescriptionFonctionnalite;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
    @ManyToOne
    @JoinColumn(name = "id_gestionnaire", nullable = false)
            //referencedColumnName = "id_gestionnaire",
    private Gestionnaire gestionnaires;

    @ManyToOne
    @JoinColumn(name = "id_coins")
    private Coins coins;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = false)
           // referencedColumnName = "id_contributeur",
    private Contributeur contributeur;

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

    public StatutFonctionnalite getStatutF() {
        return statutF;
    }

    public void setStatutF(StatutFonctionnalite statutP) {
        this.statutF = statutP;
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

    public Gestionnaire getGestionnaires() {
        return gestionnaires;
    }

    public void setGestionnaires(Gestionnaire gestionnaires) {
        this.gestionnaires = gestionnaires;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }
}
