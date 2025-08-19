package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.CoinsDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.entity.Contributeur;

import java.util.List;

public interface CoinsService {
    // Création de coins par un administrateur
    Coins createCoins(CoinsDTO dto, long idAdmin);

    // Création de coins pour un contributeur par un administrateur
    Coins createCoinsForContributeur(CoinsDTO dto, long idAdmin, long idContrib);

    // Création de coins pour un contributeur par un gestionnaire
    Coins createCoinsForContributeurByGestionnaire(CoinsDTO dto, long idGestionnaire, long idContrib);

    // Récupération de tous les coins
    List<Coins> getAllCoins();

    // Récupération d'un coin par son ID
    Coins getById(Long id);

    // Suppression d'un coin par son ID
    void deleteById(Long id);

    // Récupération de tous les coins créés par un gestionnaire
    List<Coins> getCoinsByGestionnaire(long idGestionnaire);

    List<Coins> getCoinsByContributeur(Contributeur contributeur);
    

}
