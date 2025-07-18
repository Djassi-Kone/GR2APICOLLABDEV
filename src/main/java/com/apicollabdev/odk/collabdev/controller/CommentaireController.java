package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.entity.Commentaire;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Projet;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.repository.ProjetRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.CommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@RequiredArgsConstructor
public class CommentaireController {

    @Autowired
    private  CommentaireService commentaireService;
    @Autowired
    private  ContributeurRepository contributeurRepository;
    @Autowired
    private  ProjetRepository projetRepository;

    @PostMapping("/{idContributeur}")
    public ResponseEntity<Commentaire> create(@RequestBody Commentaire commentaire,@PathVariable("idContributeur") long idContributeur, @RequestParam("idProjet") long idProjet ) {
        Contributeur c = contributeurRepository.findById(idContributeur).orElseThrow(()-> new RuntimeException("Contributeur non trouvé"));
        Projet p = projetRepository.findById(idProjet).orElseThrow(()-> new RuntimeException("Projet non trouvé"));
        return ResponseEntity.ok(commentaireService.createCommentaire(commentaire, idContributeur, idProjet));
    }


    @GetMapping("{idContributeur}")
    public List<Commentaire> getAll(@PathVariable("idContributeur") long idContributeur) {
        Contributeur contributeur = contributeurRepository.findById(idContributeur).
                orElseThrow(()->new RuntimeException("Contributeur n'existe pas"));

        return commentaireService.getAllCommentaires(idContributeur);
    }

    @GetMapping("ParId/{idContributeur}")
    public ResponseEntity<Commentaire> getById(@RequestParam Long id, @PathVariable("idContributeur") long idContributeur) {
        Contributeur contributeur = contributeurRepository.findById(idContributeur).
                orElseThrow(()->new RuntimeException("Contributeur n'existe pas"));
        return ResponseEntity.ok(commentaireService.getById(id,idContributeur));
    }

    @DeleteMapping("/{idContributeur}")
    public ResponseEntity<Void> delete(@RequestParam Long id, @PathVariable("idContributeur") long idContributeur) {
        Contributeur contributeur = contributeurRepository.findById(idContributeur).
                orElseThrow(()->new RuntimeException("Contributeur n'existe pas"));

        commentaireService.deleteById(id,idContributeur);
        return ResponseEntity.noContent().build();
    }
}
