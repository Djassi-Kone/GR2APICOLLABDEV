package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.DebloqueProjetDTO;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;
import com.apicollabdev.odk.collabdev.service.Interfaces.DebloqueProjetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debloqueProjet")
@RequiredArgsConstructor
public class DebloqueProjetController {

    @Autowired
    private DebloqueProjetService debloqueProjetService;

    @PostMapping("/contributeur/{idcontributeur}")
    public ResponseEntity<DebloqueProjet> create(
            @RequestBody DebloqueProjetDTO debloqueProjetDTO,
            @PathVariable("idcontributeur") Long idContributeur
    ) {
        return ResponseEntity.ok(debloqueProjetService.createDebloqueProjet(debloqueProjetDTO, idContributeur));
    }

    @GetMapping
    public List<DebloqueProjet> getAll() {
        return debloqueProjetService.getAllDebloqueProjet();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebloqueProjet> getById(@PathVariable Long id) {
        return ResponseEntity.ok(debloqueProjetService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        debloqueProjetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DebloqueProjet> update(
            @PathVariable int id,
            @RequestBody DebloqueProjetDTO debloqueProjetDTO
    ) {
        return ResponseEntity.ok(debloqueProjetService.updateDebloqueProjet(id, debloqueProjetDTO));
    }
}
