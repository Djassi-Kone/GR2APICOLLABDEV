package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification, long idAdministrateur);
    List<Notification> getAllNotifications();
    Notification getById(Long id);
    void deleteById(Long id);
}

