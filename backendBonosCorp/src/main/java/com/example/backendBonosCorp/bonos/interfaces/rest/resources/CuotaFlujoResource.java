package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CuotaFlujoResource(
        Integer numeroCuota,
        LocalDate fecha,
        BigDecimal cuota,
        BigDecimal interes,
        BigDecimal amortizacion,
        BigDecimal saldo
) {
} 