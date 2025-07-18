package com.apicollabdev.odk.collabdev.entity;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("GESTIONNAIRE")
@PrimaryKeyJoinColumn(name = "id_gestionnaire")
public class Gestionnaire extends Contributeur{

    private boolean validerContribution;
    private boolean validerCommentaire;
    private boolean validerDemande;

    @OneToMany(mappedBy = "gestionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projet> projets;


    public boolean isValiderContribution() {
        return validerContribution;
    }

    public void setValiderContribution(boolean validerContribution) {
        this.validerContribution = validerContribution;
    }

    public boolean isValiderCommentaire() {
        return validerCommentaire;
    }

    public void setValiderCommentaire(boolean validerCommentaire) {
        this.validerCommentaire = validerCommentaire;
    }

    public boolean isValiderDemande() {
        return validerDemande;
    }

    public void setValiderDemande(boolean validerDemande) {
        this.validerDemande = validerDemande;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
}

