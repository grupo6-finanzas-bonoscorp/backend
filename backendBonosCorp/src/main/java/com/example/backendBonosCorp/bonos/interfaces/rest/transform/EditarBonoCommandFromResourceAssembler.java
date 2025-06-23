package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.commands.EditarBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.valueobjects.*;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.EditarBonoResource;

public class EditarBonoCommandFromResourceAssembler {
    
    public static EditarBonoCommand toCommandFromResource(Long id, EditarBonoResource resource) {
        return new EditarBonoCommand(
                id,
                resource.valorNominal(),
                resource.fechaEmision(),
                resource.fechaVencimiento(),
                new TasaInteres(resource.tasaInteres()),
                TipoTasa.valueOf(resource.tipoTasa().toUpperCase()),
                FrecuenciaPago.valueOf(resource.frecuenciaPago().toUpperCase()),
                resource.cuotasTotales(),
                resource.comisiones(),
                resource.gastosAdicionales(),
                PeriodoGracia.valueOf(resource.periodoGracia().toUpperCase()),
                resource.duracionGracia()
        );
    }
} 