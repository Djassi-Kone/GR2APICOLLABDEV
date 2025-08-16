package com.apicollabdev.odk.collabdev.service.Interfaces;


import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;

import com.apicollabdev.odk.collabdev.entity.IdeeProjet;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.enums.ModeTransfert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IdeeProjetService {
    IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeur, long idDomaine);
    List<IdeeProjet> getAllIdeeProjet();
    IdeeProjet getById(Long id);
    IdeeProjet updateIdeeProjet(Long id, IdeeProjet updatedIdeeProjet);
    void deleteById(Long id);
    public Projet transfererEtTransformerIdeeLeguee(Long idIdeeProjet, Long idNouveauContributeur, ModeTransfert mode);

}
