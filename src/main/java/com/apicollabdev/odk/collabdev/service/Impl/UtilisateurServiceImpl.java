package com.apicollabdev.odk.collabdev.service.Impl;

import com.apicollabdev.odk.collabdev.entity.Utilisateur;
import com.apicollabdev.odk.collabdev.repository.UtilisateurRepository;
import com.apicollabdev.odk.collabdev.service.Interfaces.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur newData) {
        return utilisateurRepository.findById(id)
                .map(utilisateur -> {
                    utilisateur.setEmail(newData.getEmail());
                    utilisateur.setPassword(newData.getPassword());
                    return utilisateurRepository.save(utilisateur);
                }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
