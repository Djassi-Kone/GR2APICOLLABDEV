package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.LoginRequest;
import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import com.apicollabdev.odk.collabdev.repository.UtilisateurRepository;
import com.apicollabdev.odk.collabdev.security.SessionAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private  UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Utilisateur> userOpt = utilisateurRepository
                .findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            String fakeToken = UUID.randomUUID().toString(); // Génère un identifiant aléatoire

            // Stocker ce token quelque part, comme dans une map statique ou une base temporaire
            SessionAuth.sessions.put(fakeToken, user.getId());

            return ResponseEntity.ok(Map.of(
                    "token", fakeToken,
                    "message", "Connexion réussie"
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }
}
