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
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private CoinsRepository coinsRepository;


    private final Path dossierUploads = Paths.get("uploads/contributions");
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;


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

        // Sauvegarder en base
        contributionRepository.save(contribution);

        //  Notification si nécessaire

        return "Contribution ajoutée avec succès !";
    }


    @Override
    @Transactional
    public Contribution validerContribution(Long idContribution, Long idGestionnaire, boolean accepter) {
        // Récupérer la contribution
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution introuvable"));

        // Vérifier que la contribution est en attente
        if (contribution.getStatutC() != StatutContribution.EN_ATTENTE) {
            throw new RuntimeException("Contribution déjà traitée");
        }

        // Récupérer le projet et vérifier que l'utilisateur est le gestionnaire
        Projet projet = contribution.getProjet();
        Gestionnaire gestionnaire = gestionnaireRepository.findById(idGestionnaire)
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable"));

        if (!projet.getGestionnaire().getId().equals(gestionnaire.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à valider cette contribution");
        }

        //  Mise à jour du statut de la contribution
        if (accepter) {
            contribution.setStatutC(StatutContribution.VALIDEE);

            // Mettre la fonctionnalité liée comme TERMINÉE si elle existe
            if (contribution.getFonctionnalite_id() != null) {
                Fonctionnalite f = fonctionnaliteRepository.findById(contribution.getFonctionnalite_id())
                        .orElseThrow(() -> new RuntimeException("Fonctionnalité introuvable"));
                f.setStatutF(StatutFonctionnalite.TERMINEE);
                fonctionnaliteRepository.save(f);

                // Ajouter les points de la fonctionnalité aux gains du contributeur
                Contributeur c = contribution.getContributeur();
                int points = f.getPointFonctionnalite();

                // Créer l’objet Coins
                Coins coins = new Coins();
                coins.setNombreCoins(points);
                coins.setDateAcquisition(LocalDateTime.now());
                coins.setContributeur(coins.getContributeur());
                coins.setContribution(contribution); // si tu as ce lien
                coinsRepository.save(coins);

                // Mettre à jour totalCoins du contributeur
                int nouveauTotal = (c.getTotalCoins() != 1 ? c.getTotalCoins() : 1) + points;
                c.setTotalCoins(nouveauTotal);
                contributeurRepository.save(c);
            }
        } else {
            contribution.setStatutC(StatutContribution.EN_ATTENTE);
        }

        contribution.setDateValidation(LocalDateTime.now());
        contributionRepository.save(contribution);

        return contribution;
    }

    @Override
    @Transactional
    public Contribution rejeterContribution(Long idContribution, Long idGestionnaire, String motifRejet) {
        // Récupérer la contribution
        Contribution contribution = contributionRepository.findById(idContribution)
                .orElseThrow(() -> new RuntimeException("Contribution introuvable"));

        // Vérifier que la contribution est en attente
        if (contribution.getStatutC() != StatutContribution.EN_ATTENTE) {
            throw new RuntimeException("Cette contribution a déjà été traitée.");
        }

        // 3. Récupérer le projet et vérifier que l'utilisateur est bien le gestionnaire
        Projet projet = contribution.getProjet();
        Gestionnaire gestionnaire = gestionnaireRepository.findById(idGestionnaire)
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable"));

        if (!projet.getGestionnaire().getId().equals(gestionnaire.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à rejeter cette contribution.");
        }

        // 4. Rejet de la contribution
        contribution.setStatutC(StatutContribution.REJETEE);
        contribution.setDateValidation(LocalDateTime.now());
        contribution.setMotifRejet(motifRejet); // Ajoute un champ si nécessaire

        contributionRepository.save(contribution);

        // 5. (Optionnel) Rendre la fonctionnalité à nouveau disponible
        if (contribution.getFonctionnalite_id() != null) {
            Fonctionnalite f = fonctionnaliteRepository.findById(contribution.getFonctionnalite_id())
                    .orElseThrow(() -> new RuntimeException("Fonctionnalité introuvable"));
            f.setStatutF(StatutFonctionnalite.DISPONIBLE);
            fonctionnaliteRepository.save(f);
        }

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

    @Override
    public List<Contribution> getContributionsByContributeurAndProjet(Long idContributeur, Long idProjet) {
        return contributionRepository.findByContributeurIdAndProjetIdProjet(idContributeur, idProjet);
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
