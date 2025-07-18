/*package com.apicollabdev.odk.collabdev.service;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
    @RequiredArgsConstructor
    public class CustomerContributeurDetailsService implements UserDetailsService {

        private final ContributeurRepository contributeurRepository;

        @Override
        public UserDetails loadUserByUsername(String nom) {
            Contributeur contributeur = contributeurRepository.findByNom(nom)
                    .orElseThrow(() -> new UsernameNotFoundException("Contributeur non trouvé"));

            return new ContributeurDetails(
                    contributeur.getNom(),
                    contributeur.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(contributeur.getProfil().name()))
            );
        }
    */
