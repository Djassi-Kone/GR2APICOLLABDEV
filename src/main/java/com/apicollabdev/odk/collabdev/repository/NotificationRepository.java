package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
   // List<Notification> findByProjet_Gestionnaire_IdContributeur(Long idGestionnaire);


    List<Notification> findByProjet_IdProjet(Long idProjet);



}
