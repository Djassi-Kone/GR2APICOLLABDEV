package com.apicollabdev.odk.collabdev.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebloqueProjet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_debloque_projet")
    private Integer id_debloqueProjet;

    private boolean visibilite;

    private int nombreCoins;

    @ManyToOne
    @JoinColumn(name = "id_projet", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = false, referencedColumnName = "id_contributeur")
    private Contributeur contributeur;


    public Integer getId_debloqueProjet() {
        return id_debloqueProjet;
    }

    public void setId_debloqueProjet(Integer id_debloqueProjet) {
        this.id_debloqueProjet = id_debloqueProjet;
    }

    public boolean isVisibilite() {
        return visibilite;
    }

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
    }

    public int getNombreCoins() {
        return nombreCoins;
    }

    public void setNombreCoins(int nombreCoins) {
        this.nombreCoins = nombreCoins;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }
}
