package com.apicollabdev.odk.collabdev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    // Répertoire de stockage défini dans application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Fichier vide ou null");
        }

        try {
            // Nettoyer le nom du fichier
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Créer le chemin complet
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);

            // Créer le dossier si inexistant
            Files.createDirectories(targetLocation.getParent());

            // Copier le fichier
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Retourner le chemin relatif ou absolu selon ton choix
            return targetLocation.toString();

        } catch (IOException ex) {
            throw new RuntimeException("Impossible de stocker le fichier: " + ex.getMessage());
        }
    }
}
