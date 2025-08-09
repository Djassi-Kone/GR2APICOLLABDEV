package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @JoinColumn(name = "id_gestionnaire",referencedColumnName = "id_gestionnaire", nullable = false)
    private Gestionnaire gestionnaires;

    @OneToMany(mappedBy = "fonctionnalite" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coins> coins;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", referencedColumnName = "id_contributeur", nullable = false)
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_contribution") // FK dans Fonctionnalite
    private Contribution contribution;

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

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }
}
