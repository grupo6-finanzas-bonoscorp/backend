package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.commands.EditarDocumentoCommand;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.EditarDocumentoResource;

public class EditarDocumentoCommandFromResourceAssembler {
    
    public static EditarDocumentoCommand toCommandFromResource(Long id, EditarDocumentoResource resource) {
        return new EditarDocumentoCommand(
                id,
                resource.nombre(),
                resource.descripcion(),
                resource.url(),
                resource.tipo(),
                resource.bonoId()
        );
    }
} 