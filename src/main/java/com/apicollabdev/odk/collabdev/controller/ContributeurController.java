package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.dto.ContributeurDTO;
import com.apicollabdev.odk.collabdev.dto.LoginRequest;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import com.apicollabdev.odk.collabdev.mapper.ContributeurMapper;
import com.apicollabdev.odk.collabdev.repository.UtilisateurRepository;
import com.apicollabdev.odk.collabdev.security.SessionAuth;
import com.apicollabdev.odk.collabdev.service.Impl.ContributeurServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contributeurs")
@CrossOrigin(origins = "http://localhost:4200")
public class ContributeurController {
    @Autowired
    private  ContributeurServiceImpl contributeurServiceimpl;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    UtilisateurRepository utilisateurRepository;



    @PostMapping("/inscription")
    public Contributeur CreerCompte(@Valid @RequestBody ContributeurDTO dto) {
        Contributeur contributeur = ContributeurMapper.toEntity(dto);
        return contributeurServiceimpl.CreerCompte(contributeur);
    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody LoginRequest loginRequest) {
        List<Utilisateur> users = utilisateurRepository
                .findUsersByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (!users.isEmpty()) {
            Utilisateur user = users.get(0); // On prend le premier

            String fakeToken = UUID.randomUUID().toString();
            SessionAuth.sessions.put(fakeToken, user.getId());

            List<String> roles = new ArrayList<>();
            roles.add(user.getClass().getSimpleName());

            return ResponseEntity.ok(Map.of(
                    "token", fakeToken,
                    "roles", roles,
                    "id", user.getId(),
                    "message", "Connexion réussie"
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }





    @GetMapping("/deconnexion/{id}")
    public void deconnexion(@Valid @PathVariable Long id) {
        contributeurServiceimpl.deconnexion(id);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('id')")
    public Contributeur getById( @PathVariable Long id) {
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
            @Valid @PathVariable Long id,
            @RequestBody ContributeurDTO dto) {

        Contributeur contributeur = ContributeurMapper.toEntity(dto);
        Contributeur misAJour = contributeurServiceimpl.mettreAJourContributeur(id, contributeur);
        return ResponseEntity.ok(misAJour);
    }

}
