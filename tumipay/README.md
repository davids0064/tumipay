# Tumipay - Microservicio de Orquestación de Transacciones

Resumen
-------
Tumipay es un microservicio diseñado con el patrón de Arquitectura Hexagonal para orquestar y persistir transacciones recibidas vía HTTP.

Arquitectura
-----------
- Enfoque: Arquitectura Hexagonal (Ports & Adapters)
- Capas:
  - `api`: controladores REST y manejo de excepciones (entrada HTTP).
  - `application`: casos de uso, DTOs, mappers y servicios (lógica de orquestación).
  - `domain`: modelos y contratos (interfaces de repositorio).
  - `infrastructure`: adaptadores y persistencia JPA (implementaciones concretas).

Tecnologías
-----------
- Java 21
- Spring Boot (Web, Data JPA, Validation)
- Hibernate / JPA
- PostgreSQL (configurado en `docker-compose.yml`)
- Springdoc OpenAPI (Swagger UI)
- Maven
- Docker + Docker Compose
- JUnit 5 + Mockito para tests
- GitHub Actions (workflow `/.github/workflows/ci.yml`)

Estructura y decisiones de implementación
----------------------------------------
- Contrato API:
  - Endpoints versionados en `/api/v1/transactions`.
  - Campos del request en `snake_case` usando DTOs `TransactionRequest` y `ClientInfo`.
  - Respuesta estandarizada: `TransactionResponse { code, message, transaction }`.
  - Documentación OpenAPI generada por Springdoc (Swagger UI disponible en `/swagger-ui.html`).

- Validación:
  - Validaciones de campos se aplican con `jakarta.validation` en los DTOs (`@NotBlank`, `@Email`, `@Size`, `@Min`, etc.).
  - Validaciones de path/request usan `@Valid` y `@Validated` en el controlador.

- Manejo de errores:
  - `ApiExceptionHandler` (RestControllerAdvice) devuelve respuestas JSON estandarizadas con códigos de error (`001` validación, `002` interno, `003` not found, etc.).
  - Diccionario de códigos en `ERROR_CODES.md`.

- Persistencia y adaptadores:
  - Entidades JPA en `infrastructure.persistence.entity` (`ClientEntity`, `TransactionEntity`).
  - Repositorios JPA (`JpaClientRepository`, `JpaTransactionRepository`).
  - Adaptadores que implementan los contratos de dominio en `infrastructure.adapter` (`ClientRepositoryAdapter`, `TransactionRepositoryAdapter`).
  - DTO ↔ Domain ↔ Entity mappers (`ClientMapper`, `TransactionMapper`) en `application.mapper`.

- Lógica de orquestación:
  - `TransactionService` realiza:
    1. Mapear request → dominio.
    2. Persistir `Client` y `Transaction` (guarda cliente antes de transacción).
    3. Retornar `TransactionResponse` con código `000` y mensaje estándar `Successful operation`.

Pruebas y CI
------------
- Tests unitarios y de controlador con MockMvc y Mockito en `src/test/java`.
- Workflow de CI: `.github/workflows/ci.yml` compila y ejecuta tests en push/PR.

Docker y despliegue
-------------------
- `Dockerfile` multi-stage incluido para construir y ejecutar la jar.
- `docker-compose.yml` orquesta `postgres` y el servicio `app` (variables de conexión configurables mediante env vars).
- Perfil de producción: `src/main/resources/application-prod.yml` lee variables de entorno para datasource.

Scripts y SQL
-------------
- Script de creación de esquema: `scripts/schema.sql` (crea tablas `clients` y `transactions`).

Cómo ejecutar localmente
------------------------
- Compilar:

```bash
./mvnw -DskipTests package
```

- Ejecutar directamente:

```bash
./mvnw spring-boot:run
```

- Construir imagen Docker:

```bash
docker build -t tumipay:latest .
```

- Levantar con Docker Compose (crea DB y la app):

```bash
docker-compose up --build
```

