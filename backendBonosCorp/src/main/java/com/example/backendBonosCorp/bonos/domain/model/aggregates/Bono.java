package com.example.backendBonosCorp.bonos.domain.model.aggregates;

import com.example.backendBonosCorp.bonos.domain.model.valueobjects.*;
import com.example.backendBonosCorp.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bonos")
@Getter
public class Bono extends AuditableAbstractAggregateRoot<Bono> {
    
    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorNominal;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaEmision;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "tasa_interes", precision = 5, scale = 2))
    private TasaInteres tasaInteres;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTasa tipoTasa;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FrecuenciaPago frecuenciaPago;

    @Min(1)
    @Column(nullable = false)
    private int cuotasTotales;

    @DecimalMin("0.00")
    @Column(precision = 15, scale = 2)
    private BigDecimal comisiones;

    @DecimalMin("0.00")
    @Column(precision = 15, scale = 2)
    private BigDecimal gastosAdicionales;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodoGracia periodoGracia;

    @Min(0)
    @Column(nullable = false)
    private int duracionGracia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoBono estado;

    @NotBlank
    @Column(nullable = false, length = 11)
    private String creadoPor;

    protected Bono() {
        // Required by JPA
    }

    public Bono(BigDecimal valorNominal, LocalDate fechaEmision, LocalDate fechaVencimiento,
                TasaInteres tasaInteres, TipoTasa tipoTasa, FrecuenciaPago frecuenciaPago,
                int cuotasTotales, BigDecimal comisiones, BigDecimal gastosAdicionales,
                PeriodoGracia periodoGracia, int duracionGracia, String creadoPor) {
        this();
        this.valorNominal = valorNominal;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.tasaInteres = tasaInteres;
        this.tipoTasa = tipoTasa;
        this.frecuenciaPago = frecuenciaPago;
        this.cuotasTotales = cuotasTotales;
        this.comisiones = comisiones != null ? comisiones : BigDecimal.ZERO;
        this.gastosAdicionales = gastosAdicionales != null ? gastosAdicionales : BigDecimal.ZERO;
        this.periodoGracia = periodoGracia;
        this.duracionGracia = duracionGracia;
        this.estado = EstadoBono.PENDIENTE;
        this.creadoPor = creadoPor;
        
        validateDates();
    }

    public void actualizar(BigDecimal valorNominal, LocalDate fechaEmision, LocalDate fechaVencimiento,
                          TasaInteres tasaInteres, TipoTasa tipoTasa, FrecuenciaPago frecuenciaPago,
                          int cuotasTotales, BigDecimal comisiones, BigDecimal gastosAdicionales,
                          PeriodoGracia periodoGracia, int duracionGracia) {
        this.valorNominal = valorNominal;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.tasaInteres = tasaInteres;
        this.tipoTasa = tipoTasa;
        this.frecuenciaPago = frecuenciaPago;
        this.cuotasTotales = cuotasTotales;
        this.comisiones = comisiones != null ? comisiones : BigDecimal.ZERO;
        this.gastosAdicionales = gastosAdicionales != null ? gastosAdicionales : BigDecimal.ZERO;
        this.periodoGracia = periodoGracia;
        this.duracionGracia = duracionGracia;
        
        validateDates();
    }

    public boolean perteneceAUsuario(String ruc) {
        return this.creadoPor.equals(ruc);
    }

    public void aprobar() {
        this.estado = EstadoBono.APROBADO;
    }

    public void rechazar() {
        this.estado = EstadoBono.RECHAZADO;
    }

    private void validateDates() {
        if (fechaVencimiento.isBefore(fechaEmision)) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser posterior a la fecha de emisión");
        }
    }

    // Getters explícitos para garantizar acceso
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public TasaInteres getTasaInteres() {
        return tasaInteres;
    }

    public TipoTasa getTipoTasa() {
        return tipoTasa;
    }

    public FrecuenciaPago getFrecuenciaPago() {
        return frecuenciaPago;
    }

    public int getCuotasTotales() {
        return cuotasTotales;
    }

    public BigDecimal getComisiones() {
        return comisiones;
    }

    public BigDecimal getGastosAdicionales() {
        return gastosAdicionales;
    }

    public PeriodoGracia getPeriodoGracia() {
        return periodoGracia;
    }

    public int getDuracionGracia() {
        return duracionGracia;
    }

    public EstadoBono getEstado() {
        return estado;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    // Getter para ID usando reflexión para acceder al campo privado de la clase padre
    public Long getId() {
        try {
            var field = AuditableAbstractAggregateRoot.class.getDeclaredField("id");
            field.setAccessible(true);
            return (Long) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing id field", e);
        }
    }
} 