package com.example.backendBonosCorp.bonos.application.internal.commandservices;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import com.example.backendBonosCorp.bonos.domain.model.commands.CrearDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EditarDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.services.DocumentoCommandService;
import com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories.DocumentoRepository;
import com.example.backendBonosCorp.shared.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentoCommandServiceImpl implements DocumentoCommandService {

    private final DocumentoRepository documentoRepository;

    public DocumentoCommandServiceImpl(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @Override
    public Optional<Documento> handle(CrearDocumentoCommand command) {
        try {
            var documento = new Documento(
                    command.nombre(),
                    command.descripcion(),
                    command.url(),
                    command.tipo(),
                    command.bonoId(),
                    command.creadoPor()
            );
            var savedDocumento = documentoRepository.save(documento);
            return Optional.of(savedDocumento);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Documento> handle(EditarDocumentoCommand command) {
        var documento = documentoRepository.findById(command.id());
        if (documento.isEmpty()) {
            return Optional.empty();
        }
        
        try {
            documento.get().actualizar(
                    command.nombre(),
                    command.descripcion(),
                    command.url(),
                    command.tipo(),
                    command.bonoId()
            );
            var savedDocumento = documentoRepository.save(documento.get());
            return Optional.of(savedDocumento);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void handle(EliminarDocumentoCommand command) {
        if (!documentoRepository.existsById(command.id())) {
            throw new ResourceNotFoundException("Documento no encontrado");
        }
        documentoRepository.deleteById(command.id());
    }
} 