package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.GestionnaireDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutDemande;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.GestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestionnaireServiceImpl implements GestionnaireService {
    @Autowired
    private final GestionnaireRepository gestionnaireRepository;
    @Autowired
    private final ContributionRepository contributionRepository;
    @Autowired
    private final DemandeRepository demandeRepository;
    @Autowired
    private final ContributeurRepository contributeurRepository;

    public GestionnaireServiceImpl(GestionnaireRepository gestionnaireRepository,
                                   ContributionRepository contributionRepository,
                                   DemandeRepository demandeRepository,
                                   ContributeurRepository contributeurRepository) {
        this.gestionnaireRepository = gestionnaireRepository;
        this.contributionRepository = contributionRepository;
        this.demandeRepository = demandeRepository;
        this.contributeurRepository = contributeurRepository;
    }

    // Valider une contribution
    public void validerContribution(GestionnaireDTO gestionnaireDTO) {
        Optional<Contribution> contributionOpt = contributionRepository.findById(gestionnaireDTO.getContributionId());
        Optional<Gestionnaire> gestionnaireOpt = gestionnaireRepository.findById(gestionnaireDTO.getId());

        if (contributionOpt.isPresent() && gestionnaireOpt.isPresent()) {
            Contribution contribution = contributionOpt.get();
            contribution.setValidee(true);
            contributionRepository.save(contribution);

            // Attribuer des coins si nécessaire
            if (gestionnaireDTO.getCoinsAAttribuer() > 0) {
                //Contributeur contributeur = contribution.getContributeur();
                // contributeur.setCoin(contributeur.getCoins() + gestionnaireDTO.getCoinsAAttribuer());

                //contributeurRepository.save(contributeur);
            }
        } else {
            throw new RuntimeException("Contribution ou Gestionnaire non trouvé");
        }
    }

    // Valider une demande
    public void validerDemande(GestionnaireDTO gestionnaireDTO) {
        Optional<Demande> demandeOpt = demandeRepository.findById(gestionnaireDTO.getDemandeId());
        Optional<Gestionnaire> gestionnaireOpt = gestionnaireRepository.findById(gestionnaireDTO.getId());

        if (demandeOpt.isPresent() && gestionnaireOpt.isPresent()) {
            Demande demande = demandeOpt.get();
            demande.setStatut(StatutDemande.valueOf("VALIDEE"));
            demandeRepository.save(demande);
        } else {
            throw new RuntimeException("Demande ou Gestionnaire non trouvé");
        }
    }

    // Méthodes supplémentaires pour gérer les gestionnaires
    public Gestionnaire getGestionnaireById(Long id) {
        return gestionnaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gestionnaire non trouvé"));
    }

    public Gestionnaire updateGestionnaire(Gestionnaire gestionnaire) {
        return gestionnaireRepository.save(gestionnaire);
    }
}