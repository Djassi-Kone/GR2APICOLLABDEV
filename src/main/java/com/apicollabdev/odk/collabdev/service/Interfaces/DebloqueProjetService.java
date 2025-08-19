package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.DebloqueProjetDTO;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;

import java.util.List;

public interface DebloqueProjetService {
    DebloqueProjet debloquerProjet(Long idProjet, Long idContributeur, DebloqueProjetDTO debloqueProjetDTO);

    List<DebloqueProjet> getAllDebloqueProjet();

    DebloqueProjet getById(Long id);

    void deleteById(Long id);

    DebloqueProjet updateDebloqueProjet(Long id, DebloqueProjetDTO dto);

}
