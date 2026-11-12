# Sistema de Gestión de Tareas - Laura Catalina Hernández Rodríguez

Proyecto integrador de la asignatura **Diseño y Arquitectura de Software**. Consiste en una aplicación backend desarrollada con Spring Boot, conectada a una base de datos MySQL 8.0 y desplegada mediante Docker y Docker Compose.

## 1. Descripción del proyecto

El sistema permite gestionar tareas mediante una API REST que implementa las operaciones CRUD:

- Crear tareas.
- Consultar todas las tareas.
- Consultar una tarea por su ID.
- Actualizar tareas existentes.
- Eliminar tareas.

Cada tarea contiene los siguientes atributos:

| Campo | Descripción |
|---|---|
| id | Identificador único generado automáticamente |
| titulo | Título de la tarea |
| descripcion | Descripción de la tarea |
| estado | Estado actual de la tarea |
| fechaCreacion | Fecha y hora de creación |

Los estados permitidos son:

- `PENDIENTE`
- `EN_PROGRESO`
- `COMPLETADA`

Cuando se crea una tarea sin especificar su estado, se asigna automáticamente `PENDIENTE`.

## 2. Tecnologías utilizadas

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL 8.0
- Maven
- Docker
- Docker Compose

## 3. Arquitectura del proyecto

La aplicación está compuesta por dos servicios principales:

**Backend**
- API REST desarrollada con Spring Boot.
- Expone los endpoints para gestionar las tareas.
- Se comunica con MySQL mediante Spring Data JPA.

**Base de datos**
- MySQL 8.0.
- Almacena las tareas.
- Utiliza un volumen Docker para conservar los datos.

Docker Compose administra ambos servicios dentro de una red personalizada.

## 4. Estructura de archivos

```text
demo/
├── README.md
├── docker-compose.yml
├── init.sql
└── demo/
    ├── Dockerfile
    ├── .dockerignore
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/example/demo/
            │       ├── controller/
            │       ├── model/
            │       ├── repository/
            │       ├── service/
            │       └── DemoApplication.java
            └── resources/
                └── application.properties
```

## 5. Requisitos previos

Para ejecutar el proyecto se necesita:

- Docker Desktop instalado y en ejecución.
- Docker Compose disponible desde la terminal.
- Git para clonar el repositorio.

No es necesario instalar Java ni Maven localmente, ya que la construcción y ejecución del backend se realizan mediante Docker.

## 6. Ejecución del proyecto

### Clonar el repositorio

```bash
git clone https://github.com/Lauraher2906/DAS---Proyecto-Integrador-Sistema-de-Gesti-n-de-Tareas.git
```

Ingresar a la carpeta del proyecto:

```bash
cd demo
```

### Construir e iniciar los contenedores

Ejecutar:

```bash
docker compose up -d --build
```

Este comando construye la imagen del backend e inicia los servicios de Spring Boot y MySQL.

### Verificar los contenedores

```bash
docker compose ps
```

El backend debe aparecer en estado `Up` y MySQL en estado `healthy`.

### Consultar los registros

```bash
docker compose logs backend
```

La API estará disponible en:

http://localhost:8080/api/tareas

## 7. Endpoints de la API REST

La URL base es:

`http://localhost:8080/api/tareas`

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/tareas` | Consultar todas las tareas |
| GET | `/api/tareas/{id}` | Consultar una tarea por ID |
| POST | `/api/tareas` | Crear una tarea |
| PUT | `/api/tareas/{id}` | Actualizar una tarea |
| DELETE | `/api/tareas/{id}` | Eliminar una tarea |

### Crear una tarea (POST)

```bash
curl -X POST http://localhost:8080/api/tareas \
-H "Content-Type: application/json" \
-d '{"titulo":"Mi primera tarea","descripcion":"Aprender Docker"}'
```

### Consultar todas las tareas (GET)

```bash
curl http://localhost:8080/api/tareas
```

### Consultar una tarea por ID (GET)

```bash
curl http://localhost:8080/api/tareas/1
```

### Actualizar una tarea (PUT)

```bash
curl -X PUT http://localhost:8080/api/tareas/1 \
-H "Content-Type: application/json" \
-d '{"titulo":"Aprender Docker y Spring Boot","descripcion":"Practicar CRUD","estado":"EN_PROGRESO"}'
```

### Eliminar una tarea (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/tareas/1
```

## 8. Base de datos y persistencia

El proyecto utiliza MySQL 8.0 con las siguientes configuraciones para el entorno Docker:

- Base de datos: `demodb`
- Usuario de aplicación: `appuser`
- Puerto interno: `3306`

El archivo `init.sql` contiene las instrucciones SQL para crear la tabla `tareas`.

La persistencia se implementa mediante un volumen Docker llamado `db-data`, que conserva la información de MySQL aunque se detengan o eliminen los contenedores.

Para detener los contenedores sin eliminar los datos:

```bash
docker compose down
```

Para iniciar nuevamente el proyecto:

```bash
docker compose up -d
```

**Importante:** No utilizar `docker compose down -v` si se desea conservar la información, ya que este comando también elimina los volúmenes asociados al proyecto.

## 9. Dockerfile multi-stage

El Dockerfile utiliza dos etapas:

1. **Build:** utiliza Maven con Java 17 para descargar dependencias y construir el archivo JAR de Spring Boot.
2. **Runtime:** utiliza una imagen ligera de Java 17 JRE para ejecutar la aplicación.

Esta estrategia permite separar las herramientas de construcción del entorno final de ejecución y reducir el tamaño de la imagen.

## 10. Docker Compose

El archivo `docker-compose.yml` configura:

- Servicio `backend`: construye y ejecuta la API Spring Boot.
- Servicio `db`: ejecuta MySQL 8.0.
- Red personalizada `app-network`: permite la comunicación entre los servicios.
- Volumen `db-data`: garantiza la persistencia de los datos.
- Variables de entorno: permiten configurar la conexión entre el backend y MySQL.
- Healthcheck: comprueba que MySQL esté disponible antes de iniciar el backend.

## 11. Detener el proyecto

Para detener y eliminar los contenedores y la red:

```bash
docker compose down
```

Los datos almacenados en el volumen persistente se conservan.
