package com.apicollabdev.odk.collabdev.entity;


import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projet")
    private Long idProjet;

    private String titre;
    private String description;

    private LocalDateTime dateCreation;

    private boolean cahierDeCharge;

    @Enumerated(EnumType.STRING)
    private StatutProjet statut;

    public Projet(){
    }

    public Projet(List<Contribution> contributions) {
        this.contributions = contributions;
    }


    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Contribution> contributions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_idee_projet")
    @JsonBackReference
    private IdeeProjet ideeProjet;


    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DemandeParticipation> demandeParticipation;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DemandeParticipation> demandes;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DebloqueProjet> debloqueProjets;

    @ManyToOne
    @JoinColumn(name = "id_domaine", nullable = true)
    @JsonBackReference
    private Domaine domaine;

    @ManyToOne
    @JoinColumn(name = "id_gestionnaire", referencedColumnName = "id_utilisateur")
    @JsonBackReference
    private Gestionnaire gestionnaire;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> notification;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fonctionnalite> fonctionnalites;

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCahierDeCharge() {
        return cahierDeCharge;
    }

    public void setCahierDeCharge(boolean cahierDeCharge) {
        this.cahierDeCharge = cahierDeCharge;
    }

    public StatutProjet getStatut() {
        return statut;
    }

    public void setStatut(StatutProjet statut) {
        this.statut = statut;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public IdeeProjet getIdeeProjet() {
        return ideeProjet;
    }

    public void setIdeeProjet(IdeeProjet ideeProjet) {
        this.ideeProjet = ideeProjet;
    }

    public List<DemandeParticipation> getDemandeParticipation() {
        return demandeParticipation;
    }

    public void setDemandeParticipation(List<DemandeParticipation> demandeParticipation) {
        this.demandeParticipation = demandeParticipation;
    }

    public List<DemandeParticipation> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeParticipation> demandes) {
        this.demandes = demandes;
    }

    public List<DebloqueProjet> getDebloqueProjets() {
        return debloqueProjets;
    }

    public void setDebloqueProjets(List<DebloqueProjet> debloqueProjets) {
        this.debloqueProjets = debloqueProjets;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }
}

