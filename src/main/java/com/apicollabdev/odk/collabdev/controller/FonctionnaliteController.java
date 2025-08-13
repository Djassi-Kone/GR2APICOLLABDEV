// com.apicollabdev.odk.collabdev.controller.FonctionnaliteController.java
package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.service.Impl.FonctionnaliteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fonctionnalites")
public class FonctionnaliteController {

    @Autowired
    private FonctionnaliteServiceImpl fonctionnaliteService;

    @PostMapping("/gestionnaire/{idGestionnaire}/newfonctionnalite")
    public ResponseEntity<FonctionnaliteDTO> create(@RequestBody FonctionnaliteDTO dto, @PathVariable("idGestionnaire") Long id_gestionnaire) {
        FonctionnaliteDTO created = fonctionnaliteService.creerFonctionnalite(dto, id_gestionnaire);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FonctionnaliteDTO>> getAll() {
        return ResponseEntity.ok(fonctionnaliteService.ListerFonctionnalite());
    }

    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<FonctionnaliteDTO>> getByProjet(@PathVariable Long idProjet) {
        return ResponseEntity.ok(fonctionnaliteService.listerFonctionnalitesParProjet(idProjet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fonctionnaliteService.ListeFonctionnaliteParId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> update(@PathVariable Long id, @RequestBody FonctionnaliteDTO dto) {
        FonctionnaliteDTO updated = fonctionnaliteService.modifierFonctionnalite(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fonctionnaliteService.supprimerFonctionnalite(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
