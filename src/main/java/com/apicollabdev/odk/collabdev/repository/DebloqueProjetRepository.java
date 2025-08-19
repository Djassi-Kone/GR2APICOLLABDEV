package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;
import com.apicollabdev.odk.collabdev.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebloqueProjetRepository extends JpaRepository<DebloqueProjet, Integer> {
    boolean existsByProjetAndContributeur(Projet projet, Contributeur contributeur);

}
