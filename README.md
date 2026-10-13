# Prueba Grupal Unidad #3 — Ingeniería de Software

> Universidad de La Frontera · Departamento de Computación e Informática (DCI)

## 1. Descripción

La Academia Pokémon de Kanto busca desarrollar una nueva plataforma digital para gestionar y analizar información de Pokémon, Entrenadores y sus batallas, integrando los datos directamente desde la PokéAPI.

Anteriormente, la Academia usaba archivos CSV locales con información de Pokémon y entrenadores, pero ahora desea migrar su sistema a Spring Boot, conectando la aplicación directamente con la PokéAPI y permitiendo la interacción en red.

### Estado actual

Actualmente, se dispone de un sistema base que lee archivos CSV con información de Pokémon y entrenadores. Este sistema deberá ser reemplazado por peticiones HTTP a la PokéAPI, procesando los datos en tiempo real.

El objetivo de esta integración es:

- Utilizar Spring Boot como tecnología base.
- Mantener las funcionalidades actuales, pero obteniendo datos desde la PokéAPI (<https://pokeapi.co/docs/v2>).
- Escalar el proyecto a una tecnología web o panel administrativo.

## 2. Insumos

Ya no se utilizan archivos CSV. Toda la información se obtendrá desde los endpoints oficiales de la PokéAPI.

## 3. Desafíos actuales

- Los desafíos a realizar son 6 y cada uno tendrá su propia rama. El grupo es de 4 integrantes: desafíos 1–4 se realizan 1 por integrante; desafíos 5 y 6 son grupales.
- Para ello deben crear las ramas siguiendo la siguiente nomenclatura:
  - `Desafio1`
  - `Desafio2`
  - `Desafio3`
  - `Desafio4`
  - `pipeline`
  - `docker`
- Se debe hacer un "merge" de todas las ramas en una rama denominada `develop`.
- Se deberá agregar a la profesora y a los ayudantes al repositorio:
  - e.candia04@ufromail.cl
  - mnegrier
  - m.sanhueza22@ufromail.cl
  - s.sandoval18@ufromail.cl
  - c.espinoza28@ufromail.cl

### Gestión de Pull Requests

La integración de las ramas de cada desafío hacia la rama `develop` deberá realizarse **exclusivamente mediante Pull Requests (PR)**. Es requisito obligatorio que la configuración del repositorio bloquee la auto-aprobación; todo PR debe ser revisado y aprobado por al menos un integrante del equipo distinto al autor del código antes de poder realizar el merge. Se revisará la configuración del repositorio para validar el cumplimiento de esta regla.

### Configuración del repositorio

Proteja la rama `develop` para que no se permitan commits directos. Todo cambio debe integrarse mediante un Pull Request aprobado por al menos un integrante del grupo.

#### Cómo configurarlo en GitHub

1. **Agregar a la profesora y ayudantes:** `Settings` → `Collaborators` → `Add people`. Se puede invitar por usuario de GitHub (`mnegrier`) o por correo electrónico.
2. **Proteger `develop`:** `Settings` → `Rules` → `Rulesets` → `New ruleset` → `New branch ruleset` (o, alternativamente, `Settings` → `Branches` → `Add branch protection rule`). Configurar:
   - **Target branches:** `develop`.
   - **Restrict updates / Block force pushes:** activado (no se permiten commits directos ni force push).
   - **Require a pull request before merging:** activado.
   - **Required approvals:** `1` (como mínimo).
   - **Require approval of the most recent reviewable push:** activado (quien hizo el último push no puede ser quien apruebe).
   - **Require status checks to pass** (recomendado): seleccionar los jobs `test` y `build` del workflow de CI.
   - **Bypass list:** vacía, o en la regla clásica activar **Do not allow bypassing the above settings** / incluir administradores, para que nadie del equipo (ni el dueño del repositorio) pueda saltarse la regla.
3. **Auto-aprobación:** GitHub no permite que el autor de un PR apruebe su propio PR. Combinado con el mínimo de 1 aprobación, se cumple el requisito de que otro integrante revise y apruebe.

> **Importante:** en GitHub Free, la protección de ramas y los rulesets en repositorios **privados** pueden requerir un plan de pago. Lo más simple es usar un **repositorio público** (GitHub Actions es gratuito e ilimitado en repositorios públicos). Esto además es coherente con el requisito de que la imagen de Docker Hub sea pública. Verifique las condiciones vigentes del plan en la documentación de GitHub.

---

## Etapa 1

### Desafío 1: Pokémon con Defensa Débil

**Contexto:** Un Pokémon tiene "defensa débil" si su stat de `defense` es menor a 40 **Y** su stat de `sp-defense` es menor a 40. Universo de búsqueda: primeros 150 Pokémon de la Pokédex nacional.

Estos datos se obtienen desde la PokéAPI.

**Tipo:** `GET`
**URL:** `/pokemon/weak-defense`

**Respuesta (JSON):**

```json
[
  { "id": 10, "nombre": "caterpie",   "defense": 35, "sp_defense": 20, "tipos": ["bug"] },
  { "id": 13, "nombre": "weedle",     "defense": 30, "sp_defense": 20, "tipos": ["bug","poison"] },
  { "id": 19, "nombre": "rattata",    "defense": 35, "sp_defense": 35, "tipos": ["normal"] },
  { "id": 21, "nombre": "spearow",    "defense": 30, "sp_defense": 31, "tipos": ["normal","flying"] },
  { "id": 39, "nombre": "jigglypuff", "defense": 20, "sp_defense": 25, "tipos": ["normal","fairy"] },
  { "id": 92, "nombre": "gastly",     "defense": 30, "sp_defense": 35, "tipos": ["ghost","poison"] }
]
```

### Desafío 2: Dominancia de Tipo

**Contexto:** Dado un tipo (ej. `fire`, `water`, `grass`), retorna cuántos Pokémon de ese tipo existen en la Generación 1, y cuál tiene mayor `base_experience`.

Estos datos se obtienen desde la PokéAPI.

**Tipo:** `GET`
**URL:** `/pokemon/type-dominance/{type}`

**Respuesta (JSON):**

```json
{ "tipo": "fire", "total_pokemon": 12, "pokemon_mas_experimentado": { "id": 6, "nombre": "charizard", "base_experience": 267 } }

{ "tipo": "water", "total_pokemon": 32, "pokemon_mas_experimentado": { "id": 9, "nombre": "blastoise", "base_experience": 239 } }

{ "tipo": "grass", "total_pokemon": 14, "pokemon_mas_experimentado": { "id": 3, "nombre": "venusaur", "base_experience": 263 } }

{ "tipo": "psychic", "total_pokemon": 14, "pokemon_mas_experimentado": { "id": 150, "nombre": "mewtwo", "base_experience": 306 } }

{ "tipo": "normal", "total_pokemon": 22, "pokemon_mas_experimentado": { "id": 113, "nombre": "chansey", "base_experience": 395 } }
```

### Desafío 3: Cadena de Evolución

**Contexto:** Dado el ID de un Pokémon, retorna su cadena de evolución completa con orden y nivel mínimo de evolución.

Estos datos se obtienen desde la PokéAPI.

**Tipo:** `GET`
**URL:** `/pokemon/evolution-chain/{id}`

**Respuesta (JSON):**

```jsonc
// id=1 → bulbasaur
{
  "pokemon_base": "bulbasaur",
  "cadena": [
    { "nombre": "bulbasaur", "orden": 1, "min_level": null },
    { "nombre": "ivysaur",   "orden": 2, "min_level": 16 },
    { "nombre": "venusaur",  "orden": 3, "min_level": 32 }
  ]
}

// id=4 → charmander
{
  "pokemon_base": "charmander",
  "cadena": [
    { "nombre": "charmander", "orden": 1, "min_level": null },
    { "nombre": "charmeleon", "orden": 2, "min_level": 16 },
    { "nombre": "charizard",  "orden": 3, "min_level": 36 }
  ]
}

// id=7 → squirtle
{
  "pokemon_base": "squirtle",
  "cadena": [
    { "nombre": "squirtle",  "orden": 1, "min_level": null },
    { "nombre": "wartortle", "orden": 2, "min_level": 16 },
    { "nombre": "blastoise", "orden": 3, "min_level": 36 }
  ]
}

// id=10 → caterpie
{
  "pokemon_base": "caterpie",
  "cadena": [
    { "nombre": "caterpie",   "orden": 1, "min_level": null },
    { "nombre": "metapod",    "orden": 2, "min_level": 7 },
    { "nombre": "butterfree", "orden": 3, "min_level": 10 }
  ]
}

// id=147 → dratini
{
  "pokemon_base": "dratini",
  "cadena": [
    { "nombre": "dratini",   "orden": 1, "min_level": null },
    { "nombre": "dragonair", "orden": 2, "min_level": 30 },
    { "nombre": "dragonite", "orden": 3, "min_level": 55 }
  ]
}

// id=150 → mewtwo (sin evolución)
{
  "pokemon_base": "mewtwo",
  "cadena": [
    { "nombre": "mewtwo", "orden": 1, "min_level": null }
  ]
}
```

### Desafío 4: Pokémon por Región

**Contexto:** Retorna todos los Pokémon nativos de una región (`kanto`, `johto`, `hoenn`, `sinnoh`, `unova`, `kalos`, `alola`, `galar`). Si la región no existe, retornar HTTP 404.

Estos datos se obtienen desde la PokéAPI.

**Tipo:** `GET`
**URL:** `/pokemon/region/{region_name}`

**Respuesta (JSON):**

```jsonc
// GET /pokemon/region/kanto
[
  { "id": 1, "nombre": "Bulbasaur",  "tipos": ["grass","poison"], "generacion": 1 },
  { "id": 2, "nombre": "Ivysaur",    "tipos": ["grass","poison"], "generacion": 1 },
  { "id": 3, "nombre": "Venusaur",   "tipos": ["grass","poison"], "generacion": 1 },
  { "id": 4, "nombre": "Charmander", "tipos": ["fire"],           "generacion": 1 },
  { "id": 5, "nombre": "Charmeleon", "tipos": ["fire"],           "generacion": 1 }
  // ... 146 más
]

// GET /pokemon/region/mordor → región inválida
// HTTP 404
{ "error": "Region 'mordor' no encontradá. Regiónes válidas: kanto, johto, hoenn, sinnoh, unova, kalos, alola, galar" }
```

### Desafío 5: Pokémon Más Poderoso por Generación (Solo para grupo de 5 personas)

**Contexto:** Dado un número de generación (1 al 8), retorna el Pokémon con mayor `base_stat` total (suma de todos sus stats: `hp`, `attack`, `defense`, `special-attack`, `special-defense`, `speed`) dentro de esa generación. Si la generación no existe, retornar HTTP 404. Estos datos se obtienen desde la PokéAPI.

**Tipo:** `GET`
**URL:** `/pokemon/strongest/{generation}`

**Respuesta (JSON):**

```jsonc
// GET /pokemon/strongest/1
{
  "generacion": 1,
  "pokemon": {
    "id": 150,
    "nombre": "mewtwo",
    "total_base_stats": 680,
    "stats": {
      "hp": 106,
      "attack": 110,
      "defense": 90,
      "special_attack": 154,
      "special_defense": 90,
      "speed": 130
    },
    "tipos": ["psychic"]
  }
}

// GET /pokemon/strongest/4
{
  "generacion": 4,
  "pokemon": {
    "id": 487,
    "nombre": "giratina-altered",
    "total_base_stats": 680,
    "stats": {
      "hp": 150,
      "attack": 100,
      "defense": 120,
      "special_attack": 100,
      "special_defense": 120,
      "speed": 90
    },
    "tipos": ["ghost", "dragon"]
  }
}

// GET /pokemon/strongest/9 → generación inválida
// HTTP 404
{
  "error": "Generación '9' no encontrada. Generaciones válidas: 1, 2, 3, 4, 5, 6, 7, 8"
}
```

---

## Etapa 2

### Desafío 6: Dockerfile

Crear un `Dockerfile` para ejecutar el proyecto en un contenedor. El archivo debe cumplir los siguientes requisitos:

- Puerto expuesto: `EXPOSE 3001`
- Variable de entorno: `APP_ENV=production`

En el archivo `application.properties` del proyecto debe configurarse el puerto de forma dinámica:

```properties
server.port=${APP_PORT:3001}
```

**Ejemplo de `Dockerfile`** (ajustar la versión de Java a la definida en el `pom.xml`):

```dockerfile
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

ENV APP_ENV=production

EXPOSE 3001

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Desafío 7: Pipeline CI/CD con GitHub Actions

Configurar un pipeline en **GitHub Actions** que ejecute los pasos necesarios para la construcción del proyecto y su despliegue. El pipeline debe contar con las siguientes 4 etapas:

- Test
- Build
- Docker-build
- Deploy

> **Adaptación a GitHub:** el enunciado original está pensado para GitLab CI (`.gitlab-ci.yml`, *stages*). En GitHub Actions el pipeline se define en `.github/workflows/ci.yml` y cada etapa se implementa como un **job**; el orden se garantiza con `needs`. Las variables de CI/CD de GitLab pasan a ser **Secrets** y **Variables** de GitHub Actions, y `$CI_COMMIT_SHORT_SHA` se reemplaza por los primeros 7 caracteres de `github.sha`.

#### Etapa test

- Se ejecuta en cualquier rama.
- Ejecuta `mvn test`.

#### Etapa build

- Se ejecuta en cualquier rama.
- Ejecuta `mvn package -DskipTests`.
- Genera el JAR en `target/`.
- En GitHub cada job corre en una máquina limpia, por lo que el JAR se guarda con `actions/upload-artifact` para que lo use el job siguiente.

#### Etapa docker-build

- Solo se ejecuta en la rama `develop`.
- Construye la imagen Docker.
- Sube la imagen a Docker Hub con dos tags:
  - `$IMAGE_NAME:latest`
  - `$IMAGE_NAME:<SHA corto del commit>` (equivalente a `$CI_COMMIT_SHORT_SHA` de GitLab)
- Usa las variables `IMAGE_NAME`, `DOCKER_USER` y `DOCKER_PASSWORD` configuradas en GitHub.

#### Etapa deploy

- Solo se ejecuta en la rama `develop`.
- El job de esta etapa será entregado por la profesora junto con las variables de entorno necesarias para acceder al servidor (`SSH_PRIVATE_KEY`, `VPS_HOST`, `VPS_USER`, `APP_PORT`). Los estudiantes deberán completar las variables propias de su grupo en GitHub: `DOCKER_USER`, `DOCKER_PASSWORD`, ya que el script de deploy las utiliza para descargar la imagen correcta desde Docker Hub y ejecutarla en el servidor.
- Si el job entregado por la profesora está escrito para GitLab, debe traducirse a un job de GitHub Actions manteniendo el mismo script (conexión SSH, `docker pull` y `docker run`). El workflow de ejemplo incluye una versión equivalente.

#### Ejemplo de workflow: `.github/workflows/ci.yml`

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: ["**"]
  pull_request:
    branches: [develop]

jobs:
  # ---------- Etapa 1: Test (cualquier rama) ----------
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Configurar Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "17"
          cache: maven

      - name: Ejecutar tests
        run: mvn test

  # ---------- Etapa 2: Build (cualquier rama) ----------
  build:
    runs-on: ubuntu-latest
    needs: test
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Configurar Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "17"
          cache: maven

      - name: Empaquetar JAR
        run: mvn package -DskipTests

      - name: Guardar JAR como artefacto
        uses: actions/upload-artifact@v4
        with:
          name: app-jar
          path: target/*.jar
          if-no-files-found: error

  # ---------- Etapa 3: Docker-build (solo develop) ----------
  docker-build:
    runs-on: ubuntu-latest
    needs: build
    if: github.ref == 'refs/heads/develop' && github.event_name == 'push'
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Descargar JAR
        uses: actions/download-artifact@v4
        with:
          name: app-jar
          path: target

      - name: Calcular SHA corto
        run: echo "SHORT_SHA=${GITHUB_SHA::7}" >> "$GITHUB_ENV"

      - name: Login en Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USER }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Construir y subir imagen
        uses: docker/build-push-action@v6
        with:
          context: .
          push: true
          tags: |
            ${{ vars.IMAGE_NAME }}:latest
            ${{ vars.IMAGE_NAME }}:${{ env.SHORT_SHA }}

  # ---------- Etapa 4: Deploy (solo develop) ----------
  deploy:
    runs-on: ubuntu-latest
    needs: docker-build
    if: github.ref == 'refs/heads/develop' && github.event_name == 'push'
    env:
      SSH_PRIVATE_KEY: ${{ secrets.SSH_PRIVATE_KEY }}
      VPS_HOST: ${{ secrets.VPS_HOST }}
      VPS_USER: ${{ secrets.VPS_USER }}
      APP_PORT: ${{ secrets.APP_PORT }}
      DOCKER_USER: ${{ secrets.DOCKER_USER }}
      DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
      IMAGE_NAME: ${{ vars.IMAGE_NAME }}
    steps:
      - name: Configurar SSH
        run: |
          mkdir -p ~/.ssh
          printf '%s\n' "$SSH_PRIVATE_KEY" > ~/.ssh/id_rsa
          chmod 600 ~/.ssh/id_rsa
          ssh-keyscan -H "$VPS_HOST" >> ~/.ssh/known_hosts

      - name: Desplegar en el VPS
        run: |
          ssh "$VPS_USER@$VPS_HOST" "
            echo '$DOCKER_PASSWORD' | docker login -u '$DOCKER_USER' --password-stdin &&
            docker pull $IMAGE_NAME:latest &&
            (docker stop pokemon-api || true) &&
            (docker rm pokemon-api || true) &&
            docker run -d \
              --name pokemon-api \
              --restart unless-stopped \
              -e APP_PORT=$APP_PORT \
              -p $APP_PORT:$APP_PORT \
              $IMAGE_NAME:latest
          "
```

---

## Variables de CI/CD

Las siguientes variables deben configurarse en el repositorio. **Nunca** deben estar escritas en texto plano ni en el código ni en el pipeline.

| Variable | Descripción | Estado |
|---|---|:---:|
| `DOCKER_USER` | Usuario de Docker Hub | ☐ |
| `DOCKER_PASSWORD` | Token o contraseña de Docker Hub | ☐ |
| `IMAGE_NAME` | Nombre completo de la imagen (ej: `miusuario/pokemon-api`) | ☐ |
| `SSH_PRIVATE_KEY` | Llave privada SSH para conectar al VPS | ☑ |
| `VPS_HOST` | IP del VPS asignado al grupo | ☑ |
| `VPS_USER` | Usuario SSH del VPS | ☑ |
| `APP_PORT` | Puerto de la aplicación | ☑ |

> ☑ = entregada por la profesora · ☐ = debe ser completada por cada grupo.

#### Cómo configurarlas en GitHub

`Settings` → `Secrets and variables` → `Actions`:

| Variable | Pestaña donde se crea |
|---|---|
| `DOCKER_USER` | **Secrets** → `New repository secret` |
| `DOCKER_PASSWORD` | **Secrets** → `New repository secret` |
| `SSH_PRIVATE_KEY` | **Secrets** → `New repository secret` |
| `VPS_HOST` | **Secrets** → `New repository secret` |
| `VPS_USER` | **Secrets** → `New repository secret` |
| `APP_PORT` | **Secrets** → `New repository secret` |
| `IMAGE_NAME` | **Variables** → `New repository variable` (no es información sensible) |

En el workflow se leen como `${{ secrets.NOMBRE }}` y `${{ vars.NOMBRE }}`, respectivamente.

---

## Etapa 3

### Requisitos de ejecución

- Spring Boot debe usar el puerto definido en la variable `APP_PORT` (valor por defecto: `3001`).
- Respetar los formatos de nombres de archivo, así como nombre de ramas de Gitflow y de GitHub.
- El proyecto deberá ejecutarse a través de un contenedor de Docker. En caso de que no funcione correctamente dentro del contenedor, se penalizará con una reducción de 2 puntos en la nota final, ya que el objetivo principal del proyecto es garantizar su funcionamiento en un entorno de contenedores.
- La imagen debe quedar publicada en Docker Hub y ser accesible públicamente.

> **¡IMPORTANTE!** Tenga presente que el plagio parcial o total es una falta grave, por lo que es calificado con nota mínima e informado al director de carrera, arriesgando sanciones académicas.

---

## Responsables de endpoints

El equipo debe dividirse equitativamente los endpoints y debe quedar documentado quién es responsable de qué endpoints.

| Endpoint | Rama | Responsable |
|---|---|---|
| `GET /pokemon/weak-defense` | `Desafio1` | Integrante 1 |
| `GET /pokemon/type-dominance/{type}` | `Desafio2` | Integrante 2 |
| `GET /pokemon/evolution-chain/{id}` | `Desafio3` | Integrante 3 |
| `GET /pokemon/region/{region_name}` | `Desafio4` | Integrante 4 |
| `GET /pokemon/strongest/{generation}` | Grupal | Todo el equipo |
| `Dockerfile` | `docker` | Todo el equipo |
| Pipeline (`.github/workflows/ci.yml`) | `pipeline` | Todo el equipo |

---

## Pauta de Evaluación

### Criterios grupales

- Creación de ramas y trabajo en equipo (8 puntos).
- Configuración de repositorio y políticas de Pull Request (6 puntos).
- Funcionalidad de los endpoints (16 puntos, 4 c/u).
- Configuración y uso de Docker (12 puntos).
- Configuración Pipeline CI/CD completo con etapa deploy a VPS (22 puntos).

**Total puntos grupales: 64 pts**

### Criterios individuales

El equipo debe dividirse equitativamente los endpoints y debe quedar documentado quién es responsable de qué endpoints en un README dentro del proyecto.

- Exactitud en el formato de los resultados de las consultas (36 puntos).
- Correctitud en la estructura de las URLs y en los métodos HTTP (20 puntos).

**Total de puntos individuales: 56 pts**

> **NOTA:** El proyecto deberá ejecutarse a través de un contenedor de Docker. En caso de que no funcione correctamente dentro del contenedor, se penalizará con una reducción de 2 puntos en la nota final, ya que el objetivo principal del proyecto es garantizar su funcionamiento en un entorno de contenedores.

**Total de puntos: 120 pts**

### Rúbrica grupal

| Contenido | Deficiente | Funcional | Destacado |
|---|---|---|---|
| **Creación de ramas y trabajo en equipo** | Trabajo sin estrategia de ramas o trabajo individual disfrazado de grupal. | Uso parcial. | Uso consistente de ramas por funcionalidad, commits claros, evidencia de colaboración equilibrada, integración ordenada. |
| *Puntaje* | 0 | 4 | 8 |
| **Configuración de repositorio y políticas de Pull Request** | Sin políticas claras o repositorio mal configurado. | Configuración parcial; algunas políticas faltan o no se aplican consistentemente. | Repositorio bien configurado, reglas de PR activas, revisiones obligatorias y protección de ramas. |
| *Puntaje* | 0 | 3 | 6 |
| **Funcionalidad de endpoints** | Falla o no están implementados. | Endpoints que funcionan, pero hay errores funcionales o inconsistencias. | Endpoints funcionan correctamente, responden según especificación y sin errores relevantes. |
| *Puntaje* | 0 (cada uno, por separado) | 2 (cada uno, por separado) | 4 (cada uno, por separado) |
| **Configuración y uso de Docker** | Docker inexistente o no funcional. | — | Proyecto dockerizado correctamente, reproducible y funcional con configuración clara. |
| *Puntaje* | 0 | — | 12 |
| **Pipeline CI/CD** | Sin pipeline funcional. | Pipeline parcial o poco confiable. | Pipeline completo según etapas indicadas. |
| *Puntaje* | 0 | 11 | 22 |

### Rúbrica individual

| Contenido | Deficiente | Funcional | Destacado |
|---|---|---|---|
| **Exactitud en formato de resultados de consultas** | Formato incorrecto o incumplimiento generalizado. | Formato parcialmente correcto, con inconsistencias menores o errores repetidos. | Respuestas correctas, consistentes y cumplen completamente formato esperado. |
| *Puntaje* | 0 | 18 | 36 |
| **URLs y métodos HTTP correctos** | Diseño incorrecto o mal uso general de endpoints/métodos. | Algunos errores de diseño o inconsistencias REST. | URLs bien estructuradas, convenciones REST correctas y uso apropiado de métodos HTTP. |
| *Puntaje* | 0 | 10 | 20 |

### Tabla de conversión Puntaje → Nota

Para obtener la nota de un puntaje, ubique la fila de la decena y la columna de la unidad (por ejemplo, 87 puntos → fila `80` · columna `+7` → **4.9**).

| Puntaje | +0 | +1 | +2 | +3 | +4 | +5 | +6 | +7 | +8 | +9 |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **0** | 1.0 | 1.0 | 1.1 | 1.1 | 1.2 | 1.2 | 1.3 | 1.3 | 1.3 | 1.4 |
| **10** | 1.4 | 1.5 | 1.5 | 1.5 | 1.6 | 1.6 | 1.7 | 1.7 | 1.8 | 1.8 |
| **20** | 1.8 | 1.9 | 1.9 | 2.0 | 2.0 | 2.0 | 2.1 | 2.1 | 2.2 | 2.2 |
| **30** | 2.3 | 2.3 | 2.3 | 2.4 | 2.4 | 2.5 | 2.5 | 2.5 | 2.6 | 2.6 |
| **40** | 2.7 | 2.7 | 2.8 | 2.8 | 2.8 | 2.9 | 2.9 | 3.0 | 3.0 | 3.0 |
| **50** | 3.1 | 3.1 | 3.2 | 3.2 | 3.3 | 3.3 | 3.3 | 3.4 | 3.4 | 3.5 |
| **60** | 3.5 | 3.5 | 3.6 | 3.6 | 3.7 | 3.7 | 3.8 | 3.8 | 3.8 | 3.9 |
| **70** | 3.9 | 4.0 | 4.0 | 4.1 | 4.1 | 4.2 | 4.3 | 4.3 | 4.4 | 4.4 |
| **80** | 4.5 | 4.6 | 4.6 | 4.7 | 4.8 | 4.8 | 4.9 | 4.9 | 5.0 | 5.1 |
| **90** | 5.1 | 5.2 | 5.3 | 5.3 | 5.4 | 5.4 | 5.5 | 5.6 | 5.6 | 5.7 |
| **100** | 5.8 | 5.8 | 5.9 | 5.9 | 6.0 | 6.1 | 6.1 | 6.2 | 6.3 | 6.3 |
| **110** | 6.4 | 6.4 | 6.5 | 6.6 | 6.6 | 6.7 | 6.8 | 6.8 | 6.9 | 6.9 |
| **120** | 7.0 | | | | | | | | | |