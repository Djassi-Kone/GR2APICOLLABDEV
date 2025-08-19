package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeDemandeParticipation;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeParticipationRepository extends JpaRepository<DemandeParticipation, Long> {


    @Override
    java.util.Optional<DemandeParticipation> findById(Long idDemande);
    List<DemandeParticipation> findByContributeur(Contributeur contributeur);
    List<DemandeParticipation> findDemandeParticipationByContributeur(Contributeur contributeur);
    List<DemandeParticipation> findByTypeDemandeParticipation(TypeDemandeParticipation typeDemande);
    List<DemandeParticipation> findByContributeurAndTypeDemandeParticipation(Contributeur contributeur, TypeDemandeParticipation typeDemande);


}
