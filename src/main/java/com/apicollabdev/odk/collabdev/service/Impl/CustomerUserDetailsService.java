package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // Déclare cette classe comme un service Spring injectable
public class CustomerUserDetailsService implements UserDetailsService {



    @Autowired
    private AdministrateurRepository administrateurRepository;

    /**
     * Méthode appelée automatiquement par Spring Security lors de la tentative de connexion.
     * @paramusernameemail ou identifiant de l'utilisateur
     * @return UserDetails (détails de l'utilisateur pour Spring Security)
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String emailAdmin) throws UsernameNotFoundException {
        // Recherche de l'administrateur par email
        Administrateur admin = administrateurRepository.findByEmail(emailAdmin)
                .orElseThrow(() -> new UsernameNotFoundException(" Administrateur introuvable avec l'email : " + emailAdmin));

        // Création d'un objet UserDetails à partir des données de l'administrateur
        return new User(
                admin.getEmail(),                  // Nom d'utilisateur (email ici)
                admin.getPassword(),               // Mot de passe (déjà encodé en base)
                Collections.singleton(             // Liste des rôles/authorities
                        new SimpleGrantedAuthority("ROLE_" + admin)
                )
        );
    }
}
