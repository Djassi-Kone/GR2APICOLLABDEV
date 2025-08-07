package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    List<Utilisateur> getAllUtilisateurs();

    Optional<Utilisateur> getUtilisateurById(Long id);

    Utilisateur createUtilisateur(Utilisateur utilisateur);

    Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur);

    void deleteUtilisateur(Long id);
}
