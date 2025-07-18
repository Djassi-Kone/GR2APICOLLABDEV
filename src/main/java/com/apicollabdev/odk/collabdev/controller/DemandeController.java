package com.apicollabdev.odk.collabdev.controller;


import com.apicollabdev.odk.collabdev.dto.DemandeDTO;
import com.apicollabdev.odk.collabdev.entity.Demande;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/demandes")
public class DemandeController {
    @Autowired
    private  DemandeService demandeService;

    @PostMapping("/projet/{idProjet}/contributeur/{idContributeur}")
    public ResponseEntity<Demande> faireDemande(
            @PathVariable Long idProjet,
            @PathVariable Long idContributeur,
            @RequestBody String description
    ) {
        Demande demande = demandeService.creerDemande(idProjet, idContributeur, description);
        return ResponseEntity.ok(demande);
    }

    @PostMapping("/{idcontributeur}/projets/{idprojet}")
    public ResponseEntity<Demande> create(@RequestBody DemandeDTO demandeDTO, @PathVariable("idcontributeur") long idContributeur, @PathVariable("idprojet") long idProjet) {
        return ResponseEntity.ok(demandeService.createDemande(demandeDTO, idContributeur, idProjet));
    }

    @GetMapping
    public List<Demande> getAll() {
        return demandeService.getAllDemandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getById(@PathVariable Long id) {
        return ResponseEntity.ok(demandeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        demandeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
