package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.DebloqueProjetDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.CoinsRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.DebloqueProjetRepository;

import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.DebloqueProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DebloqueProjetServiceImpl implements DebloqueProjetService {
    @Autowired
    private DebloqueProjetRepository debloqueProjetRepository;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private CoinsRepository coinsRepository;


    @Override
    public DebloqueProjet debloquerProjet(Long idProjet, Long idContributeur, DebloqueProjetDTO debloqueProjetDTO) {
        Projet projet = projetRepository.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec id : " + idProjet));

        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable avec id : " + idContributeur));

        // Vérifier si déjà débloqué
        if (debloqueProjetRepository.existsByProjetAndContributeur(projet, contributeur)) {
            throw new RuntimeException("Projet déjà débloqué par ce contributeur !");
        }

        // Récupérer les coins du contributeur
        int totalCoins = coinsRepository.findByContributeur(contributeur)
                .stream()
                .mapToInt(Coins::getNombreCoins)
                .sum();



        // Déterminer le nombre de coins requis selon le niveau
        int coinsRequis = getCoinsRequis(projet);

        if (totalCoins < coinsRequis) {
            throw new RuntimeException("Coins insuffisants : " + totalCoins + "/" + coinsRequis);
        }

        contributeur.setTotalCoins(contributeur.getTotalCoins() - coinsRequis);
        contributeurRepository.save(contributeur);

        // Débloquer le projet
        DebloqueProjet debloqueProjet = new DebloqueProjet();
        debloqueProjet.setProjet(projet);
        debloqueProjet.setContributeur(contributeur);
        debloqueProjet.setVisibilite(true);
        debloqueProjet.setNombreCoins(coinsRequis);

        return debloqueProjetRepository.save(debloqueProjet);
    }

    private int getCoinsRequis(Projet projet) {
        if (projet.getIdeeProjet() == null) {
            throw new RuntimeException("Ce projet n'a pas d'idée associée avec un niveau !");
        }

        switch (projet.getIdeeProjet().getNiveau()) {
            case DEBUTANT: return 0;
            case INTERMEDIAIRE: return 300;
            case AVANCE: return 800;
            default: throw new RuntimeException("Niveau inconnu !");
        }
    }

    @Override
    public List<DebloqueProjet> getAllDebloqueProjet() {
        return debloqueProjetRepository.findAll();
    }

    @Override
    public DebloqueProjet getById(Long id) {
        return debloqueProjetRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("DebloqueProjet non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!debloqueProjetRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("Le debloqueProjet avec l'id " + id + " n'existe pas.");
        }
        debloqueProjetRepository.deleteById(Math.toIntExact(id));
    }



    @Override
    public DebloqueProjet updateDebloqueProjet(Long id, DebloqueProjetDTO debloqueProjetDTO) {
        DebloqueProjet debloqueProjet = debloqueProjetRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("DebloqueProjet introuvable avec l'id : " + id));

        debloqueProjet.setVisibilite(debloqueProjetDTO.isVisibilite());
        debloqueProjet.setNombreCoins(debloqueProjetDTO.getNombreCoins());

        return debloqueProjetRepository.save(debloqueProjet);
    }


}
