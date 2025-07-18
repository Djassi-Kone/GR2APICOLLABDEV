package com.apicollabdev.odk.collabdev.mapper;

import com.apicollabdev.odk.collabdev.dto.ContributeurDTO;
import com.apicollabdev.odk.collabdev.entity.Contributeur;
import lombok.*;

public class ContributeurMapper {
    public static Contributeur toEntity(ContributeurDTO dto) {
        Contributeur c = new Contributeur();
        c.setNom(dto.getNom());
        c.setPrenom(dto.getPrenom());
       // c.setEmail(dto.getEmail());
       // c.setPassword(dto.getPassword());
        c.setProfil(dto.getProfil());
        c.setNiveau(dto.getNiveau());
        return c;
    }
}
