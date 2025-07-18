package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.Commentaire;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.CommentaireRepository;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.CommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaireServiceImpl implements CommentaireService {
    @Autowired
    private CommentaireRepository commentaireRepository;
    @Autowired
    private  ProjetRepository projetRepository;
    @Autowired
    private  ContributeurRepository contributeurRepository;

    @Override
    public Commentaire createCommentaire(Commentaire commentaire, long contributeur, long projet) {
        Contributeur c = contributeurRepository.findById(contributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));
        Projet p = projetRepository.findById(projet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        commentaire.setAuteur(commentaire.getAuteur());
        commentaire.setContenu(commentaire.getContenu());
        commentaire.setContributeur(c);
        commentaire.setProjet(p);

        return commentaireRepository.save(commentaire);
    }

    @Override
    public List<Commentaire> getAllCommentaires(Long idContributeur) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire getById(Long id, Long idContributeur) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));
        return commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));
    }

    @Override
    public void deleteById(Long id, Long idContributeur) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));

        commentaireRepository.deleteById(id);
    }
}
