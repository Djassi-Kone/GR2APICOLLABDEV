package com.apicollabdev.odk.collabdev.service.Interfaces;

import com.apicollabdev.odk.collabdev.dto.BadgeDTO;
import com.apicollabdev.odk.collabdev.entity.Badge;

import java.util.List;

public interface BadgeService {
    Badge createBadge(BadgeDTO dto, long idAdmin);
    List<Badge> getAllBadges();
    Badge getById(Long id);
    void deleteById(Long id);
}

