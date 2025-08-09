package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeParticipationController {

    @Autowired
    private DemandeParticipationService demandeParticipationService;

    // Créer une demande de participation à un projet existant
    @PostMapping("/participation")
    public DemandeParticipation createDemandeParticipation(
            @RequestParam Long idProjet,
            @RequestParam Long idContributeur,
            @RequestParam String description
    ) {
        return demandeParticipationService.createDemandeParticipation(idProjet, idContributeur, description);
    }

    // Accepter une demande pour devenir participation
    @PutMapping("/gestionnaire/accepter/{idDemande}")
    public DemandeParticipation accepterDemandeParticipation(@PathVariable Long idDemande) {
        return demandeParticipationService.accepterDemandeParticipation(idDemande);
    }

    // Rejeter une demande pour devenir gestionnaire
    @PutMapping("/gestionnaire/rejeter/{idDemande}")
    public DemandeParticipation rejeterDemandeParticipation(@PathVariable Long idDemande) {
        return demandeParticipationService.rejeterDemandeParticipation(idDemande);
    }

    // Faire une demande pour devenir gestionnaire d'une idée de projet
    @PostMapping("/contributeur/{contributeur}/idIdeeProjet/{idIdeeProjet}")
    public DemandeParticipation faireDemandeGestionnaire(
            @RequestParam Long idIdeeProjet,
            @RequestParam Long idContributeur
    ) {
        return demandeParticipationService.faireDemandeGestionnaire(idIdeeProjet, idContributeur);
    }

    // Accepter une demande pour devenir gestionnaire
    /*@PutMapping("/gestionnaire/accepter/{idDemande}")
    public DemandeParticipation accepterDemandeGestionnaire(@PathVariable Long idDemande) {
        return demandeParticipationService.accepterDemandeGestionnaire(idDemande);
    }*/

    // Rejeter une demande pour devenir gestionnaire
    /*@PutMapping("/gestionnaire/rejeter/{idDemande}")
    public DemandeParticipation rejeterDemandeGestionnaire(@PathVariable Long idDemande) {
        return demandeParticipationService.rejeterDemandeGestionnaire(idDemande);
    }*/

    // Récupérer toutes les demandes
    @GetMapping
    public List<DemandeParticipation> getAllDemandes() {
        return demandeParticipationService.getAllDemandeParticipation();
    }

    // Récupérer une demande par ID
    @GetMapping("/{id}")
    public DemandeParticipation getById(@PathVariable Long id) {
        return demandeParticipationService.getById(id);
    }

    // Supprimer une demande
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        demandeParticipationService.deleteById(id);
    }
}
