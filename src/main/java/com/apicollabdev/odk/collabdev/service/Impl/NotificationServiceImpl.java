package com.apicollabdev.odk.collabdev.service.Impl;


import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Demande;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.NotificationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationRepository notificationRepository;
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


    public void notifierContributeurPourReponse(Demande demande, String statut) {
        Contributeur contributeurs = demande.getContributeur();
        StatutDemande statutDemande = statut.equalsIgnoreCase("ACCEPTEE") ? StatutDemande.ACCEPTEE : StatutDemande.REJETEE;

        Notification notifications = new Notification();
        notifications.setContributeur(contributeurs);
        notifications.setDescription("Votre demande a été " + statutDemande);
        notifications.setDateNotification(LocalDateTime.now());
        notifications.setStatutDemande(statutDemande);
        notificationRepository.save(notifications);

        // Envoi email
        emailService.sendEmail(contributeurs.getEmail(), "Réponse à votre demande", notifications.getDescription());
    }

  /*  public List<Notification> getNotificationsByGestionnaire(Long idGestionnaire) {
        return notificationRepository.findByProjet_Gestionnaire_IdContributeur(idGestionnaire);
    }*/


}
