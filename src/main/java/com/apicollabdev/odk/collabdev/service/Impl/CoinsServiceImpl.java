package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.CoinsDTO;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.BadgeRepository;
import com.apicollabdev.odk.collabdev.repository.CoinsRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.BadgeService;
import com.apicollabdev.odk.collabdev.service.Interfaces.CoinsService;
import com.apicollabdev.odk.collabdev.service.Interfaces.NotificationService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Data@Service
public class CoinsServiceImpl implements CoinsService {

    @Autowired
    private CoinsRepository coinsRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;
    @Autowired
    private BadgeServiceImpl badgeService;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    // --- Coins par administrateur déjà existant ---
    @Override
    public Coins createCoinsForContributeur(CoinsDTO dto, long idAdmin, long idContrib) {
        Contributeur contrib = contributeurRepository.findById(idContrib)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        List<Coins> existingCoins = coinsRepository.findByContributeur(contrib);

        Coins coin;
        if (existingCoins.isEmpty()) {
            coin = new Coins();
            coin.setNombreCoins(dto.getNombreCoins());
            coin.setContributeur(contrib);
        } else {
            coin = existingCoins.get(0);
            coin.setNombreCoins(coin.getNombreCoins() + dto.getNombreCoins());
        }

        Coins savedCoin = coinsRepository.save(coin);
        if (!contrib.getCoins().contains(savedCoin)) {
            contrib.getCoins().add(savedCoin);
        }

        badgeService.checkAndAssignBadgeIfEligible(contrib);

        return savedCoin;
    }

    // --- Nouveautés pour gestionnaire ---

    // Création de coins par un gestionnaire pour un contributeur
    @Transactional
    public Coins createCoinsForContributeurByGestionnaire(CoinsDTO dto, long idGestionnaire, long idContrib) {
        // Récupérer le contributeur
        Contributeur contrib = contributeurRepository.findById(idContrib)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        // Créer le coin et l’assigner directement au contributeur
        Coins coin = new Coins();
        coin.setNombreCoins(dto.getNombreCoins());
        coin.setContributeur(contrib);

        // Sauvegarder le coin dans la base
        Coins savedCoin = coinsRepository.save(coin);

        // Ajouter le coin à la liste du contributeur si ce n’est pas déjà fait
        if (!contrib.getCoins().contains(savedCoin)) {
            contrib.getCoins().add(savedCoin);
            contributeurRepository.save(contrib); // ⚡ persistance de la relation bidirectionnelle
        }

        // Vérifier et attribuer un badge si nécessaire
        badgeService.checkAndAssignBadgeIfEligible(contrib);

        return savedCoin;
    }


    // Récupération des coins créés par un gestionnaire
    public List<Coins> getCoinsByGestionnaire(long idGestionnaire) {
        // Si tu stockes le gestionnaire dans le champ administrateur :
        return coinsRepository.findByAdministrateurId(idGestionnaire);
        // Sinon, il faudra créer un champ spécifique "gestionnaire" dans Coins et faire :
        // return coinsRepository.findByGestionnaireId(idGestionnaire);
    }

    // --- Autres méthodes déjà existantes ---
    @Override
    public List<Coins> getAllCoins() {
        return coinsRepository.findAll();
    }

    @Override
    public Coins getById(Long id) {
        return coinsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coins non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!coinsRepository.existsById(id)) {
            throw new RuntimeException("Coins avec l'id " + id + " n'existe pas.");
        }
        coinsRepository.deleteById(id);
    }

    @Override
    public Coins createCoins(CoinsDTO dto, long idAdmin) {
        // Ici tu peux créer des coins sans contributeur, uniquement liés à l'admin
        Coins coins = new Coins();
        coins.setNombreCoins(dto.getNombreCoins());

        // Si tu veux lier l’admin, il faut le récupérer
        Administrateur admin = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));
        coins.setAdministrateur(admin);

        return coinsRepository.save(coins);
    }

    @Override
    public List<Coins> getCoinsByContributeur(Contributeur contributeur) {
        return coinsRepository.findByContributeur(contributeur);
    }




}