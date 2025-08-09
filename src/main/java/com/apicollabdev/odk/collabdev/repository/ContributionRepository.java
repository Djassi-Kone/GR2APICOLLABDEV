package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Contribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    Contribution save(Contribution contribution);
    List<Contribution> findByFonctionnaliteIdFonctionnalite(int fonctionnaliteId);
    List<Contribution> findByContributeurId(Long contributeurId);
}

