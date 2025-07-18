package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.entity.Administrateur;

import java.util.List;

public interface AdministrateurService {
    Administrateur administrateur(Administrateur administrateur);
    List<Administrateur> getAllAdministrateurs();
    Administrateur getById(Long idAdmin);
    void deleteById(Long idAdmin);
}
