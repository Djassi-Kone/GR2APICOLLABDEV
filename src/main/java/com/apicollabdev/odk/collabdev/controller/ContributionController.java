package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private FonctionnaliteService fonctionnaliteService;

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

