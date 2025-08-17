package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Entity
public class Fonctionnalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fonctionnalite")
    private Long idFonctionnalite;

    private int pointFonctionnalite;
    @Enumerated(EnumType.STRING)
    private StatutFonctionnalite statutF;
    private String nomFonctionnalite;
    private String DescriptionFonctionnalite;
    private String CahierDeCharge;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonBackReference
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "id_gestionnaire", nullable = false)
    @JsonBackReference
    private Gestionnaire gestionnaire;



    @OneToMany(mappedBy = "fonctionnalite" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Coins> coins;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_contribution")
    @JsonBackReference
    private Contribution contribution;


    public Long getIdFonctionnalite() {
        return idFonctionnalite;
    }

    public void setIdFonctionnalite(Long idFonctionnalite) {
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

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaires) {
        this.gestionnaire = gestionnaires;
    }

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }

    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    public String getCahierDeCharge() {
        return CahierDeCharge;
    }

    public void setCahierDeCharge(String cahierDeCharge) {
        CahierDeCharge = cahierDeCharge;
    }
}



