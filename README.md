# Guía del Proyecto Bonos Corporativos

## Configuración de Seguridad

### Roles del Sistema
```java
public enum Roles {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_ANALYST,
}
```

### Permisos por Endpoint
La configuración de seguridad en `WebSecurityConfiguration.java` establece los siguientes permisos:

- `/api/v1/authentication/**`: Acceso público (para login/registro)
- `/api/v1/roles/**`: Solo usuarios con rol ADMIN
- `/api/v1/users/**`: Solo usuarios con rol ADMIN 
- Documentación Swagger: Acceso público

## Autenticación

El sistema utiliza autenticación JWT (Bearer Token). Para acceder a endpoints protegidos:

1. Obtén un token mediante `/api/v1/authentication/sign-in`
2. Incluye el token en el header: `Authorization: Bearer {token}`

## Endpoints Principales

- **Autenticación**: `/api/v1/authentication/sign-in` (POST)
- **Usuarios**: `/api/v1/users/{rucId}` (GET)
- **Roles**: `/api/v1/roles` (GET)

## Documentación API

La API está documentada con Swagger en:
- `/swagger-ui.html`

## Ejecución del Proyecto

1. Asegúrate de tener Java 17+ y Maven instalados
2. Configura la base de datos en `application.properties`
3. Ejecuta: `mvn spring-boot:run`
4. La aplicación estará disponible en: `http://localhost:8080`

## Solución de Problemas Comunes

- **Error 404 en endpoints de usuarios**: Verifica que el token pertenezca a un usuario con rol ADMIN
- **Error de autenticación**: Asegúrate de que el token JWT sea válido y no esté expirado

## Referencias Útiles

* [Documentación Spring Boot](https://docs.spring.io/spring-boot/3.5.0/reference/html/)
* [Documentación Spring Security](https://docs.spring.io/spring-security/reference/index.html)
