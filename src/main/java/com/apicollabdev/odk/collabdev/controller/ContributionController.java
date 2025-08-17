package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.service.Impl.ContributionServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    @Autowired
    private ContributionServiceImpl contributionServiceImpl;

    @Autowired
    private FonctionnaliteService fonctionnaliteService;

    // 📁 Ajouter une contribution avec document (type DOCUMENT)
    @PostMapping(value = "/idFonctionnalites/{idFonctionnalites}/ajoutcontribution/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ajouterContributionAvecFichier(
            @RequestPart("dto") ContributionDTO dto,
            @RequestPart(value = "fichier", required = false) MultipartFile fichier,
            @PathVariable Long idFonctionnalites
    ) {
        dto.setFichier(fichier);
        String message = contributionServiceImpl.ajouterContribution(dto, idFonctionnalites);
        return ResponseEntity.ok(message);
    }

    // Ajouter une contribution autres types (GITHUB, FIGMA, EDITEUR)
    @PostMapping("/idFonctionnalites/{idFonctionnalites}/ajoutcontribution/autres")
    public ResponseEntity<String> ajouterContributionAutres(
            @RequestBody ContributionDTO dto,
            @PathVariable Long idFonctionnalites
    ) {
        try {
            dto.setFichier(null); // Pas de fichier attendu ici
            String message = contributionServiceImpl.ajouterContribution(dto, idFonctionnalites);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur interne est survenue : " + e.getMessage());
        }
    }

    //  Réserver une fonctionnalité
    @PostMapping("/fonctionnalite/{idFonctionnalite}/reserver/contributeur/{idContributeur}")
    public ResponseEntity<Contribution> reserverFonctionnalite(
            @PathVariable Long idFonctionnalite,
            @PathVariable Long idContributeur
    ) {
        Contribution contribution = contributionServiceImpl.reserverFonctionnalite(idFonctionnalite, idContributeur);
        return ResponseEntity.ok(contribution);
    }

    // Valider (accepter) une contribution
    @PutMapping("/{id}/valider")
    public ResponseEntity<Contribution> validerContribution(
            @PathVariable("id") Long idContribution,
            @RequestParam Long idGestionnaire
    ) {
        Contribution contribution = contributionServiceImpl.validerContribution(idContribution, idGestionnaire, true);
        return ResponseEntity.ok(contribution);
    }

    // Rejeter une contribution
    @PutMapping("/{id}/rejeter")
    public ResponseEntity<Contribution> rejeterContribution(
            @PathVariable("id") Long idContribution,
            @RequestParam Long idGestionnaire,
            @RequestParam String motifRejet
    ) {
        Contribution contribution = contributionServiceImpl.rejeterContribution(idContribution, idGestionnaire, motifRejet);
        return ResponseEntity.ok(contribution);
    }


    //  Liste des contributions d’un contributeur
    @GetMapping("/contributeur/{idContributeur}")
    public ResponseEntity<List<Contribution>> getByContributeur(@PathVariable Long idContributeur) {
        List<Contribution> contributions = contributionServiceImpl.getContributionsByContributeur(idContributeur);
        return ResponseEntity.ok(contributions);
    }

    // Récupérer toutes les contributions
    @GetMapping
    public ResponseEntity<List<Contribution>> getAllContributions() {
        return ResponseEntity.ok(contributionServiceImpl.getAllContributions());
    }

    @GetMapping("/contributeur/{idContributeur}/projet/{idProjet}")
    public ResponseEntity<List<Contribution>> getByContributeurAndProjet(
            @PathVariable Long idContributeur,
            @PathVariable Long idProjet
    ) {
        List<Contribution> contributions = contributionServiceImpl.getContributionsByContributeurAndProjet(idContributeur, idProjet);
        return ResponseEntity.ok(contributions);
    }


    // Récupérer une contribution par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Contribution> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contributionServiceImpl.getById(id));
    }

    // Supprimer une contribution
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contributionServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
