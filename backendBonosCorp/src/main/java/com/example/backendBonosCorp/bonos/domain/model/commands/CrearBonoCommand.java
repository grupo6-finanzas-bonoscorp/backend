package com.example.backendBonosCorp.bonos.domain.model.commands;

import com.example.backendBonosCorp.bonos.domain.model.valueobjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CrearBonoCommand(
        BigDecimal valorNominal,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        TasaInteres tasaInteres,
        TipoTasa tipoTasa,
        FrecuenciaPago frecuenciaPago,
        int cuotasTotales,
        BigDecimal comisiones,
        BigDecimal gastosAdicionales,
        PeriodoGracia periodoGracia,
        int duracionGracia,
        String creadoPor
) {
} 