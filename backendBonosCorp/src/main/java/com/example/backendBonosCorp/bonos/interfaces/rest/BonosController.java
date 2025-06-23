package com.example.backendBonosCorp.bonos.interfaces.rest;

import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarBonoCommand;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerBonoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.services.BonoCommandService;
import com.example.backendBonosCorp.bonos.domain.services.BonoQueryService;
import com.example.backendBonosCorp.bonos.interfaces.acl.UserContextFacade;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.BonoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.CrearBonoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.EditarBonoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.BonoResourceFromEntityAssembler;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.CrearBonoCommandFromResourceAssembler;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.EditarBonoCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/bonos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bonos", description = "Endpoints para gestión de bonos corporativos")
public class BonosController {

    private final BonoCommandService bonoCommandService;
    private final BonoQueryService bonoQueryService;
    private final UserContextFacade userContextFacade;

    public BonosController(BonoCommandService bonoCommandService, 
                          BonoQueryService bonoQueryService, 
                          UserContextFacade userContextFacade) {
        this.bonoCommandService = bonoCommandService;
        this.bonoQueryService = bonoQueryService;
        this.userContextFacade = userContextFacade;
    }

    @GetMapping
    public ResponseEntity<List<BonoResource>> getAllBonos() {
        var obtenerBonosQuery = new ObtenerBonosQuery();
        var bonos = bonoQueryService.handle(obtenerBonosQuery);
        var bonoResources = bonos.stream()
                .map(BonoResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(bonoResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonoResource> getBonoById(@PathVariable Long id) {
        var obtenerBonoPorIdQuery = new ObtenerBonoPorIdQuery(id);
        var bono = bonoQueryService.handle(obtenerBonoPorIdQuery);
        if (bono.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bonoResource = BonoResourceFromEntityAssembler.toResourceFromEntity(bono.get());
        return ResponseEntity.ok(bonoResource);
    }

    @PostMapping
    public ResponseEntity<BonoResource> createBono(@RequestBody CrearBonoResource resource) {
        var currentUserRuc = userContextFacade.getCurrentUserRuc();
        var crearBonoCommand = CrearBonoCommandFromResourceAssembler.toCommandFromResource(resource, currentUserRuc);
        var bono = bonoCommandService.handle(crearBonoCommand);
        if (bono.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var bonoResource = BonoResourceFromEntityAssembler.toResourceFromEntity(bono.get());
        return new ResponseEntity<>(bonoResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BonoResource> updateBono(@PathVariable Long id, @RequestBody EditarBonoResource resource) {
        var editarBonoCommand = EditarBonoCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var bono = bonoCommandService.handle(editarBonoCommand);
        if (bono.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bonoResource = BonoResourceFromEntityAssembler.toResourceFromEntity(bono.get());
        return ResponseEntity.ok(bonoResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBono(@PathVariable Long id) {
        var eliminarBonoCommand = new EliminarBonoCommand(id);
        bonoCommandService.handle(eliminarBonoCommand);
        return ResponseEntity.noContent().build();
    }
} 