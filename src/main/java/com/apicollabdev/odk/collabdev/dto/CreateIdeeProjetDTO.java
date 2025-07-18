package com.apicollabdev.odk.collabdev.dto;

import com.apicollabdev.odk.collabdev.enums.Niveau;
import lombok.Data;

@Data
public class CreateIdeeProjetDTO {
    private String titre;
    private String description;
    private Niveau niveau;
    private boolean leguer;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public boolean isLeguer() {
        return leguer;
    }

    public void setLeguer(boolean leguer) {
        this.leguer = leguer;
    }
}
