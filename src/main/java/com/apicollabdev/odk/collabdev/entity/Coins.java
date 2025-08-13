package com.apicollabdev.odk.collabdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coins")
    private Long idCoin;

    private int nombreCoins;
    private LocalDateTime dateAcquisition;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    @JsonIgnoreProperties("coins")
    @JsonBackReference
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = true, referencedColumnName = "id_administrateur")
    @JsonBackReference
    private Administrateur administrateur;

    @ManyToOne
    @JoinColumn(name = "idFonctionnalite", nullable = false)
    @JsonBackReference
    private Fonctionnalite fonctionnalite;

    /*@ManyToOne
    @JoinColumn(name = "id_contribution")
    private Contribution contributions;*/

    @ManyToOne
    @JoinColumn(name = "contribution_id")
    @JsonBackReference
    private Contribution contribution;

    public Long getIdCoin() {
        return idCoin;
    }

    public void setIdCoin(Long idCoin) {
        this.idCoin = idCoin;
    }

    public int getNombreCoins() {
        return nombreCoins;
    }

    public void setNombreCoins(int nombreCoins) {
        this.nombreCoins = nombreCoins;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public LocalDateTime getDateAcquisition(LocalDateTime now) {
        return dateAcquisition;
    }

    public void setDateAcquisition(LocalDateTime dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public LocalDateTime getDateAcquisition() {
        return dateAcquisition;
    }

    public Fonctionnalite getFonctionnalite() {
        return fonctionnalite;
    }

    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }
}
