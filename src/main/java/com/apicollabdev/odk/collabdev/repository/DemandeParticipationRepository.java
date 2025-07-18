package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeParticipationRepository extends JpaRepository<DemandeParticipation, Integer> {

    List<DemandeParticipation> findByContributeur(Contributeur contributeur);
}
