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
@RequestMapping("/fonctionnalites")
public class FonctionnaliteController {

    @Autowired
    private FonctionnaliteServiceImpl fonctionnaliteService;

    @PostMapping
    public ResponseEntity<FonctionnaliteDTO> create(@RequestBody FonctionnaliteDTO dto) {
        FonctionnaliteDTO created = fonctionnaliteService.creerFonctionnalite(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FonctionnaliteDTO>> getAll() {
        return ResponseEntity.ok(fonctionnaliteService.ListerFonctionnalite());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(fonctionnaliteService.ListeFonctionnaliteParId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FonctionnaliteDTO> update(@PathVariable int id, @RequestBody FonctionnaliteDTO dto) {
        FonctionnaliteDTO updated = fonctionnaliteService.modifierFonctionnalite(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        fonctionnaliteService.supprimerFonctionnalite(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
