package com.apicollabdev.odk.collabdev.entity;
import com.apicollabdev.odk.collabdev.enums.ChoixRole;
import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import jakarta.persistence.*;
import lombok.*;

import javax.net.ssl.SSLSession;

@Entity
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long idDemande;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;


    @Enumerated(EnumType.STRING)
    private ChoixRole choixRole;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_projet", nullable = true)
    private Projet projet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    private Contributeur contributeur;

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

    public ChoixRole getChoixRole() {
        return choixRole;
    }

    public void setChoixRole(ChoixRole choixRole) {
        this.choixRole = choixRole;
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

