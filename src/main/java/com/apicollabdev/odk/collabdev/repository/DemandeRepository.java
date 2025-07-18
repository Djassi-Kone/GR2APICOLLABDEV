package com.apicollabdev.odk.collabdev.repository;



import com.apicollabdev.odk.collabdev.entity.Demande;
import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByStatut(StatutDemande statut);

}

