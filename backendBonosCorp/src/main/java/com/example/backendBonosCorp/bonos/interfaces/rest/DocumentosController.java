package com.example.backendBonosCorp.bonos.interfaces.rest;

import com.example.backendBonosCorp.bonos.domain.model.commands.CrearDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EditarDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.commands.EliminarDocumentoCommand;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentoPorIdQuery;
import com.example.backendBonosCorp.bonos.domain.model.queries.ObtenerDocumentosPorBonoQuery;
import com.example.backendBonosCorp.bonos.domain.services.DocumentoCommandService;
import com.example.backendBonosCorp.bonos.domain.services.DocumentoQueryService;
import com.example.backendBonosCorp.bonos.interfaces.acl.UserContextFacade;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.DocumentoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.CrearDocumentoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.EditarDocumentoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.DocumentoResourceFromEntityAssembler;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.CrearDocumentoCommandFromResourceAssembler;
import com.example.backendBonosCorp.bonos.interfaces.rest.transform.EditarDocumentoCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/documents", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Documentos", description = "Endpoints para gestión de documentos")
public class DocumentosController {

    private final DocumentoCommandService documentoCommandService;
    private final DocumentoQueryService documentoQueryService;
    private final UserContextFacade userContextFacade;

    public DocumentosController(DocumentoCommandService documentoCommandService,
                               DocumentoQueryService documentoQueryService,
                               UserContextFacade userContextFacade) {
        this.documentoCommandService = documentoCommandService;
        this.documentoQueryService = documentoQueryService;
        this.userContextFacade = userContextFacade;
    }

    @GetMapping
    public ResponseEntity<List<DocumentoResource>> getAllDocumentos() {
        var obtenerDocumentosQuery = new ObtenerDocumentosQuery();
        var documentos = documentoQueryService.handle(obtenerDocumentosQuery);
        var documentoResources = documentos.stream()
                .map(DocumentoResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(documentoResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResource> getDocumentoById(@PathVariable Long id) {
        var obtenerDocumentoPorIdQuery = new ObtenerDocumentoPorIdQuery(id);
        var documento = documentoQueryService.handle(obtenerDocumentoPorIdQuery);
        if (documento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var documentoResource = DocumentoResourceFromEntityAssembler.toResourceFromEntity(documento.get());
        return ResponseEntity.ok(documentoResource);
    }

    @GetMapping("/bono/{bonoId}")
    public ResponseEntity<List<DocumentoResource>> getDocumentosByBono(@PathVariable Long bonoId) {
        var obtenerDocumentosPorBonoQuery = new ObtenerDocumentosPorBonoQuery(bonoId);
        var documentos = documentoQueryService.handle(obtenerDocumentosPorBonoQuery);
        var documentoResources = documentos.stream()
                .map(DocumentoResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(documentoResources);
    }

    @PostMapping
    public ResponseEntity<DocumentoResource> createDocumento(@RequestBody CrearDocumentoResource resource) {
        var currentUserRuc = userContextFacade.getCurrentUserRuc();
        var crearDocumentoCommand = CrearDocumentoCommandFromResourceAssembler.toCommandFromResource(resource, currentUserRuc);
        var documento = documentoCommandService.handle(crearDocumentoCommand);
        if (documento.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var documentoResource = DocumentoResourceFromEntityAssembler.toResourceFromEntity(documento.get());
        return new ResponseEntity<>(documentoResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoResource> updateDocumento(@PathVariable Long id, @RequestBody EditarDocumentoResource resource) {
        var editarDocumentoCommand = EditarDocumentoCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var documento = documentoCommandService.handle(editarDocumentoCommand);
        if (documento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var documentoResource = DocumentoResourceFromEntityAssembler.toResourceFromEntity(documento.get());
        return ResponseEntity.ok(documentoResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        var eliminarDocumentoCommand = new EliminarDocumentoCommand(id);
        documentoCommandService.handle(eliminarDocumentoCommand);
        return ResponseEntity.noContent().build();
    }
} 