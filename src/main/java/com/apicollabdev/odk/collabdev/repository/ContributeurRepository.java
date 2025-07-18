package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Contributeur;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContributeurRepository extends JpaRepository<Contributeur, Long> {
    Optional<Contributeur> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}

