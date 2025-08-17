package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Commentaire;

import java.util.List;

public interface CommentaireService {

    Commentaire createCommentaireSurProjet(Commentaire commentaire, long contributeur, long projet);
    Commentaire createCommentaireSurIdeeProjet (Commentaire commentaire, long idContributeur, long idIdeeProjet);
    List<Commentaire> getAllCommentaires(Long idContributeur);
    Commentaire getById(Long idn, Long idContributeur);
    void deleteById(Long id, Long idContributeur);
}
