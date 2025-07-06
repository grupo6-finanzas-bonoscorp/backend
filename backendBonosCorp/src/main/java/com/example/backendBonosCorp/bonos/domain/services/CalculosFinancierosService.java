package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.FlujoCajaResource;

public interface CalculosFinancierosService {
    FlujoCajaResource calcularFlujoCaja(Bono bono);
} 