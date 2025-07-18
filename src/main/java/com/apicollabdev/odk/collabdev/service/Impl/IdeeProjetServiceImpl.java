package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.dto.CreateIdeeProjetDTO;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.IdeeProjetService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IdeeProjetServiceImpl implements IdeeProjetService {

    @Autowired
    private  IdeeProjetRepository ideeProjetRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private EntityManager entityManager;



    @Override
    @Transactional
    public IdeeProjet createIdeeProjet(CreateIdeeProjetDTO dto, long idContributeur, long idDomaine) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé"));
        Domaine d = domaineRepository.findById(idDomaine)
                .orElseThrow(() -> new RuntimeException("Domaine non trouvé"));

        // Création de l'idée
        IdeeProjet ideeProjet = new IdeeProjet();
        ideeProjet.setTitre(dto.getTitre());
        ideeProjet.setDescription(dto.getDescription());
        ideeProjet.setNiveau(dto.getNiveau());
        ideeProjet.setLeguer(dto.isLeguer());
        ideeProjet.setContributeur(c);
        ideeProjet.setDomaine(d);
        ideeProjet.setDateCreation(LocalDate.now());
        ideeProjet.setStatut(StatutIdee.ACCEPTEE);

        // Promotion en gestionnaire
        if (!dto.isLeguer()) {
            boolean dejaGestionnaire = gestionnaireRepository.existsById(c.getId());
            Query query = null;

            if (!dejaGestionnaire) {
                // Requête native d'insertion dans la table GESTIONNAIRE
                String sql = "INSERT INTO gestionnaire (id_gestionnaire, valider_commentaire, valider_contribution, valider_demande)" +
                        "VALUES (:id, false, false, false)";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("id", c.getId());
                query.executeUpdate();
            }
        }

        return ideeProjetRepository.save(ideeProjet);
    }

    @Override
    public List<IdeeProjet> getAllIdeeProjet() {
        return ideeProjetRepository.findAll();
    }

    @Override
    public IdeeProjet getById(Long id) {
        return ideeProjetRepository.findById((id))
                .orElseThrow(() -> new RuntimeException("IdeeProjet non trouvé avec l'id : " + id));

    }

    @Override
    public IdeeProjet updateIdeeProjet(Long id, IdeeProjet updatedIdeeProjet) {
        IdeeProjet existingIdeeProjet = ideeProjetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("IdeeProjet non trouvée avec l'id : " + id));

        // Mise à jour des champs (ajuste les champs selon ton entité)
        existingIdeeProjet.setTitre(updatedIdeeProjet.getTitre());
        existingIdeeProjet.setDescription(updatedIdeeProjet.getDescription());
        existingIdeeProjet.setDomaine(updatedIdeeProjet.getDomaine());
        existingIdeeProjet.setNiveau(updatedIdeeProjet.getNiveau());
        existingIdeeProjet.setDateCreation(updatedIdeeProjet.getDateCreation());

        return ideeProjetRepository.save(existingIdeeProjet);
    }

    @Override
    public void deleteById(Long id) {
        if (!ideeProjetRepository.existsById(id)) {

            throw new RuntimeException("Le IdeeProjet avec l'id " + id + " n'existe pas.");
        }
        ideeProjetRepository.deleteById(id);
    }

    @Transactional
    public Projet transformerIdeeEnProjet(Long idIdeeProjet) {
        IdeeProjet ideeProjet = ideeProjetRepository.findById(idIdeeProjet)
                .orElseThrow(() -> new RuntimeException("Idée de projet non trouvée"));

        Contributeur contributeur = ideeProjet.getContributeur();
        if (contributeur == null) {
            throw new RuntimeException("Aucun contributeur associé à cette idée");
        }

        if (Boolean.TRUE.equals(ideeProjet.isLeguer())) {
            throw new RuntimeException("Ce contributeur a légué l'idée et ne peut pas devenir gestionnaire.");
        }

        // ⚠️ Ne pas créer un nouvel objet — utiliser l'existant en le "castant"
        Gestionnaire gestionnaire = new Gestionnaire();
        gestionnaire.setId(contributeur.getId()); // conserve l'ID
        gestionnaire.setNom(contributeur.getNom());
        gestionnaire.setEmail(contributeur.getEmail());
        gestionnaire.setPassword(contributeur.getPassword());


        // Attributs propres au gestionnaire
        gestionnaire.setValiderCommentaire(true);
        gestionnaire.setValiderContribution(true);
        gestionnaire.setValiderDemande(true);

        // On ne le sauvegarde pas — Hibernate gère déjà l’objet via l’ID unique.
        // Pas de delete sur le contributeur non plus.

        Projet projet = new Projet();
        projet.setTitre(ideeProjet.getTitre());
        projet.setDescription(ideeProjet.getDescription());
        projet.setDateCreation(LocalDate.now());
        projet.setStatut(StatutProjet.EN_COURS);
        projet.setCahierDeCharge(false);
        projet.setGestionnaire(gestionnaire); // ✅ affecte l’objet déjà géré
        projet.setDomaine(ideeProjet.getDomaine());

        Projet projetCree = projetRepository.save(projet);

        ideeProjet.setProjet(projetCree);
        ideeProjet.setStatut(StatutIdee.ACCEPTEE);
        ideeProjetRepository.save(ideeProjet);

        return projetCree;

    }



}
