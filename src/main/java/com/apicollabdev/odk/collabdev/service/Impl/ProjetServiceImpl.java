package com.apicollabdev.odk.collabdev.service.Impl;


import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjetServiceImpl implements ProjetService {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;

    private  IdeeProjet ideeProjet;

    @Autowired
    private IdeeProjetRepository ideeProjetRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private DomaineRepository domaineRepository;

   /* @Override
    public Projet createProjet(Projet projet, Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));
        projet.setTitre(projet.getTitre());
        projet.setDescription(projet.getDescription());
        projet.setDate(projet.getDate());
        projet.setCahierDeCharge(projet.isCahierDeCharge());
        projet.setStatut(EN_COURS);
        return projetRepository.save(projet);
    } */
    @Override
    public Projet createProjetFromIdee(CreateProjetRequest request) {
        IdeeProjet idee = ideeProjetRepository.findById(request.getIdIdee())
                .orElseThrow(() -> new RuntimeException("Idée introuvable"));

        if (!idee.getStatut().equals(StatutIdee.ACCEPTEE)) {
            throw new IllegalStateException("L'idée doit être acceptée avant de devenir un projet.");
        }

        Contributeur contributeur = contributeurRepository.findById(request.getIdContributeur())
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable"));

        Gestionnaire gestionnaire = gestionnaireRepository.findById(request.getIdGestionnaire())
                .orElseThrow(() -> new RuntimeException("Gestionnaire introuvable"));

        Domaine domaine = domaineRepository.findById(request.getIdDomaine())
                .orElseThrow(() -> new RuntimeException("Domaine introuvable"));

        Projet projet = new Projet();
        projet.setTitre(request.getTitre() != null ? request.getTitre() : idee.getTitre());
        projet.setDescription(request.getDescription() != null ? request.getDescription() : idee.getDescription());
        projet.setDate(request.getDateCreation() != null ? request.getDateCreation() : LocalDate.now());
        projet.setCahierDeCharge(request.isCahierDeCharge());
        projet.setStatut(StatutProjet.EN_COURS);

        projet.setIdeeProjet(idee);
        projet.setDomaine(domaine);
        projet.setGestionnaire(gestionnaire);

        projet.setContributions(new ArrayList<>());
        projet.setDemandes(new ArrayList<>());
        projet.setDebloqueProjets(new ArrayList<>());
        projet.setDemandeParticipation(new ArrayList<>());

        return projetRepository.save(projet);
    }

    @Override
    public Projet createProjet(Projet projet, Long id_contributeur) {
        return null;
    }


    @Override

    public Projet getProjetById(Long id,Long id_contributeur) {

        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));

        return projetRepository.findById(id).orElse(null);
    }

    @Override
    public List<Projet> getAllProjets(Long id_contributeur) {
        Contributeur contributeur= contributeurRepository.findById(id_contributeur).
                orElseThrow(()->new RuntimeException("Cet admin n'existe pas"));

        return projetRepository.findAll();
    }

    @Override
    public void deleteProjet(Long id, Long idAdmin) {
        Administrateur administrateur = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));

        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (projet.getContributions() != null && !projet.getContributions().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : le projet a déjà des contributions.");
        }

        projetRepository.deleteById(id);
    }

    @Override
    public Projet updateProjet(Long id) {
        return null;
    }

}
