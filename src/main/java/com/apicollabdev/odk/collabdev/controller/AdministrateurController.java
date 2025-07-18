package com.apicollabdev.odk.collabdev.controller;

// Importation des classes nécessaires

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController // Indique que cette classe est un contrôleur REST
@RequestMapping("/admin") // Tous les endpoints ici auront le préfixe "/admin"
public class AdministrateurController {

    // Injection du repository pour accéder aux administrateurs en base
    @Autowired
    private AdministrateurRepository administrateurRepository;

    // Instanciation d'un encodeur de mot de passe (utilisé ici pour la vérification uniquement)
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Classe interne servant de modèle pour recevoir les données de la requête de connexion (DTO)
    public static class LoginRequest {
        public String email;     // L'email fourni par l'utilisateur
        public String password;  // Le mot de passe fourni par l'utilisateur
    }

    // Endpoint POST pour gérer la connexion d'un administrateur
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // On cherche l'administrateur par son email
        Optional<Administrateur> adminOpt = administrateurRepository.findByEmail(request.email);

        // Si aucun administrateur non trouvé, on retourne une erreur 404
        if (adminOpt.isEmpty()) {
            return ResponseEntity.status(404).body(" Administrateur introuvable !");
        }

        // Sinon, on récupère l'administrateur
        Administrateur admin = adminOpt.get();

        // Vérification du mot de passe entré avec celui (haché) en base
        if (!passwordEncoder.matches(request.password, admin.getPassword())) {
            return ResponseEntity.status(401).body(" Mot de passe incorrect !");
        }

        // Si tout est correct, on retourne une réponse de succès
        return ResponseEntity.ok(" Connexion réussie. BIENVENU ! " + admin.getEmail());
    }
}
