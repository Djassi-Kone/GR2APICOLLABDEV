package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutDemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeDemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.IdeeProjetRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.repository.DemandeParticipationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DemandeParticipationServiceImpl implements DemandeParticipationService {

    @Autowired
    private DemandeParticipationRepository demandeParticipationRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private IdeeProjetRepository ideeProjetRepository;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private EmailService emailService;

    @Override
    public DemandeParticipation createDemandeParticipation(Long idProjet, Long idContributeur, String description) {
        // Récupération du projet
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'id: " + idProjet));

        // Récupération du contributeur
        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé avec l'id: " + idContributeur));

        // Récupération du gestionnaire du projet
        Gestionnaire gestionnaire = projet.getGestionnaire();

        // Création de la demande
        DemandeParticipation demandeParticipation = new DemandeParticipation();
        demandeParticipation.setProjet(projet);
        demandeParticipation.setContributeur(contributeur);
        demandeParticipation.setDescription(description);
        demandeParticipation.setDatedemande(LocalDateTime.now());
        demandeParticipation.setStatutDemandeParticipation(StatutDemandeParticipation.EN_ATTENTE);

        // Sauvegarde
        DemandeParticipation saved = demandeParticipationRepository.save(demandeParticipation);

        // Notifications
        try {
            // Message personnalisé pour contributeur
            String messageContribNotif = "Vous avez fait une demande de participation sur le projet : " + projet.getTitre();

            // Notification contributeur
            notificationServiceImpl.notifierEtEnvoyer(
                    TypeNotification.DEMANDEPARTICIPATION,
                    contributeur,
                    messageContribNotif
            );

            // Message personnalisé pour gestionnaire
            if (gestionnaire != null) {
                String messageGestNotif = "Le contributeur " + contributeur.getNom() +
                        " a fait une demande de participation sur le projet : " + projet.getTitre() +
                        ". Veuillez valider ou rejeter sa demande.";

                notificationServiceImpl.notifierEtEnvoyer(
                        TypeNotification.DEMANDECONTRIBUTION,
                        gestionnaire,
                        messageGestNotif
                );
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }

        // Emails
      /*  try {
            // Email au contributeur
            String sujetContributeur = "Demande de participation envoyée";
            String messageContributeur = "Bonjour " + contributeur.getNom() + ",\n\n" +
                    "Vous avez fait une demande de participation sur le projet \"" + projet.getTitre() + "\".\n\n" +
                    "Merci de patienter en attendant une réponse.";

            emailService.sendEmail(contributeur.getEmail(), sujetContributeur, messageContributeur);

            // Email au gestionnaire
            if (gestionnaire != null) {
                String sujetGestionnaire = "Nouvelle demande de participation";
                String messageGestionnaire = "Bonjour " + gestionnaire.getNom() + ",\n\n" +
                        "Le contributeur " + contributeur.getNom() + " a demandé à participer au projet \"" + projet.getTitre() + "\".\n\n" +
                        "Veuillez vous rendre sur la plateforme pour valider ou refuser sa demande.";

                emailService.sendEmail(gestionnaire.getEmail(), sujetGestionnaire, messageGestionnaire);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi d'email :");
            e.printStackTrace();
        }*/

        return saved;
    }
 /*
    @Override
    @Transactional
    public DemandeParticipation accepterDemandeParticipation(Long idDemande) {
        DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatutDemandeParticipation(StatutDemandeParticipation.ACCEPTEE);
        demandeParticipationRepository.save(demande);

        IdeeProjet idee = demande.getIdeeProjet();
        Contributeur contributeur = demande.getContributeur();

        // Notifier le demandeur
        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.DEMADEACCEPTEE,
                contributeur,
                idee.getTitre()
        );

        Notification notif = NotificationFactory.creerNotificationDemandeGestionnaireAcceptee(
                idee.getTitre()
        );
        notificationServiceImpl.createNotification(notif, contributeur.getId());


        return demande;
    }*/
 @Transactional
 public DemandeParticipation accepterDemandeParticipation(Long idDemande) {
     DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
             .orElseThrow(() -> new RuntimeException("Demande introuvable"));

     demande.setStatutDemandeParticipation(StatutDemandeParticipation.ACCEPTEE);
     demandeParticipationRepository.save(demande);

     IdeeProjet idee = demande.getIdeeProjet();
     Contributeur contributeur = demande.getContributeur();

     try {
         notificationServiceImpl.notifierEtEnvoyer(
                 TypeNotification.DEMADEACCEPTEE,
                 contributeur,
                 "Votre demande pour l'idée \"" + idee.getTitre() + "\" a été acceptée."
         );
     } catch (Exception e) {
         System.err.println("Erreur lors de la notification : " + e.getMessage());
     }

     return demande;
 }


    @Override
    @Transactional
    public DemandeParticipation rejeterDemandeParticipation(Long idDemande) {
        DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatutDemandeParticipation(StatutDemandeParticipation.REFUSEE);
        demandeParticipationRepository.save(demande);

        Contributeur contributeur = demande.getContributeur();
        IdeeProjet idee = demande.getIdeeProjet();

        // Notifier le demandeur
        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.DEMANDEREJETEE,
                contributeur,
                idee.getTitre()
        );

        Notification notif = NotificationFactory.creerNotificationDemandeGestionnaireRejetee(
                idee.getTitre()
        );
        notificationServiceImpl.createNotification(notif, contributeur.getId());
        return demande;
    }

 /*
    @Override
    @Transactional
    public DemandeParticipation faireDemandeGestionnaire(Long idIdeeProjet, Long idContributeur) {
        IdeeProjet idee = ideeProjetRepository.findById(idIdeeProjet)
                .orElseThrow(() -> new RuntimeException("Idée de projet introuvable"));

        if (idee.getProjet() != null) {
            throw new RuntimeException("Ce projet a déjà un gestionnaire.");
        }

        if (idee.getProjet() != null && idee.getProjet().getGestionnaire() != null) {
            throw new RuntimeException("Ce projet a déjà un gestionnaire.");
        }

        Contributeur contributeurs = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        DemandeParticipation demande = new DemandeParticipation();
        demande.setContributeur(contributeurs);
        demande.setIdeeProjet(idee);
        demande.setStatutDemandeParticipation(StatutDemandeParticipation.EN_ATTENTE);
        demande.setTypeDemandeParticipationemande(TypeDemandeParticipation.GESTIONNAIRE);
        demande.setDatedemande(LocalDateTime.now());
        demandeParticipationRepository.save(demande);

        // Notifier le créateur de l'idée
       Contributeur demandeur = idee.getContributeur();

        Contributeur createurIdee = idee.getContributeur();
        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.DEMANDEGESTIONNAIRE,
                demandeur,
                idee.getTitre(),
                createurIdee
        );

        // Enregistrer la notification dans la base
        Notification notif = NotificationFactory.creerNotificationDemandeGestionnaire(
                contributeurs, idee.getTitre(), null
        );
        notificationServiceImpl.createNotification(notif, demandeur.getId());
        return demande;

    }*/
 @Transactional
 public DemandeParticipation faireDemandeGestionnaire(Long idIdeeProjet, Long idContributeur) {
     IdeeProjet idee = ideeProjetRepository.findById(idIdeeProjet)
             .orElseThrow(() -> new RuntimeException("Idée de projet introuvable"));

     if (idee.getProjet() != null && idee.getProjet().getGestionnaire() != null) {
         throw new RuntimeException("Ce projet a déjà un gestionnaire.");
     }

     Contributeur contributeur = contributeurRepository.findById(idContributeur)
             .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

     DemandeParticipation demande = new DemandeParticipation();
     demande.setContributeur(contributeur);
     demande.setIdeeProjet(idee);
     demande.setStatutDemandeParticipation(StatutDemandeParticipation.EN_ATTENTE);
     demande.setTypeDemandeParticipationemande(TypeDemandeParticipation.GESTIONNAIRE);
     demande.setDatedemande(LocalDateTime.now());
     demandeParticipationRepository.save(demande);

     // Notifier le créateur de l'idée
     Contributeur createurIdee = idee.getContributeur();

     try {
         notificationServiceImpl.notifierEtEnvoyer(
                 TypeNotification.DEMANDEGESTIONNAIRE,
                 createurIdee,
                 "Le contributeur \"" + contributeur.getNom() + "\" a fait une demande pour gérer l'idée \"" + idee.getTitre() + "\"."
         );
     } catch (Exception e) {
         System.err.println("Erreur lors de la notification : " + e.getMessage());
     }

     return demande;
 }


    @Override
    @Transactional
    public DemandeParticipation accepterDemandeGestionnaire(Long idDemande) {
        DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatutDemandeParticipation(StatutDemandeParticipation.ACCEPTEE);
        demandeParticipationRepository.save(demande);

        IdeeProjet idee = demande.getIdeeProjet();
        Contributeur contributeur = demande.getContributeur();

        // Transformer l'idée en projet
        Projet projet = new Projet();
        projet.setTitre(idee.getTitre());
        projet.setDescription(idee.getDescription());
        projet.setDateCreation(LocalDateTime.now());
       // projet.se(contributeur);
        projet.setIdeeProjet(idee);
        projetRepository.save(projet);

        // Marquer l'idée comme leguer
        idee.setProjet(projet);
        ideeProjetRepository.save(idee);

        // Notifier le demandeur
        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.DEMANDEGESTIONNAIREACCEPTEE,
                contributeur,
                idee.getTitre()
        );

        Notification notif = NotificationFactory.creerNotificationDemandeGestionnaireAcceptee(
                idee.getTitre()
        );
        notificationServiceImpl.createNotification(notif, contributeur.getId());
        return demande;
    }

    @Override
    @Transactional
    public DemandeParticipation rejeterDemandeGestionnaire(Long idDemande) {
        DemandeParticipation demande = demandeParticipationRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatutDemandeParticipation(StatutDemandeParticipation.REFUSEE);
        demandeParticipationRepository.save(demande);

        Contributeur contributeur = demande.getContributeur();
        IdeeProjet idee = demande.getIdeeProjet();

        // Notifier le demandeur
        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.DEMANDEGESTIONNAIREREJETEE,
                contributeur,
                idee.getTitre()
        );

        Notification notif = NotificationFactory.creerNotificationDemandeGestionnaireRejetee(
                idee.getTitre()
        );
        notificationServiceImpl.createNotification(notif, contributeur.getId());
        return demande;
    }


    @Override
    public List<DemandeParticipation> getAllDemandeParticipation() {
        return demandeParticipationRepository.findAll();
    }

    @Override
    public DemandeParticipation getById(Long id) {
        return demandeParticipationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!demandeParticipationRepository.existsById(id)) {
            throw new RuntimeException("Le DemandeParticipation avec l'id " + id + " n'existe pas.");
        }
        demandeParticipationRepository.deleteById(id);
    }
}
