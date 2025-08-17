package com.apicollabdev.odk.collabdev.service.Impl;


import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.dto.CreateProjetRequest;
import com.apicollabdev.odk.collabdev.entity.*;
import com.apicollabdev.odk.collabdev.enums.StatutIdee;
import com.apicollabdev.odk.collabdev.enums.StatutProjet;
import com.apicollabdev.odk.collabdev.repository.*;
import com.apicollabdev.odk.collabdev.service.Interfaces.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjetServiceImpl implements ProjetService {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private ContributeurRepository contributeurRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;

    private  IdeeProjet ideeProjet;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IdeeProjetRepository ideeProjetRepository;

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private DomaineRepository domaineRepository;

   /* @Override
    public Projet createProjet(Projet projet, Long id_contributeur) {
        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));
        projet.setTitre(projet.getTitre());
        projet.setDescription(projet.getDescription());
        projet.setDate(projet.getDate());
        projet.setCahierDeCharge(projet.isCahierDeCharge());
        projet.setStatut(EN_COURS);
        return projetRepository.save(projet);
    } */

    public List<Projet> getProjetsByNewGestionnaire(Long newGestionnaireId) {
        return projetRepository.findByNewGestionnaire(newGestionnaireId);
    }

    @Override
    public Projet createProjetFromIdee(CreateProjetRequest request) {
        IdeeProjet idee = ideeProjetRepository.findById(request.getIdIdee())
                .orElseThrow(() -> new RessourceNotFoundException("Idée introuvable"));

        if (!idee.getStatut().equals(StatutIdee.ACCEPTEE)) {
            throw new IllegalStateException("L'idée doit être acceptée avant de devenir un projet.");
        }

        Contributeur contributeur = contributeurRepository.findById(request.getIdContributeur())
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur introuvable"));

        Gestionnaire gestionnaire = gestionnaireRepository.findById(request.getIdGestionnaire())
                .orElseThrow(() -> new RessourceNotFoundException("Gestionnaire introuvable"));

        Domaine domaine = domaineRepository.findById(request.getIdDomaine())
                .orElseThrow(() -> new RessourceNotFoundException("Domaine introuvable"));

        Projet projet = new Projet();
        projet.setTitre(request.getTitre() != null ? request.getTitre() : idee.getTitre());
        projet.setDescription(request.getDescription() != null ? request.getDescription() : idee.getDescription());
        projet.setDateCreation(request.getDateCreation() != null ? request.getDateCreation().atStartOfDay() : LocalDateTime.now());
        projet.setCahierDeCharge(request.isCahierDeCharge());
        projet.setStatut(StatutProjet.EN_COURS);

        projet.setIdeeProjet(idee);
        projet.setDomaine(domaine);
        projet.setGestionnaire(gestionnaire);
        projet.setContributions(new ArrayList<>());
        projet.setDemandes(new ArrayList<>());
        projet.setDebloqueProjets(new ArrayList<>());
        projet.setDemandeParticipation(new ArrayList<>());

        return projetRepository.save(projet);
    }


    // ---------------- Vérifier si toutes les fonctionnalités sont terminées ----------------
    private boolean toutesFonctionnalitesTerminees(Projet projet) {
        if (projet.getFonctionnalites() == null || projet.getFonctionnalites().isEmpty()) {
            return false;
        }
        return projet.getFonctionnalites()
                .stream()
                .allMatch(f -> f.getStatutF().equals("TERMINE")); // f.getStatut() == StatutFonctionnalite.TERMINE
    }

    // ---------------- Vérification et clôture automatique du projet ----------------
    public void verifierEtCloturerProjet(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RessourceNotFoundException("Projet introuvable"));

        if (toutesFonctionnalitesTerminees(projet)) {
            projet.setStatut(StatutProjet.TERMINE);
            projetRepository.save(projet);

            try {
                File zip = genererZipProjet(projet);
                String lienTelechargement = "http://localhost:8080/projets/" + projet.getIdProjet() + "/download";

                String corps = "Bonjour " + projet.getGestionnaire().getNom() + ",\n\n" +
                        "Toutes les fonctionnalités de votre projet \"" + projet.getTitre() + "\" sont terminées.\n" +
                        "Vous pouvez télécharger le projet complet ici : " + lienTelechargement + "\n\n" +
                        "Cordialement,\nL'équipe CollabDev";

                emailService.sendEmail(
                        projet.getGestionnaire().getEmail(), // destinataire
                        "Votre projet est terminé !",        // sujet
                        corps                                // contenu du mail
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ---------------- Génération du ZIP du projet ----------------
    public File genererZipProjet(Projet projet) throws IOException {
        String zipFileName = "projet_" + projet.getIdProjet() + ".zip";
        File zipFile = new File(System.getProperty("java.io.tmpdir"), zipFileName);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (Fonctionnalite f : projet.getFonctionnalites()) {
                ZipEntry entry = new ZipEntry(f.getNomFonctionnalite() + ".txt");
                zos.putNextEntry(entry);
                byte[] data = f.getDescriptionFonctionnalite().getBytes();
                zos.write(data, 0, data.length);
                zos.closeEntry();
            }
        }

        return zipFile;
    }


    @Override
    public Projet createProjet(Projet projet, Long id_contributeur) {
        return null;
    }


    @Override

    public Projet getProjetById(Long id,Long id_contributeur) {

        Contributeur contributeur = contributeurRepository.findById(id_contributeur)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));

        return projetRepository.findById(id).orElse(null);
    }

    @Override
    public List<Projet> getAllProjets(Long id_contributeur) {
        Contributeur contributeur= contributeurRepository.findById(id_contributeur).
                orElseThrow(()->new RuntimeException("Ce contributeur n'existe pas "));

        // Récupérer ses projets
        List<Projet> projets = null;

       // List<Projet> projets = projetRepository.findByContributeurIdContributeur(id_contributeur);

        // Vérifier si la liste est vide
        if (projets.isEmpty()) {
            throw new RuntimeException("Ce contributeur n'a aucun projet.");
        }

        return projets;
    }

    @Override
    public void deleteProjet(Long id, Long idAdmin) {
        Administrateur administrateur = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Cet admin n'existe pas"));

        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (projet.getContributions() != null && !projet.getContributions().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : le projet a déjà des contributions.");
        }

        projetRepository.deleteById(id);
    }

    @Override
    public Projet updateProjet(Long id) {
        return null;
    }

    @Override
    public List<Projet> getAllProjetsSysteme() {
        return projetRepository.findAll();
    }

}
