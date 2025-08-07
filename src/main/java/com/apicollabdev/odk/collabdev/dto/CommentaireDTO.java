package com.apicollabdev.odk.collabdev.dto;


public class CommentaireDTO {
    private String auteur;
    private String contenu;
    private boolean supprime;
    private boolean modifie;
    private Long idContributeur;
    private Long idProjet;

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean isSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }

    public boolean isModifie() {
        return modifie;
    }

    public void setModifie(boolean modifie) {
        this.modifie = modifie;
    }

    public Long getIdContributeur() {
        return idContributeur;
    }

    public void setIdContributeur(Long idContributeur) {
        this.idContributeur = idContributeur;
    }

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }
}
