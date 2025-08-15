package com.apicollabdev.odk.collabdev.controller;
import com.apicollabdev.odk.collabdev.dto.CoinsDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.service.Impl.CoinsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CoinsController {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private CoinsServiceImpl coinsServiceImpl;

    // Création de coins par un administrateur
    @PostMapping(
            value = "/administrateur/{idAdmin}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Coins> create(
            @RequestBody CoinsDTO dto,
            @PathVariable("idAdmin") long idAdmin
    ) {
        return ResponseEntity.ok(coinsServiceImpl.createCoins(dto, idAdmin));
    }

    // Création de coins pour un contributeur par un gestionnaire
    // Création simple de coins par un gestionnaire pour un contributeur
    @PostMapping("/gestionnaire/coins")
    public ResponseEntity<Coins> createCoinByGestionnaire(@RequestBody CoinsDTO dto) {
        // Appel du service pour créer le coin et l’assigner directement au contributeur
        Coins result = coinsServiceImpl.createCoinsForContributeurByGestionnaire(
                dto, dto.getIdGestionnaire(), dto.getIdContributeur());

        return ResponseEntity.ok(result);
    }




    // Récupération de tous les coins
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Coins> coinsList = coinsServiceImpl.getAllCoins();
            return ResponseEntity.ok(coinsList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur serveur: " + e.getMessage()));
        }
    }

    // Récupération d'un coin par ID
    @GetMapping("/{id}")
    public ResponseEntity<Coins> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coinsServiceImpl.getById(id));
    }

    // Suppression d'un coin par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coinsServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gestionnaire/{idGest}")
    public ResponseEntity<List<Coins>> getCoinsByGestionnaire(@PathVariable Long idGest) {
        List<Coins> coinsList = coinsServiceImpl.getCoinsByGestionnaire(idGest);
        return ResponseEntity.ok(coinsList);
    }


}

