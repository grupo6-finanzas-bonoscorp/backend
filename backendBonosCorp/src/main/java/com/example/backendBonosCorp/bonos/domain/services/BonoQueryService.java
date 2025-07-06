package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonosPorUsuarioQuery;

import java.util.List;
import java.util.Optional;

public interface BonoQueryService {
    List<Bono> handle(ObtenerBonosQuery query);
    Optional<Bono> handle(ObtenerBonoPorIdQuery query);
    List<Bono> handle(ObtenerBonosPorUsuarioQuery query);
} 