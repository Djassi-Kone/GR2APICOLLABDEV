package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.BadgeDTO;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Badge;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.BadgeRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    @Autowired
    private  BadgeService badgeService;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;
    @PostMapping(
            value = "/administrateur/{idadmin}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Badge> create(@RequestBody BadgeDTO dto, @PathVariable("idadmin") long idAmin ) {
        Administrateur a = administrateurRepository.findById(idAmin)
                .orElseThrow( ()-> new RuntimeException("Administrateur non trouvé"));
        return ResponseEntity.ok(badgeService.createBadge(dto, idAmin));
    }

    @GetMapping("/contributeur/{id}")
    public ResponseEntity<List<Badge>> getBadgesByContributeur(@PathVariable Long id) {
        Contributeur contributeur = contributeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));
        return ResponseEntity.ok(contributeur.getBadges());
    }

    @GetMapping
    public List<Badge> getAll() {
        return badgeService.getAllBadges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getById(@PathVariable Long id) {
        return ResponseEntity.ok(badgeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        badgeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
