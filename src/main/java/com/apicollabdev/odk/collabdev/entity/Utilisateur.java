package com.apicollabdev.odk.collabdev.entity;

import com.apicollabdev.odk.collabdev.enums.RoleUtilisateur;
import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role_utilisateur" , discriminatorType = DiscriminatorType.STRING)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long id;
    private String email;
    private String password;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
