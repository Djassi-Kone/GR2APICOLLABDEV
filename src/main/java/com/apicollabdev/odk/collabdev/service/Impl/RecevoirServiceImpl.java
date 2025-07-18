package com.apicollabdev.odk.collabdev.service.Impl;


import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.entity.Recevoir;
import com.apicollabdev.odk.collabdev.Exception.RecevoirNotFoundException;
import com.apicollabdev.odk.collabdev.repository.NotificationRepository;
import com.apicollabdev.odk.collabdev.repository.RecevoirRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.RecevoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecevoirServiceImpl implements RecevoirService {
    @Autowired
    private RecevoirRepository recevoirRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Recevoir createRecevoir(Recevoir recevoir) {
        return recevoirRepository.save(recevoir);
    }

    @Override
    public List<Recevoir> getAllRecevoir() {
        return recevoirRepository.findAll();
    }

    @Override
    public Recevoir getById(Long id) {
        return recevoirRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RecevoirNotFoundException("Notification non trouvée pour l'id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        recevoirRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public void notifierGainBadge(Contributeur contributeur, String badgeGagner) {
        Notification notificationBadge = NotificationFactory.creerNotificationBadge(badgeGagner);
        notificationRepository.save(notificationBadge);

        Recevoir recevoirBadge = new Recevoir();
        recevoirBadge.setNotification(notificationBadge);
        recevoirBadge.setContributeur(contributeur);
        recevoirBadge.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoirBadge);
    }

    @Override
    public void notifierGainBadgeEtCoins(Contributeur contributeur, int coins) {
        if (coins > 0) {
            Notification notificationCoins = NotificationFactory.creerNotificationCoins(coins);
            notificationRepository.save(notificationCoins);

            Recevoir recevoirCoins = new Recevoir();
            recevoirCoins.setNotification(notificationCoins);
            recevoirCoins.setContributeur(contributeur);
            recevoirCoins.setDateReception(LocalDateTime.now());
            recevoirRepository.save(recevoirCoins);
        }
    }

    @Override
    public void creerNotificationIdeeProjet(Contributeur contributeur, String titreProjet) {
        Notification notification = NotificationFactory.creerNotificationIdeeProjet(titreProjet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void creerNotificationCommentaire(Contributeur contributeur, String commentaire) {
        Notification notification = NotificationFactory.creerNotificationCommentaire(contributeur.getNom(), commentaire);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierInscription(Contributeur contributeur) {
        Notification notification = NotificationFactory.creerNotificationInscription();
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeContribution(Gestionnaire gestionnaire, String nomProjet, Contributeur contributeur) {
        Notification notification = NotificationFactory.creerNotificationDemandeContribution(gestionnaire, nomProjet, contributeur);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeAcceptee(Contributeur contributeur, String nomProjet) {
        Notification notification = NotificationFactory.creerNotificationDemandeAcceptee(contributeur, nomProjet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeRejetee(Contributeur contributeur, String nomProjet) {
        Notification notification = NotificationFactory.creerNotificationDemandeRejetee(contributeur, nomProjet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeGestionnaire(Contributeur contributeur, String nomProjet, Gestionnaire gestionnaire) {
        Notification notification = NotificationFactory.creerNotificationDemandeGestionnaire(contributeur, nomProjet, gestionnaire);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeGestionnaireAcceptee(Contributeur contributeur, String nomProjet) {
        Notification notification = NotificationFactory.creerNotificationDemandeGestionnaireAcceptee(nomProjet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }

    @Override
    public void notifierDemandeGestionnaireRejetee(Contributeur contributeur, String nomProjet) {
        Notification notification = NotificationFactory.creerNotificationDemandeGestionnaireRejetee(nomProjet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);
    }
}
