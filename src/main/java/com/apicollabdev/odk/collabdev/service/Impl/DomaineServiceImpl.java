package com.apicollabdev.odk.collabdev.service.Impl;

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
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));

        domaine.setAdministrateur(a);
        domaine.setDescription(domaine.getDescription());
        domaine.setTitre(domaine.getTitre());

        return domaineRepository.save(domaine);
    }

    @Override
    public List<Domaine> getAllDomaine() {
        return domaineRepository.findAll();
    }

    @Override
    public Domaine getById(Long id) {
        return domaineRepository.findById((id))
                .orElseThrow(() -> new RuntimeException("Domaine non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!domaineRepository.existsById((id))) {
            throw new RuntimeException("Le domaine avec l'id " + id + " n'existe pas.");
        }
        domaineRepository.deleteById((id));
    }

    @Override
    public Domaine updateDomaine(Long idDomaine, Domaine newDomaineData, Administrateur admin) {
        Domaine domaine = domaineRepository.findById(idDomaine)
                .orElseThrow(() -> new RuntimeException("Domaine introuvable"));

        domaine.setTitre(newDomaineData.getTitre());
        domaine.setDescription(newDomaineData.getDescription());
        domaine.setAdministrateur(admin); // On confirme que c’est bien cet admin

        return domaineRepository.save(domaine);
    }

}
