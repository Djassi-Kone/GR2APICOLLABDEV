package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

@Data
public class GestionnaireDTO {
    private Long id;
    private boolean validerContribution;
    private boolean validerDemande;
    private int coinsAAttribuer; // Pour l'attribution de coins
    private Long contributionId; // Pour la validation de contribution
    private Long demandeId; // Pour la validation de demande

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isValiderContribution() {
        return validerContribution;
    }

    public void setValiderContribution(boolean validerContribution) {
        this.validerContribution = validerContribution;
    }

    public boolean isValiderDemande() {
        return validerDemande;
    }

    public void setValiderDemande(boolean validerDemande) {
        this.validerDemande = validerDemande;
    }

    public int getCoinsAAttribuer() {
        return coinsAAttribuer;
    }

    public void setCoinsAAttribuer(int coinsAAttribuer) {
        this.coinsAAttribuer = coinsAAttribuer;
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public Long getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(Long demandeId) {
        this.demandeId = demandeId;
    }
}