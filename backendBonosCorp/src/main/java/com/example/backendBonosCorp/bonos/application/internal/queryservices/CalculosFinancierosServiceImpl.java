package com.example.backendBonosCorp.bonos.application.internal.queryservices;

import com.example.backendBonosCorp.bonos.domain.model.aggregates.Bono;
import com.example.backendBonosCorp.bonos.domain.model.valueobjects.FrecuenciaPago;
import com.example.backendBonosCorp.bonos.domain.model.valueobjects.PeriodoGracia;
import com.example.backendBonosCorp.bonos.domain.model.valueobjects.TipoTasa;
import com.example.backendBonosCorp.bonos.domain.services.CalculosFinancierosService;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.CuotaFlujoResource;
import com.example.backendBonosCorp.bonos.interfaces.rest.resources.FlujoCajaResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculosFinancierosServiceImpl implements CalculosFinancierosService {

    @Override
    public FlujoCajaResource calcularFlujoCaja(Bono bono) {
        List<CuotaFlujoResource> cuotas = calcularCuotas(bono);
        BigDecimal tcea = calcularTCEA(bono, cuotas);
        BigDecimal trea = calcularTREA(bono, cuotas);
        BigDecimal duracion = calcularDuracion(bono, cuotas);
        BigDecimal duracionModificada = calcularDuracionModificada(duracion, tcea);
        BigDecimal convexidad = calcularConvexidad(bono, cuotas);
        BigDecimal precioMaximo = calcularPrecioMaximo(bono, cuotas);

        return new FlujoCajaResource(
                bono.getId(),
                cuotas,
                tcea,
                trea,
                duracion,
                duracionModificada,
                convexidad,
                precioMaximo
        );
    }

    private List<CuotaFlujoResource> calcularCuotas(Bono bono) {
        List<CuotaFlujoResource> cuotas = new ArrayList<>();
        BigDecimal valorNominal = bono.getValorNominal();
        BigDecimal tasaInteres = bono.getTasaInteres().valor();
        int cuotasTotales = bono.getCuotasTotales();
        int duracionGracia = bono.getDuracionGracia();
        PeriodoGracia periodoGracia = bono.getPeriodoGracia();
        FrecuenciaPago frecuenciaPago = bono.getFrecuenciaPago();
        BigDecimal tasaEfectiva = bono.getTipoTasa() == TipoTasa.NOMINAL ? convertirTasaNominalAEfectiva(tasaInteres, frecuenciaPago) : tasaInteres;
        BigDecimal cuotaBase = calcularCuotaFija(valorNominal, tasaEfectiva, cuotasTotales);
        BigDecimal saldo = valorNominal;
        LocalDate fechaCuota = bono.getFechaEmision();
        for (int i = 1; i <= cuotasTotales; i++) {
            fechaCuota = calcularSiguienteFecha(fechaCuota, frecuenciaPago);
            BigDecimal interes = BigDecimal.ZERO;
            BigDecimal amortizacion = BigDecimal.ZERO;
            BigDecimal cuota = BigDecimal.ZERO;
            if (i <= duracionGracia && periodoGracia != PeriodoGracia.NINGUNO) {
                if (periodoGracia == PeriodoGracia.TOTAL) {
                    interes = saldo.multiply(tasaEfectiva).divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
                    cuota = BigDecimal.ZERO;
                    amortizacion = BigDecimal.ZERO;
                    saldo = saldo.add(interes); // Capitalización del interés
                } else if (periodoGracia == PeriodoGracia.PARCIAL) {
                    interes = saldo.multiply(tasaEfectiva).divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
                    cuota = interes;
                    amortizacion = BigDecimal.ZERO;
                }
            } else {
                interes = saldo.multiply(tasaEfectiva).divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
                cuota = cuotaBase;
                amortizacion = cuota.subtract(interes);
                saldo = saldo.subtract(amortizacion);
            }
            if (saldo.compareTo(BigDecimal.ZERO) < 0) saldo = BigDecimal.ZERO;
            cuotas.add(new CuotaFlujoResource(i, fechaCuota, cuota.setScale(2, RoundingMode.HALF_UP), interes.setScale(2, RoundingMode.HALF_UP), amortizacion.setScale(2, RoundingMode.HALF_UP), saldo.setScale(2, RoundingMode.HALF_UP)));
        }
        return cuotas;
    }

    // PMT = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
    private BigDecimal calcularCuotaFija(BigDecimal principal, BigDecimal tasaEfectiva, int cuotas) {
        BigDecimal tasa = tasaEfectiva.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        BigDecimal unoMasTasa = BigDecimal.ONE.add(tasa);
        BigDecimal unoMasTasaElevadoN = unoMasTasa.pow(cuotas);
        BigDecimal numerador = principal.multiply(tasa).multiply(unoMasTasaElevadoN);
        BigDecimal denominador = unoMasTasaElevadoN.subtract(BigDecimal.ONE);
        return numerador.divide(denominador, 2, RoundingMode.HALF_UP);
    }

    // TEA = (1 + TNA/m)^m - 1
    private BigDecimal convertirTasaNominalAEfectiva(BigDecimal tasaNominal, FrecuenciaPago frecuencia) {
        int periodosPorAno = obtenerPeriodosPorAno(frecuencia);
        BigDecimal tasaPeriodica = tasaNominal.divide(BigDecimal.valueOf(periodosPorAno), 8, RoundingMode.HALF_UP);
        BigDecimal tasaPeriodicaDecimal = tasaPeriodica.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        BigDecimal unoMasTasaPeriodica = BigDecimal.ONE.add(tasaPeriodicaDecimal);
        BigDecimal unoMasTasaPeriodicaElevadoM = unoMasTasaPeriodica.pow(periodosPorAno);
        BigDecimal tasaEfectiva = unoMasTasaPeriodicaElevadoM.subtract(BigDecimal.ONE);
        return tasaEfectiva.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    private int obtenerPeriodosPorAno(FrecuenciaPago frecuencia) {
        return switch (frecuencia) {
            case MENSUAL -> 12;
            case BIMESTRAL -> 6;
            case TRIMESTRAL -> 4;
            case SEMESTRAL -> 2;
            case ANUAL -> 1;
        };
    }

    private LocalDate calcularSiguienteFecha(LocalDate fecha, FrecuenciaPago frecuencia) {
        return switch (frecuencia) {
            case MENSUAL -> fecha.plusMonths(1);
            case BIMESTRAL -> fecha.plusMonths(2);
            case TRIMESTRAL -> fecha.plusMonths(3);
            case SEMESTRAL -> fecha.plusMonths(6);
            case ANUAL -> fecha.plusYears(1);
        };
    }

    // TCEA: TIR de los flujos considerando gastos y comisiones
    private BigDecimal calcularTCEA(Bono bono, List<CuotaFlujoResource> cuotas) {
        BigDecimal comisionesGastos = bono.getComisiones().add(bono.getGastosAdicionales());
        BigDecimal valorNeto = bono.getValorNominal().subtract(comisionesGastos);
        List<BigDecimal> flujos = new ArrayList<>();
        flujos.add(valorNeto.negate());
        for (CuotaFlujoResource cuota : cuotas) flujos.add(cuota.cuota());
        return calcularTIR(flujos).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    // TREA: TIR de los flujos sin gastos
    private BigDecimal calcularTREA(Bono bono, List<CuotaFlujoResource> cuotas) {
        BigDecimal valorNominal = bono.getValorNominal();
        List<BigDecimal> flujos = new ArrayList<>();
        flujos.add(valorNominal.negate());
        for (CuotaFlujoResource cuota : cuotas) flujos.add(cuota.cuota());
        return calcularTIR(flujos).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    // TIR usando Newton-Raphson
    private BigDecimal calcularTIR(List<BigDecimal> flujos) {
        BigDecimal tasa = new BigDecimal("0.1");
        BigDecimal tolerancia = new BigDecimal("0.000001");
        int maxIteraciones = 100;
        for (int i = 0; i < maxIteraciones; i++) {
            BigDecimal vpn = calcularVPN(flujos, tasa);
            BigDecimal derivada = calcularDerivadaVPN(flujos, tasa);
            if (derivada.compareTo(BigDecimal.ZERO) == 0) break;
            BigDecimal nuevaTasa = tasa.subtract(vpn.divide(derivada, 8, RoundingMode.HALF_UP));
            if (nuevaTasa.subtract(tasa).abs().compareTo(tolerancia) < 0) return nuevaTasa;
            tasa = nuevaTasa;
        }
        return tasa;
    }
    private BigDecimal calcularVPN(List<BigDecimal> flujos, BigDecimal tasa) {
        BigDecimal vpn = BigDecimal.ZERO;
        for (int i = 0; i < flujos.size(); i++) {
            BigDecimal factorDescuento = BigDecimal.ONE.divide(BigDecimal.ONE.add(tasa).pow(i), 8, RoundingMode.HALF_UP);
            vpn = vpn.add(flujos.get(i).multiply(factorDescuento));
        }
        return vpn;
    }
    private BigDecimal calcularDerivadaVPN(List<BigDecimal> flujos, BigDecimal tasa) {
        BigDecimal derivada = BigDecimal.ZERO;
        for (int i = 1; i < flujos.size(); i++) {
            BigDecimal factorDescuento = BigDecimal.valueOf(i).multiply(BigDecimal.ONE.divide(BigDecimal.ONE.add(tasa).pow(i + 1), 8, RoundingMode.HALF_UP));
            derivada = derivada.add(flujos.get(i).multiply(factorDescuento).negate());
        }
        return derivada;
    }

    // Duración de Macaulay
    private BigDecimal calcularDuracion(Bono bono, List<CuotaFlujoResource> cuotas) {
        BigDecimal valorPresente = BigDecimal.ZERO;
        BigDecimal valorPresentePonderado = BigDecimal.ZERO;
        BigDecimal tasaEfectiva = bono.getTipoTasa() == TipoTasa.NOMINAL ? convertirTasaNominalAEfectiva(bono.getTasaInteres().valor(), bono.getFrecuenciaPago()) : bono.getTasaInteres().valor();
        BigDecimal tasa = tasaEfectiva.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        for (int i = 0; i < cuotas.size(); i++) {
            CuotaFlujoResource cuota = cuotas.get(i);
            BigDecimal tiempo = BigDecimal.valueOf(i + 1);
            BigDecimal factorDescuento = BigDecimal.ONE.divide(BigDecimal.ONE.add(tasa).pow(i + 1), 8, RoundingMode.HALF_UP);
            BigDecimal valorPresenteCuota = cuota.cuota().multiply(factorDescuento);
            valorPresente = valorPresente.add(valorPresenteCuota);
            valorPresentePonderado = valorPresentePonderado.add(valorPresenteCuota.multiply(tiempo));
        }
        return valorPresentePonderado.divide(valorPresente, 2, RoundingMode.HALF_UP);
    }

    // Duración Modificada
    private BigDecimal calcularDuracionModificada(BigDecimal duracion, BigDecimal tcea) {
        BigDecimal tasa = tcea.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        return duracion.divide(BigDecimal.ONE.add(tasa), 2, RoundingMode.HALF_UP);
    }

    // Convexidad estándar
    private BigDecimal calcularConvexidad(Bono bono, List<CuotaFlujoResource> cuotas) {
        BigDecimal valorPresente = BigDecimal.ZERO;
        BigDecimal valorPresentePonderado = BigDecimal.ZERO;
        BigDecimal tasaEfectiva = bono.getTipoTasa() == TipoTasa.NOMINAL ? convertirTasaNominalAEfectiva(bono.getTasaInteres().valor(), bono.getFrecuenciaPago()) : bono.getTasaInteres().valor();
        BigDecimal tasa = tasaEfectiva.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        BigDecimal unoMasTasaCuadrado = BigDecimal.ONE.add(tasa).pow(2);
        for (int i = 0; i < cuotas.size(); i++) {
            CuotaFlujoResource cuota = cuotas.get(i);
            BigDecimal tiempo = BigDecimal.valueOf(i + 1);
            BigDecimal tiempoMasUno = tiempo.add(BigDecimal.ONE);
            BigDecimal factorDescuento = BigDecimal.ONE.divide(BigDecimal.ONE.add(tasa).pow(i + 1), 8, RoundingMode.HALF_UP);
            BigDecimal valorPresenteCuota = cuota.cuota().multiply(factorDescuento);
            valorPresente = valorPresente.add(valorPresenteCuota);
            valorPresentePonderado = valorPresentePonderado.add(valorPresenteCuota.multiply(tiempo).multiply(tiempoMasUno));
        }
        return valorPresentePonderado.divide(valorPresente.multiply(unoMasTasaCuadrado), 2, RoundingMode.HALF_UP);
    }

    // Precio del bono
    private BigDecimal calcularPrecioMaximo(Bono bono, List<CuotaFlujoResource> cuotas) {
        BigDecimal valorPresente = BigDecimal.ZERO;
        BigDecimal tasaEfectiva = bono.getTipoTasa() == TipoTasa.NOMINAL ? convertirTasaNominalAEfectiva(bono.getTasaInteres().valor(), bono.getFrecuenciaPago()) : bono.getTasaInteres().valor();
        BigDecimal tasa = tasaEfectiva.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
        for (int i = 0; i < cuotas.size(); i++) {
            CuotaFlujoResource cuota = cuotas.get(i);
            BigDecimal factorDescuento = BigDecimal.ONE.divide(BigDecimal.ONE.add(tasa).pow(i + 1), 8, RoundingMode.HALF_UP);
            BigDecimal valorPresenteCuota = cuota.cuota().multiply(factorDescuento);
            valorPresente = valorPresente.add(valorPresenteCuota);
        }
        return valorPresente.setScale(2, RoundingMode.HALF_UP);
    }
} 