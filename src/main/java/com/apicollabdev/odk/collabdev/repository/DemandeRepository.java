package com.apicollabdev.odk.collabdev.repository;



import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.StatutDemandeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<DemandeParticipation, Long> {
    List<DemandeParticipation> findByStatutDemandeParticipation(StatutDemandeParticipation statutDemandeParticipation);
    List<DemandeParticipation> findByProjetIdProjet(Long idProjet);

}

