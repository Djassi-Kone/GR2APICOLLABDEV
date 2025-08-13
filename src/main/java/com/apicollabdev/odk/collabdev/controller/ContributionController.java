package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    @PostMapping("/reserver")
    public ResponseEntity<Contribution> reserverFonctionnalite(
            @RequestParam Long idFonctionnalite,
            @RequestParam Long idContributeur) {
        Contribution contribution = contributionService.reserverFonctionnalite(idFonctionnalite, idContributeur);
        return ResponseEntity.ok(contribution);
    }

    @PostMapping("/deposer")
    public ResponseEntity<Contribution> deposerContribution(
            @RequestParam Long idFonctionnalite,
            @RequestParam Long idContributeur,
            @RequestParam String urlCode) {
        Contribution contribution = contributionService.deposerContribution(idFonctionnalite, idContributeur, urlCode);
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
