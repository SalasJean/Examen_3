# 🔍 Sunat-Consulta Microservice

Microservicio Spring Boot para consultar información de RUC en la API externa de Decolecta (SUNAT),
con persistencia en PostgreSQL, manejo de errores robusto y cache inteligente.

---

## 🛠️ Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.2 |
| Spring Cloud OpenFeign | 2025.0.0 |
| Spring Data JPA | (incluido en Boot) |
| PostgreSQL | 15+ |
| Maven | 3.8+ |

---

## 📁 Estructura del Proyecto

```
com.tecsup.examen
 ├── client/
 │   └── SunatClient.java          # FeignClient hacia Decolecta
 ├── config/
 │   ├── DecolectaProperties.java  # Mapea properties del token
 │   └── FeignConfig.java          # Interceptor Authorization: Bearer
 ├── controller/
 │   └── SunatController.java      # 2 endpoints REST
 ├── dto/
 │   ├── request/
 │   │   └── RucRequest.java       # record
 │   └── response/
 │       ├── SunatRucResponse.java     # record - respuesta proveedor
 │       ├── ProviderErrorResponse.java # record - error proveedor
 │       ├── CompanyResponse.java      # record - respuesta tu API
 │       ├── ConsultaResponse.java     # record - historial
 │       └── ErrorResponse.java        # record - errores uniformes
 ├── entity/
 │   ├── Company.java              # Entidad empresa
 │   └── Consulta.java             # Entidad historial
 ├── enums/
 │   ├── EstadoContribuyente.java  # ACTIVO, BAJA, SUSPENDIDO, DESCONOCIDO
 │   ├── CondicionDomicilio.java   # HABIDO, NO_HABIDO, PENDIENTE, DESCONOCIDO
 │   └── ResultadoConsulta.java    # SUCCESS, ERROR
 ├── exception/
 │   ├── ProviderException.java        # Error del proveedor
 │   ├── RucValidationException.java   # RUC inválido
 │   ├── DecolectaErrorDecoder.java    # Feign ErrorDecoder
 │   └── GlobalExceptionHandler.java   # @RestControllerAdvice
 ├── mapper/
 │   └── SunatMapper.java          # Entity ↔ Record
 ├── repository/
 │   ├── CompanyRepository.java
 │   └── ConsultaRepository.java
 ├── service/
 │   └── SunatService.java  
          ConsultaService
# Lógica principal
 └── SunatConsultaApplication.java # @EnableFeignClients
```

---

## ⚙️ Configuración

### 1. Base de datos PostgreSQL

Crea la base de datos antes de correr el proyecto:

```sql
CREATE DATABASE sunatdb;
```

> Las tablas se crean automáticamente con `ddl-auto=update`

### 2. Configurar el token de Decolecta

El token **nunca** está hardcodeado. Se configura mediante variable de entorno:

**Linux / Mac:**
```bash
export DECOLECTA_TOKEN=sk_7701.tu_token_aqui
```

**Windows CMD:**
```cmd
set DECOLECTA_TOKEN=sk_7701.tu_token_aqui
```

**Windows PowerShell:**
```powershell
$env:DECOLECTA_TOKEN="sk_7701.tu_token_aqui"
```

**IntelliJ IDEA:**
> Run → Edit Configurations → Environment Variables → agregar `DECOLECTA_TOKEN=sk_xxxx`

---

## 🚀 Cómo correr el proyecto

### Opción 1: Maven

```bash
# Clonar el repositorio
git clone https://github.com/SalasJean/Examen_3.git
cd Examen_3

# Configurar token
export DECOLECTA_TOKEN=sk_7701.tu_token_aqui

# Correr
mvn spring-boot:run
```

### Opción 2: JAR

```bash
mvn clean package -DskipTests
java -jar target/sunat-consulta-0.0.1-SNAPSHOT.jar
```

El servicio levanta en: `http://localhost:8080`

---

## 📡 Endpoints

### 1. Consultar RUC

```
GET /api/sunat/ruc/{ruc}
```

- Valida que el RUC tenga exactamente 11 dígitos
- Llama a la API de Decolecta via OpenFeign
- Guarda o actualiza la empresa en PostgreSQL
- Registra la consulta en el historial
- Si la empresa fue consultada en los últimos 10 minutos → usa cache (no llama al proveedor)

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "ruc": "20601030013",
  "razonSocial": "REXTIE S.A.C.",
  "estado": "ACTIVO",
  "condicion": "HABIDO",
  "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202",
  "ubigeo": "150131",
  "departamento": "LIMA",
  "provincia": "LIMA",
  "distrito": "SAN ISIDRO",
  "esAgenteRetencion": false,
  "esBuenContribuyente": false,
  "createdAt": "2025-01-01T10:00:00"
}
```

### 2. Historial de consultas por RUC

```
GET /api/sunat/ruc/{ruc}/consultas
```

Devuelve todas las consultas realizadas para ese RUC, ordenadas de más reciente a más antigua.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 3,
    "rucConsultado": "20601030013",
    "resultado": "SUCCESS",
    "mensajeError": null,
    "providerStatusCode": null,
    "createdAt": "2025-01-01T10:05:00"
  },
  {
    "id": 1,
    "rucConsultado": "20601030013",
    "resultado": "SUCCESS",
    "mensajeError": null,
    "providerStatusCode": null,
    "createdAt": "2025-01-01T10:00:00"
  }
]
```

