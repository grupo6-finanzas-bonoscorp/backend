package com.example.backendBonosCorp.bonos.domain.model.commands;

public record CrearDocumentoCommand(
        String nombre,
        String descripcion,
        String url,
        String tipo,
        Long bonoId,
        String creadoPor
) {
} 