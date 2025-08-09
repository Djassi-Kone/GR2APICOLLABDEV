package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.Notification.NotificationFactory;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.repository.ContributeurRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.ContributeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public  class ContributeurServiceImpl implements ContributeurService {
    @Autowired
    private  ContributeurRepository contributeurRepository;
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public Contributeur CreerCompte(Contributeur dto) {
        // Vérification si l'email existe déjà
        if (contributeurRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Un compte avec cet email existe déjà !");
        }

        // Création de l'objet Contributeur à enregistrer

        Contributeur contributeur = new Contributeur();
        contributeur.setNom(dto.getNom());
        contributeur.setPrenom(dto.getPrenom());
        contributeur.setEmail(dto.getEmail());
        contributeur.setPassword(dto.getPassword()); // À encoder si besoin
        contributeur.setNiveau(dto.getNiveau());
        contributeur.setProfil(dto.getProfil());

        // Sauvegarde
        Contributeur saved = contributeurRepository.save(contributeur);

       /* // Vérification : l’ID est bien généré
        if (saved.getId() == null) {
            throw new IllegalStateException("L'ID du contributeur est null après sauvegarde.");
        }
=======
        contributeur.setActive(dto.isActive());

        // Sauvegarde dans la base
        Contributeur saved = contributeurRepository.save(contributeur);*/

        // Envoi automatique de la notification
        notificationServiceImpl.notifierEtEnvoyer(TypeNotification.INSCRIPTION, saved);

        return saved;
    }

    @Override
    public Contributeur connexion(String email, String password) {
        Contributeur contributeur = contributeurRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        Notification notification = NotificationFactory.creerNotificationCommentaire(contributeur.getNom(), "Connexion réussie.");
        emailService.sendEmail(email, "Connexion réussie", notification.getDescription());
        return contributeur;
    }



    @Override
    public Void deconnexion(Long idContributeur) {
        Contributeur c = contributeurRepository.findById(idContributeur)
                .orElseThrow(() -> new RessourceNotFoundException("Contributeur non trouvé"));

        c.setActive(false); // Supposons que tu as un champ `actif` dans l'entité
        contributeurRepository.save(c);
        System.out.println("Contributeur déconnecté (état inactif)");
        return null;
    }


    @Override
    public Contributeur getContributeurById(Long id) {
        return contributeurRepository.findById(id).orElse(null);
    }

    @Override
    public List<Contributeur> getAllContributeurs() {
        return contributeurRepository.findAll();
    }

    @Override
    public void deleteContributeur(Long id) {
        contributeurRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Contributeur mettreAJourContributeur(Long id, Contributeur dto) {
        Contributeur existant = contributeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contributeur non trouvé avec l'id: " + id));

        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setEmail(dto.getEmail());
        existant.setPassword(dto.getPassword());
        existant.setNiveau(dto.getNiveau());
        existant.setProfil(dto.getProfil());

        return contributeurRepository.save(existant);
    }

}
