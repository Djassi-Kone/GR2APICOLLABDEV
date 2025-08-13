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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

    @PutMapping(
            value = "/{idBadge}/administrateur/{idAdmin}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Badge> updateBadge(
            @PathVariable Long idBadge,
            @PathVariable Long idAdmin,
            @RequestBody BadgeDTO dto
    ) {
        return ResponseEntity.ok(badgeService.updateBadge(idBadge, dto, idAdmin));
    }


    @GetMapping("/contributeur/{id}")
    public ResponseEntity<?> getBadgesByContributeur(@PathVariable Long id) {
        try {
            Contributeur contributeur = contributeurRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));
            List<BadgeDTO> dtos = contributeur.getBadges().stream().map(badge -> {
                BadgeDTO dto = new BadgeDTO();
                dto.setNom(badge.getNom());
                dto.setDescription(badge.getDescription());
                dto.setImage(badge.getImage());
                dto.setNombre(badge.getNombre());
                return dto;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne : " + e.getMessage());
        }
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
