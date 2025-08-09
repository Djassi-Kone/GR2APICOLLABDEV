package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;

import java.util.List;

public interface DemandeParticipationService {
    DemandeParticipation createDemandeParticipation(Long idProjet, Long idContributeur, String description);
    List<DemandeParticipation> getAllDemandeParticipation();
    DemandeParticipation getById(Long id);
    void deleteById(Long id);
    DemandeParticipation faireDemandeGestionnaire (Long idIdeeProjet, Long idContributeur);
    DemandeParticipation accepterDemandeGestionnaire(Long idDomaine);
    DemandeParticipation rejeterDemandeGestionnaire (Long idDemande);
    DemandeParticipation accepterDemandeParticipation(Long idDomaine);
    DemandeParticipation rejeterDemandeParticipation (Long idDemande);
}
