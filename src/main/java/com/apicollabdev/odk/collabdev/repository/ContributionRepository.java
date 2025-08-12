package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    Contribution save(Contribution contribution);
    Optional<Contribution> findById(Long contributionId);
    List<Contribution> findByFonctionnaliteIdFonctionnalite(int fonctionnaliteId);
    List<Contribution> findByContributeurId(Long contributeurId);
}

