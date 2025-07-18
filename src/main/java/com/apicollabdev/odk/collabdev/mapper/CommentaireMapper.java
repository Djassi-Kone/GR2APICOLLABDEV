package com.apicollabdev.odk.collabdev.mapper;

import com.apicollabdev.odk.collabdev.dto.CommentaireDTO;
import com.apicollabdev.odk.collabdev.entity.Commentaire;
import lombok.*;

public class CommentaireMapper {
    public static Commentaire toEntity (CommentaireDTO dto) {
        Commentaire comment = new Commentaire();
        comment.setAuteur(dto.getAuteur());
        comment.setContenu(dto.getContenu());
        comment.setSupprime(dto.isSupprime());
        comment.setModifie(dto.isModifie());
        return comment;
    }

}
