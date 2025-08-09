package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;

import java.util.List;

public interface FonctionnaliteService {
    public FonctionnaliteDTO creerFonctionnalite(FonctionnaliteDTO dto);
    public List<FonctionnaliteDTO> ListerFonctionnalite();
    public FonctionnaliteDTO ListeFonctionnaliteParId(Long id);
    public  FonctionnaliteDTO modifierFonctionnalite(Long id , FonctionnaliteDTO dto);
    public void supprimerFonctionnalite(Long id);


}
