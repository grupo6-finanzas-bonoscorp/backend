## 🎯 **Cómo Probar los Endpoints en Swagger**

### 🔐 **1. PRIMERO: Autenticación (Requerida)**
Antes de probar cualquier endpoint de bonos, necesitas autenticarte:

**Endpoint:** `POST /api/v1/authentication/sign-in`
```json
{
  "ruc": "12345678901",
  "password": "tu-password"
}
```

**Respuesta:**
```json
{
  "id": 1,
  "ruc": "12345678901",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**⚠️ IMPORTANTE:** Copia el `token` y úsalo en el botón "Authorize" de Swagger:
- Formato: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

---

### 🚀 **2. Crear Bono (POST /api/v1/bonos)**

**JSON Correcto:**
```json
{
  "valorNominal": 1000.00,
  "fechaEmision": "2025-06-23",
  "fechaVencimiento": "2026-06-23",
  "tasaInteres": 5.0,
  "tipoTasa": "EFECTIVA",
  "frecuenciaPago": "TRIMESTRAL",
  "cuotasTotales": 4,
  "comisiones": 0.0,
  "gastosAdicionales": 0.0,
  "periodoGracia": "NINGUNO",
  "duracionGracia": 0
}
```

**Valores Válidos:**
- `tipoTasa`: `"EFECTIVA"` o `"NOMINAL"`
- `frecuenciaPago`: `"MENSUAL"`, `"BIMESTRAL"`, `"TRIMESTRAL"`, `"SEMESTRAL"`, `"ANUAL"`
- `periodoGracia`: `"NINGUNO"`, `"PARCIAL"`, `"TOTAL"`

---

### 📝 **3. Más Ejemplos de Bonos para Crear:**

**Bono Mensual:**
```json
{
  "valorNominal": 5000.00,
  "fechaEmision": "2025-01-15",
  "fechaVencimiento": "2027-01-15",
  "tasaInteres": 7.5,
  "tipoTasa": "NOMINAL",
  "frecuenciaPago": "MENSUAL",
  "cuotasTotales": 24,
  "comisiones": 100.0,
  "gastosAdicionales": 50.0,
  "periodoGracia": "PARCIAL",
  "duracionGracia": 3
}
```

**Bono Semestral:**
```json
{
  "valorNominal": 10000.00,
  "fechaEmision": "2025-03-01",
  "fechaVencimiento": "2028-03-01",
  "tasaInteres": 6.25,
  "tipoTasa": "EFECTIVA",
  "frecuenciaPago": "SEMESTRAL",
  "cuotasTotales": 6,
  "comisiones": 200.0,
  "gastosAdicionales": 150.0,
  "periodoGracia": "TOTAL",
  "duracionGracia": 6
}
```

---

### 📋 **4. Listar Bonos (GET /api/v1/bonos)**
- **Sin body**
- Solo necesita el token de autorización
- Retorna todos los bonos del usuario autenticado

---

### 🔍 **5. Obtener Bono por ID (GET /api/v1/bonos/{id})**
- **Ejemplo:** `GET /api/v1/bonos/1`
- Solo necesita el token de autorización
- Reemplaza `{id}` con el ID real del bono

---

### ✏️ **6. Editar Bono (PUT /api/v1/bonos/{id})**
**Ejemplo:** `PUT /api/v1/bonos/1`
```json
{
  "valorNominal": 1200.00,
  "fechaEmision": "2025-06-23",
  "fechaVencimiento": "2026-12-23",
  "tasaInteres": 4.5,
  "tipoTasa": "EFECTIVA",
  "frecuenciaPago": "BIMESTRAL",
  "cuotasTotales": 6,
  "comisiones": 25.0,
  "gastosAdicionales": 15.0,
  "periodoGracia": "NINGUNO",
  "duracionGracia": 0
}
```

---

### 🗑️ **7. Eliminar Bono (DELETE /api/v1/bonos/{id})**
- **Ejemplo:** `DELETE /api/v1/bonos/1`
- Sin body
- Solo necesita el token de autorización

---

## 🔧 **Pasos para Probar en Swagger:**

1. **Inicia el backend:** `./mvnw.cmd spring-boot:run`
2. **Abre Swagger:** `http://localhost:8080/swagger-ui.html`
3. **Autentícate:** Usa el endpoint de sign-in
4. **Autoriza:** Clic en "Authorize" y pega: `Bearer tu-token`
5. **Prueba endpoints:** Usa los JSONs de ejemplo de arriba

---

## ⚠️ **Errores Comunes a Evitar:**

- ❌ `"tipoTasa": "string"` → ✅ `"tipoTasa": "EFECTIVA"`
- ❌ `"frecuenciaPago": "string"` → ✅ `"frecuenciaPago": "TRIMESTRAL"`
- ❌ `"periodoGracia": "string"` → ✅ `"periodoGracia": "NINGUNO"`
- ❌ Fechas inválidas → ✅ `fechaVencimiento` debe ser posterior a `fechaEmision`
- ❌ Sin token → ✅ Siempre autorízate primero

¡Con estos ejemplos podrás probar completamente tu API de bonos en Swagger! 🚀