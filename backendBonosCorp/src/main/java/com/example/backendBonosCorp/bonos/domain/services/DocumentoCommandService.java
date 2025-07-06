package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import com.example.backendBonosCorp.bonos.domain.model.commands.CrearDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EditarDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarDocumentoCommand;

import java.util.Optional;

public interface DocumentoCommandService {
    Optional<Documento> handle(CrearDocumentoCommand command);
    Optional<Documento> handle(EditarDocumentoCommand command);
    void handle(EliminarDocumentoCommand command);
} 