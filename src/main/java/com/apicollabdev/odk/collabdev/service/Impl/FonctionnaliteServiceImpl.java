package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.mapper.FonctionnaliteMapper;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ContributionRepository contributionRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Override
    public FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto, Long id_gestionnaire) {
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

        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        Projet projet = projetRepository.findByIdProjet(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec ID: " + dto.getProjetId()));
        f.setProjet(projet);

        // Gestion du fichier cahier de charge
        if (dto.getCahierDeCharge() != null && !dto.getCahierDeCharge().isEmpty()) {
            try {
                // Définir le dossier local de sauvegarde
                String dossier = "uploads/cahier_de_charge/";
                Files.createDirectories(Paths.get(dossier)); // crée le dossier si n'existe pas

                String nomFichier = System.currentTimeMillis() + "_" + dto.getCahierDeCharge().getOriginalFilename();
                Path chemin = Paths.get(dossier + nomFichier);

                // Sauvegarder le fichier
                dto.getCahierDeCharge().transferTo(chemin.toFile());

                // Enregistrer le chemin dans l'entité
                f.setCahierDeCharge(chemin.toString());

            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'upload du fichier cahier de charge.", e);
            }
        }

        Fonctionnalite saved = fonctionnaliteRepository.save(f);
        return FonctionnaliteMapper.toDTO(saved);
    }



    @Override
    public FonctionnaliteDTO modifierFonctionnalite(Long id, FonctionnaliteDTO dto) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));

        // Modification sécurisée
        if (dto.getPointFonctionnalite() != null) {
            try {
                f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Le pointFonctionnalite doit être un nombre valide.");
            }
        }

        if (dto.getStatut() != null) {
            try {
                f.setStatutF(StatutFonctionnalite.valueOf(dto.getStatut().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Statut de fonctionnalité invalide : " + dto.getStatut());
            }
        }

        if (dto.getFonctionnaliteNom() != null) f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        if (dto.getFonctionnaliteDescription() != null) f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        Fonctionnalite updated = fonctionnaliteRepository.save(f);
        return FonctionnaliteMapper.toDTO(updated);
    }


    @Override
    public void supprimerFonctionnalite(Long id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        fonctionnaliteRepository.delete(f);
    }

    @Override
    public List<FonctionnaliteDTO> listerFonctionnalitesParProjet(Long idProjet) {
        return fonctionnaliteRepository.findByProjetIdProjet(idProjet)
                .stream()
                .map(FonctionnaliteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FonctionnaliteDTO getFonctionnaliteParId(Long id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        return FonctionnaliteMapper.toDTO(f);
    }

    @Override
    public List<FonctionnaliteDTO> listerFonctionnalitesParGestionnaire(Long idGestionnaire) {
        Gestionnaire g = gestionnaireRepository.findById(idGestionnaire)
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable avec l'id : " + idGestionnaire));

        return fonctionnaliteRepository.findByGestionnaire(g)
                .stream()
                .map(FonctionnaliteMapper::toDTO)
                .collect(Collectors.toList());
    }

   /* @Override
    public List<FonctionnaliteDTO> listerFonctionnalitesParProjet(Long idProjet) {
        return fonctionnaliteRepository.findByProjetIdProjet(idProjet)
                .stream()
                .map(FonctionnaliteMapper::toDTO)
                .collect(Collectors.toList());
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

    */


}
