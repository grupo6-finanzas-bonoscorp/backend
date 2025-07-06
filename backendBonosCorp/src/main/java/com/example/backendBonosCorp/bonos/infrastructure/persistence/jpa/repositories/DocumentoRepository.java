package com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByBonoId(Long bonoId);
    List<Documento> findByCreadoPor(String creadoPor);
    List<Documento> findByBonoIdAndCreadoPor(Long bonoId, String creadoPor);
} 