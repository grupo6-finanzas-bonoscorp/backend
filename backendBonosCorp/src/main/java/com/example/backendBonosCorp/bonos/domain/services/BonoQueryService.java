package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonoPorIdQuery;

import java.util.List;
import java.util.Optional;

public interface BonoQueryService {
    List<Bono> handle(ObtenerBonosQuery query);
    Optional<Bono> handle(ObtenerBonoPorIdQuery query);
} 