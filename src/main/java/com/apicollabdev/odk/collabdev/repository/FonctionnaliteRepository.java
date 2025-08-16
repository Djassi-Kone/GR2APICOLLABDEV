package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalite, Long> {
    List<Fonctionnalite> findByProjetIdProjetAndStatutF(Long idProjet, StatutFonctionnalite statutF);

    // Récupérer toutes les fonctionnalités d’un projet par l’ID du projet
    List<Fonctionnalite> findByProjetIdProjet(Long idProjet);

    // Récupérer toutes les fonctionnalités créées par un gestionnaire
    List<Fonctionnalite> findByGestionnaire(Gestionnaire gestionnaire);
}
