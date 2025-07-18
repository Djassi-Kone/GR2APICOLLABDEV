package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.IdeeProjet;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeeProjetRepository extends JpaRepository<IdeeProjet, Long> {
    List<IdeeProjet> findByStatut(StatutIdee statut);

}

