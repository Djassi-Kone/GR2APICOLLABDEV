package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;

import java.util.List;

public interface ContributionService {
    Contribution createContribution(ContributionDTO contribution, long idProjet, long idContributeur);
    List<Contribution> getAllContributions();
    Contribution getById(Long id);
    void deleteById(Long id);
}
