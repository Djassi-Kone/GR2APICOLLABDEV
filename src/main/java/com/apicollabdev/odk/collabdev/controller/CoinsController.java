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
@RequiredArgsConstructor()
@CrossOrigin(origins = "http://localhost:4200")
public class CoinsController {


    @Autowired
    private  AdministrateurRepository administrateurRepository;
    @Autowired
    private  CoinsServiceImpl coinsServiceImpl;



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


    @GetMapping("/{id}")
    public ResponseEntity<Coins> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coinsServiceImpl.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coinsServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/administrateur/{idadmin}/contributeur/{idcontrib}")
    public ResponseEntity<Coins> createCoinForContributeur(
            @RequestBody CoinsDTO dto,
            @PathVariable("idadmin") long idAdmin,
            @PathVariable("idcontrib") long idContrib) {

        Coins result = coinsServiceImpl.createCoinsForContributeur(dto, idAdmin, idContrib);
        return ResponseEntity.ok(result);
    }
}

