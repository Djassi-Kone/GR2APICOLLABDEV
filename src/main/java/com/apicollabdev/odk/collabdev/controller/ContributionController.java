package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.service.Impl.ContributionServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
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
    private ContributionServiceImpl contributionService;

    @Autowired
    private FonctionnaliteService fonctionnaliteService;



    @PostMapping(value = "/ajoutcontribution/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ajouterContribution(
            @RequestPart("dto") ContributionDTO dto,
            @RequestPart(value = "fichier", required = false) MultipartFile fichier
    ) {
        dto.setFichier(fichier);
        String message = contributionService.ajouterContribution(dto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/ajoutcontribution/autres")
    public ResponseEntity<String> ajouterContributionAutres(@RequestBody ContributionDTO dto) {
        try {
            // Le fichier est null ici, on traite juste le contenu texte ou lien
            dto.setFichier(null);
            String message = contributionService.ajouterContribution(dto);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur interne est survenue : " + e.getMessage());
        }
    }



    @PostMapping("/fonctionnalite/{idFonctionnalite}/reserver/contributeur/{idContributeur}")
    public ResponseEntity<Contribution> reserverFonctionnalite(
            @PathVariable Long idFonctionnalite,
            @PathVariable Long idContributeur) {
        Contribution contribution = contributionService.reserverFonctionnalite(idFonctionnalite, idContributeur);
        return ResponseEntity.ok(contribution);
    }

    @PostMapping("/fonctionnalite/{idFonctionnalite}/idContributeur/{idContributeur}/idProjet/{idProjet}")
    public ResponseEntity<Contribution> deposerContribution(
            @PathVariable Long idFonctionnalite,
            @PathVariable Long idContributeur,
            @PathVariable Long idProjet) {
        Contribution contribution = contributionService.deposerContribution(idFonctionnalite, idContributeur, idProjet);
        return ResponseEntity.ok(contribution);
    }

    @PutMapping("/valider/{id}")
    public ResponseEntity<Contribution> validerContribution(@PathVariable Long id) {
        Contribution contribution = contributionService.validerContribution(id);
        return ResponseEntity.ok(contribution);
    }

    @PutMapping("/rejeter/{id}")
    public ResponseEntity<Contribution> rejeterContribution(@PathVariable Long id) {
        Contribution contribution = contributionService.rejeterContribution(id);
        return ResponseEntity.ok(contribution);
    }

    @GetMapping("/contributeur/{idContributeur}")
    public ResponseEntity<List<Contribution>> getByContributeur(@PathVariable Long idContributeur) {
        List<Contribution> contributions = contributionService.getContributionsByContributeur(idContributeur);
        return ResponseEntity.ok(contributions);
    }

    /*@GetMapping("/contributeur/{idContributeur}/projet/{idProjet}")
    public ResponseEntity<List<Contribution>> getByContributeurAndProjet(
            @PathVariable Long idContributeur,
            @PathVariable Long idProjet) {
        List<Contribution> contributions = contributionService.getContributionsByContributeurAndProjet(idContributeur, idProjet);
        return ResponseEntity.ok(contributions);
    }*/

    @GetMapping
    public ResponseEntity<List<Contribution>> getAllContributions() {
        return ResponseEntity.ok(contributionService.getAllContributions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contribution> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contributionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contributionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

