package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutContribution;
import com.apicollabdev.odk.collabdev.enums.StatutFonctionnalite;
import com.apicollabdev.odk.collabdev.enums.TypeContribution;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.FileStorageService;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CoinsRepository coinsRepository;

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

        /*// 4️⃣ Mettre à jour la fonctionnalité associée
        Long idFonc = contribution.getNewIdFonctionnalite();
        Fonctionnalite f = contribution.getFonctionnalite();
        f.setIdFonctionnalite(idFonc);

        if (f == null) throw new RuntimeException("Fonctionnalité introuvable");
        f.setStatutF(StatutFonctionnalite.TERMINEE);
        fonctionnaliteRepository.save(f);*/

        // Récupérer la nouvelle fonctionnalité
        Fonctionnalite f = fonctionnaliteRepository.findById(contribution.getNewIdFonctionnalite())
                .orElseThrow(() -> new RuntimeException("Nouvelle fonctionnalité introuvable"));

// Associer la contribution à cette nouvelle fonctionnalité
        contribution.setFonctionnalite(f);

// Mettre à jour le statut de la nouvelle fonctionnalité
        f.setStatutF(StatutFonctionnalite.TERMINEE);
        fonctionnaliteRepository.save(f);

// Sauvegarder la contribution mise à jour
       // contributionRepository.save(contribution);




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

        coinsRepository.save(gain);


        int total = coinsRepository.sumCoinsByContributeur(c.getId()); // voir méthode repo ci-dessous
        c.setTotalCoins(total);
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



    @Transactional
    @Override
    public Contribution deposerContribution(Long idFonctionnalite, String contenu, Long idProjet, Long idContributeur) {



        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        Fonctionnalite fonctionnalite = fonctionnaliteRepository.findById(idFonctionnalite)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité introuvable"));

        Projet projet = fonctionnalite.getProjet();
        if (projet == null || !projet.getIdProjet().equals(idProjet)) {
            throw new RuntimeException("Projet incorrect pour cette fonctionnalité.");
        }

        Contribution contribution = new Contribution();
        contribution.setContributeur(contributeur);
        contribution.setFonctionnalite(fonctionnalite);
        contribution.setContenu(contenu);
        contribution.setStatutC(StatutContribution.EN_ATTENTE);
        contribution.setDateSoumission(LocalDateTime.now());
        contribution = contributionRepository.save(contribution);

        // Notification au gestionnaire
        Gestionnaire gestionnaire = projet.getGestionnaire();
        Notification notification = NotificationFactory.creerNotificationContribution(
                gestionnaire,
                projet.getTitre(),
                fonctionnalite.getNomFonctionnalite()
        );
        notification.setContributeur(contributeur);
        notification.setProjet(projet);
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(gestionnaire);
        recevoir.setDateReception(LocalDateTime.now());
        recevoir.setLue(false);
        recevoirRepository.save(recevoir);

        emailService.sendEmail(
                gestionnaire.getEmail(),
                "Nouvelle contribution reçue",
                notification.getDescription()
        );

        return contribution;
    }





    @Transactional
    public Contribution modifierContribution(Long idContribution, String nouveauContenu, String nouveauTitre, String nouvelleDescription) {
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        // Vérifier que la contribution peut être modifiée (statut EN_ATTENTE)
        if (contribution.getStatutC() != StatutContribution.EN_ATTENTE) {
            throw new RuntimeException("Seules les contributions en attente peuvent être modifiées");
        }

        // Mettre à jour les champs
        if (nouveauContenu != null) {
            contribution.setContenu(nouveauContenu);
        }
        if (nouveauTitre != null) {
            contribution.setTitre(nouveauTitre);
        }
        if (nouvelleDescription != null) {
            contribution.setContenu(nouvelleDescription);
        }

        contribution.setDateSoumission(LocalDateTime.now()); // Mettre à jour la date
        return contributionRepository.save(contribution);
    }






    @Transactional
    public String ajouterContribution(ContributionDTO dto, Long idFonctionnalite) {
        Fonctionnalite fonctionnalite = fonctionnaliteRepository.findById(idFonctionnalite).orElseThrow(() -> new RuntimeException("fonctionnalite introuvable avec l'id : " + idFonctionnalite));;
        // 1. Vérifier que le type est correct
        TypeContribution typeEnum;
        try {
            typeEnum = TypeContribution.valueOf(dto.getType().name());
        } catch (Exception e) {
            throw new RuntimeException("Type de contribution invalide");
        }

        // 2. Récupérer le projet et le contributeur
        Projet projet = projetRepository.findByIdProjet(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        Contributeur contributeur = contributeurRepository.findById(dto.getContributeurId())
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        // 3. Créer la contribution
        Contribution contribution = new Contribution();
        contribution.setTitre(dto.getTitre());
        contribution.setType(typeEnum.name());
        contribution.setContenu(dto.getContenu());
        contribution.setDateSoumission(LocalDateTime.now());
        contribution.setStatutC(StatutContribution.EN_ATTENTE); // par exemple
        contribution.setProjet(projet);
        contribution.setContributeur(contributeur);
        contribution.setNewIdFonctionnalite(idFonctionnalite);

        // 4. Gestion spécifique selon le type
        switch (typeEnum) {
            case DOCUMENT:
                if (dto.getFichier() == null) {
                    throw new RuntimeException("Fichier requis pour type DOCUMENT");
                }
                // Sauvegarde du fichier sur le serveur
                String cheminFichier = fileStorageService.storeFile(dto.getFichier());
                contribution.setContenu(cheminFichier);
                break;

            case GITHUB:
                if (!dto.getContenu().startsWith("https://github.com/")) {
                    throw new RuntimeException("Lien GitHub invalide");
                }
                break;

            case FIGMA:
                if (!dto.getContenu().startsWith("https://www.figma.com/")) {
                    throw new RuntimeException("Lien Figma invalide");
                }
                break;

            case EDITEUR:
                if (dto.getContenu() == null || dto.getContenu().isEmpty()) {
                    throw new RuntimeException("Le code ne peut pas être vide pour EDITEUR");
                }
                break;
        }

        // Sauvegarder en base
        contributionRepository.save(contribution);

        //  Notification si nécessaire

        return "Contribution ajoutée avec succès !";
    }



}
