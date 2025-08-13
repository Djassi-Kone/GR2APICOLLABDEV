package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.dto.DomaineRequestDto;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Domaine;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.DomaineRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.DomaineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class DomaineServiceImpl implements DomaineService {
    @Autowired
    private  DomaineRepository domaineRepository;
    @Autowired
    private  AdministrateurRepository administrateurRepository;


    @Override
    public Domaine createDomaine(DomaineRequestDto domaineDto, long idAdmin) {
        Domaine domaine = new Domaine();

        Administrateur a = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RessourceNotFoundException("Cet admin n'existe pas"));

        domaine.setAdministrateur(a);
        domaine.setDescription(domaineDto.getDescription());
        domaine.setTitre(domaineDto.getTitre());

        return domaineRepository.save(domaine);
    }

    @Override
    public List<Domaine> getAllDomaine() {
        return domaineRepository.findAll();
    }

    @Override
    public Domaine getById(Long id) {
        return domaineRepository.findById((id))
                .orElseThrow(() -> new RessourceNotFoundException("Domaine non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!domaineRepository.existsById((id))) {
            throw new RessourceNotFoundException("Le domaine avec l'id " + id + " n'existe pas.");
        }
        domaineRepository.deleteById((id));
    }

    @Override
    public Domaine updateDomaine(Long idDomaine, Domaine newDomaineData, Administrateur admin) {
        // Chercher le domaine
        Domaine domaine = domaineRepository.findByIdDomaine(idDomaine)
                .orElseThrow(() -> new RessourceNotFoundException("Domaine introuvable"));

        // Mise à jour des champs
        if (newDomaineData.getTitre() != null) {
            domaine.setTitre(newDomaineData.getTitre());
        }
        if (newDomaineData.getDescription() != null) {
            domaine.setDescription(newDomaineData.getDescription());
        }
        // Lier l’administrateur
        domaine.setAdministrateur(admin);
        // Sauvegarder
        return domaineRepository.save(domaine);
    }

}
