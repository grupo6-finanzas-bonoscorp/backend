# API Endpoints - Backend Bonos Corp

## Autenticación

### POST /api/v1/auth/sign-in
Iniciar sesión
```json
{
  "ruc": "12345678901",
  "password": "password123"
}
```

### POST /api/v1/auth/sign-up
Registrar nuevo usuario
```json
{
  "ruc": "12345678901",
  "razonSocial": "Empresa Ejemplo S.A.C.",
  "direccion": "Av. Principal 123",
  "sectorEmpresarial": "Tecnología",
  "password": "password123"
}
```

## Bonos

### GET /api/v1/bonos
Obtener todos los bonos

### GET /api/v1/bonos/usuario
Obtener bonos del usuario autenticado

### GET /api/v1/bonos/{id}
Obtener bono por ID

### GET /api/v1/bonos/{id}/flujo-caja
Obtener flujo de caja del bono

### POST /api/v1/bonos
Crear nuevo bono
```json
{
  "valorNominal": 100000.00,
  "fechaEmision": "2024-01-01",
  "fechaVencimiento": "2025-01-01",
  "tasaInteres": 5.5,
  "tipoTasa": "efectiva",
  "frecuenciaPago": "mensual",
  "cuotasTotales": 12,
  "comisiones": 1000.00,
  "gastosAdicionales": 500.00,
  "periodoGracia": "ninguno",
  "duracionGracia": 0
}
```

### PUT /api/v1/bonos/{id}
Actualizar bono
```json
{
  "valorNominal": 100000.00,
  "fechaEmision": "2024-01-01",
  "fechaVencimiento": "2025-01-01",
  "tasaInteres": 5.5,
  "tipoTasa": "efectiva",
  "frecuenciaPago": "mensual",
  "cuotasTotales": 12,
  "comisiones": 1000.00,
  "gastosAdicionales": 500.00,
  "periodoGracia": "ninguno",
  "duracionGracia": 0
}
```

### DELETE /api/v1/bonos/{id}
Eliminar bono

## Documentos

### GET /api/v1/documents
Obtener todos los documentos

### GET /api/v1/documents/{id}
Obtener documento por ID

### GET /api/v1/documents/bono/{bonoId}
Obtener documentos por bono

### POST /api/v1/documents
Crear nuevo documento
```json
{
  "nombre": "Contrato de Bono",
  "descripcion": "Documento legal del bono corporativo",
  "url": "https://ejemplo.com/documento.pdf",
  "tipo": "contrato",
  "bonoId": 1
}
```

### PUT /api/v1/documents/{id}
Actualizar documento
```json
{
  "nombre": "Contrato de Bono Actualizado",
  "descripcion": "Documento legal del bono corporativo actualizado",
  "url": "https://ejemplo.com/documento-actualizado.pdf",
  "tipo": "contrato",
  "bonoId": 1
}
```

### DELETE /api/v1/documents/{id}
Eliminar documento

## Formatos de Respuesta

### Bono
```json
{
  "id": 1,
  "userRuc": "12345678901",
  "valorNominal": 100000.00,
  "fechaEmision": "2024-01-01",
  "fechaVencimiento": "2025-01-01",
  "tasaInteres": 5.5,
  "tipoTasa": "efectiva",
  "frecuenciaPago": "mensual",
  "comisiones": 1000.00,
  "gastos": 500.00,
  "periodoGracia": "ninguno",
  "duracionPeriodoGracia": 0,
  "estado": "PENDIENTE",
  "createdAt": "2024-01-01T10:00:00.000+00:00",
  "updatedAt": "2024-01-01T10:00:00.000+00:00"
}
```

### Flujo de Caja
```json
{
  "bondId": 1,
  "cuotas": [
    {
      "numeroCuota": 1,
      "fecha": "2024-02-01",
      "cuota": 8500.00,
      "interes": 458.33,
      "amortizacion": 8041.67,
      "saldo": 91958.33
    }
  ],
  "tcea": 5.75,
  "trea": 5.50,
  "duracion": 11.5,
  "duracionModificada": 10.9,
  "convexidad": 132.5,
  "precioMaximo": 105000.00
}
```

### Documento
```json
{
  "id": 1,
  "nombre": "Contrato de Bono",
  "descripcion": "Documento legal del bono corporativo",
  "url": "https://ejemplo.com/documento.pdf",
  "tipo": "contrato",
  "bonoId": 1,
  "creadoPor": "12345678901",
  "fechaCreacion": "2024-01-01T10:00:00.000+00:00",
  "fechaActualizacion": "2024-01-01T10:00:00.000+00:00"
}
```

## Valores de Enums

### TipoTasa
- `nominal`
- `efectiva`

### FrecuenciaPago
- `mensual`
- `bimestral`
- `trimestral`
- `semestral`
- `anual`

### PeriodoGracia
- `ninguno`
- `parcial`
- `total`

### EstadoBono
- `PENDIENTE`
- `APROBADO`
- `RECHAZADO` 