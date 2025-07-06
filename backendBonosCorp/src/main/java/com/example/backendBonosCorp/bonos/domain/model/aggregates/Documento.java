package com.example.backendBonosCorp.bonos.domain.model.aggregates;

import com.example.backendBonosCorp.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "documentos")
public class Documento extends AuditableAbstractAggregateRoot<Documento> {
    
    @NotBlank
    @Column(nullable = false, length = 255)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @NotBlank
    @Column(nullable = false, length = 500)
    private String url;
    
    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipo;
    
    @NotNull
    @Column(nullable = false)
    private Long bonoId;
    
    @NotBlank
    @Column(nullable = false, length = 11)
    private String creadoPor;
    
    @NotNull
    @Column(nullable = false)
    private Date fechaCreacion;
    
    @Column
    private Date fechaActualizacion;

    protected Documento() {
        // Required by JPA
    }

    public Documento(String nombre, String descripcion, String url, String tipo, 
                    Long bonoId, String creadoPor) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.tipo = tipo;
        this.bonoId = bonoId;
        this.creadoPor = creadoPor;
        this.fechaCreacion = new Date();
    }

    public void actualizar(String nombre, String descripcion, String url, String tipo, Long bonoId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.tipo = tipo;
        this.bonoId = bonoId;
        this.fechaActualizacion = new Date();
    }

    public boolean perteneceAUsuario(String ruc) {
        return this.creadoPor.equals(ruc);
    }

    public boolean perteneceABono(Long bonoId) {
        return this.bonoId.equals(bonoId);
    }

    // Getters explícitos
    public Long getId() {
        try {
            var field = AuditableAbstractAggregateRoot.class.getDeclaredField("id");
            field.setAccessible(true);
            return (Long) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing id field", e);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrl() {
        return url;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getBonoId() {
        return bonoId;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }
} 