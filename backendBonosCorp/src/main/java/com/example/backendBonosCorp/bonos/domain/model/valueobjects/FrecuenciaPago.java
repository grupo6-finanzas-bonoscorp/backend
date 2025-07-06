package com.example.backendBonosCorp.bonos.domain.model.valueobjects;

public enum FrecuenciaPago {
    MENSUAL,
    BIMESTRAL,
    TRIMESTRAL,
    SEMESTRAL,
    ANUAL;
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
} 