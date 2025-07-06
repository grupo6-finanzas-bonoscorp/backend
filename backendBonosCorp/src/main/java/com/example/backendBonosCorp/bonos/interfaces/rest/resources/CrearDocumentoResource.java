package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

public record CrearDocumentoResource(
        String nombre,
        String descripcion,
        String url,
        String tipo,
        Long bonoId
) {
} 