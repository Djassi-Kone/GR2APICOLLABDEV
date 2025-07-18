package com.apicollabdev.odk.collabdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_badge")
    private Long idBadge;

    private String nom;
    private String description;
    private String image; // image en URL ou nom de fichier
    private int nombre;


    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true)
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = true, referencedColumnName = "id_administrateur")
    @JsonBackReference
    private Administrateur administrateur;

    public Long getIdBadge() {
        return idBadge;
    }

    public void setIdBadge(Long idBadge) {
        this.idBadge = idBadge;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }
}

