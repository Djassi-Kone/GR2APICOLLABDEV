package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface


CoinsRepository extends JpaRepository<Coins, Long> {
    List<Coins> findByContributeur(Contributeur contributeur);

    Optional<Coins> deleteCoinsByIdCoin(int idCoin);


    // Récupérer tous les coins créés par un administrateur
    List<Coins> findByAdministrateurId(long idAdministrateur);

    List<Coins> findByGestionnaireId(Long idGestionnaire);

    @Query("SELECT COALESCE(SUM(c.nombreCoins), 0) FROM Coins c WHERE c.contributeur.id = :contributeurId")
    int sumCoinsByContributeur(@Param("contributeurId") Long contributeurId);

}


