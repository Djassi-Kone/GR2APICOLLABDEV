package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;

import java.util.List;

public interface DemandeParticipationService {
    DemandeParticipation createDemandeParticipation(DemandeParticipation demandeParticipation);
    List<DemandeParticipation> getAllDemandeParticipation();
    DemandeParticipation getById(Long id);
    void deleteById(Long id);
}