---

## 🧪 Ejemplos de prueba

### ✅ Prueba 1: RUC válido (SUCCESS)

**curl:**
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013
```

**Postman:**
- Method: `GET`
- URL: `http://localhost:8080/api/sunat/ruc/20601030013`
- No headers adicionales necesarios

**Resultado esperado:** `200 OK` con datos de la empresa REXTIE S.A.C.

<img width="1184" height="769" alt="image" src="https://github.com/user-attachments/assets/b74db9b7-5a08-447d-83f2-48a1b601b477" />
---

### ❌ Prueba 2: RUC inválido (ERROR - formato)

**curl:**
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/123
```

**Resultado esperado:** `400 Bad Request`
```json
{
  "message": "RUC debe tener exactamente 11 dígitos"
}
```
<img width="1195" height="573" alt="image" src="https://github.com/user-attachments/assets/23cc2ac1-f849-400f-bbf1-051699f71795" />


---

### ❌ Prueba 3: RUC inexistente (ERROR - proveedor)

**curl:**
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/99999999999
```

**Resultado esperado:** `400 Bad Request` o `404 Not Found`
```json
{
  "message": "ruc no valido"
}
```
<img width="1174" height="572" alt="image" src="https://github.com/user-attachments/assets/ee126bb9-1124-4ccc-b5ca-98650d5de97a" />

---

### 📋 Prueba 4: Historial de consultas

**curl:**
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013/consultas
```
<img width="1167" height="923" alt="image" src="https://github.com/user-attachments/assets/16390888-5f10-4bd1-8980-7b083bc2d406" />

<img width="1174" height="760" alt="image" src="https://github.com/user-attachments/assets/2857ec7e-e98a-42bf-a8e9-47b5f87c3e56" />

**Resultado esperado:** `200 OK` con lista de consultas ordenadas desc.

---
<img width="1602" height="306" alt="image" src="https://github.com/user-attachments/assets/2ed16bb3-4e39-4220-ab7f-a6241329dfa4" />


## 🗄️ Modelo de Base de Datos

```sql
-- Tabla companies
CREATE TABLE companies (
    id                   BIGSERIAL PRIMARY KEY,
    ruc                  VARCHAR(11) UNIQUE NOT NULL,
    razon_social         VARCHAR(255) NOT NULL,
    estado               VARCHAR(50)  NOT NULL,
    condicion            VARCHAR(50)  NOT NULL,
    direccion            VARCHAR(500),
    ubigeo               VARCHAR(10),
    departamento         VARCHAR(100),
    provincia            VARCHAR(100),
    distrito             VARCHAR(100),
    es_agente_retencion  BOOLEAN DEFAULT FALSE,
    es_buen_contribuyente BOOLEAN DEFAULT FALSE,
    created_at           TIMESTAMP NOT NULL
);

-- Tabla consultas
CREATE TABLE consultas (
    id                   BIGSERIAL PRIMARY KEY,
    ruc_consultado       VARCHAR(11) NOT NULL,
    resultado            VARCHAR(20) NOT NULL,
    mensaje_error        TEXT,
    provider_status_code INTEGER,
    created_at           TIMESTAMP NOT NULL,
    company_id           BIGINT REFERENCES companies(id)
);
```

> Estas tablas se crean automáticamente con `spring.jpa.hibernate.ddl-auto=update`

---

## 🏗️ Decisiones de diseño

**Cache de 10 minutos:** Si una empresa ya fue consultada y su registro tiene menos de 10 minutos de antigüedad, el servicio no llama al proveedor externo. Esto reduce latencia y evita consumo innecesario del API de Decolecta. La consulta igualmente se registra como `SUCCESS` en el historial.

**Consultas fallidas:** Cuando el proveedor devuelve error (RUC inválido, token incorrecto, etc.), la consulta se registra con `resultado = ERROR` y `company_id = null`. Esto permite tener un historial completo sin romper la integridad referencial.

**Fallback en enums:** Si el proveedor manda un valor no reconocido para `estado` o `condicion`, se mapea automáticamente a `DESCONOCIDO` usando el método `fromString()` de cada enum.

**ErrorDecoder:** Toda la lógica de parseo de errores del proveedor está centralizada en `DecolectaErrorDecoder`, separada completamente del flujo de negocio.

---

## 👥 Autor

Desarrollado como examen del curso - Tecsup   por Jean Salas
Microservicio: **sunat-consulta**  
Grupo: **G14**
