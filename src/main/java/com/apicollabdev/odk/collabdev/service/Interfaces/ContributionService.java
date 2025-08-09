package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contribution;

import java.util.List;

public interface ContributionService {
    public List<Contribution> getAllContributions();
    public Contribution getById(Long id);
    public void deleteById(Long id);
    public Contribution reserverFonctionnalite(Long idFonctionnalite, Long idContributeur);
    public Contribution deposerContribution(Long idFonctionnalite, Long idContributeur, String urlCode);
    public Contribution validerContribution(Long idContribution);
    public Contribution rejeterContribution(Long idContribution);





}
