# PetAPI

A RESTful API for managing pets built with Spring Boot.

## Table of Contents / Indice

- [English](#english)
  - [Getting Started](#getting-started)
  - [API Endpoints](#api-endpoints)
  - [Swagger Documentation](#swagger-documentation)
- [Italiano](#italiano)
  - [Inizio](#inizio)
  - [Endpoint API](#endpoint-api)
  - [Documentazione Swagger](#documentazione-swagger)

---

## English

### Getting Started

#### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use the included Maven Wrapper)
- Docker and Docker Compose (optional, for containerized deployment)

#### Running Without Docker

1. **Clone the repository** (if not already done):
   ```bash
   git clone <repository-url>
   cd PetAPI
   ```

2. **Build the application**:
   ```bash
   ./mvnw clean package
   ```
   Or on Windows:
   ```bash
   mvnw.cmd clean package
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

   Alternatively, you can run the JAR file directly:
   ```bash
   java -jar target/PetAPI-0.0.1-SNAPSHOT.jar
   ```

4. **Access the application**:
   - API Base URL: `http://localhost:8080`
   - The application will start on port 8080 by default.

#### Running With Docker

1. **Build and start the application using Docker Compose**:
   ```bash
   docker-compose up --build
   ```

   To run in detached mode:
   ```bash
   docker-compose up -d --build
   ```

2. **Stop the application**:
   ```bash
   docker-compose down
   ```

3. **View logs**:
   ```bash
   docker-compose logs -f petapi
   ```

The application will be available at `http://localhost:8080`, and the database data will be persisted in the `./data` directory.

### API Endpoints

The PetAPI provides the following REST endpoints under the `/pet` base path:

- **POST `/pet`** - Create a new pet
  - Request Body: `PetDto` (JSON)
  - Returns: Created `PetDto`

- **GET `/pet`** - Get all pets with optional filters
  - Query Parameters: `PetFiltersDto` (optional filters for species, name, etc.)
  - Returns: List of `PetDto`

- **GET `/pet/{id}`** - Get a pet by ID
  - Path Parameter: `id` (Long)
  - Returns: `PetDto`

- **PATCH `/pet/{id}`** - Partially update a pet
  - Path Parameter: `id` (Long)
  - Request Body: `PetUpdateDto` (JSON)
  - Returns: Updated `PetDto`

- **DELETE `/pet/{id}`** - Delete a pet by ID
  - Path Parameter: `id` (Long)
  - Returns: No content (204)

### Swagger Documentation

The API documentation is available through Swagger UI, which provides an interactive interface to explore and test all endpoints.

**Access Swagger UI:**
- URL: `http://localhost:8080/swagger-ui.html`
- Alternative URL: `http://localhost:8080/swagger-ui/index.html`

**Access OpenAPI JSON:**
- URL: `http://localhost:8080/v3/api-docs`

The Swagger UI allows you to:
- View all available endpoints
- See request/response schemas
- Test endpoints directly from the browser
- View example requests and responses

---

## Italiano

### Inizio

#### Prerequisiti

- Java 17 o superiore
- Maven 3.6+ (oppure utilizzare il Maven Wrapper incluso)
- Docker e Docker Compose (opzionale, per la distribuzione containerizzata)

#### Esecuzione Senza Docker

1. **Clonare il repository** (se non già fatto):
   ```bash
   git clone <repository-url>
   cd PetAPI
   ```

2. **Compilare l'applicazione**:
   ```bash
   ./mvnw clean package
   ```
   Oppure su Windows:
   ```bash
   mvnw.cmd clean package
   ```

3. **Eseguire l'applicazione**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Oppure su Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

   In alternativa, è possibile eseguire direttamente il file JAR:
   ```bash
   java -jar target/PetAPI-0.0.1-SNAPSHOT.jar
   ```

4. **Accedere all'applicazione**:
   - URL Base API: `http://localhost:8080`
   - L'applicazione si avvierà sulla porta 8080 per impostazione predefinita.

#### Esecuzione Con Docker

1. **Compilare e avviare l'applicazione utilizzando Docker Compose**:
   ```bash
   docker-compose up --build
   ```

   Per eseguire in modalità detached:
   ```bash
   docker-compose up -d --build
   ```

2. **Fermare l'applicazione**:
   ```bash
   docker-compose down
   ```

3. **Visualizzare i log**:
   ```bash
   docker-compose logs -f petapi
   ```

L'applicazione sarà disponibile su `http://localhost:8080` e i dati del database verranno persistiti nella directory `./data`.

### Endpoint API

La PetAPI fornisce i seguenti endpoint REST sotto il percorso base `/pet`:

- **POST `/pet`** - Crea un nuovo animale domestico
  - Request Body: `PetDto` (JSON)
  - Restituisce: `PetDto` creato

- **GET `/pet`** - Ottiene tutti gli animali domestici con filtri opzionali
  - Query Parameters: `PetFiltersDto` (filtri opzionali per specie, nome, ecc.)
  - Restituisce: Lista di `PetDto`

- **GET `/pet/{id}`** - Ottiene un animale domestico per ID
  - Path Parameter: `id` (Long)
  - Restituisce: `PetDto`

- **PATCH `/pet/{id}`** - Aggiorna parzialmente un animale domestico
  - Path Parameter: `id` (Long)
  - Request Body: `PetUpdateDto` (JSON)
  - Restituisce: `PetDto` aggiornato

- **DELETE `/pet/{id}`** - Elimina un animale domestico per ID
  - Path Parameter: `id` (Long)
  - Restituisce: Nessun contenuto (204)

### Documentazione Swagger

La documentazione dell'API è disponibile tramite Swagger UI, che fornisce un'interfaccia interattiva per esplorare e testare tutti gli endpoint.

**Accedere a Swagger UI:**
- URL: `http://localhost:8080/swagger-ui.html`
- URL alternativo: `http://localhost:8080/swagger-ui/index.html`

**Accedere al JSON OpenAPI:**
- URL: `http://localhost:8080/v3/api-docs`

Swagger UI consente di:
- Visualizzare tutti gli endpoint disponibili
- Vedere gli schemi di richiesta/risposta
- Testare gli endpoint direttamente dal browser
- Visualizzare esempi di richieste e risposte

