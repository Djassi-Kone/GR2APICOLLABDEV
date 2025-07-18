package com.apicollabdev.odk.collabdev.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domaine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domaine")
    private Long idDomaine;

    private String titre;
    private String description;

    @OneToMany(mappedBy = "domaine", cascade = CascadeType.ALL)
    @JsonManagedReference("domaine-idee_projet")
    private List<IdeeProjet> ideeProjets;

    @ManyToOne
    @JoinColumn(name = "administrateur_id",nullable = true,
                referencedColumnName = "id_administrateur" )
    private Administrateur administrateur;



    public Long getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(Long idDomaine) {
        this.idDomaine = idDomaine;
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

    public List<IdeeProjet> getIdeeProjets() {
        return ideeProjets;
    }

    public void setIdeeProjets(List<IdeeProjet> ideeProjets) {
        this.ideeProjets = ideeProjets;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }
}
