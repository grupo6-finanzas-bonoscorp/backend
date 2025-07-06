package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.commands.CrearDocumentoCommand;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.CrearDocumentoResource;

public class CrearDocumentoCommandFromResourceAssembler {
    
    public static CrearDocumentoCommand toCommandFromResource(CrearDocumentoResource resource, String creadoPor) {
        return new CrearDocumentoCommand(
                resource.nombre(),
                resource.descripcion(),
                resource.url(),
                resource.tipo(),
                resource.bonoId(),
                creadoPor
        );
    }
} 