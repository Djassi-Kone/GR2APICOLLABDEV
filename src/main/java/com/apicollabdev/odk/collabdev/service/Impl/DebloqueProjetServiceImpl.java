package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.DebloqueProjetDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.DebloqueProjet;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.DebloqueProjetRepository;

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

    @Override
    public DebloqueProjet createDebloqueProjet(DebloqueProjetDTO debloqueProjetDTO, Long idContributeur) {
        Contributeur contributeur = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur introuvable avec l'id : " + idContributeur));

        DebloqueProjet debloqueProjet = new DebloqueProjet();
        debloqueProjet.setVisibilite(debloqueProjetDTO.isVisibilite());
        debloqueProjet.setNombreCoins(debloqueProjetDTO.getNombreCoins());
        debloqueProjet.setContributeur(contributeur);

        return debloqueProjetRepository.save(debloqueProjet);
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
    public DebloqueProjet updateDebloqueProjet(int id, DebloqueProjetDTO debloqueProjetDTO) {
        DebloqueProjet debloqueProjet = debloqueProjetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DebloqueProjet introuvable avec l'id : " + id));

        debloqueProjet.setVisibilite(debloqueProjetDTO.isVisibilite());
        debloqueProjet.setNombreCoins(debloqueProjetDTO.getNombreCoins());

        return debloqueProjetRepository.save(debloqueProjet);
    }
}
