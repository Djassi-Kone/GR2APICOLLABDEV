package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.IdeeProjetService;
import com.apicollabdev.odk.collabdev.repository.IdeeProjetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdeeProjetServiceImpl implements IdeeProjetService {

    @Autowired
    private  IdeeProjetRepository ideeProjetRepository;

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


    @Override
    @Transactional
    public IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeur, long idDomaine) {
        // 1. Chargement des entités avec verrouillage pessimiste pour éviter les conflits
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur non trouvé"));

        Domaine d = domaineRepository.findById(idDomaine)
                .orElseThrow(() -> new RessourceNotFoundException("Domaine non trouvé"));

        // 2. Création de l'idée
        IdeeProjet ideeProjet = new IdeeProjet();
        ideeProjet.setTitre(dto.getTitre());
        ideeProjet.setDescription(dto.getDescription());
        ideeProjet.setNiveau(dto.getNiveau());
        ideeProjet.setLeguer(dto.isLeguer());
        ideeProjet.setContributeur(c);
        ideeProjet.setDomaine(d);
        ideeProjet.setDateCreation(LocalDateTime.now());
        ideeProjet.setStatut(StatutIdee.PROPOSEE);

        if (!dto.isLeguer()) {
            // 3. Gestion de la promotion en gestionnaire
            Gestionnaire gestionnaire = gestionnaireRepository.findById(c.getId())
                    .orElseGet(() -> {
                        // Création d'un nouveau gestionnaire si inexistant
                        Gestionnaire newGestionnaire = new Gestionnaire();
                        newGestionnaire.setId(c.getId()); // Même ID que le contributeur
                        newGestionnaire.setNom(c.getNom());
                        newGestionnaire.setPrenom(c.getPrenom());
                        newGestionnaire.setEmail(c.getEmail());
                        newGestionnaire.setPassword(c.getPassword());
                        newGestionnaire.setActive(c.isActive());
                        newGestionnaire.setProfil(c.getProfil());
                        newGestionnaire.setNiveau(c.getNiveau());
                        // Initialisation explicite des champs de version
                        newGestionnaire.setVersion(0L);
                        return gestionnaireRepository.save(newGestionnaire);
                    });

            // 4. Création du projet
            Projet projet = new Projet();
            projet.setTitre(dto.getTitre());
            projet.setDescription(dto.getDescription());
            projet.setDateCreation(LocalDateTime.now());
            projet.setStatut(StatutProjet.PAS_DEBUTER);
            projet.setCahierDeCharge(false);
            projet.setGestionnaire(gestionnaire);
            projet.setDomaine(d);

            Projet projetCree = projetRepository.save(projet);
            ideeProjet.setProjet(projetCree);
            ideeProjet.setStatut(StatutIdee.ACCEPTEE);
        }

        // 5. Sauvegarde finale
        IdeeProjet saved = ideeProjetRepository.save(ideeProjet);

        // 6. Notification (asynchrone pour éviter les problèmes de transaction)
        try {
            String message = dto.isLeguer()
                    ? "Votre idée \"" + saved.getTitre() + "\" a été enregistrée avec succès"
                    : "Votre idée \"" + saved.getTitre() + "\" a été transformée en projet";

            notificationServiceImpl.notifierEtEnvoyer(
                    dto.isLeguer() ? TypeNotification.PROPOSITIONIDEEPROJET : TypeNotification.CREATIONPROJET,
                    c,
                    message
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }

        return saved;
    }

    // Autres méthodes...
/*

        // Envoi de notification + mail
        try {
            if (!dto.isLeguer()) {
                notificationServiceImpl.notifierEtEnvoyer(
                        TypeNotification.CREATIONPROJET,
                        c, // contributeur devenir gestionnaire
                        "Votre idée \"" + saved.getTitre() + "\" a été automatiquement transformée en projet"
                );
            } else {
                notificationServiceImpl.notifierEtEnvoyer(
                        TypeNotification.PROPOSITIONIDEEPROJET,
                        c,
                        "Votre idée \"" + saved.getTitre() + "\" a été enregistrée avec succès"
                );
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }
        return ideeProjet;*/
    //}


        @Override
    public List<IdeeProjet> getAllIdeeProjet() {
        return ideeProjetRepository.findAll();
    }

    @Override
    public IdeeProjet getById(Long id) {
        return ideeProjetRepository.findById((id))
                .orElseThrow(() -> new RuntimeException("IdeeProjet non trouvé avec l'id : " + id));

    }

    @Override
    public IdeeProjet updateIdeeProjet(Long id, IdeeProjet updatedIdeeProjet) {
        IdeeProjet existingIdeeProjet = ideeProjetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("IdeeProjet non trouvée avec l'id : " + id));

        // Mise à jour des champs (ajuste les champs selon ton entité)
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
            throw new RuntimeException("Le IdeeProjet avec l'id " + id + " n'existe pas.");
        }
        ideeProjetRepository.deleteById(id);
    }

    @Transactional
    public Projet transformerIdeeEnProjet(Long idIdeeProjet) {
        IdeeProjet ideeProjet = ideeProjetRepository.findById(idIdeeProjet)
                .orElseThrow(() -> new RuntimeException("Idée de projet non trouvée"));

        Contributeur contributeur = ideeProjet.getContributeur();
        if (contributeur == null) {
            throw new RuntimeException("Aucun contributeur associé à cette idée");
        }

        if (Boolean.TRUE.equals(ideeProjet.isLeguer())) {
            throw new RuntimeException("Ce contributeur a légué l'idée et ne peut pas devenir gestionnaire.");
        }

        // Vérifie si le contributeur est déjà gestionnaire
        Gestionnaire gestionnaire = entityManager.find(Gestionnaire.class, contributeur.getId());

        if (gestionnaire == null) {
            // Insère automatiquement une ligne dans la table gestionnaire avec le même id
            entityManager.createNativeQuery("INSERT INTO gestionnaire (id_gestionnaire) VALUES (:id)")
                    .setParameter("id", contributeur.getId())
                    .executeUpdate();

            // Recharge l'entité en tant que Gestionnaire
            gestionnaire = entityManager.find(Gestionnaire.class, contributeur.getId());
        }

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




}
