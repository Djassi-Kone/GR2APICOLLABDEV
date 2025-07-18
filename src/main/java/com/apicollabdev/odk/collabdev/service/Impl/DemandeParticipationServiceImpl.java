package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.DemandeParticipation;
import com.apicollabdev.odk.collabdev.repository.DemandeParticipationRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.DemandeParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DemandeParticipationServiceImpl implements DemandeParticipationService {
    @Autowired
    private DemandeParticipationRepository demandeParticipationRepository;

    @Override
    public DemandeParticipation createDemandeParticipation(DemandeParticipation demandeParticipation) {
        return demandeParticipationRepository.save(demandeParticipation);
    }

    @Override
    public List<DemandeParticipation> getAllDemandeParticipation() {
        return demandeParticipationRepository.findAll();
    }

    @Override
    public DemandeParticipation getById(Long id) {
        return demandeParticipationRepository.findById(Math.toIntExact((id)))
                .orElseThrow(() -> new RuntimeException("Coins non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!demandeParticipationRepository.existsById(Math.toIntExact((id)))) {
            throw new RuntimeException("Le DemandeParticipation avec l'id " + id + " n'existe pas.");
        }
        demandeParticipationRepository.deleteById(Math.toIntExact((id)));
    }
}
