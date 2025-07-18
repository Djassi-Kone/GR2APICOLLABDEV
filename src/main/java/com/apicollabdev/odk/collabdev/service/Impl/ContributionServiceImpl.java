package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.ContributionDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Contribution;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.ContributionRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContributionServiceImpl implements ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;
    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   ContributeurRepository contributeurRepository,
                                   ProjetRepository projetRepository) {
        this.contributionRepository = contributionRepository;
        this.contributeurRepository = contributeurRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public Contribution createContribution(ContributionDTO contributionDTO, long idProjet, long idContributeur) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        Projet p = projetRepository.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        Contribution contribution = new Contribution();

        contribution.setTitre(contributionDTO.getTitre());
        contribution.setContenu(contributionDTO.getContenu());
        contribution.setType(contributionDTO.getType());
        contribution.setProjet(p);
        contribution.setContributeur(c);
        contribution.setDateSoumission(LocalDateTime.now());

        contributionRepository.save(contribution);
        return contribution;
    }

    @Override
    public List<Contribution> getAllContributions() {
        return contributionRepository.findAll();
    }

    @Override
    public Contribution getById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        contributionRepository.deleteById(id);
    }
}
