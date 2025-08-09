package com.apicollabdev.odk.collabdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = true, referencedColumnName = "id_administrateur")
    @JsonBackReference
    private Administrateur administrateur;

    @OneToMany(mappedBy = "id_coins")
    private List<Fonctionnalite> fonctionnalites;

    @OneToMany(mappedBy = "coins", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contribution> contributions;


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

    public List<Fonctionnalite> getFonctionnalites() {
        return fonctionnalites;
    }

    public void setFonctionnalites(List<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public LocalDateTime getDateAcquisition(LocalDateTime now) {
        return dateAcquisition;
    }

    public void setDateAcquisition(LocalDateTime dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }
}
