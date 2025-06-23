package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.commands.CrearBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.valueobjects.*;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.CrearBonoResource;

public class CrearBonoCommandFromResourceAssembler {
    
    public static CrearBonoCommand toCommandFromResource(CrearBonoResource resource, String currentUserRuc) {
        return new CrearBonoCommand(
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
                resource.duracionGracia(),
                currentUserRuc
        );
    }
} 