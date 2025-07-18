package com.apicollabdev.odk.collabdev.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Recevoir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recevoir")
    private int idRecevoir;

    private LocalDateTime dateReception = LocalDateTime.now();

    private boolean lue;

    @ManyToOne
    @JoinColumn(name = "id_notification", nullable = false)
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = false)
    private Contributeur contributeur;

    public void setContributeur(Contributeur gestionnaireContributeur) {
    }

    public int getIdRecevoir() {
        return idRecevoir;
    }

    public void setIdRecevoir(int idRecevoir) {
        this.idRecevoir = idRecevoir;
    }

    public LocalDateTime getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDateTime dateReception) {
        this.dateReception = dateReception;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }
}
