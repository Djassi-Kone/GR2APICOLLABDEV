package com.apicollabdev.odk.collabdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = true, referencedColumnName = "id_administrateur")
    @JsonBackReference
    private Administrateur administrateur;


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


}
