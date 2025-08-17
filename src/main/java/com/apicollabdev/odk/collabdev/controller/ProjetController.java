package com.apicollabdev.odk.collabdev.controller;


import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Impl.ProjetServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin("*")
public class ProjetController {

    @Autowired
    private ProjetServiceImpl projetServiceImpl;
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;

    public ProjetController(ProjetService projetService, AdministrateurRepository administrateurRepository, ContributeurRepository contributeurRepository) {
        this.projetServiceImpl = projetServiceImpl;
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
    Projet projetCree = projetServiceImpl.createProjetFromIdee(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(projetCree);
}

    // ---------------- Vérifier et clôturer un projet ----------------
    @PostMapping("/{id}/cloturer")
    public ResponseEntity<String> verifierEtCloturerProjet(@PathVariable Long id) {
        projetServiceImpl.verifierEtCloturerProjet(id);
        return ResponseEntity.ok("Vérification et clôture du projet effectuées.");
    }

    // ---------------- Télécharger le projet en ZIP ----------------
    @GetMapping("/{id}/download")
    public ResponseEntity<FileSystemResource> downloadProjet(@PathVariable Long id) throws IOException {
        Projet projet = projetServiceImpl.getProjetById(id, 1L);
        File zipFile = projetServiceImpl.genererZipProjet(projet);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(zipFile));
    }


    @GetMapping("/{id_contributeur}")
    public Projet getById(@RequestParam Long id, @PathVariable Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Ce contributeur n'existe pas"));
        return projetServiceImpl.getProjetById(id, id_contributeur);
    }

    @GetMapping("/gestionnaire/{idNewGestionnaire}")
    public ResponseEntity<List<Projet>> getProjetsByNewGestionnaire(
            @PathVariable("idNewGestionnaire") Long idNewGestionnaire) {

        List<Projet> projets = projetServiceImpl.getProjetsByNewGestionnaire(idNewGestionnaire);

        if (projets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(projets);
    }

    @GetMapping("/recupere/id_contributeur/{idContributeur}")
    public List<Projet> getAllProjets(@PathVariable Long idContributeur) {
        return projetServiceImpl.getAllProjets(idContributeur);
    }


    @GetMapping("/allprojets")
    public ResponseEntity<List<Projet>> getAllProjetsSysteme() {
        List<Projet> projets = projetServiceImpl.getAllProjetsSysteme();
        return ResponseEntity.ok(projets);
    }

    @DeleteMapping("supprime/{idAdmin}")
    public ResponseEntity<String> deleteProjet(@RequestParam Long id, @PathVariable Long idAdmin) {
        try {
            projetServiceImpl.deleteProjet(id, idAdmin);
            return ResponseEntity.ok("Projet supprimé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
