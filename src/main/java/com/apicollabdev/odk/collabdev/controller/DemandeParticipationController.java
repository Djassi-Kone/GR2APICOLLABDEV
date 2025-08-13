package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.service.Impl.DemandeParticipationServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeParticipationController {

    @Autowired
    private DemandeParticipationServiceImpl demandeParticipationServiceImpl;

    // Créer une demande de participation à un projet existant
    @PostMapping("/participation")
    public DemandeParticipation createDemandeParticipation(
            @PathVariable Long idProjet,
            @PathVariable Long idContributeur,
            @RequestParam String description
    ) {
        return demandeParticipationServiceImpl.createDemandeParticipation(idProjet, idContributeur, description);
    }

    // Accepter une demande pour devenir participation
    @PutMapping("/gestionnaire/accepter/{idDemande}")
    public DemandeParticipation accepterDemandeParticipation(@PathVariable Long idDemande) {
        return demandeParticipationServiceImpl.accepterDemandeParticipation(idDemande);
    }

    // Rejeter une demande pour devenir gestionnaire
    @PutMapping("/gestionnaire/rejeter/{idDemande}")
    public DemandeParticipation rejeterDemandeParticipation(@PathVariable Long idDemande) {
        return demandeParticipationServiceImpl.rejeterDemandeParticipation(idDemande);
    }

    // Faire une demande pour devenir gestionnaire d'une idée de projet
    @PostMapping("/idContributeur/{idContributeur}/idIdeeProjet/{idIdeeProjet}")
    public DemandeParticipation faireDemandeGestionnaire(
            @PathVariable Long idContributeur,
            @PathVariable Long idIdeeProjet

    ) {
        return demandeParticipationServiceImpl.faireDemandeGestionnaire(idIdeeProjet, idContributeur);
    }

    // Récupérer toutes les demandes
    @GetMapping
    public List<DemandeParticipation> getAllDemandes() {
        return demandeParticipationServiceImpl.getAllDemandeParticipation();
    }

    // Récupérer une demande par ID
    @GetMapping("/{id}")
    public DemandeParticipation getById(@PathVariable Long id) {
        return demandeParticipationServiceImpl.getById(id);
    }

    // Supprimer une demande
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        demandeParticipationServiceImpl.deleteById(id);
    }
}
