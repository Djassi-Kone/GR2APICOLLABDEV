package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.LoginRequest;
import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import com.apicollabdev.odk.collabdev.repository.GestionnaireRepository;
import com.apicollabdev.odk.collabdev.repository.UtilisateurRepository;
import com.apicollabdev.odk.collabdev.security.SessionAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        List<Utilisateur> users = utilisateurRepository
                .findUsersByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Identifiants incorrects");
        }

        Utilisateur user = users.get(0); // récupère le premier utilisateur
        String fakeToken = UUID.randomUUID().toString();
        SessionAuth.sessions.put(fakeToken, user.getId());

        List<String> roles = new ArrayList<>();
        roles.add(user.getClass().getSimpleName());

        // Vérifie si l'utilisateur est aussi gestionnaire
        gestionnaireRepository.findById(user.getId()).ifPresent(g -> {
            if (!roles.contains("Gestionnaire")) roles.add("Gestionnaire");
        });

        Map<String, Object> response = new HashMap<>();
        response.put("token", fakeToken);
        response.put("roles", roles);
        response.put("id", user.getId());
        response.put("message", "Connexion réussie");

        return ResponseEntity.ok(response);
    }

}
