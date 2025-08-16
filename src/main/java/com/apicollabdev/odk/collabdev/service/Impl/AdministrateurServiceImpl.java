package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.*;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.AdministrateurService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    @Autowired
    private  BadgeRepository badgeRepository;
    @Autowired
    private  CoinsRepository coinsRepository;
    @Autowired
    private  AdministrateurRepository administrateurRepository;
    @Autowired
    private IdeeProjetRepository ideeProjetRepository;
    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private GestionnaireRepository gestionnaireRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private IdeeProjetServiceImpl ideeProjetServiceImpl;
    @Autowired
    private DemandeParticipationRepository demandeParticipationRepository;
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;


    //creation de badges
    public Badge creerBadges(Badge badge, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloquer pour les contributeurs");
        }
        return badgeRepository.save(badge);
    }

    //Mise a jours des badges de badges
    public Badge updateBadge(int id,Badge badge, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloquer pour les contributeurs");
        }
        Badge badge1 = new Badge();
        badge1.setIdBadge(badge.getIdBadge());
        badge1.setNom(badge.getNom());
        badge1.setDescription(badge.getDescription());
        badge1.setImage(badge.getImage());
        badge1.setNombre(badge.getNombre());
        return badgeRepository.save(badge);
    }

    //Suppression des badges
    public void deleteBadge(int idBadge,Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
         Badge badge = badgeRepository.deleteBadgeByIdBadge(idBadge).
                 orElseThrow(()-> new RuntimeException("Badge introuvable"));

         badgeRepository.delete(badge);
    }

    public List<Badge> getAllBadges(Administrateur administrateur) {

        return  badgeRepository.findAll();

    }

    // Pour les coins
    //Creation des coins
    public Coins CreerCoin(Coins coins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }

        return coinsRepository.save(coins);
    }

    //Suppressions des coins
    public void SupprimerCoins(int idCoins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
        Coins coins = coinsRepository.deleteCoinsByIdCoin(idCoins).orElseThrow(()-> new RuntimeException("Coins introuvable"));

         coinsRepository.delete(coins);
    }

    //Mise a jours des coins

    public Coins updateCoins(int id,Coins coins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
        Coins coins1 = new Coins();
        coins1.setIdCoin(coins.getIdCoin());
        coins1.setNombreCoins(coins.getNombreCoins());
        coins1.setAdministrateur(administrateur);
        return coinsRepository.save(coins);
    }

    public List<Coins> getAllCoins(Administrateur administrateur) {
        return  coinsRepository.findAll();

    }


    @Override
    public Administrateur administrateur(Administrateur administrateur) {
        return null;
    }

    @Override
    public List<Administrateur> getAllAdministrateurs() {
        return List.of();
    }

    @Override
    public Administrateur getById(Long idAdmin) {
        return null;
    }

    @Override
    public void deleteById(Long idAdmin) {

    }


    @Transactional
    public Projet accepterDemandeGestionnairePourIdee(Long idDemande, Long idAdmin) {
        // Récupération de la demande
        DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        if (demande.getStatutDemandeParticipation() != StatutDemandeParticipation.EN_ATTENTE) {
            throw new RuntimeException("Demande déjà traitée");
        }

        // Mettre à jour le statut de la demande
        demande.setStatutDemandeParticipation(StatutDemandeParticipation.ACCEPTEE);
        demandeParticipationRepository.save(demande);

        IdeeProjet idee = demande.getIdeeProjet();
        if (idee == null) {
            throw new RuntimeException("Cette demande n'est pas liée à une idée de projet");
        }

        // Mettre à jour le statut de l'idée
        idee.setStatut(StatutIdee.ACCEPTEE);
        IdeeProjet savedIdee = ideeProjetRepository.save(idee);

        // Créer le gestionnaire à partir du contributeur
        Contributeur contrib = demande.getContributeur();
        Gestionnaire gestionnaire = new Gestionnaire();
        gestionnaire.setNom(contrib.getNom());
        gestionnaire.setPrenom(contrib.getPrenom());
        gestionnaire.setEmail(contrib.getEmail());
        gestionnaire.setPassword(contrib.getPassword());
        gestionnaire.setProfil(contrib.getProfil());
        gestionnaire.setNiveau(contrib.getNiveau());
        gestionnaire.setParent(contrib.getId());
        Gestionnaire savedGestionnaire = gestionnaireRepository.save(gestionnaire);

        // Créer le projet à partir de l'idée
        Projet projet = new Projet();
        projet.setTitre(idee.getTitre());
        projet.setDescription(idee.getDescription());
        projet.setDateCreation(LocalDateTime.now());
        projet.setStatut(StatutProjet.EN_COURS);
        projet.setGestionnaire(savedGestionnaire);
        projet.setNewGestionnaire(contrib.getId());
        projet.setIdeeProjet(savedIdee);

        // Assigner l'administrateur
        Administrateur admin = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
        projet.setAdministrateur(admin);

        Projet savedProjet = projetRepository.save(projet);

        // Notification
        try {
            notificationServiceImpl.notifierEtEnvoyer(
                    TypeNotification.DEMANDEGESTIONNAIREACCEPTEE,
                    contrib,
                    "Votre demande pour devenir gestionnaire du projet \"" + savedProjet.getTitre() + "\" a été acceptée."
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }

        return savedProjet;
    }


}
