package com.apicollabdev.odk.collabdev.Notification;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;
import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactory {

    public static Notification creerNotificationCoins(int coinsGagner) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.GAINCOINS);
        notif.setDescription("Bravo ! Vous avez gagné " + coinsGagner + " coins.");
        return notif;
    }

    public static Notification creerNotificationBadge(String BadgeGagner) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.GAINBADGE);
        notif.setDescription("Félicitations ! Vous avez gagné le badge : " + BadgeGagner);
        return notif;
    }

    public static Notification creerNotificationIdeeProjet(String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.PROPOSITIONIDEEPROJET);
        notif.setDescription("Vous avez proposée Une nouvelle idée de projet : \"" + titreProjet + "\"");
        return notif;
    }

    public static Notification creerNotificationCommentaire(String nomContributeur, String contenuCommentaire) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.COMMENTAIRE);
        notif.setDescription(nomContributeur + " a fait un commentaire : \"" + contenuCommentaire + "\"");
        return notif;
    }

    public static Notification creerNotificationInscription() {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.INSCRIPTION);
        notif.setDescription("Bienvenue sur la plateforme COLLABDEV ! Votre compte a été créer avec succès.");
        return notif;
    }

    public static Notification creerNotificationDemandeContribution(Gestionnaire gestionnaire, String titreProjet, Contributeur contributeur) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDECONTRIBUTION);
        notif.setDescription("Le contributeur " + contributeur.getNom() + " souhaite contribuer à votre projet \"" + titreProjet + "\".");
        return notif;
    }

    public static Notification creerNotificationDemandeParticipation(String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEPARTICIPATION);
        notif.setDescription("Votre demande de participation au projet \"" + titreProjet + "\" a été envoyée. Veuillez attendre la validation.");
        return notif;
    }


    public static Notification creerNotificationDemandeAcceptee(Contributeur contributeur, String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMADEACCEPTEE);
        notif.setDescription("Votre demande de contribution au projet \"" + titreProjet + "\" a été acceptée.");
        return notif;
    }

    public static Notification creerNotificationDemandeRejetee(Contributeur contributeur, String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEREJETEE);
        notif.setDescription("Votre demande de contribution au projet \"" + titreProjet + "\" a été rejetée.");
        return notif;
    }

    public static Notification creerNotificationContribution(Contributeur contributeur, String titreProjet, String nomFonctionnalite) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.FAIRECONTRIBUTION);
        notif.setDescription("Le contributeur \"" + contributeur + "\" a fait une contribution sur le projet \"" + titreProjet + "\" pour la fonctionnalité \"" + nomFonctionnalite + "\"  veillez vérifier pour valider ou rejeter.");
        return notif;
    }

    public static Notification creerNotificationContributionValider(Contributeur contributeur, String titreProjet, String nomFonctionnalite) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.CONTRIBUTIONVALIDER);
        notif.setDescription("Votre contribution a été sur le projet \"" + titreProjet + "\" pour la fonctionnalite \"" + nomFonctionnalite+ "\"  a été validée.");
        return notif;
    }

    public static Notification creerNotificationContributionRejetee(Contributeur contributeur, String titreProjet, String nomFonctionnalite) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEREJETEE);
        notif.setDescription("Votre contribution a été sur le projet \"" + titreProjet + "\" pour la fonctionnalite \"" + nomFonctionnalite+ "\" a été rejetée.");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaire(Contributeur demandeur, String titreProjet, Contributeur createur) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIRE);
        notif.setDescription("Vous avez reçu une demande pour être gestionnaire du projet \"" + titreProjet + "\" de la part de " + demandeur.getNom() + ".");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaireAcceptee(String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIREACCEPTEE);
        notif.setDescription("Votre demande à être gestionnaire du projet \"" + titreProjet + "\" a été acceptée.");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaireRejetee(String titreProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIREREJETEE);
        notif.setDescription("Votre demande à être gestionnaire du projet \"" + titreProjet + "\" a été rejetée.");
        return notif;
    }
}
