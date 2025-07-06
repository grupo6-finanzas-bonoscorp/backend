package com.example.backendBonosCorp.bonos.domain.model.commands;

public record EditarDocumentoCommand(
        Long id,
        String nombre,
        String descripcion,
        String url,
        String tipo,
        Long bonoId
) {
} 