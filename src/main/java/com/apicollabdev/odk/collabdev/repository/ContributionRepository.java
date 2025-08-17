package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    Contribution save(Contribution contribution);
    Optional<Contribution> findById(Long contributionId);
    @Query("SELECT c FROM Contribution c WHERE c.contributeur.id = :idContributeur")
    List<Contribution> findContributionByContributeur(@Param("idContributeur") Long idContributeur);
    List<Contribution> findByContributeurIdAndProjetIdProjet(Long idContributeur, Long idProjet);


}

