package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutContribution;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RecevoirRepository recevoirRepository;

    @Override
    @Transactional
    public Contribution reserverFonctionnalite(Long idFonctionnalite, Long idContributeur) {
        // Récupérer la fonctionnalité
        Fonctionnalite f = fonctionnaliteRepository.findById(idFonctionnalite)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));

        // Vérifier si disponible
        if (f.getStatutF() != StatutFonctionnalite.DISPONIBLE) {
            throw new RuntimeException("Fonctionnalité non disponible");
        }

        // Récupérer le contributeur
        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        // Créer une nouvelle contribution
        Contribution contribution = new Contribution();
        contribution.setContributeur(contributeur);
        contribution.setFonctionnalite(f);

        // Sauvegarder la contribution
        contributionRepository.save(contribution);

        // Mettre à jour la fonctionnalité
        f.setStatutF(StatutFonctionnalite.RESERVEE);
        f.setContribution(contribution);
        fonctionnaliteRepository.save(f);

        return contribution;
    }



    @Override
    public Contribution deposerContribution(Long idFonctionnalite, Long idContributeur, String urlCode) {
        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        Fonctionnalite fonctionnalite = fonctionnaliteRepository.findById(idFonctionnalite)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité introuvable"));

        Projet projet = fonctionnalite.getProjet();
        if (projet == null) {
            throw new RuntimeException("La fonctionnalité n'est liée à aucun projet.");
        }

        Gestionnaire gestionnaire = projet.getGestionnaire();
        if (gestionnaire == null) {
            throw new RuntimeException("Le projet n'a pas de gestionnaire.");
        }

        Contribution contribution = new Contribution();
        contribution.setContenu(urlCode);
        contribution.setFonctionnalite(fonctionnalite);
        contribution.setContributeur(contributeur);
        contribution.setStatutC(StatutContribution.EN_ATTENTE);
        contribution.setDateSoumission(LocalDateTime.now());

        contribution = contributionRepository.save(contribution);

        // Notification
        Notification notification = NotificationFactory.creerNotificationContribution(
                gestionnaire,
                projet.getTitre(),
                fonctionnalite.getNomFonctionnalite()
        );
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(gestionnaire); // Gestionnaire hérite de Contributeur
        recevoir.setLue(false);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);

        // Email
        emailService.sendEmail(
                gestionnaire.getEmail(),
                "Nouvelle contribution reçue",
                notification.getDescription()
        );

        return contribution;
    }

    @Override
    @Transactional
    public Contribution validerContribution(Long idContribution) {
        // 1️⃣ Récupérer la contribution
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        // 2️⃣ Vérifier si déjà validée
        if (contribution.getStatutC() == StatutContribution.VALIDEE) {
            throw new RuntimeException("Contribution déjà validée");
        }

        // 3️⃣ Mettre à jour le statut de la contribution
        contribution.setStatutC(StatutContribution.VALIDEE);
        contributionRepository.save(contribution);

        // 4️⃣ Mettre à jour la fonctionnalité associée
        Fonctionnalite f = contribution.getFonctionnalite();
        if (f == null) throw new RuntimeException("Fonctionnalité introuvable");
        f.setStatutF(StatutFonctionnalite.TERMINEE);
        fonctionnaliteRepository.save(f);

        // 5️⃣ Créer et assigner les coins
        Contributeur c = contribution.getContributeur();
        if (c == null) throw new RuntimeException("Contributeur introuvable");

        Coins gain = new Coins();
        gain.setNombreCoins(f.getPointFonctionnalite()); // nombre de coins = points de la fonctionnalité
        gain.setDateAcquisition(LocalDateTime.now());
        gain.setFonctionnalite(f);
        gain.setContribution(contribution);
        gain.setContributeur(c);

        c.getCoins().add(gain);
        contributeurRepository.save(c);

        // 6️⃣ Notifications et email hors de la transaction critique
        try {
            notificationService.notifierEtEnvoyer(
                    TypeNotification.CONTRIBUTIONVALIDER,
                    c,
                    f.getProjet().getTitre(),
                    f.getNomFonctionnalite()
            );
        } catch (Exception e) {
            System.err.println("Erreur notification/email : " + e.getMessage());
        }

        return contribution;
    }


    @Override
    public Contribution rejeterContribution(Long idContribution) {
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        contribution.setStatutC(StatutContribution.REJETEE);
        contributionRepository.save(contribution);

        Fonctionnalite f = contribution.getFonctionnalite();
        Contributeur c = contribution.getContributeur();

        Notification notification = NotificationFactory.creerNotificationContributionRejetee(
                c,
                f.getProjet().getTitre(),
                f.getNomFonctionnalite()
        );
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(c);
        recevoir.setLue(false);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);

        emailService.sendEmail(
                c.getEmail(),
                "Contribution rejetée",
                notification.getDescription()
        );

        return contribution;
    }
    @Override
    public List<Contribution> getAllContributions() {

        return contributionRepository.findAll();
    }

    @Override
    public List<Contribution> getContributionsByContributeur(Long idContributeur) {
        return contributionRepository.findContributionByContributeur(idContributeur);
    }

   /* @Override
    public List<Contribution> getContributionsByContributeurAndProjet(Long idContributeur, Long idProjet) {
        return contributionRepository.findContributionByContributeurIdAndProjetId(idContributeur, idProjet);
    }*/

    @Override
    public Contribution getById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        contributionRepository.deleteById(id);
    }

    public List<Contribution> getContributionsByProjet(Long idProjet) {
        return contributionRepository.findByProjetIdProjet(idProjet);
    }


    public List<Contribution> getContributionsParProjetEtContributeur(Long idProjet, Long idContributeur) {
        return contributionRepository.findByProjetIdProjetAndContributeurId(idProjet, idContributeur);
    }


    @Transactional
    public Coins attribuerCoinsParGestionnaire(Contribution contribution) {
        Fonctionnalite f = contribution.getFonctionnalite();
        Contributeur c = contribution.getContributeur();

        if (f == null || c == null) {
            throw new RuntimeException("Fonctionnalité ou contributeur introuvable");
        }

        Coins gain = new Coins();
        gain.setNombreCoins(f.getPointFonctionnalite());
        gain.setDateAcquisition(LocalDateTime.now());
        gain.setFonctionnalite(f);
        gain.setContribution(contribution);
        gain.setContributeur(c);

        c.getCoins().add(gain);
        contributeurRepository.save(c);

        try {
            notificationService.notifierEtEnvoyer(TypeNotification.GAINCOINS, c, f.getPointFonctionnalite());
        } catch (Exception e) {
            System.err.println("Erreur notification/email : " + e.getMessage());
        }

        return gain;
    }










}
