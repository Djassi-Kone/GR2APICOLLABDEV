package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine, Long> {
     Optional<Domaine> findByIdDomaine(Long idDomaine);
}

