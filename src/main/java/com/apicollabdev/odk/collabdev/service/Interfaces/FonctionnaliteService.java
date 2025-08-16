package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;

import java.util.List;

public interface FonctionnaliteService {

    // Créer une fonctionnalité
    FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto, Long idGestionnaire);

    // Modifier une fonctionnalité existante
    FonctionnaliteDTO modifierFonctionnalite(Long id, FonctionnaliteDTO dto);

    // Supprimer une fonctionnalité par son ID
    void supprimerFonctionnalite(Long id);

    // Lister toutes les fonctionnalités d’un projet
    List<FonctionnaliteDTO> listerFonctionnalitesParProjet(Long idProjet);

    // Récupérer une fonctionnalité par son ID
    FonctionnaliteDTO getFonctionnaliteParId(Long id);

    // Lister toutes les fonctionnalités créées par un gestionnaire
    List<FonctionnaliteDTO> listerFonctionnalitesParGestionnaire(Long idGestionnaire);
}
