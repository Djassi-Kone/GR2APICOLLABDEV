// com.apicollabdev.odk.collabdev.controller.FonctionnaliteController.java
package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.service.Impl.FonctionnaliteServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fonctionnalites")
public class FonctionnaliteController {

    @Autowired
    private FonctionnaliteServiceImpl fonctionnaliteServiceImpl;

    public FonctionnaliteController(FonctionnaliteService fonctionnaliteService) {
        this.fonctionnaliteServiceImpl = fonctionnaliteServiceImpl;
    }

    @PostMapping("/creer/{idGestionnaire}")
    public ResponseEntity<FonctionnaliteDTO> creerFonctionnalite(
            @PathVariable Long idGestionnaire,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("point") String point,
            @RequestParam("statut") String statut,
            @RequestParam("projetId") Long projetId,
            @RequestParam(value = "cahierDeCharge", required = false) MultipartFile cahierDeChargeFile
    ) {
        // Construire le DTO
        FonctionnaliteDTO dto = new FonctionnaliteDTO();
        dto.setFonctionnaliteNom(nom);
        dto.setFonctionnaliteDescription(description);
        dto.setPointFonctionnalite(point);
        dto.setStatut(statut);
        dto.setProjetId(projetId);
        dto.setCahierDeCharge(cahierDeChargeFile);

        FonctionnaliteDTO saved = fonctionnaliteServiceImpl.creerFonctionnalite(dto, idGestionnaire);
        return ResponseEntity.ok(saved);
    }

    // Modifier une fonctionnalité
    @PutMapping("/modifier/{id}")
    public ResponseEntity<FonctionnaliteDTO> modifierFonctionnalite(
            @PathVariable Long id,
            @RequestBody FonctionnaliteDTO dto
    ) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.modifierFonctionnalite(id, dto));
    }

    //Supprimer une fonctionnalité
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerFonctionnalite(@PathVariable Long id) {
        fonctionnaliteServiceImpl.supprimerFonctionnalite(id);
        return ResponseEntity.noContent().build();
    }

    // Lister toutes les fonctionnalités d’un projet
    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<FonctionnaliteDTO>> listerFonctionnalitesParProjet(@PathVariable Long idProjet) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.listerFonctionnalitesParProjet(idProjet));
    }

    // Récupérer une fonctionnalité par son ID
    @GetMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> getFonctionnaliteParId(@PathVariable Long id) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.getFonctionnaliteParId(id));
    }

    // Lister toutes les fonctionnalités créées par un gestionnaire
    @GetMapping("/gestionnaire/{idGestionnaire}")
    public ResponseEntity<List<FonctionnaliteDTO>> listerFonctionnalitesParGestionnaire(@PathVariable Long idGestionnaire) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.listerFonctionnalitesParGestionnaire(idGestionnaire));
    }


    /*@GetMapping
    public ResponseEntity<List<FonctionnaliteDTO>> getAll() {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.ListerFonctionnalite());
    }

    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<FonctionnaliteDTO>> getByProjet(@PathVariable Long idProjet) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.listerFonctionnalitesParProjet(idProjet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fonctionnaliteServiceImpl.ListeFonctionnaliteParId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> update(@PathVariable Long id, @RequestBody FonctionnaliteDTO dto) {
        FonctionnaliteDTO updated = fonctionnaliteServiceImpl.modifierFonctionnalite(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fonctionnaliteServiceImpl.supprimerFonctionnalite(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

     */
}
