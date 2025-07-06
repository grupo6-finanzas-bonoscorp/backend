package com.example.backendBonosCorp.bonos.application.internal.queryservices;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosPorBonoQuery;
import com.example.backendBonosCorp.bonos.domain.services.DocumentoQueryService;
import com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories.DocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoQueryServiceImpl implements DocumentoQueryService {

    private final DocumentoRepository documentoRepository;

    public DocumentoQueryServiceImpl(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @Override
    public List<Documento> handle(ObtenerDocumentosQuery query) {
        return documentoRepository.findAll();
    }

    @Override
    public Optional<Documento> handle(ObtenerDocumentoPorIdQuery query) {
        return documentoRepository.findById(query.id());
    }

    @Override
    public List<Documento> handle(ObtenerDocumentosPorBonoQuery query) {
        return documentoRepository.findByBonoId(query.bonoId());
    }
} 