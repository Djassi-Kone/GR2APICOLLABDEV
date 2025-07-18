/*
package com.apicollabdev.odk.collabdev.Auth;

import org.springframework.security.authentication.AuthenticationManager;

public class AuthController {

    private final AuthenticationManager authenticationManager;


    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
*/

import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    // Supprime AuthenticationManager du constructeur

    public AuthController() {
        // ou mets ce que tu veux
    }

    // ...
}
