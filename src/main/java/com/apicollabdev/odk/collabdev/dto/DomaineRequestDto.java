package com.apicollabdev.odk.collabdev.dto;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class DomaineRequestDto {
    private String titre;
    private String description;
}
