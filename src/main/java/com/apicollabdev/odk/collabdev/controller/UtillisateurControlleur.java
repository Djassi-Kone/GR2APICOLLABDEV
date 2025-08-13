package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import com.apicollabdev.odk.collabdev.service.Impl.UtilisateurServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/utilisateurs")
public class UtillisateurControlleur {

    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurServiceImpl.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        return utilisateurServiceImpl.getUtilisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurServiceImpl.createUtilisateur(utilisateur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        try {
            return ResponseEntity.ok(utilisateurServiceImpl.updateUtilisateur(id, utilisateur));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurServiceImpl.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
