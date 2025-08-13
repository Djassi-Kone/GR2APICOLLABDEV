package com.apicollabdev.odk.collabdev.repository;


import com.apicollabdev.odk.collabdev.entity.Gestionnaire;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GestionnaireRepository extends JpaRepository<Gestionnaire, Long> {
    @Query("SELECT g FROM Gestionnaire g WHERE g.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Gestionnaire> findByIdWithLock(@Param("id") Long id);

}

