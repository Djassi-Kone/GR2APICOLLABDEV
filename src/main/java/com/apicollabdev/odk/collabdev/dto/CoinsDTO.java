package com.apicollabdev.odk.collabdev.dto;

import lombok.Data;

@Data
public class CoinsDTO {
    private int nombreCoins;

    public int getNombreCoins() {
        return nombreCoins;
    }

    public void setNombreCoins(int nombreCoins) {
        this.nombreCoins = nombreCoins;
    }
}
