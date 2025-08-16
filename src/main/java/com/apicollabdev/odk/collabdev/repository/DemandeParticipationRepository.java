package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeParticipationRepository extends JpaRepository<DemandeParticipation, Long> {


    @Override
    java.util.Optional<DemandeParticipation> findById(Long idDemande);
    List<DemandeParticipation> findByContributeur(Contributeur contributeur);
}
