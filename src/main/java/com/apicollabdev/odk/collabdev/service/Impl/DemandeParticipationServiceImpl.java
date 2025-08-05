package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutDemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.repository.DemandeParticipationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
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
            // Notification pour contributeur
            notificationServiceImpl.notifierEtEnvoyer(
                    TypeNotification.DEMANDEPARTICIPATION,
                    contributeur,
                    projet.getTitre()
            );

            // Notification pour gestionnaire
            if (gestionnaire != null) {
                notificationServiceImpl.notifierEtEnvoyer(
                        TypeNotification.DEMANDECONTRIBUTION,
                        gestionnaire,
                        "Demande de participation au projet : " + projet.getTitre()
                );
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la notification : " + e.getMessage());
        }

        // Emails
        try {
            // Email au contributeur
            String sujetContributeur = "Demande de participation envoyée";
            String messageContributeur = "Bonjour " + contributeur.getNom() + ",\n\n" +
                    "Votre demande de participation au projet \"" + projet.getTitre() + "\" a bien été envoyée.\n\n" +
                    "Merci de patienté !";

            emailService.sendEmail(contributeur.getEmail(), sujetContributeur, messageContributeur);

            // Email au gestionnaire
            if (gestionnaire != null) {
                String sujetGestionnaire = "Nouvelle demande de participation";
                String messageGestionnaire = "Bonjour " + gestionnaire.getNom() + ",\n\n" +
                        "Le contributeur " + contributeur.getNom() + " a demandé à participer au projet \"" + projet.getTitre() + "\".\n\n" +
                        "Veuillez vous rendre sur la plateforme pour valider ou refuser sa demande.";
                System.out.println("Email gestionnaire : " + (gestionnaire != null ? gestionnaire.getEmail() : "null"));
                System.out.println("Gestionnaire email = " + gestionnaire.getEmail());
                System.out.println("Gestionnaire projet : " + (projet.getGestionnaire() != null ? projet.getGestionnaire().getNom() : "Aucun"));


                emailService.sendEmail(gestionnaire.getEmail(), sujetGestionnaire, messageGestionnaire);
            }

        }  catch (Exception e) {
        System.err.println("Erreur lors de l'envoi d'email au gestionnaire :");
        e.printStackTrace(); // pour avoir le détail complet de l'erreur
    }


        return saved;
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
