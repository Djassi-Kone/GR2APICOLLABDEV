package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutContribution;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

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
    public Contribution reserverFonctionnalite(Long idFonctionnalite, Long idContributeur) {
        Fonctionnalite f = fonctionnaliteRepository.findById(idFonctionnalite)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));

        if (f.getStatutF() != StatutFonctionnalite.DISPONIBLE) {
            throw new RuntimeException("Fonctionnalité non disponible");
        }

        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        f.setStatutF(StatutFonctionnalite.RESERVEE);
        f.setContributeur(c);
        fonctionnaliteRepository.save(f);

        return null;
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
    public Contribution validerContribution(Long idContribution) {
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        contribution.setStatutC(StatutContribution.VALIDEE);
        contributionRepository.save(contribution);

        Fonctionnalite f = contribution.getFonctionnalite();
        f.setStatutF(StatutFonctionnalite.TERMINEE);
        fonctionnaliteRepository.save(f);

        Contributeur c = contribution.getContributeur();
        Coins gain = new Coins();
        int totalCoins = f.getCoins().stream().mapToInt(Coins::getNombreCoins).sum();
        gain.setNombreCoins(totalCoins);
        gain.setDateAcquisition(LocalDateTime.now());
        c.getCoins().add(gain); // ajout du gain à la liste

        contributeurRepository.save(c);

        Notification notification = NotificationFactory.creerNotificationContributionValider(
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
                "Contribution validée",
                notification.getDescription()
        );

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
    public Contribution getById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        contributionRepository.deleteById(id);
    }


}
