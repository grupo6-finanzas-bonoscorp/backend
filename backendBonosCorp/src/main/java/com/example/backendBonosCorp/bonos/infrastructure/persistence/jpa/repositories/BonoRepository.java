package com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {
    Optional<Bono> findByIdAndCreadoPor(Long id, String creadoPor);
    List<Bono> findByCreadoPor(String creadoPor);
    boolean existsByIdAndCreadoPor(Long id, String creadoPor);
} 