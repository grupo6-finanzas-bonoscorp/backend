package com.example.backendBonosCorp.bonos.domain.model.valueobjects;

public enum PeriodoGracia {
    NINGUNO,
    PARCIAL,
    TOTAL;
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
} 