Endpoints principales
---------------------
- POST `/api/v1/transactions` — Crear transacción. Body en `snake_case` según `TransactionRequest`.
- GET `/api/v1/transactions/{transaction_id}` — Obtener transacción por id (UUID).
- GET `/api/v1/transactions/client/{client_transaction_id}` — Obtener transacción por id del cliente.

Rutas de documentación
----------------------
- Swagger UI (navegador): http://localhost:8080/swagger-ui.html
- OpenAPI JSON (raw): http://localhost:8080/v3/api-docs
- Nota: si ejecutas la aplicación en Docker con otro mapeo de puertos, reemplaza `localhost:8080` por la URL/host y puerto correspondientes.

Actuator y métricas
-------------------
- Salud (HTTP): http://localhost:8080/actuator/health (estado y detalles cuando esté autorizado)
- Información: http://localhost:8080/actuator/info
- Métricas: http://localhost:8080/actuator/metrics (lista de medidores disponibles)
- Formato Prometheus: http://localhost:8080/actuator/prometheus (formato de exposición para Prometheus)

Notas:
- Actuator está habilitado y `health,info,metrics,prometheus` están expuestos por defecto en `application.yaml` para conveniencia en entornos de desarrollo. En producción se recomienda restringir la exposición y proteger estos endpoints.

CI e imagen Docker
-------------------
- El workflow de GitHub Actions `/.github/workflows/ci.yml` ejecuta `mvn verify` (incluye análisis estático mediante Checkstyle), construye la imagen Docker y la publica en GitHub Container Registry (GHCR) cuando se ejecuta en las ramas `main`/`master`.
- Etiquetas de imagen publicadas:
  - `ghcr.io/<OWNER>/tumipay:latest`
  - `ghcr.io/<OWNER>/tumipay:<sha>`

Cómo acceder a las métricas localmente
-------------------------------------
1. Iniciar la aplicación:

```bash
./mvnw spring-boot:run
```

2. Obtener métricas en formato Prometheus:

```bash
curl http://localhost:8080/actuator/prometheus
```

3. Verificar estado de salud:

```bash
curl http://localhost:8080/actuator/health
```

Análisis estático
-----------------
- Checkstyle está configurado en la compilación Maven y el CI ejecuta `mvn verify`. Actualmente Checkstyle genera un informe pero no falla la compilación por defecto (de este modo CI y la publicación de la imagen continúan). Se recomienda ajustar las reglas progresivamente y corregir las violaciones más relevantes.

Seguridad y siguientes pasos
---------------------------
- Proteger los endpoints de Actuator en producción (usar `management.server.port`, Spring Security y restringir la exposición de endpoints).
- Añadir una tarea de scrape en Prometheus para recopilar métricas desde `/actuator/prometheus` en el entorno de monitorización.
- Opcional: integrar SpotBugs, SonarCloud u otros analizadores en CI para reglas de calidad más estrictas.

Archivos relevantes
------------------
- Implementación principal: `src/main/java/com/tumipay`.
- DTOs: `src/main/java/com/tumipay/application/dto`.
- Mappers: `src/main/java/com/tumipay/application/mapper`.
- Servicios: `src/main/java/com/tumipay/application/service/TransactionService.java`.
- Controllers: `src/main/java/com/tumipay/api/controller/TransactionController.java`.
- Exception handler: `src/main/java/com/tumipay/api/handler/ApiExceptionHandler.java`.
- Docker: `Dockerfile`, `docker-compose.yml`.
- CI: `.github/workflows/ci.yml`.

Siguientes mejoras recomendadas
------------------------------
- Añadir tests de integración que levantan contexto completo (`@SpringBootTest` + `@AutoConfigureMockMvc`) y usen la DB en memoria o Postgres de CI.
- Centralizar códigos de error en una clase/enum y usar i18n para mensajes.
- Añadir métricas/healthchecks y un `Makefile` para tareas comunes.

Contacto
--------
- Este repo contiene los archivos de la entrega técnica y la documentación añadida.

