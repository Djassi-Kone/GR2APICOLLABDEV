package com.apicollabdev.odk.collabdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commentaire")
    private int idCommentaire;

    private String auteur;

    private String contenu;

    @ManyToOne
    @JoinColumn(name = "id_contributeur", nullable = true, referencedColumnName = "id_contributeur")
    @JsonBackReference
    private Contributeur contributeur;

    @ManyToOne
    @JoinColumn(name = "id_projet", nullable = true)
    @JsonBackReference
    private Projet projet;


    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

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

    public Contributeur getContributeur() {
        return contributeur;
    }

    public void setContributeur(Contributeur contributeur) {
        this.contributeur = contributeur;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}

