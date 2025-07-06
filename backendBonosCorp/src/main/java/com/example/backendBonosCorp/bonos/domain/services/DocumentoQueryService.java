package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosPorBonoQuery;

import java.util.List;
import java.util.Optional;

public interface DocumentoQueryService {
    List<Documento> handle(ObtenerDocumentosQuery query);
    Optional<Documento> handle(ObtenerDocumentoPorIdQuery query);
    List<Documento> handle(ObtenerDocumentosPorBonoQuery query);
} 