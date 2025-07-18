package com.apicollabdev.odk.collabdev;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdministrateurRepository administrateurRepository;

    public AdminInitializer(AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si un admin existe déjà
        if (administrateurRepository.count() == 0) {
            Administrateur admin = new Administrateur();
            admin.setNom("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123"); // mot de passe stocké en clair (à éviter en prod)

            administrateurRepository.save(admin);

            System.out.println("Administrateur par défaut créé avec succès.");
        } else {
            System.out.println("Un administrateur existe déjà. Aucun ajout effectué.");
        }
    }
}
