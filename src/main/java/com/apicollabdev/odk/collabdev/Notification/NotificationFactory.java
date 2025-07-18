package com.apicollabdev.odk.collabdev.Notification;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Notification;
import com.apicollabdev.odk.collabdev.enums.TypeNotification;

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
        notif.setDescription("Une nouvelle idée de projet a été proposée : \"" + titreProjet + "\"");
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
        notif.setDescription("Bienvenue sur la plateforme COLLABDEV ! Votre inscription a été enregistrée avec succès.");
        return notif;
    }

    public static Notification creerNotificationDemandeContribution(Gestionnaire gestionnaire, String nomProjet, Contributeur contributeur) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDECONTRIBUTION);
        notif.setDescription("Le contributeur " + contributeur.getNom() + " souhaite contribuer à votre projet \"" + nomProjet + "\".");
        return notif;
    }

    public static Notification creerNotificationDemandeAcceptee(Contributeur contributeur, String nomProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMADEACCEPTEE);
        notif.setDescription("Votre demande de contribution au projet \"" + nomProjet + "\" a été acceptée.");
        return notif;
    }

    public static Notification creerNotificationDemandeRejetee(Contributeur contributeur, String nomProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEREJETEE);
        notif.setDescription("Votre demande de contribution au projet \"" + nomProjet + "\" a été rejetée.");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaire(Contributeur contributeur, String nomProjet, Gestionnaire gestionnaire) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIRE);
        notif.setDescription("Vous avez reçu une demande pour être gestionnaire du projet \"" + nomProjet + "\" de la part de " + contributeur.getNom() + ".");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaireAcceptee(String nomProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIREACCEPTEE);
        notif.setDescription("Votre demande à être gestionnaire du projet \"" + nomProjet + "\" a été acceptée.");
        return notif;
    }

    public static Notification creerNotificationDemandeGestionnaireRejetee(String nomProjet) {
        Notification notif = new Notification();
        notif.setTypeNotyf(TypeNotification.DEMANDEGESTIONNAIREREJETEE);
        notif.setDescription("Votre demande à être gestionnaire du projet \"" + nomProjet + "\" a été rejetée.");
        return notif;
    }
}
