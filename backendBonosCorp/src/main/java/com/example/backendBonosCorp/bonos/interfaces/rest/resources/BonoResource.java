package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record BonoResource(
        Long id,
        String userRuc,
        BigDecimal valorNominal,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        BigDecimal tasaInteres,
        String tipoTasa,
        String frecuenciaPago,
        Integer cuotasTotales,
        BigDecimal comisiones,
        BigDecimal gastos,
        String periodoGracia,
        Integer duracionPeriodoGracia,
        String estado,
        Date createdAt,
        Date updatedAt
) {
} 