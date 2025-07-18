package com.apicollabdev.odk.collabdev.service.Interfaces;


import com.apicollabdev.odk.collabdev.entity.Contributeur;

import java.util.List;

public interface ContributeurService {
    Contributeur CreerCompte(Contributeur contributeur);
    Contributeur connexion(String email, String password);
    Void deconnexion(Long idContributeur);
    Contributeur getContributeurById(Long id);
    List<Contributeur> getAllContributeurs();
    void deleteContributeur(Long id);
    Contributeur mettreAJourContributeur(Long id, Contributeur dto);
}

