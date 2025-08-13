package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.mapper.FonctionnaliteMapper;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /* @Override
    public FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto, Long id_gestionnaire) {
        Gestionnaire g = gestionnaireRepository.findById(id_gestionnaire)
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable avec l'id : " + id_gestionnaire));

        Fonctionnalite f = new Fonctionnalite();
        f.setGestionnaire(g);

        // 1. Gestion des points
        try {
            f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Le pointFonctionnalite doit être un nombre valide.");
        }

        // 2. Enum Statut sécurisé
        try {
            f.setStatutF(StatutFonctionnalite.valueOf(dto.getStatut().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut de fonctionnalité invalide : " + dto.getStatut());
        }

        // 3. Nom et description
        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        // 4. Association avec projet
        Projet projet = projetRepository.findByIdProjet(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec ID: " + dto.getProjetId()));
        f.setProjet(projet);

        // 5. Association avec gestionnaire (OBLIGATOIRE pour éviter l'erreur SQL)
        Gestionnaire gestionnaire = gestionnaireRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable avec ID: " + dto.getId()));
        f.setGestionnaire(gestionnaire);

        // 6. Sauvegarde
        Fonctionnalite saved = fonctionnaliteRepository.save(f);

        return FonctionnaliteMapper.toDTO(saved);
    }*/
    @Override
    public FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto, Long id_gestionnaire) {
        // Récupération du gestionnaire avec l'ID de l'URL
        Gestionnaire gestionnaire = gestionnaireRepository.findById(id_gestionnaire)
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable avec l'id : " + id_gestionnaire));

        Fonctionnalite f = new Fonctionnalite();
        f.setGestionnaire(gestionnaire);

        // Points
        try {
            f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Le pointFonctionnalite doit être un nombre valide.");
        }

        // Statut
        try {
            f.setStatutF(StatutFonctionnalite.valueOf(dto.getStatut().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut de fonctionnalité invalide : " + dto.getStatut());
        }

        // Nom + description
        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        // Projet
        Projet projet = projetRepository.findByIdProjet(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec ID: " + dto.getProjetId()));
        f.setProjet(projet);

        // Sauvegarde
        Fonctionnalite saved = fonctionnaliteRepository.save(f);
        return FonctionnaliteMapper.toDTO(saved);
    }



    @Override
    public List<FonctionnaliteDTO> ListerFonctionnalite() {
        return fonctionnaliteRepository.findAll()
                .stream()
                .map(FonctionnaliteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FonctionnaliteDTO ListeFonctionnaliteParId(Long id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        return FonctionnaliteMapper.toDTO(f);
    }

    @Override
    public void supprimerFonctionnalite(Long id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        fonctionnaliteRepository.delete(f);
    }

    @Override
    public FonctionnaliteDTO modifierFonctionnalite(Long id, FonctionnaliteDTO dto) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));

        f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
        f.setStatutF(StatutFonctionnalite.valueOf(dto.getStatut()));
        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        Fonctionnalite updated = fonctionnaliteRepository.save(f);
        return FonctionnaliteMapper.toDTO(updated);
    }
}
