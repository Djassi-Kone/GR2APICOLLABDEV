package com.apicollabdev.odk.collabdev.repository;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.enums.StatutDemandeParticipation;
import com.apicollabdev.odk.collabdev.enums.TypeDemandeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemandeParticipationRepository extends JpaRepository<DemandeParticipation, Long> {

    List<DemandeParticipation> findByContributeur(Contributeur contributeur);

    List<DemandeParticipation> findByProjetIdProjet(Long idProjet);

    List<DemandeParticipation> findByContributeurIdAndStatutDemandeParticipationAndTypeDemandeParticipationemande(
            Long contributeurId,
            StatutDemandeParticipation statut,
            TypeDemandeParticipation type
    );

    @Query("SELECT dp.projet FROM DemandeParticipation dp " +
            "WHERE dp.contributeur.id = :idContributeur " +
            "AND dp.statutDemandeParticipation = 'ACCEPTEE'")
    List<Projet> findProjetsAcceptesByContributeur(@Param("idContributeur") Long idContributeur);

}
