package com.apicollabdev.odk.collabdev.entity;


import com.apicollabdev.odk.collabdev.enums.StatutContribution;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribution")
    private Long idContribution;

    private String titre;

    private String contenu;
    private Long fonctionnalite_id;
    @Enumerated(EnumType.STRING)
    private StatutContribution statutC;
    private String type; // Exemple : "code", "design", "idée", etc.

    private String motifRejet;
    private LocalDateTime dateSoumission;
    private LocalDateTime dateValidation;

    @ManyToOne
    @JoinColumn(name = "id_projet", nullable = true)
    @JsonBackReference
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "contributeur_id")
    @JsonBackReference
    private Contributeur contributeur;


    /*@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fonctionnalite_id")
    @JsonBackReference
    private Fonctionnalite fonctionnalite; */

    @OneToMany(mappedBy = "contribution", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Coins> coins;

    public Long getIdContribution() {
        return idContribution;
    }

    public void setIdContribution(Long idContribution) {
        this.idContribution = idContribution;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu(String contenu) {
        return this.contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(LocalDateTime dateSoumission) {
        this.dateSoumission = dateSoumission;
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

    public StatutContribution getStatutC() {
        return statutC;
    }

    public void setStatutC(StatutContribution statutC) {
        this.statutC = statutC;
    }


    public Long getFonctionnalite_id() {
        return fonctionnalite_id;
    }

    public void setFonctionnalite_id(Long fonctionnalite_id) {
        this.fonctionnalite_id = fonctionnalite_id;
    }

    public String getContenu() {
        return contenu;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }
}


