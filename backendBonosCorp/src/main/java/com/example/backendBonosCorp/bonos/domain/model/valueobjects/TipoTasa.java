package com.example.backendBonosCorp.bonos.domain.model.valueobjects;

public enum TipoTasa {
    EFECTIVA,
    NOMINAL;
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
} 