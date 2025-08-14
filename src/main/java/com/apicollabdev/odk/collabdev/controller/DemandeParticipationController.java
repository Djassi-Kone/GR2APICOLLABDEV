package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.service.Impl.DemandeParticipationServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeParticipationController {

    @Autowired
    private DemandeParticipationServiceImpl demandeParticipationServiceImpl;

    // Créer une demande de participation à un projet existant
    @PostMapping("/participation/idProjet/{idProjet}/idContributeur/{idContributeur}")
    public DemandeParticipation createDemandeParticipation(
            @PathVariable Long idProjet,
            @PathVariable Long idContributeur,
            @RequestParam String description
    ) {
        return demandeParticipationServiceImpl.createDemandeParticipation(idProjet, idContributeur, description);
    }


    @PutMapping("/gestionnaire/accepter/{idDemande}")
    public ResponseEntity<Projet> accepterDemandeGestionnaire(@PathVariable Long idDemande) {
        try {
            Projet projet = demandeParticipationServiceImpl.accepterDemandeGestionnaire(idDemande);
            return ResponseEntity.ok(projet);  // 200 OK avec le projet créé
        } catch (RuntimeException e) {
            System.err.println("Erreur métier : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Pour toute autre erreur interne
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
