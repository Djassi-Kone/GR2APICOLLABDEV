package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.IdeeProjetService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdeeProjetServiceImpl implements IdeeProjetService {

    @Autowired
    private IdeeProjetRepository ideeProjetRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

   // @Override
   // @Transactional
    /*public IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeur, long idDomaine) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur non trouvé"));

        Domaine d = domaineRepository.findById(idDomaine)
                .orElseThrow(() -> new RessourceNotFoundException("Domaine non trouvé"));

        IdeeProjet ideeProjet = new IdeeProjet();
        ideeProjet.setTitre(dto.getTitre());
        ideeProjet.setDescription(dto.getDescription());
        ideeProjet.setNiveau(dto.getNiveau());
        ideeProjet.setLeguer(dto.isLeguer());
        ideeProjet.setContributeur(c);
        ideeProjet.setDomaine(d);
        ideeProjet.setDateCreation(LocalDateTime.now());
        ideeProjet.setStatut(StatutIdee.PROPOSEE);

        IdeeProjet saved = ideeProjetRepository.save(ideeProjet);

        if (!dto.isLeguer()) {
            // On transfère et transforme l'idée léguée en projet pour ce contributeur
            Projet projetCree = transfererEtTransformerIdeeLeguee(saved.getIdIdeeProjet(), idContributeur);
            // Recharge l'idée projet à jour
            saved = ideeProjetRepository.findById(saved.getIdIdeeProjet()).orElse(saved);
        }*/


    @Override
    @Transactional
    public IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeurOriginal, long idDomaine) {
        Contributeur contributeurOriginal = contributeurRepository.findById(idContributeurOriginal)
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur non trouvé"));

        Domaine domaine = domaineRepository.findById(idDomaine)
                .orElseThrow(() -> new RessourceNotFoundException("Domaine non trouvé"));

        IdeeProjet ideeProjet = new IdeeProjet();
        ideeProjet.setTitre(dto.getTitre());
        ideeProjet.setDescription(dto.getDescription());
        ideeProjet.setNiveau(dto.getNiveau());
        ideeProjet.setLeguer(dto.isLeguer());
        ideeProjet.setDomaine(domaine);
        ideeProjet.setDateCreation(LocalDateTime.now());

        if (!dto.isLeguer()) {
            // Création d'un nouveau contributeur (clone)
            Contributeur nouveauContributeur = new Contributeur();
            nouveauContributeur.setNom(contributeurOriginal.getNom());
            nouveauContributeur.setPrenom(contributeurOriginal.getPrenom());
            nouveauContributeur.setEmail(contributeurOriginal.getEmail()); // Génère un email unique ici !
            nouveauContributeur.setPassword(contributeurOriginal.getPassword());
            nouveauContributeur.setActive(true);
            nouveauContributeur.setProfil(contributeurOriginal.getProfil());
            nouveauContributeur.setNiveau(contributeurOriginal.getNiveau());
            // Initialiser autres champs nécessaires...

            Contributeur contributeurCree = contributeurRepository.save(nouveauContributeur);

            ideeProjet.setContributeur(contributeurCree);
            ideeProjet.setStatut(StatutIdee.ACCEPTEE);

            // Créer gestionnaire à partir du nouveau contributeur
            Gestionnaire gestionnaire = new Gestionnaire();
            gestionnaire.setNom(contributeurCree.getNom());
            gestionnaire.setPrenom(contributeurCree.getPrenom());
            gestionnaire.setEmail(contributeurCree.getEmail());
            gestionnaire.setPassword(contributeurCree.getPassword());
            gestionnaire.setActive(true);
            gestionnaire.setProfil(contributeurCree.getProfil());
            gestionnaire.setNiveau(contributeurCree.getNiveau());
            gestionnaire.setVersion(0L);

            Gestionnaire gestionnaireCree = gestionnaireRepository.save(gestionnaire);

            // Créer projet lié
            Projet projet = new Projet();
            projet.setTitre(dto.getTitre());
            projet.setDescription(dto.getDescription());
            projet.setDateCreation(LocalDateTime.now());
            projet.setStatut(StatutProjet.PAS_DEBUTER);
            projet.setCahierDeCharge(false);
            projet.setGestionnaire(gestionnaireCree);
            projet.setDomaine(domaine);

            Projet projetCree = projetRepository.save(projet);

            ideeProjet.setProjet(projetCree);

        } else {
            // Si leguer == true, on garde le contributeur original
            ideeProjet.setContributeur(contributeurOriginal);
            ideeProjet.setStatut(StatutIdee.PROPOSEE);
        }

        IdeeProjet saved = ideeProjetRepository.save(ideeProjet);

        try {
            notificationServiceImpl.notifierEtEnvoyer(
                    dto.isLeguer() ? TypeNotification.PROPOSITIONIDEEPROJET : TypeNotification.CREATIONPROJET,
                    saved.getContributeur(),
                    dto.isLeguer()
                            ? "Votre idée \"" + saved.getTitre() + "\" a été enregistrée avec succès"
                            : "Votre idée \"" + saved.getTitre() + "\" a été automatiquement transformée en projet"
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }

        return saved;
    }

    @Override
    public List<IdeeProjet> getAllIdeeProjet() {
        return ideeProjetRepository.findAll();
    }

    @Override
    public IdeeProjet getById(Long id) {
        return ideeProjetRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("IdeeProjet non trouvé avec l'id : " + id));
    }

    @Override
    public IdeeProjet updateIdeeProjet(Long id, IdeeProjet updatedIdeeProjet) {
        IdeeProjet existingIdeeProjet = ideeProjetRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("IdeeProjet non trouvée avec l'id : " + id));

        existingIdeeProjet.setTitre(updatedIdeeProjet.getTitre());
        existingIdeeProjet.setDescription(updatedIdeeProjet.getDescription());
        existingIdeeProjet.setDomaine(updatedIdeeProjet.getDomaine());
        existingIdeeProjet.setNiveau(updatedIdeeProjet.getNiveau());
        existingIdeeProjet.setDateCreation(updatedIdeeProjet.getDateCreation());

        return ideeProjetRepository.save(existingIdeeProjet);
    }

    @Override
    public void deleteById(Long id) {
        if (!ideeProjetRepository.existsById(id)) {
            throw new RessourceNotFoundException("Le IdeeProjet avec l'id " + id + " n'existe pas.");
        }
        ideeProjetRepository.deleteById(id);
    }

    @Transactional
    public Projet transformerIdeeEnProjet(Long idIdeeProjet) {
        IdeeProjet ideeProjet = ideeProjetRepository.findById(idIdeeProjet)
                .orElseThrow(() -> new RessourceNotFoundException("Idée de projet non trouvée"));

        Contributeur contributeur = ideeProjet.getContributeur();
        if (contributeur == null) {
            throw new RuntimeException("Aucun contributeur associé à cette idée");
        }

        if (Boolean.TRUE.equals(ideeProjet.isLeguer())) {
            throw new RuntimeException("Ce contributeur a légué l'idée et ne peut pas devenir gestionnaire.");
        }

        Gestionnaire gestionnaire = gestionnaireRepository.findById(contributeur.getId())
                .orElseGet(() -> {
                    Gestionnaire g = new Gestionnaire();
                    g.setNom(contributeur.getNom());
                    g.setPrenom(contributeur.getPrenom());
                    g.setEmail(contributeur.getEmail());
                    g.setNiveau(contributeur.getNiveau());
                    g.setProfil(contributeur.getProfil());
                    g.setPassword(contributeur.getPassword());
                    // g.setVersion(0L); // si besoin
                    return gestionnaireRepository.save(g);
                });

        Projet projet = new Projet();
        projet.setTitre(ideeProjet.getTitre());
        projet.setDescription(ideeProjet.getDescription());
        projet.setDateCreation(LocalDateTime.now());
        projet.setStatut(StatutProjet.EN_COURS);
        projet.setCahierDeCharge(false);
        projet.setGestionnaire(gestionnaire);
        projet.setDomaine(ideeProjet.getDomaine());

        Projet projetCree = projetRepository.save(projet);

        ideeProjet.setProjet(projetCree);
        ideeProjet.setStatut(StatutIdee.ACCEPTEE);
        ideeProjetRepository.save(ideeProjet);

        return projetCree;
    }

    @Transactional
    public Projet transfererEtTransformerIdeeLeguee(Long idIdeeProjet, Long idNouveauContributeur) {
        IdeeProjet ideeProjet = ideeProjetRepository.findById(idIdeeProjet)
                .orElseThrow(() -> new RessourceNotFoundException("Idée de projet non trouvée"));

        if (!Boolean.FALSE.equals(ideeProjet.isLeguer())) {
            throw new RuntimeException("Cette idée n'est pas léguée, transfert impossible");
        }

        Contributeur nouveauContributeur = contributeurRepository.findById(idNouveauContributeur)
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur à affecter non trouvé"));

        ideeProjet.setContributeur(nouveauContributeur);
        ideeProjet.setLeguer(false);

        Gestionnaire gestionnaire = gestionnaireRepository.findById(nouveauContributeur.getId())
                .orElseGet(() -> {
                    Gestionnaire g = new Gestionnaire();
                    g.setId(nouveauContributeur.getId());
                    g.setNom(nouveauContributeur.getNom());
                    g.setPrenom(nouveauContributeur.getPrenom());
                    g.setEmail(nouveauContributeur.getEmail());
                    g.setNiveau(nouveauContributeur.getNiveau());
                    g.setProfil(nouveauContributeur.getProfil());
                    g.setPassword(nouveauContributeur.getPassword());
                    g.setVersion(0L);
                    return gestionnaireRepository.save(g);
                });

        Projet projet = new Projet();
        projet.setTitre(ideeProjet.getTitre());
        projet.setDescription(ideeProjet.getDescription());
        projet.setDateCreation(LocalDateTime.now());
        projet.setStatut(StatutProjet.EN_COURS);
        projet.setCahierDeCharge(false);
        projet.setDomaine(ideeProjet.getDomaine());
        projet.setGestionnaire(gestionnaire);

        Projet projetCree = projetRepository.save(projet);

        ideeProjet.setProjet(projetCree);
        ideeProjet.setStatut(StatutIdee.ACCEPTEE);
        ideeProjetRepository.save(ideeProjet);

        return projetCree;
    }

}
