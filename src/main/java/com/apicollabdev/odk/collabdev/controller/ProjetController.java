package com.apicollabdev.odk.collabdev.controller;


import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Impl.ProjetServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin("*")
public class ProjetController {

    @Autowired
    private ProjetServiceImpl projetServiceimpl;
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;

    public ProjetController(ProjetService projetService, AdministrateurRepository administrateurRepository, ContributeurRepository contributeurRepository) {
        this.projetServiceimpl = projetServiceimpl;
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
    Projet projetCree = projetServiceimpl.createProjetFromIdee(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(projetCree);
}

    @GetMapping("/{id_contributeur}")
    public Projet getById(@RequestParam Long id, @PathVariable Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Ce contributeur n'existe pas"));
        return projetServiceimpl.getProjetById(id, id_contributeur);
    }

    @GetMapping("/recupere/id_contributeur/{idContributeur}")
    public List<Projet> getAllProjets(@PathVariable Long idContributeur) {
        return projetServiceimpl.getAllProjets(idContributeur);
    }


    @GetMapping("/allprojets")
    public ResponseEntity<List<Projet>> getAllProjetsSysteme() {
        List<Projet> projets = projetServiceimpl.getAllProjetsSysteme();
        return ResponseEntity.ok(projets);
    }

    @DeleteMapping("supprime/{idAdmin}")
    public ResponseEntity<String> deleteProjet(@RequestParam Long id, @PathVariable Long idAdmin) {
        try {
            projetServiceimpl.deleteProjet(id, idAdmin);
            return ResponseEntity.ok("Projet supprimé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
