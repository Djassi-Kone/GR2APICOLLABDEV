package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;

import java.util.List;

public interface FonctionnaliteService {
    FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto, Long id_gestionnaire);
    public List<FonctionnaliteDTO> ListerFonctionnalite();
    public FonctionnaliteDTO ListeFonctionnaliteParId(Long id);
    public  FonctionnaliteDTO modifierFonctionnalite(Long id , FonctionnaliteDTO dto);
    public void supprimerFonctionnalite(Long id);


}
