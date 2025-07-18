package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.DomaineRequestDto;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Domaine;

import java.util.List;

public interface DomaineService {
    Domaine createDomaine(DomaineRequestDto domaine, long idAdmin);
    List<Domaine> getAllDomaine();
    Domaine getById(Long id);
    void deleteById(Long id);
    Domaine updateDomaine(Long idDomaine, Domaine newDomaineData, Administrateur admin);
}
