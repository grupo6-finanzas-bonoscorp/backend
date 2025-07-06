package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

public record EditarDocumentoResource(
        String nombre,
        String descripcion,
        String url,
        String tipo,
        Long bonoId
) {
} 