package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.NotificationRepository;
import com.apicollabdev.odk.collabdev.repository.RecevoirRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private RecevoirRepository recevoirRepository;

    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Notification createNotification(Notification notification, long idAdmin) {
        Administrateur a = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));
        notification.setDateNotification(LocalDateTime.now());
        notification.setAdministrateur(a);
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Envoie une notification par mail à un contributeur en fonction du type de notification
     */
    @Override
    public void notifierEtEnvoyer(TypeNotification type, Contributeur cible, Object... data) {
        Notification notification = null;

        switch (type) {
            case INSCRIPTION -> notification = NotificationFactory.creerNotificationInscription();

            case GAINCOINS -> {
                int coins = (int) data[0];
                notification = NotificationFactory.creerNotificationCoins(coins);
            }

            case GAINBADGE -> {
                String badge = (String) data[0];
                notification = NotificationFactory.creerNotificationBadge(badge);
            }

            case PROPOSITIONIDEEPROJET -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationIdeeProjet(titreProjet);
            }

            case COMMENTAIRE -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationCommentaire(cible.getNom(), titreProjet);
            }

            case DEMANDECONTRIBUTION -> {
                Gestionnaire gestionnaire = (Gestionnaire) data[0];
                String titreProjet = (String) data[1];
                Contributeur demandeur = (Contributeur) data[2];

                notification = NotificationFactory.creerNotificationDemandeContribution(gestionnaire, titreProjet, demandeur);
            }

            case DEMADEACCEPTEE -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationDemandeAcceptee(cible, titreProjet);
            }

            case DEMANDEREJETEE -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationDemandeRejetee(cible, titreProjet);
            }

            case FAIRECONTRIBUTION -> {
                Gestionnaire gestionnaire = (Gestionnaire) data[0];
                String titreProjet = (String) data[1];
                String nomFonctionnalite = (String) data[2];
                notification = NotificationFactory.creerNotificationContribution(gestionnaire, titreProjet, nomFonctionnalite);
            }

            case CONTRIBUTIONVALIDER -> {
                String titreProjet = (String) data[0];
                String nomFonctionnalite = (String) data[1];
                notification = NotificationFactory.creerNotificationContributionValider(cible, titreProjet, nomFonctionnalite);
            }

            case CONTRIBUTIONREJETEE -> {
                String titreProjet = (String) data[0];
                String nomFonctionnalite = (String) data[1];
                notification = NotificationFactory.creerNotificationContributionRejetee(cible, titreProjet, nomFonctionnalite);
            }

            case DEMANDEGESTIONNAIRE -> {
                Contributeur demandeur = (Contributeur) data[0];
                String titreProjet = (String) data[1];
                Gestionnaire gestionnaire = (Gestionnaire) data[2];
                notification = NotificationFactory.creerNotificationDemandeGestionnaire(demandeur, titreProjet, gestionnaire);
            }

            case DEMANDEGESTIONNAIREACCEPTEE -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationDemandeGestionnaireAcceptee(titreProjet);
            }

            case DEMANDEGESTIONNAIREREJETEE -> {
                String titreProjet = (String) data[0];
                notification = NotificationFactory.creerNotificationDemandeGestionnaireRejetee(titreProjet);
            }

            default -> throw new IllegalArgumentException("Type de notification non géré : " + type);
        }

        // Sauvegarde + envoi si notification générée
        if (notification != null) {
            saveAndDispatchNotification(notification, cible);

            emailService.sendEmail(
                    cible.getEmail(),
                    "Notification : " + type.name(),
                    notification.getDescription()
            );
        }
    }

    @Transactional
    public void saveAndDispatchNotification(Notification notification, Contributeur contributeur) {
        notification.setContributeur(contributeur);
        notification.setDateNotification(LocalDateTime.now());
        notification.setEtat(false);

        Notification savedNotification = notificationRepository.save(notification);

        if (contributeur.getId() == null) {
            throw new IllegalStateException("Impossible d'associer une notification à un contributeur sans ID !");
        }

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(savedNotification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoir.setLue(false);

        recevoirRepository.save(recevoir);
    }

    @Override
    public void envoyerNotification(Contributeur contributeur, Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contributeur.getEmail());
        message.setSubject("Notification : " + notification.getTypeNotyf().name());
        message.setText(notification.getDescription());
        message.setFrom("group2apicollabdev@gmail.com");

        mailSender.send(message);
    }
}
