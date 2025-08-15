package com.apicollabdev.odk.collabdev.dto;

import java.time.LocalDateTime;

public class DemandeProjetDto {
    private Long idDemandeParticipation;
    private String description;
    private String statutDemandeParticipation;
    private LocalDateTime datedemande;
    private String typeDemandeParticipationemande;
    private String nomContributeur; // <-- Nouveau champ

    // Getters et Setters
    public Long getIdDemandeParticipation() { return idDemandeParticipation; }
    public void setIdDemandeParticipation(Long id) { this.idDemandeParticipation = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatutDemandeParticipation() { return statutDemandeParticipation; }
    public void setStatutDemandeParticipation(String statut) { this.statutDemandeParticipation = statut; }

    public LocalDateTime getDatedemande() { return datedemande; }
    public void setDatedemande(LocalDateTime date) { this.datedemande = date; }

    public String getTypeDemandeParticipationemande() { return typeDemandeParticipationemande; }
    public void setTypeDemandeParticipationemande(String type) { this.typeDemandeParticipationemande = type; }

    public String getNomContributeur() { return nomContributeur; }
    public void setNomContributeur(String nom) { this.nomContributeur = nom; }
}

