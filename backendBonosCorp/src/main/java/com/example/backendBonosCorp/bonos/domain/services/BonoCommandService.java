package com.example.backendBonosCorp.bonos.domain.services;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.commands.CrearBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EditarBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarBonoCommand;

import java.util.Optional;

public interface BonoCommandService {
    Optional<Bono> handle(CrearBonoCommand command);
    Optional<Bono> handle(EditarBonoCommand command);
    void handle(EliminarBonoCommand command);
} 