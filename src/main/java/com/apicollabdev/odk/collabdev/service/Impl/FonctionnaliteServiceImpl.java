package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.mapper.FonctionnaliteMapper;
import com.apicollabdev.odk.collabdev.repository.FonctionnaliteRepository;
import com.apicollabdev.odk.collabdev.repository.GestionnaireRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.FonctionnaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service

public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    public FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto) {
        Fonctionnalite f = new Fonctionnalite();
        f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
        f.setStatutP(StatutProjet.valueOf(dto.getStatut()));
        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        Projet projet = projetRepository.findByIdProjet(dto.getId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));
        f.setProjet(projet);

        Fonctionnalite saved = fonctionnaliteRepository.save(f);

        return FonctionnaliteMapper.toDTO(saved);
    }

    public List<FonctionnaliteDTO> ListerFonctionnalite() {
        return fonctionnaliteRepository.findAll()
                .stream()
                .map(FonctionnaliteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FonctionnaliteDTO ListeFonctionnaliteParId(int id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        return FonctionnaliteMapper.toDTO(f);
    }
    public void supprimerFonctionnalite(int id) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));
        fonctionnaliteRepository.delete(f);
    }
    public FonctionnaliteDTO modifierFonctionnalite(int id, FonctionnaliteDTO dto) {
        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonctionnalité non trouvée"));

        f.setPointFonctionnalite(Integer.parseInt(dto.getPointFonctionnalite()));
        f.setStatutP(StatutProjet.valueOf(dto.getStatut()));
        f.setNomFonctionnalite(dto.getFonctionnaliteNom());
        f.setDescriptionFonctionnalite(dto.getFonctionnaliteDescription());

        Fonctionnalite updated = fonctionnaliteRepository.save(f);
        return FonctionnaliteMapper.toDTO(updated);
    }

}
