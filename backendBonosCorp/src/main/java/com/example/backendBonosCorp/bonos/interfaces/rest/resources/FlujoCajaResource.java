package com.example.backendBonosCorp.bonos.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

public record FlujoCajaResource(
        Long bondId,
        List<CuotaFlujoResource> cuotas,
        BigDecimal tcea,
        BigDecimal trea,
        BigDecimal duracion,
        BigDecimal duracionModificada,
        BigDecimal convexidad,
        BigDecimal precioMaximo
) {
} 