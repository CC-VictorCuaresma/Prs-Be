# 🧩 Arquitectura de Microservicios - Lineamientos Generales

Este repositorio contiene las guías y convenciones para el desarrollo de microservicios en la organización, asegurando una estructura uniforme, escalable y mantenible.

---

## 📛 Denominación de Microservicios

Todos los microservicios deben seguir la siguiente convención de nombres:

ms-[nombre-funcional]

markdown
Copy
Edit

- Usar nombres en **inglés** y en **minúsculas**
- Separar palabras con guiones (`-`)
- Evitar tildes, espacios y guiones bajos

### 📌 Ejemplos:
| Área funcional     | Nombre del microservicio   |
|--------------------|----------------------------|
| Autenticación      | `ms-auth`                  |
| Usuarios           | `ms-user`                  |
| Distribución       | `ms-distribution`          |
| Encuestas          | `ms-survey`                |
| Configuración      | `ms-config`                |

---

## 🌿 Estrategia de Ramas Git

Se aplica un flujo de trabajo basado en **Git Flow**, adaptado para entornos con integración y despliegue continuo.

### Estructura de ramas:

| Rama        | Propósito                                                      |
|-------------|----------------------------------------------------------------|
| `main`      | Código en producción (estable)                                 |
| `develop`   | Rama principal para integración de nuevas funcionalidades      |
| `feature/*` | Nuevas funcionalidades (por microservicio)                     |
| `bugfix/*`  | Corrección de errores menores                                  |
| `hotfix/*`  | Correcciones críticas directamente sobre `main`                |
| `release/*` | Preparación para despliegues                                   |

### Ejemplos de nombres de rama:

- `feature/ms-distribution-program-crud`
- `bugfix/ms-user-update-role`
- `hotfix/ms-auth-token-expiry`

---

## 🧠 Buenas Prácticas

### 📁 Estructura del Proyecto

Cada microservicio debe mantener la siguiente estructura estándar:

ms-[nombre]/
├── src/main/java/com/empresa/ms-[nombre]/
│ ├── controller/
│ ├── service/
│ ├── repository/
│ ├── dto/
│ ├── model/
│ ├── config/
│ └── Application.java
├── resources/
│ ├── application.yml
│ ├── application-dev.yml
│ └── application-prod.yml
├── Dockerfile
├── README.md
└── pom.xml / build.gradle

yaml
Copy
Edit

### ⚙️ Configuración

- Separación clara por entorno (`dev`, `prod`, `test`) usando Spring Profiles
- Uso de **Spring Config Server** o variables de entorno para valores sensibles
- No exponer contraseñas o claves en el repositorio

### 🚀 CI/CD

- Automatización mediante **GitHub Actions**, **GitLab CI**, o similar
- Despliegue automático en ambientes de desarrollo y staging
- Pruebas ejecutadas en cada Pull Request

### 🧪 Testing

- Pruebas unitarias obligatorias (JUnit, Mockito, etc.)
- Cobertura mínima recomendada: **80%**
- Pruebas de contrato para microservicios que se comunican entre sí

### 📦 Contenedores

- Cada microservicio debe tener su propio `Dockerfile`
- Definir correctamente variables de entorno, puertos y volúmenes
- Evitar imágenes pesadas, usar `openjdk:17-slim` u otras optimizadas

### 📚 Documentación

- Swagger/OpenAPI habilitado en `/swagger-ui.html`
- Cada endpoint debe estar documentado
- `README.md` por microservicio detallando:
  - Descripción
  - Endpoints principales
  - Variables de entorno necesarias
  - Cómo correrlo localmente

---

## 🔒 Seguridad

- Evitar hardcodeo de credenciales
- Usar GitHub Secrets o `.env` para entornos locales
- Implementar autenticación/autorización basada en JWT o OAuth2

---

## 📈 Observabilidad

- Logging estructurado (SLF4J, Logback)
- Integración con herramientas de monitoreo (Prometheus, Grafana, ELK, etc.)
- Health checks vía `/actuator/health` en cada microservicio

---

## ✅ Requisitos generales

- Java 17+ / Spring Boot 3+
- Base de datos desacoplada (Mongo, PostgreSQL, etc.)
- Independencia total: cada microservicio debe compilar y correr por sí solo
- Estándar de código respetado (checkstyle, prettier, sonarqube)

---

## 👨‍💻 Colaboración

- Toda nueva funcionalidad debe ir en una rama `feature/*` desde `develop`
- Toda corrección urgente debe aplicarse desde `hotfix/*` hacia `main`
- Pull Requests obligatorios antes de fusionar a `develop` o `main`
- Código revisado al menos por 1 integrante del equipo

---

## 📬 Contacto

Para dudas o soporte, contactar al equipo de arquitectura o enviar un issue al repositorio.

---
