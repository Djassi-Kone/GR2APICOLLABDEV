package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.NotificationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import com.apicollabdev.odk.collabdev.service.Impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private  NotificationService notificationService;
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;
    @Autowired
    private  AdministrateurRepository administrateurRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    /*@GetMapping("/gestionnaire/{idGestionnaire}")
    public ResponseEntity<List<Notification>> getNotificationsByGestionnaire(@PathVariable Long idGestionnaire) {
        List<Notification> notifications = notificationServiceImpl.getNotificationsByGestionnaire(idGestionnaire);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<Notification>> getNotificationsByProjet(@PathVariable Long idProjet) {
        return ResponseEntity.ok(notificationRepository.findByProjet_IdProjet(idProjet));
    }*/



    @PostMapping("/administrateurs/{idadmin}")
    public ResponseEntity<Notification> create(@RequestBody Notification notification,@PathVariable("idadmin") long idAdministrateur) {
        Administrateur a = administrateurRepository.findById(idAdministrateur).get();
        return ResponseEntity.ok(notificationService.createNotification(notification, idAdministrateur));
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

