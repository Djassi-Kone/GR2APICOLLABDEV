package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.StatutDemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeDemandeParticipation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_participation")
    private Long idDemandeParticipation;


    @Enumerated(EnumType.STRING)
    private StatutDemandeParticipation statutDemandeParticipation;

    private String description;

    @Enumerated(EnumType.STRING)
    private TypeDemandeParticipation typeDemandeParticipation;

    private LocalDateTime datedemande;

    @ManyToOne
    @JoinColumn(name = "id_projet", nullable = true)
    @JsonBackReference
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "id_ideeprojet", nullable = true)
    @JsonBackReference
    private IdeeProjet ideeProjet;


    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    @JsonBackReference
    private Contributeur contributeur;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdDemandeParticipation() {
        return idDemandeParticipation;
    }

    public void setIdDemandeParticipation(Long idDemandeParticipation) {
        this.idDemandeParticipation = idDemandeParticipation;
    }

    public StatutDemandeParticipation getStatutDemandeParticipation() {
        return statutDemandeParticipation;
    }

    public void setStatutDemandeParticipation(StatutDemandeParticipation statutDemandeParticipation) {
        this.statutDemandeParticipation = statutDemandeParticipation;
    }


    public TypeDemandeParticipation getTypeDemandeParticipation() {
        return typeDemandeParticipation;
    }

    public void setTypeDemandeParticipationemande(TypeDemandeParticipation typeDemandeParticipationemande) {
        this.typeDemandeParticipation = typeDemandeParticipation;
    }

    public LocalDateTime getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(LocalDateTime datedemande) {
        this.datedemande = datedemande;
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

    public IdeeProjet getIdeeProjet() {
        return ideeProjet;
    }

    public void setIdeeProjet(IdeeProjet ideeProjet) {
        this.ideeProjet = ideeProjet;
    }
}

