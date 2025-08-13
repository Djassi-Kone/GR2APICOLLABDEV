package com.apicollabdev.odk.collabdev.controller;


import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.dto.ProjetDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.ProjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {


    private final ProjetService projetService;
    private final AdministrateurRepository administrateurRepository;
    private final ContributeurRepository contributeurRepository;

    public ProjetController(ProjetService projetService, AdministrateurRepository administrateurRepository, ContributeurRepository contributeurRepository) {
        this.projetService = projetService;
        this.administrateurRepository = administrateurRepository;
        this.contributeurRepository = contributeurRepository;
    }

/*
    @PostMapping("/{id_contributeur}")
    public Projet create(@RequestBody Projet p,@PathVariable("id_contributeur") long id_contributeur) {
      Contributeur contributeur = contributeurRepository.findById(id_contributeur).
                orElseThrow(()-> new RuntimeException("Ce contributeur n'existe pas n'existe pas"));


        return projetService.createProjet(p,id_contributeur);
    }*/
@PostMapping("/create-from-idee")
public ResponseEntity<Projet> createProjet(@RequestBody CreateProjetRequest request) {
    Projet projetCree = projetService.createProjetFromIdee(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(projetCree);
}

    @GetMapping("/{id_contributeur}")
    public Projet getById(@RequestParam Long id, @PathVariable Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Le administrateur n'existe pas"));
        return projetService.getProjetById(id, id_contributeur);
    }

    @GetMapping("recupere/{id_contributeur}")
    public ResponseEntity<List<ProjetDTO>> getAllProjetsDTO(@PathVariable Long id_contributeur) {
        List<Projet> projets = projetService.getAllProjets(id_contributeur);
        System.out.println("Nombre de projets récupérés : " + projets.size());

        List<ProjetDTO> dtos = projets.stream().map(p -> {
            System.out.println("Conversion du projet: " + p.getTitre());
            ProjetDTO dto = new ProjetDTO();
            dto.setTitre(p.getTitre());
            dto.setDescription(p.getDescription());
            dto.setStatut(p.getStatut() != null ? p.getStatut().name() : null);
            dto.setCahierDeCharge(Boolean.toString(p.isCahierDeCharge()));
            dto.setDomaineId(p.getDomaine() != null ? p.getDomaine().getIdDomaine() : null);
            if(p.getGestionnaire() != null) {
                System.out.println("Gestionnaire id: " + p.getGestionnaire().getId());
                dto.setGestionnaireId(p.getGestionnaire().getId());
            } else {
                System.out.println("Gestionnaire null");
                dto.setGestionnaireId(null);
            }
            dto.setIdeeProjetId(p.getIdeeProjet() != null ? p.getIdeeProjet().getIdIdeeProjet() : null);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/recupere/id_contributeur/{id_contributeur}")
    public List<Projet> getAllProjets(@PathVariable Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Le contributeur n'existe pas"));
        return projetService.getAllProjets(id_contributeur);
    }



    @DeleteMapping("supprime/{idAdmin}")
    public ResponseEntity<String> deleteProjet(@RequestParam Long id, @PathVariable Long idAdmin) {
        try {
            projetService.deleteProjet(id, idAdmin);
            return ResponseEntity.ok("Projet supprimé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping
    public List<Projet> getAllProjets() {
        return projetService.getAllProjetsSansFiltre();
    }

}
