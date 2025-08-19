package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.DemandeProjetDto;
import com.apicollabdev.odk.collabdev.dto.ProjetDTO;
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
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/gestionnaire/valides/{idContributeur}")
    public ResponseEntity<List<Projet>> getProjetsGestionnaireValides(@PathVariable Long idContributeur) {
        List<Projet> projets = demandeParticipationServiceImpl.getProjetsOuJeSuisGestionnaire(idContributeur);
        return ResponseEntity.ok(projets);
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

    // Récupérer toutes les demandes pour un projet donné
    @GetMapping("/projet/{idProjet}")
    public List<DemandeProjetDto> getDemandesByProjet(@PathVariable Long idProjet) {
        return demandeParticipationServiceImpl.getDemandesDTOByProjet(idProjet);
    }



    // Accepter une demande de participation en tant que contributeur
    @PutMapping("/contributeur/accepter/{idDemande}")
    public ResponseEntity<ProjetDTO> accepterDemandeContributeur(@PathVariable Long idDemande) {
        try {
            Projet projet = demandeParticipationServiceImpl.accepterDemandeContributeur(idDemande);
            return ResponseEntity.ok(new ProjetDTO());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/projets-acceptes/{idContributeur}")
    public ResponseEntity<List<Projet>> getProjetsAcceptes(@PathVariable Long idContributeur) {
        List<Projet> projets = demandeParticipationServiceImpl.getProjetsAcceptesParContributeur(idContributeur);
        return ResponseEntity.ok(projets);
    }


    @GetMapping("/gestionnaire")
    public ResponseEntity<List<DemandeParticipation>> getDemandesGestionnaire() {
        List<DemandeParticipation> demandes = demandeParticipationServiceImpl.getDemandesGestionnaire();
        return ResponseEntity.ok(demandes);
    }


}
