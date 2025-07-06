package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

import java.util.Date;

public record DocumentoResource(
        Long id,
        String nombre,
        String descripcion,
        String url,
        String tipo,
        Long bonoId,
        String creadoPor,
        Date fechaCreacion,
        Date fechaActualizacion
) {
} 