package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.service.Impl.ContributionServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
@CrossOrigin(origins = "http://localhost:4200")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private ContributionServiceImpl contributionServiceImpl;

    @Autowired
    private FonctionnaliteService fonctionnaliteService;

    @PostMapping("/fonctionnalite/{idFonctionnalite}/reserver/contributeur/{idContributeur}")
    public ResponseEntity<Contribution> reserverFonctionnalite(
            @PathVariable Long idFonctionnalite,
            @PathVariable Long idContributeur) {
        Contribution contribution = contributionService.reserverFonctionnalite(idFonctionnalite, idContributeur);
        return ResponseEntity.ok(contribution);
    }

    @PostMapping("/fonctionnalite/{idFonctionnalite}/deposer")
    public ResponseEntity<Contribution> deposerContribution(
            @PathVariable Long idFonctionnalite,
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



    @GetMapping("/{id}")
    public ResponseEntity<Contribution> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contributionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contributionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //pour toutes les contributions avec les contributeurs
    @GetMapping("/all-contributions")
    public ResponseEntity<List<Contribution>> getAllContributions() {
        List<Contribution> contributions = contributionService.getAllContributions();
        return ResponseEntity.ok(contributions); // chaque Contribution inclut maintenant le contributeur
    }

    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<Contribution>> getContributionsByProjet(@PathVariable Long idProjet) {
        List<Contribution> contributions = contributionServiceImpl.getContributionsByProjet(idProjet);
        return ResponseEntity.ok(contributions);
    }



    //Modification
    // Valider une contribution
    @PostMapping("/gestionnaire/contribution/{idContribution}")
    public ResponseEntity<Coins> createCoinsParGestionnaire(
            @PathVariable("idContribution") Long idContribution
    ) {
        Contribution contribution = contributionService.getById(idContribution);
        Coins coins = ((ContributionServiceImpl) contributionService).attribuerCoinsParGestionnaire(contribution);
        return ResponseEntity.ok(coins);
    }



}

