package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalite, Long> {
    List<Fonctionnalite> findByProjetIdProjetAndStatutF(Long idProjet, StatutFonctionnalite statutF);
    List<Fonctionnalite> findByProjetIdProjet(Long idProjet);

}
