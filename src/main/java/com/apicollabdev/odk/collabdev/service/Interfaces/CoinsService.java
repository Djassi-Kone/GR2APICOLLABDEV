package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.CoinsDTO;
import com.apicollabdev.odk.collabdev.entity.Coins;

import java.util.List;

public interface CoinsService {
    Coins createCoins(CoinsDTO dto, long idAdmin);
    List<Coins> getAllCoins();
    Coins getById(Long id);
    void deleteById(Long id);
    

}
