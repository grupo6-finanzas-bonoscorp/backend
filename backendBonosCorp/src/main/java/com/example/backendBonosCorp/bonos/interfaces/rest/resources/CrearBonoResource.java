package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CrearBonoResource(
        BigDecimal valorNominal,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        BigDecimal tasaInteres,
        String tipoTasa,
        String frecuenciaPago,
        Integer cuotasTotales,
        BigDecimal comisiones,
        BigDecimal gastosAdicionales,
        String periodoGracia,
        Integer duracionGracia
) {
} 