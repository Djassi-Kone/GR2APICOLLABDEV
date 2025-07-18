package com.apicollabdev.odk.collabdev;

import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@SpringBootApplication
public class CollabdevApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollabdevApplication.class, args);

	}
}

@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

// Classe qui s'exécute automatiquement au lancement de l'application
@Component // Permet à Spring de gérer cette classe et d'injecter les dépendances
class AdminInitializer implements CommandLineRunner {

	@Autowired // Injection automatique du repository Admin
	private AdministrateurRepository administrateurRepository;

	@Autowired // Injection automatique du password encoder
	private PasswordEncoder passwordEncoder;


	@Override
	public void run(String... args) throws Exception {
		// Définition des informations de l'admin par défaut
		String emailAdmin = "admin1234@gmail.com";
		String password = "admin1234";

		// Recherche si un admin avec cet email existe déjà
		Optional<Administrateur> adminOpt = administrateurRepository.findByEmail(emailAdmin);

		// Si l'admin n'existe pas, on le crée
		if (adminOpt.isEmpty()) {
			Administrateur admin = new Administrateur();
			admin.setEmail(emailAdmin); // Correction : on utilise la variable et non un texte fixe
			admin.setPassword(passwordEncoder.encode(password)); // On encode le mot de passe avant de le sauvegarder

			administrateurRepository.save(admin); // Sauvegarde de l'admin dans la base de données
			System.out.println("L'Admin par défaut créé avec succès.");
		} else {
			// Si l'admin existe déjà
			System.out.println("Admin existe déjà.");
			System.out.println("Veillez vous connecter !");

			// Vérification du mot de passe : si le mot de passe par défaut correspond au mot de passe en base lors de la connexion
			Administrateur adminExist = adminOpt.get();
			if (passwordEncoder.matches(password, adminExist.getPassword())) {
				System.out.println("Admin connecté avec succès !");
				System.out.println("Vous êtes connecter avec l'email :" + adminExist.getEmail());
			} else {
				System.out.println("Mot de passe ou Email incorrect, veillez vérifier puis réessayer !");
			}
		}
	}
}

