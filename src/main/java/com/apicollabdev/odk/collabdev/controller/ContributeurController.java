package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.ContributeurDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.mapper.ContributeurMapper;
import com.apicollabdev.odk.collabdev.service.Impl.ContributeurServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contributeurs")
public class ContributeurController {
    @Autowired
    private  ContributeurServiceImpl contributeurServiceimpl;



    @PostMapping("/inscription")
    public Contributeur CreerCompte(@RequestBody ContributeurDTO dto) {
        Contributeur contributeur = ContributeurMapper.toEntity(dto);
        return contributeurServiceimpl.CreerCompte(contributeur);
    }

    @PostMapping("/connexion")
    public Contributeur connexion(@RequestParam String email, @RequestParam String password) {
        return contributeurServiceimpl.connexion(email, password);
    }

    @GetMapping("/deconnexion/{id}")
    public void deconnexion(@PathVariable Long id) {
        contributeurServiceimpl.deconnexion(id);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('id')")
    public Contributeur getById(@PathVariable Long id) {
        return contributeurServiceimpl.getContributeurById(id);
    }

    @GetMapping
    public List<Contributeur> getAll() {
        return contributeurServiceimpl.getAllContributeurs();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('id')")
    public void delete(@PathVariable Long id) {
        contributeurServiceimpl.deleteContributeur(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Contributeur> mettreAJourContributeur(
            @PathVariable Long id,
            @RequestBody ContributeurDTO dto) {

        Contributeur contributeur = ContributeurMapper.toEntity(dto);
        Contributeur misAJour = contributeurServiceimpl.mettreAJourContributeur(id, contributeur);
        return ResponseEntity.ok(misAJour);
    }

}
