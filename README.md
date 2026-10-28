# denunciaservice

API REST de denuncias ciudadanas (Spring Boot + MySQL/MariaDB) para el curso Taller de Desarrollo de Aplicaciones Móviles.

El backend vive en esta carpeta, separada del proyecto Android `denunciasapp`.

## Requisitos locales

- JDK 17 o superior (en este equipo se puede usar el JBR de Android Studio, Java 21).
- Maven Wrapper incluido (`mvnw` / `mvnw.cmd`).
- XAMPP con MariaDB/MySQL en el puerto `3306`.
- Base de datos ya creada: `examen_denuncias`.

## Credenciales de la base de datos

No se guardan contraseñas reales en GitHub. La conexión se configura con variables de entorno:

| Variable | Uso | Valor local detectado en phpMyAdmin |
| --- | --- | --- |
| `DB_URL` | URL JDBC | `jdbc:mysql://localhost:3306/examen_denuncias?...` |
| `DB_USERNAME` | Usuario | `root` |
| `DB_PASSWORD` | Contraseña | vacía (configuración por defecto de XAMPP) |

Si tu usuario o contraseña son distintos, cámbialos **antes** de iniciar Spring Boot:

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "tu_clave"
$env:DB_URL = "jdbc:mysql://localhost:3306/examen_denuncias?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima"
```

También puedes editar los valores por defecto en `src/main/resources/application.properties`.

## Cómo ejecutar (Windows)

En PowerShell, desde esta carpeta:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\mvnw.cmd spring-boot:run
```

La API queda en `http://localhost:8080`.

Para solo compilar:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\mvnw.cmd -DskipTests compile
```

## Endpoints

Base: `http://localhost:8080/denuncias`

### 1. Registrar denuncia

`POST /denuncias/`

```json
{
  "dni": "12345678",
  "tipo": "ROBO",
  "direccion": "Av. Principal 123",
  "descripcion": "Se reporta un robo en la vía pública"
}
```

Respuesta esperada: `201 Created` con la denuncia (incluye `id`, `fecha` y `estado = 1`).

### 2. Listar con paginación

`GET /denuncias/?offset=0&limit=5`

Orden: `id` descendente. `limit` permitido: 1 a 50.

### 3. Obtener por ID

`GET /denuncias/{id}`

Si no existe: `404`.

### 4. Consultar por DNI

`GET /denuncias/ciudadano/{dni}`

El DNI debe tener 8 dígitos. Si no hay resultados, responde `[]`.

### 5. Marcar como atendida

`PUT /denuncias/{id}`

Cambia `estado` de `1` (pendiente) a `0` (atendida). No borra el registro.

## Colección rápida para Postman

1. POST `http://localhost:8080/denuncias/` con el JSON de ejemplo.
2. GET `http://localhost:8080/denuncias/?offset=0&limit=5`
3. GET `http://localhost:8080/denuncias/1`
4. GET `http://localhost:8080/denuncias/ciudadano/12345678`
5. PUT `http://localhost:8080/denuncias/1` (sin cuerpo)
6. GET `http://localhost:8080/denuncias/1` para confirmar `estado: 0`

## Docker (preparación para Render)

Docker no es necesario para las pruebas locales. El `Dockerfile` sirve para el despliegue posterior.

```bash
docker build -t denunciaservice .
docker run -p 8080:8080 -e DB_URL="..." -e DB_USERNAME="..." -e DB_PASSWORD="..." denunciaservice
```

La base de datos de XAMPP no es accesible desde Internet. En producción hay que usar un MySQL/MariaDB alojado y las mismas variables de entorno.

## Significado del estado

- `1`: pendiente
- `0`: atendida
