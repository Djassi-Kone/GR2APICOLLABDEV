package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.DomaineRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributeurService;
import com.apicollabdev.odk.collabdev.service.Interfaces.DomaineService;
import com.apicollabdev.odk.collabdev.service.Interfaces.IdeeProjetService;
import com.apicollabdev.odk.collabdev.service.Impl.IdeeProjetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/ideeProjets")
public class IdeeProjetController {
    @Autowired
    private IdeeProjetService ideeProjetService;

    @Autowired
    private ContributeurService contributeurService;
     @Autowired
     private ContributeurRepository contributeurRepository;
     @Autowired
     private DomaineRepository domaineRepository;

     @Autowired
     private DomaineService domaineService;

     @Autowired
     private IdeeProjetServiceImpl ideeProjetServiceImpl;


     private Domaine domaine;
     //  Constructeur pour injecter le service
    @Autowired
    public IdeeProjetController(IdeeProjetService ideeProjetService) {
        this.ideeProjetService = ideeProjetService;
    }

    // Créer une idée de projet

    @PostMapping("/contributeur/{idContributeur}/domaine/{idDomaine}")
    public ResponseEntity<IdeeProjet> createIdeeProjet(
            @RequestBody CreateIdeeProjetDTO dto,
            @PathVariable("idContributeur") Long idContributeur,
            @PathVariable("idDomaine") Long idDomaine
    ) {
        IdeeProjet created = ideeProjetServiceImpl.createIdeeProjet(dto, idContributeur, idDomaine);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @PostMapping("/transformer-en-projet/{idIdee}")
    public ResponseEntity<Projet> transformerEnProjet(@PathVariable Long idIdee) {
        Projet projet = ideeProjetServiceImpl.transformerIdeeEnProjet(idIdee);
        return ResponseEntity.ok(projet);
    }


    // Obtenir toutes les idées
    @GetMapping
    public ResponseEntity<List<IdeeProjet>> getAll() {
        List<IdeeProjet> list = ideeProjetService.getAllIdeeProjet();
        return ResponseEntity.ok(list);
    }

    //  Obtenir une idée par ID
    @GetMapping("/{id}")
    public ResponseEntity<IdeeProjet> getById(@PathVariable Long id) {
        IdeeProjet idee = ideeProjetService.getById((long) Math.toIntExact(id));
        return ResponseEntity.ok(idee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ideeProjetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // Mettre à jour une idée
    @PutMapping("/{id}")
    public ResponseEntity<IdeeProjet> update(@PathVariable Long id, @RequestBody IdeeProjet updated) {
        IdeeProjet ideeUpdated = ideeProjetService.updateIdeeProjet((long) Math.toIntExact(id), updated);
        return ResponseEntity.ok(ideeUpdated);
    }

}
