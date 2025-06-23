package com.example.backendBonosCorp.bonos.application.internal.commandservices;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.commands.CrearBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EditarBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarBonoCommand;
import com.example.backendBonosCorp.bonos.domain.services.BonoCommandService;
import com.example.backendBonosCorp.bonos.infrastructure.persistence.jpa.repositories.BonoRepository;
import com.example.backendBonosCorp.shared.application.exceptions.ResourceNotFoundException;
import com.example.backendBonosCorp.shared.application.exceptions.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BonoCommandServiceImpl implements BonoCommandService {
    
    private final BonoRepository bonoRepository;

    public BonoCommandServiceImpl(BonoRepository bonoRepository) {
        this.bonoRepository = bonoRepository;
    }

    @Override
    public Optional<Bono> handle(CrearBonoCommand command) {
        var bono = new Bono(
                command.valorNominal(),
                command.fechaEmision(),
                command.fechaVencimiento(),
                command.tasaInteres(),
                command.tipoTasa(),
                command.frecuenciaPago(),
                command.cuotasTotales(),
                command.comisiones(),
                command.gastosAdicionales(),
                command.periodoGracia(),
                command.duracionGracia(),
                command.creadoPor()
        );
        
        var savedBono = bonoRepository.save(bono);
        return Optional.of(savedBono);
    }

    @Override
    public Optional<Bono> handle(EditarBonoCommand command) {
        var currentUserRuc = SecurityContextHolder.getContext().getAuthentication().getName();
        
        var bono = bonoRepository.findByIdAndCreadoPor(command.id(), currentUserRuc)
                .orElseThrow(() -> new ResourceNotFoundException("Bono no encontrado o no autorizado"));

        bono.actualizar(
                command.valorNominal(),
                command.fechaEmision(),
                command.fechaVencimiento(),
                command.tasaInteres(),
                command.tipoTasa(),
                command.frecuenciaPago(),
                command.cuotasTotales(),
                command.comisiones(),
                command.gastosAdicionales(),
                command.periodoGracia(),
                command.duracionGracia()
        );

        var updatedBono = bonoRepository.save(bono);
        return Optional.of(updatedBono);
    }

    @Override
    public void handle(EliminarBonoCommand command) {
        var currentUserRuc = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (!bonoRepository.existsByIdAndCreadoPor(command.id(), currentUserRuc)) {
            throw new ResourceNotFoundException("Bono no encontrado o no autorizado");
        }
        
        bonoRepository.deleteById(command.id());
    }
} 