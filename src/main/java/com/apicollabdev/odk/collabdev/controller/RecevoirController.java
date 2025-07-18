package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Recevoir;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributeurService;
import com.apicollabdev.odk.collabdev.service.Interfaces.GestionnaireService;
import com.apicollabdev.odk.collabdev.service.Interfaces.RecevoirService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recevoirs")
@RequiredArgsConstructor
public class RecevoirController {

    private  RecevoirService recevoirService;
    private  ContributeurService contributeurService;
    private  GestionnaireService gestionnaireService;

    @PostMapping
    public ResponseEntity<Recevoir> create(@RequestBody Recevoir recevoir) {
        return ResponseEntity.ok(recevoirService.createRecevoir(recevoir));
    }

    @GetMapping
    public List<Recevoir> getAll() {
        return recevoirService.getAllRecevoir();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recevoir> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recevoirService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recevoirService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contributeur/badge")
    public void notifierBadge(@RequestParam Long contributeurId, @RequestParam String badgeGagner) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierGainBadge(contributeur, badgeGagner);
    }

    @PostMapping("/contributeur/coins")
    public void notifierCoins(@RequestParam Long contributeurId, @RequestParam int coins) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierGainBadgeEtCoins(contributeur, coins);
    }

    @PostMapping("/contributeur/idee-projet")
    public void notifierIdeeProjet(@RequestParam Long contributeurId, @RequestParam String titreProjet) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.creerNotificationIdeeProjet(contributeur, titreProjet);
    }

    @PostMapping("/contributeur/commentaire")
    public void notifierCommentaire(@RequestParam Long contributeurId, @RequestParam String commentaire) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.creerNotificationCommentaire(contributeur, commentaire);
    }

    @PostMapping("/contributeur/inscription")
    public void notifierInscription(@RequestParam Long contributeurId) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierInscription(contributeur);
    }

    @PostMapping("/gestionnaire/demande-contribution")
    public void notifierDemandeContribution(@RequestParam Long gestionnaireId,
                                            @RequestParam String nomProjet,
                                            @RequestParam Long contributeurId) {
        Gestionnaire gestionnaire = gestionnaireService.getGestionnaireById(gestionnaireId);
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierDemandeContribution(gestionnaire, nomProjet, contributeur);
    }

    @PostMapping("/contributeur/demande-acceptee")
    public void notifierDemandeAcceptee(@RequestParam Long contributeurId,
                                        @RequestParam String nomProjet) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierDemandeAcceptee(contributeur, nomProjet);
    }

    @PostMapping("/contributeur/demande-rejetee")
    public void notifierDemandeRejetee(@RequestParam Long contributeurId,
                                       @RequestParam String nomProjet) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierDemandeRejetee(contributeur, nomProjet);
    }

    @PostMapping("/demande-gestionnaire")
    public void notifierDemandeGestionnaire(@RequestParam Long contributeurId,
                                            @RequestParam String nomProjet,
                                            @RequestParam Long gestionnaireId) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        Gestionnaire gestionnaire = gestionnaireService.getGestionnaireById(gestionnaireId);
        recevoirService.notifierDemandeGestionnaire(contributeur, nomProjet, gestionnaire);
    }

    @PostMapping("/contributeur/demande-gestionnaire-acceptee")
    public void notifierDemandeGestionnaireAcceptee(@RequestParam Long contributeurId,
                                                    @RequestParam String nomProjet) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierDemandeGestionnaireAcceptee(contributeur, nomProjet);
    }

    @PostMapping("/contributeur/demande-gestionnaire-rejetee")
    public void notifierDemandeGestionnaireRejetee(@RequestParam Long contributeurId,
                                                   @RequestParam String nomProjet) {
        Contributeur contributeur = contributeurService.getContributeurById(contributeurId);
        recevoirService.notifierDemandeGestionnaireRejetee(contributeur, nomProjet);
    }
}
