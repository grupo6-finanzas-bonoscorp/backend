package com.example.backendBonosCorp.bonos.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public record TasaInteres(@NotNull @DecimalMin("0.01") @DecimalMax("100.00") BigDecimal valor) {
    public TasaInteres {
        if (valor == null) {
            throw new IllegalArgumentException("Tasa de interés no puede ser nula");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tasa de interés debe ser mayor a 0");
        }
        if (valor.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Tasa de interés no puede ser mayor a 100%");
        }
    }

    public TasaInteres() {
        this(new BigDecimal("0.01"));
    }
} 