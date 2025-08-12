package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.Niveau;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IdeeProjet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idee_projet")
    private Long idIdeeProjet;

    private String description;

    private String titre;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(length = 50) // pour éviter tout dépassement
    private StatutIdee statut;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;



    private boolean Leguer;

    @Version
    @Column(name = "version")
    private Long version;


    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    private Contributeur contributeur;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_projet")
    private Projet projet;

    @OneToMany(mappedBy = "idDemandeParticipation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeParticipation> demandes;


    @ManyToOne
    @JoinColumn(name = "domaine_id")
    @JsonBackReference("domaine-idee_projet")
    private Domaine domaine;


    public Long getIdIdeeProjet() {
        return idIdeeProjet;
    }

    public void setIdIdeeProjet(Long idIdeeProjet) {
        this.idIdeeProjet = idIdeeProjet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutIdee getStatut() {
        return statut;
    }

    public void setStatut(StatutIdee statut) {
        this.statut = statut;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public boolean isLeguer(boolean b) {
        return Leguer;
    }

    public void setLeguer(boolean leguer) {
        Leguer = leguer;
    }

    public List<DemandeParticipation> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeParticipation> demandes) {
        this.demandes = demandes;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}


