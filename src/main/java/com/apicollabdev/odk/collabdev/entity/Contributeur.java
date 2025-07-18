package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.Niveau;
import com.apicollabdev.odk.collabdev.enums.Profil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id_contributeur")
public class Contributeur extends Utilisateur{


    private String nom;
    private String prenom;
    private boolean Active;


    @Enumerated(EnumType.STRING)
    private Profil profil; // DEVELOPPER, DESIGNER, etc

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    //@Column(nullable = false)
    //private int coin = 0;


//Relation

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "contributeur-notification")
    private List<Notification> notification;


    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "contributeur-idee_projet")
    private List<IdeeProjet> ideeProjets;


    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coins> coins;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Badge> badges;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeParticipation> demandeParticipations;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demande> demandes;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebloqueProjet> debloqueProjets;

    @OneToMany(mappedBy = "contributeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recevoir> recevoirs;


    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public List<IdeeProjet> getIdeeProjets() {
        return ideeProjets;
    }

    public void setIdeeProjets(List<IdeeProjet> ideeProjets) {
        this.ideeProjets = ideeProjets;
    }

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }

    public List<DemandeParticipation> getDemandeParticipations() {
        return demandeParticipations;
    }

    public void setDemandeParticipations(List<DemandeParticipation> demandeParticipations) {
        this.demandeParticipations = demandeParticipations;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public List<DebloqueProjet> getDebloqueProjets() {
        return debloqueProjets;
    }

    public void setDebloqueProjets(List<DebloqueProjet> debloqueProjets) {
        this.debloqueProjets = debloqueProjets;
    }

    public List<Recevoir> getRecevoirs() {
        return recevoirs;
    }

    public void setRecevoirs(List<Recevoir> recevoirs) {
        this.recevoirs = recevoirs;
    }
}

