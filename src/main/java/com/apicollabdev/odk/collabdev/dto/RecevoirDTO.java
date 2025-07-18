package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class RecevoirDTO {





    @Data
    public class RecevoirDto {

        private int idRecevoir;

        private LocalDateTime dateReception;

        private boolean lue;

        private int idNotification;     // On expose uniquement l’ID, pas toute l'entité
        private int idContributeur;
    }


}
