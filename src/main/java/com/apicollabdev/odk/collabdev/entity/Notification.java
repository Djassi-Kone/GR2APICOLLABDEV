package com.apicollabdev.odk.collabdev.entity;


import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @ManyToOne
    @JoinColumn(name = "idAdministrateur",  nullable = true)
    @JsonBackReference(value = "admin-notification")
    private Administrateur administrateur;

    @ManyToOne
    @JoinColumn(name = "idContributeur",  nullable = true)
    @JsonBackReference(value = "contributeur-notification")
    private Contributeur contributeur;

    private String description;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TypeNotification TypeNotyf;

    @Enumerated(EnumType.STRING)
    private StatutDemande statutDemande;

    private boolean etat;


    @ManyToOne
    @JoinColumn(name = "id_projet")
    private Projet projet;


    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recevoir> recevoirs;


    public Long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateNotification() {
        return dateCreation;
    }

    public void setDateNotification(LocalDateTime dateNotification) {
        this.dateCreation = dateNotification;
    }

    public TypeNotification getTypeNotyf() {
        return TypeNotyf;
    }

    public void setTypeNotyf(TypeNotification typeNotyf) {
        TypeNotyf = typeNotyf;
    }

    public StatutDemande getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(StatutDemande statutDemande) {
        this.statutDemande = statutDemande;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<Recevoir> getRecevoirs() {
        return recevoirs;
    }

    public void setRecevoirs(List<Recevoir> recevoirs) {
        this.recevoirs = recevoirs;
    }
}

