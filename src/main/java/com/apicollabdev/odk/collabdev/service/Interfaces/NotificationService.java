package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification, long idAdministrateur);
    List<Notification> getAllNotifications();
    Notification getById(Long id);
    void deleteById(Long id);

        /**
         * Envoie une notification par mail à un contributeur selon le type et les données fournies.
         * @param type le type de notification à envoyer
         * @param contributeur le contributeur destinataire
         * @param data données supplémentaires pour construire la notification (ex : nom projet, nombre coins, etc.)
         */
        void notifierEtEnvoyer(TypeNotification type, Contributeur contributeur, Object... data);
        void envoyerNotification(Contributeur contributeur, Notification notification);




}

