package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Documento;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.DocumentoResource;

public class DocumentoResourceFromEntityAssembler {
    
    public static DocumentoResource toResourceFromEntity(Documento entity) {
        return new DocumentoResource(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getUrl(),
                entity.getTipo(),
                entity.getBonoId(),
                entity.getCreadoPor(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
} 