# Spring Security - JDBC Authentication with PostgreSQL

Proyecto desarrollado con Spring Boot y Spring Security utilizando autenticación basada en JDBC contra una base de datos PostgreSQL ejecutándose en Docker.

## Tecnologías

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL 15
- Docker & Docker Compose
- Maven
- Lombok

## Funcionalidades

- Autenticación mediante Spring Security.
- Implementación personalizada de `UserDetailsService`.
- Usuarios y roles almacenados en PostgreSQL.
- Carga de usuarios desde base de datos utilizando JDBC.
- Configuración de autorización basada en roles.
- Inicialización automática de esquema y datos mediante scripts SQL.

## Estructura del proyecto

```text
src/main/java/com/spring/security/app_security
├── controllers
├── entities
├── repository
└── security
    ├── CustomerUserDetails
    └── SecurityConfig
```

## Base de datos

La aplicación utiliza PostgreSQL ejecutándose en Docker.

### Docker Compose

```yaml
services:
  db:
    image: postgres:15.2
    container_name: security_bank
    restart: always
    environment:
      POSTGRES_DB: security_bank
      POSTGRES_USER: sam
      POSTGRES_PASSWORD: 123456
    ports:
      - "5433:5432"
```

### Levantar la base de datos

```bash
docker compose up -d
```

Verificar:

```bash
docker ps
```

## Conexión a PostgreSQL

| Parámetro | Valor |
|------------|---------|
| Host | localhost |
| Puerto | 5433 |
| Base de datos | security_bank |
| Usuario | sam |
| Contraseña | 123456 |

## Configuración de autenticación

La autenticación se realiza mediante una implementación personalizada de `UserDetailsService`:

```java
@Service
public class CustomerUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        ...
    }
}
```

El usuario es recuperado desde PostgreSQL a través de `CustomerRepository` y convertido a un objeto `UserDetails` de Spring Security.

## Scripts SQL

Los scripts se ejecutan automáticamente al iniciar el contenedor:

```text
db/sql/create_schema.sql
db/sql/data.sql
```

- `create_schema.sql`: crea las tablas.
- `data.sql`: inserta usuarios y roles iniciales.

## Ejecutar la aplicación

```bash
mvn spring-boot:run
```

o

```bash
mvn clean install
java -jar target/app_security.jar
```

## Autor

Samuel Garcia