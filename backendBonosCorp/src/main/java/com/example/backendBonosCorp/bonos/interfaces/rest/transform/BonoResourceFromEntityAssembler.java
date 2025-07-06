package com.example.backendBonosCorp.bonos.interfaces.rest.transform;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.BonoResource;

public class BonoResourceFromEntityAssembler {
    
    public static BonoResource toResourceFromEntity(Bono entity) {
        return new BonoResource(
                entity.getId(),
                entity.getCreadoPor(),
                entity.getValorNominal(),
                entity.getFechaEmision(),
                entity.getFechaVencimiento(),
                entity.getTasaInteres().valor(),
                entity.getTipoTasa().toString(),
                entity.getFrecuenciaPago().toString(),
                entity.getCuotasTotales(),
                entity.getComisiones(),
                entity.getGastosAdicionales(),
                entity.getPeriodoGracia().toString(),
                entity.getDuracionGracia(),
                entity.getEstado().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
} 