package com.apicollabdev.odk.collabdev.controller;
import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/demandeParticipation")
@RequiredArgsConstructor
public class DemandeParticipationController {
    @Autowired
    private DemandeParticipationService demandeParticipationService;

    @PostMapping
    public ResponseEntity<DemandeParticipation> create(@RequestBody DemandeParticipation demandeParticipation) {
        return ResponseEntity.ok(demandeParticipationService.createDemandeParticipation(demandeParticipation));
    }

    @GetMapping
    public List<DemandeParticipation> getAll() {
        return demandeParticipationService.getAllDemandeParticipation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeParticipation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(demandeParticipationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        demandeParticipationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
