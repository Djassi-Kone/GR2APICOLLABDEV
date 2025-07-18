package com.apicollabdev.odk.collabdev.service.Interfaces;


import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;

import com.apicollabdev.odk.collabdev.entity.IdeeProjet;

import java.util.List;

public interface IdeeProjetService {
    IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeur, long idDomaine);
    List<IdeeProjet> getAllIdeeProjet();
    IdeeProjet getById(Long id);
    IdeeProjet updateIdeeProjet(Long id, IdeeProjet updatedIdeeProjet);
    void deleteById(Long id);

}
