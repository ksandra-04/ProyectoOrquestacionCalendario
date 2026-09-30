# Orquestación de contenedores - API Festivos y API Calendario

Orquestación con Docker Compose de los microservicios de Festivos (Node.js + MongoDB) y Calendario (Spring Boot + PostgreSQL), conectados en la red `redcalendario`.

## Contenedores

| Servicio | Contenedor | Tecnología | Puerto (host:contenedor) |
|---|---|---|---|
| bdfestivos | dockerbdfestivos | MongoDB 7 | 27018:27017 |
| apifestivos | dockerapifestivos | Node.js 18 | 3030:3030 |
| bdcalendario | dockerbdcalendario | PostgreSQL 15 | 5433:5432 |
| apicalendario | dockerapicalendario | Spring Boot (Java 17) | 8081:8081 |

La API de Calendario consume la API de Festivos a través de la red interna usando `http://dockerapifestivos:3030`.

## Estructura

```
├── apiFestivos/        API de festivos (Node.js)
├── bdFestivos/         Imagen de MongoDB con el script de carga inicial
├── apiCalendario/      API de calendario (Spring Boot)
├── bdCalendario/       Imagen de PostgreSQL con DDL y DML
└── docker-compose.yml
```

## Ejecución

```bash
docker compose up -d --build
docker compose ps
```

## Pruebas

- Swagger festivos: http://localhost:3030/api-docs
- Swagger calendario: http://localhost:8081/swagger-ui/index.html
- Festivos de un año: http://localhost:3030/api/festivos/obtener/2026
- Verificar fecha: http://localhost:3030/api/festivos/verificar/2026/12/25
- Generar calendario: http://localhost:8081/api/calendario/generar/2026
- Listar calendario: http://localhost:8081/api/calendario/listar/2026
- Festivos consultados desde Calendario: http://localhost:8081/api/festivos/obtener/2026

## Detener

```bash
docker compose down        # detiene y elimina los contenedores
docker compose down -v     # además borra los volúmenes (datos)
```
