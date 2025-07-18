/*package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.config.JwtUtils;
import com.apicollabdev.odk.collabdev.dto.ContributeurDTO;
import com.apicollabdev.odk.collabdev.dto.LoginRequest;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ContributeurService contributeurService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNom(),
                            request.getPassword()
                    )
            );

            String jwt = jwtUtils.generateToken(authentication.getName());
            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ContributeurDTO dto) {
        if (contributeurService.existsByNom(dto.getNom())) {
            return ResponseEntity.badRequest().body("Ce nom est déjà utilisé");
        }

        Contributeur newContributeur = contributeurService.createContributeur();
        return ResponseEntity.status(HttpStatus.CREATED).body(newContributeur);
    }
}
*/
