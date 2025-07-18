package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Badge;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.repository.BadgeRepository;
import com.apicollabdev.odk.collabdev.repository.CoinsRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.AdministrateurService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
@AllArgsConstructor
@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    @Autowired
    private  BadgeRepository badgeRepository;
    @Autowired
    private  CoinsRepository coinsRepository;
    @Autowired
    private  AdministrateurRepository administrateurRepository;


    //creation de badges
    public Badge creerBadges(Badge badge, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloquer pour les contributeurs");
        }
        return badgeRepository.save(badge);
    }

    //Mise a jours des badges de badges
    public Badge updateBadge(int id,Badge badge, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloquer pour les contributeurs");
        }
        Badge badge1 = new Badge();
        badge1.setIdBadge(badge.getIdBadge());
        badge1.setNom(badge.getNom());
        badge1.setDescription(badge.getDescription());
        badge1.setImage(badge.getImage());
        badge1.setNombre(badge.getNombre());
        return badgeRepository.save(badge);
    }

    //Suppression des badges
    public void deleteBadge(int idBadge,Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
         Badge badge = badgeRepository.deleteBadgeByIdBadge(idBadge).
                 orElseThrow(()-> new RuntimeException("Badge introuvable"));

         badgeRepository.delete(badge);
    }

    public List<Badge> getAllBadges(Administrateur administrateur) {

        return  badgeRepository.findAll();

    }

    // Pour les coins
    //Creation des coins
    public Coins CreerCoin(Coins coins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }

        return coinsRepository.save(coins);
    }

    //Suppressions des coins
    public void SupprimerCoins(int idCoins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
        Coins coins = coinsRepository.deleteCoinsByIdCoin(idCoins).orElseThrow(()-> new RuntimeException("Coins introuvable"));

         coinsRepository.delete(coins);
    }

    //Mise a jours des coins

    public Coins updateCoins(int id,Coins coins, Administrateur administrateur) throws AccessDeniedException {
        if(administrateur == null) {
            throw new AccessDeniedException("Acces bloque pour les contributeurs");
        }
        Coins coins1 = new Coins();
        coins1.setIdCoin(coins.getIdCoin());
        coins1.setNombreCoins(coins.getNombreCoins());
        coins1.setAdministrateur(administrateur);
        return coinsRepository.save(coins);
    }

    public List<Coins> getAllCoins(Administrateur administrateur) {
        return  coinsRepository.findAll();

    }


    @Override
    public Administrateur administrateur(Administrateur administrateur) {
        return null;
    }

    @Override
    public List<Administrateur> getAllAdministrateurs() {
        return List.of();
    }

    @Override
    public Administrateur getById(Long idAdmin) {
        return null;
    }

    @Override
    public void deleteById(Long idAdmin) {

    }
}
