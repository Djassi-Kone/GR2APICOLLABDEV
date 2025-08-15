package com.apicollabdev.odk.collabdev.entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("GESTIONNAIRE")
@PrimaryKeyJoinColumn(name = "id_gestionnaire")
public class Gestionnaire extends Contributeur{


    @OneToMany(mappedBy = "gestionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Projet> projets;

    @OneToMany(mappedBy = "gestionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fonctionnalite> fonctionnalites;

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public List<Fonctionnalite> getFonctionnalites() {
        return fonctionnalites;
    }

    public void setFonctionnalites(List<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }
}

