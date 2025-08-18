package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/contributions")
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



    @PostMapping("/deposer")
    public Contribution deposer(@RequestParam Long idFonctionnalite,
                                @RequestParam String contenu,
                                @RequestParam Long idProjet,
                                @RequestParam Long idContributeur) {
        return contributionService.deposerContribution(idFonctionnalite, contenu, idProjet, idContributeur);
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


    @GetMapping("/projet/{idProjet}/contributeur/{idContributeur}")
    public List<Contribution> getContributionsParProjetEtContributeur(
            @PathVariable Long idProjet,
            @PathVariable Long idContributeur) {
        return contributionServiceImpl.getContributionsParProjetEtContributeur(idProjet, idContributeur);
    }


    // Vérifier si la contribution existe déjà



    @PutMapping("/{idContribution}")
    public ResponseEntity<Contribution> modifierContribution(
            @PathVariable Long idContribution,
            @RequestParam(required = false) String contenu,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String description) {

        Contribution contributionModifiee = contributionServiceImpl.modifierContribution(
                idContribution, contenu, titre, description);

        return ResponseEntity.ok(contributionModifiee);
    }





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
    public ResponseEntity<?> ajouterContributionAutres(
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

}

