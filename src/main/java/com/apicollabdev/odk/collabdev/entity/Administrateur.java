package com.apicollabdev.odk.collabdev.entity;


import com.apicollabdev.odk.collabdev.enums.ProfilAdmin;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Setter
@Getter
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ADMINISTRATEUR")
@PrimaryKeyJoinColumn(name = "id_administrateur")
public class Administrateur extends Utilisateur{


    @Enumerated(EnumType.STRING)
    private ProfilAdmin profilAdmin; // DEVELOPPER, DESIGNER, etc

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Coins> coins;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Badge> badge;


    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> notification;
  
    @OneToMany(mappedBy = "administrateur")
    private List<Domaine> domaine;

}

