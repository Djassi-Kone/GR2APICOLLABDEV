package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Contributeur;
import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import com.apicollabdev.odk.collabdev.entity.Recevoir;

import java.util.List;

public interface RecevoirService {
    Recevoir createRecevoir(Recevoir recevoir);
    List<Recevoir> getAllRecevoir();
    Recevoir getById(Long id);
    void deleteById(Long id);

    void notifierGainBadge(Contributeur contributeur, String badgeGagner);
    void notifierGainBadgeEtCoins(Contributeur contributeur, int coinsGagner);
    void creerNotificationIdeeProjet(Contributeur contributeur, String titreProjet);
    void creerNotificationCommentaire(Contributeur contributeur, String commentaire);
    void notifierInscription(Contributeur contributeur);
    void notifierDemandeContribution(Gestionnaire gestionnaire, String titreProjet, Contributeur contributeur);
    void notifierDemandeAcceptee(Contributeur contributeur, String titreProjet);
    void notifierDemandeRejetee(Contributeur contributeur, String titreProjet);
    void notifierDemandeGestionnaire(Contributeur contributeur, String titreProjet, Gestionnaire gestionnaire);
    void notifierDemandeGestionnaireAcceptee(Contributeur contributeur, String titreProjet);
    void notifierDemandeGestionnaireRejetee(Contributeur contributeur, String titreProjet);

}
