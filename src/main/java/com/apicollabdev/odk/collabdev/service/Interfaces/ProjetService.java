package com.apicollabdev.odk.collabdev.service.Interfaces;


import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.entity.Projet;
import java.util.List;

public interface ProjetService {
    Projet createProjetFromIdee(CreateProjetRequest request);
    Projet createProjet(Projet projet, Long id_contributeur);
    Projet getProjetById(Long id,Long id_contributeur);
    List<Projet> getAllProjets(Long id_contributeur);
    void deleteProjet(Long id,Long idAdmin);
    Projet updateProjet(Long id);
}
