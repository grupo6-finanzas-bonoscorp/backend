package com.example.backendBonosCorp.bonos.application.internal.queryservices;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.services.BonoQueryService;
import com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories.BonoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonoQueryServiceImpl implements BonoQueryService {
    
    private final BonoRepository bonoRepository;

    public BonoQueryServiceImpl(BonoRepository bonoRepository) {
        this.bonoRepository = bonoRepository;
    }

    @Override
    public List<Bono> handle(ObtenerBonosQuery query) {
        var currentUserRuc = SecurityContextHolder.getContext().getAuthentication().getName();
        return bonoRepository.findByCreadoPor(currentUserRuc);
    }

    @Override
    public Optional<Bono> handle(ObtenerBonoPorIdQuery query) {
        var currentUserRuc = SecurityContextHolder.getContext().getAuthentication().getName();
        return bonoRepository.findByIdAndCreadoPor(query.id(), currentUserRuc);
    }
} 