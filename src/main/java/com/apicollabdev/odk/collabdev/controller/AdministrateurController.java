package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministrateurController {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    // DTO pour login
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Recherche de l'admin en base
        Optional<Administrateur> adminOpt = administrateurRepository.findByEmail(request.email);

        if (adminOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Administrateur introuvable !");
        }

        Administrateur admin = adminOpt.get();

        // Comme le mot de passe n'est pas haché, on compare directement
        if (!request.password.equals(admin.getPassword())) {
            return ResponseEntity.status(401).body("Mot de passe incorrect !");
        }

        // Réponse JSON pour le front
        return ResponseEntity.ok(new Object() {
            public String message = "Connexion réussie. BIENVENU !";
            public String email = admin.getEmail();
            public String[] roles = new String[]{"Administrateur"};
            public String token = "admin-token"; // token statique pour test
            public long id = admin.getId(); // id réel depuis la base
        });
    }

}
