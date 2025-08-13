package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {


        @Query("SELECT u FROM Utilisateur u WHERE u.email = :email AND u.password = :password")
        List<Utilisateur> findUsersByEmailAndPassword(@Param("email") String email,
                                                      @Param("password") String password);



}
