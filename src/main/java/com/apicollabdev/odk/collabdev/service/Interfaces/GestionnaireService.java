package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.GestionnaireDTO;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;

public interface GestionnaireService {
    void validerContribution(GestionnaireDTO gestionnaireDTO);
    void validerDemande(GestionnaireDTO gestionnaireDTO);
    Gestionnaire getGestionnaireById(Long id);
    Gestionnaire updateGestionnaire(Gestionnaire gestionnaire);
}