package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.DebloqueProjetDTO;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;

import java.util.List;

public interface DebloqueProjetService {
    DebloqueProjet createDebloqueProjet(DebloqueProjetDTO debloqueProjet, Long idContributeur);
    List<DebloqueProjet> getAllDebloqueProjet();
    DebloqueProjet getById(Long id);
    void deleteById(Long id);
    DebloqueProjet updateDebloqueProjet(
            int id,
            DebloqueProjetDTO debloqueProjetDTO
    );
}
