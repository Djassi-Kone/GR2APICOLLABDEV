// com.apicollabdev.odk.collabdev.mapper.FonctionnaliteMapper.java
package com.apicollabdev.odk.collabdev.mapper;

import com.apicollabdev.odk.collabdev.dto.FonctionnaliteDTO;
import com.apicollabdev.odk.collabdev.entity.Fonctionnalite;

public class FonctionnaliteMapper {

    public static FonctionnaliteDTO toDTO(Fonctionnalite f) {
        FonctionnaliteDTO dto = new FonctionnaliteDTO();
        dto.setId(f.getIdFonctionnalite());
        dto.setPointFonctionnalite(String.valueOf(f.getPointFonctionnalite()));
        dto.setStatut(f.getStatutP().name());
         // suppose que Gestionnaire a getId()
        dto.setProjetId((long) Math.toIntExact(f.getProjet().getIdProjet())); // suppose que Projet a getId()
        dto.setFonctionnaliteNom(f.getNomFonctionnalite());
        dto.setFonctionnaliteDescription(f.getDescriptionFonctionnalite());
        return dto;

    }
}

