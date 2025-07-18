package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.DemandeDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.ChoixRole;
import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Getter
@Setter
@Service
public class DemandeServiceImpl implements DemandeService {
    @Autowired
    private final DemandeRepository demandeRepository;
    @Autowired
    private final ContributeurRepository contributeurRepository;
    @Autowired
    private final ProjetRepository projetRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RecevoirRepository recevoirRepository;

    @Autowired
    public DemandeServiceImpl(ContributeurRepository contributeurRepository, DemandeRepository demandeRepository, ProjetRepository projetRepository) {
        this.contributeurRepository = contributeurRepository;
        this.demandeRepository = demandeRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public Demande createDemande(DemandeDTO demandeDTO, long idContributeur, long idProjet) {
         Contributeur contributeur = contributeurRepository.findById(idContributeur)
                 .orElseThrow(() -> new RuntimeException("Contributeur Introuvable"));
            Projet projet = projetRepository.findById(idProjet)
                    .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
            Demande demande = new Demande();
            demande.setStatut(demandeDTO.getStatut());
            demande.setDescription(demandeDTO.getDescription());
            demande.setChoixRole(demandeDTO.getChoixRole());
            demande.setContributeur(contributeur);
            demande.setProjet(projet);
            demandeRepository.save(demande);

        Notification notification = new Notification();
        notification.setProjet(projet); // ✅ tu dois utiliser "projet" (et pas "p" ou autre)
        notification.setContributeur(contributeur); // ✅ "contributeur" et pas "c"
        notification.setDescription("Nouvelle demande de participation pour le projet : " + projet.getTitre());
        notification.setDateNotification(LocalDateTime.now());
        notification.setStatutDemande(StatutDemande.EN_ATTENTE);
        notification.setTypeNotyf(TypeNotification.DEMANDEPARTICIPATION);
        notification.setEtat(false);
        notificationRepository.save(notification);


        return demande;
    }

    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @Override
    public Demande getById(Long id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        demandeRepository.deleteById(id);
    }

    @Override
    public Demande creerDemande(long idProjet, long idContributeur, String description) {
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        Demande demande = new Demande();
        demande.setProjet(projet);
        demande.setContributeur(contributeur);
        demande.setDescription(description);
        demande.setStatut(StatutDemande.EN_ATTENTE);
        demande.setChoixRole(ChoixRole.CONTRIBUTEUR); // par défaut, à adapter si besoin

        Demande savedDemande = demandeRepository.save(demande);

        // Générer la notification pour le gestionnaire
        Utilisateur gestionnaire = projet.getGestionnaire();
        if (gestionnaire instanceof Contributeur gestionnaireContributeur) {
            Notification notification = new Notification();
            notification.setDateNotification(LocalDateTime.now());
            notification.setDescription("Nouvelle demande de participation pour le projet : " + projet.getTitre());
            notification.setContributeur(contributeur);
            notification.setTypeNotyf(TypeNotification.DEMANDEPARTICIPATION);
            notification.setStatutDemande(StatutDemande.EN_ATTENTE);
            notification.setEtat(false);

            Notification savedNotification = notificationRepository.save(notification);

            Recevoir recevoir = new Recevoir();
            recevoir.setContributeur(gestionnaireContributeur);
            recevoir.setNotification(savedNotification);
            recevoir.setDateReception(LocalDateTime.now());
            recevoirRepository.save(recevoir);
        }

        return savedDemande;
    }
}
