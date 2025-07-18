package com.apicollabdev.odk.collabdev.service.Impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class ContributeurDetails implements UserDetails {
    private final String nom;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public ContributeurDetails(String nom, String password,
                               Collection<? extends GrantedAuthority> authorities) {
        this.nom = nom;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Corrigé: retourne les authorities passées au constructeur
    }

    @Override
    public String getPassword() {
        return password; // Corrigé: retourne le vrai mot de passe
    }

    @Override
    public String getUsername() {
        return nom; // Corrigé: retourne le vrai nom
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}