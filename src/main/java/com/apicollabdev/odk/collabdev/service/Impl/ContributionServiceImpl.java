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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProjetRepository projetRepository;

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



    private final Path dossierUploads = Paths.get("uploads/contributions");
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;


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
        contribution.setFonctionnalite_id(fonctionnalite.getIdFonctionnalite());

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

         /*// 5. Ajouter une fonctionnalité si besoin
        if (dto.getFonctionnaliteNom() != null) {
            Gestionnaire g = new Gestionnaire();
            Fonctionnalite fonctionnalite = new Fonctionnalite();
            fonctionnalite.setGestionnaire(g);
            fonctionnalite.setNomFonctionnalite(dto.getFonctionnaliteNom());
            fonctionnalite.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());
            fonctionnaliteRepository.save(fonctionnalite);
            // Sauvegarde automatique via cascade
            contribution.setFonctionnalite(fonctionnalite);
        }


        notificationServiceImpl.notifierEtEnvoyer(
                TypeNotification.FAIRECONTRIBUTION,
                contributeur,
                contribution.getTitre()
        );

        */

        // 6. Sauvegarder en base
        contributionRepository.save(contribution);

        // 7. Notification si nécessaire



        return "Contribution ajoutée avec succès !";
    }


   /* @Transactional
    public String ajouterContribution(ContributionDTO dto) {
        if (dto.getType() == null) {
            throw new IllegalArgumentException("Type de contribution manquant.");
        }

        switch (dto.getType()) {
            case DOCUMENT:
                try {
                    return traiterDocument(dto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            case GITHUB:
                return traiterLienGithub(dto);

            case FIGMA:
                return traiterLienFigma(dto);

            case EDITEUR:
                return traiterEditeur(dto);

            default:
                throw new IllegalArgumentException("Type de contribution inconnu.");
        }
    }

    private String traiterDocument(ContributionDTO dto) throws IOException {
        MultipartFile fichier = dto.getFichier();
        if (fichier == null || fichier.isEmpty()) {
            throw new IllegalArgumentException("Fichier manquant pour type DOCUMENT.");
        }

        try {
            Files.createDirectories(dossierUploads);
            Path cheminFichier = dossierUploads.resolve(fichier.getOriginalFilename());
            fichier.transferTo(cheminFichier);
            return "Document uploadé avec succès: " + cheminFichier;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du document.", e);
        }
    }

    private String traiterLienGithub(ContributionDTO dto) {
        if (dto.getLien() == null || !dto.getLien().startsWith("https://github.com/")) {
            throw new IllegalArgumentException("Lien GitHub invalide.");
        }
        // Ici on peut stocker le lien dans la base
        return "Lien GitHub enregistré: " + dto.getLien();
    }

    private String traiterLienFigma(ContributionDTO dto) {
        if (dto.getLien() == null || !dto.getLien().startsWith("https://www.figma.com/")) {
            throw new IllegalArgumentException("Lien Figma invalide.");
        }
        // Ici on peut stocker le lien dans la base
        return "Lien Figma enregistré: " + dto.getLien();
    }

    private String traiterEditeur(ContributionDTO dto) {
        if (dto.getContenu() == null || dto.getContenu().isBlank()) {
            throw new IllegalArgumentException("Code ou texte obligatoire pour type EDITEUR.");
        }
        // Ici on peut sauvegarder le code dans un fichier ou base de données
        return "Code enregistré: " + dto.getContenu().substring(0, Math.min(dto.getContenu().length(), 50)) + "...";
    }  */


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
        //contribution.setFonctionnalite(f);

        // Sauvegarder la contribution
        contributionRepository.save(contribution);

        // Mettre à jour la fonctionnalité
        f.setStatutF(StatutFonctionnalite.RESERVEE);
        fonctionnaliteRepository.save(f);

        return contribution;
    }



    @Override
    public Contribution deposerContribution(Long idFonctionnalite, Long idContributeur, Long idProjet) {
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
        //contribution.setContenu(contribution.getContenu());
        //contribution.setFonctionnalite(fonctionnalite);
        contribution.setContributeur(contributeur);
        contribution.setStatutC(StatutContribution.EN_ATTENTE);
        contribution.setDateSoumission(LocalDateTime.now());

        contribution = contributionRepository.save(contribution);

        // Notification
       Notification notification = NotificationFactory.creerNotificationContribution(
                contributeur,
                projet.getTitre(),
                fonctionnalite.getNomFonctionnalite()
        );
        notificationRepository.save(notification);

        Recevoir recevoir = new Recevoir();
        recevoir.setNotification(notification);
        recevoir.setContributeur(contributeur); // Gestionnaire hérite de Contributeur
        recevoir.setLue(false);
        recevoir.setDateReception(LocalDateTime.now());
        recevoirRepository.save(recevoir);

        // Email
        emailService.sendEmail(
                contributeur.getEmail(),
                "Nouvelle contribution reçue",
                notification.getDescription()
        );

        return contribution;
    }

    @Override
    public Contribution validerContribution(Long idContribution) {
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        /* contribution.setStatutC(StatutContribution.VALIDEE);
        contributionRepository.save(contribution);

        //Fonctionnalite f = contribution.getFonctionnalite();
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
*/
        return contribution;
    }



    @Override
    public Contribution rejeterContribution(Long idContribution) {
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));

        /*contribution.setStatutC(StatutContribution.REJETEE);
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
        );*/

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


}
