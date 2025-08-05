package com.apicollabdev.odk.collabdev.service.Impl;


import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.NotificationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.apicollabdev.odk.collabdev.Notification.NotificationFactory.creerNotificationDemandeParticipation;
import static com.apicollabdev.odk.collabdev.Notification.NotificationFactory.creerNotificationDemandeContribution;
import static com.apicollabdev.odk.collabdev.enums.TypeNotification.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;

    private Administrateur administrateur;


    @Override
    public Notification createNotification(Notification notification,long idAdmin) {
        Administrateur a = administrateurRepository.findById(idAdmin).orElseThrow(() -> new RuntimeException("Administrateur non trouv<UNK>"));
        notification.setDateNotification(notification.getDateNotification());
        notification.setDescription(notification.getDescription());
        notification.setTypeNotyf(notification.getTypeNotyf());
        notification.setEtat(notification.isEtat());
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
    public void notifierEtEnvoyer(TypeNotification type, Contributeur contributeur, Object... data) {
        Notification notification = null;

        switch (type) {
            case INSCRIPTION:
                notification = NotificationFactory.creerNotificationInscription();
                break;

            case GAINCOINS:
                notification = NotificationFactory.creerNotificationCoins((int) data[0]);
                break;

            case GAINBADGE:
                notification = NotificationFactory.creerNotificationBadge((String) data[0]);
                break;

            case PROPOSITIONIDEEPROJET:
                notification = NotificationFactory.creerNotificationIdeeProjet((String) data[0]);
                break;

            case COMMENTAIRE:
                notification = NotificationFactory.creerNotificationCommentaire(
                        contributeur.getNom(), (String) data[0]);
                break;

            case DEMANDECONTRIBUTION:
                Gestionnaire gestionnaire = (Gestionnaire) data[0];
                String titreProjet = (String) data[1];
                Contributeur contributeurs = (Contributeur) data[2];

                Notification notifGestionnaire = NotificationFactory.creerNotificationDemandeContribution(
                        gestionnaire, titreProjet, contributeurs);

                notification = NotificationFactory.creerNotificationDemandeParticipation(titreProjet);
                break;


            case DEMADEACCEPTEE:
                notification = NotificationFactory.creerNotificationDemandeAcceptee(
                        contributeur, (String) data[0]);
                break;

            case DEMANDEREJETEE:
                notification = NotificationFactory.creerNotificationDemandeRejetee(
                        contributeur, (String) data[0]);
                break;

            case DEMANDEGESTIONNAIRE:
                notification = NotificationFactory.creerNotificationDemandeGestionnaire(
                        contributeur, (String) data[0], (Gestionnaire) data[1]);
                break;

            case DEMANDEGESTIONNAIREACCEPTEE:
                notification = NotificationFactory.creerNotificationDemandeGestionnaireAcceptee(
                        (String) data[0]);
                break;

            case DEMANDEGESTIONNAIREREJETEE:
                notification = NotificationFactory.creerNotificationDemandeGestionnaireRejetee(
                        (String) data[0]);
                break;

            default:
                throw new IllegalArgumentException("Type de notification non géré : " + type);
        }

        // Envoie du mail
        emailService.sendEmail(
                contributeur.getEmail(),
                "Notification : " + type.name(),
                notification.getDescription()
        );


    }

    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void envoyerNotification( Contributeur contributeur, Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contributeur.getEmail());
        message.setSubject("Notification : " + notification.getTypeNotyf().name());
        message.setText(notification.getDescription());
        message.setFrom("group2apicollabdev@gmail.com");

        mailSender.send(message);
    }

    }


  /*  public List<Notification> getNotificationsByGestionnaire(Long idGestionnaire) {
        return notificationRepository.findByProjet_Gestionnaire_IdContributeur(idGestionnaire);
    }*/




