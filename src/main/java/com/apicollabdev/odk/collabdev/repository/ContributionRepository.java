package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;

import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

   // String ajouterContribution(ContributionDTO dto, Long idFonctionnalite);
    Contribution save(Contribution contribution);
    Optional<Contribution> findById(Long contributionId);
    @Query("SELECT c FROM Contribution c WHERE c.contributeur.id = :idContributeur")
    List<Contribution> findContributionByContributeur(@Param("idContributeur") Long idContributeur);
   // List<Contribution> findContributionByContributeurIdAndProjetId(Long idContributeur, Long idProjet);
   List<Contribution> findByProjetIdProjet(Long idProjet);

    List<Contribution> findByProjetIdProjetAndContributeurId(Long idProjet, Long idContributeur);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Contribution c " +
            "WHERE c.contributeur.id = :idContributeur " +
            "AND c.fonctionnalite.idFonctionnalite = :idFonctionnalite")
    boolean existsByContributeurAndFonctionnalite(
            @Param("idContributeur") Long idContributeur,
            @Param("idFonctionnalite") Long idFonctionnalite);

    @Query("SELECT c FROM Contribution c " +
            "WHERE c.contributeur.id = :idContributeur " +
            "AND c.fonctionnalite.idFonctionnalite = :idFonctionnalite")
    Optional<Contribution> findByContributeurAndFonctionnalite(
            @Param("idContributeur") Long idContributeur,
            @Param("idFonctionnalite") Long idFonctionnalite);




}

