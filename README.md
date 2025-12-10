# spring-boot-tracing-issue-sample

Minimal Spring Boot 3.5.8 web API project with Micrometer 1.6.0 for demonstrating tracing issues, featuring custom logback configuration and OAuth2 JWT security.

## Tech Stack

- Spring Boot 3.5.8
- Kotlin 1.9.25
- Gradle 8.5
- Micrometer Core 1.6.0
- Micrometer Tracing 1.6.0
- Spring Security with OAuth2 Resource Server
- Java 17

## Features

### Custom Logback Configuration
The application includes a custom logback configuration with the following pattern:
- Date and time (`yyyy-MM-dd HH:mm:ss.SSS`)
- Thread name
- Highlighted log level
- TraceId and SpanId (for distributed tracing)
- Cyan-colored logger name
- Log message

Example output:
```
2025-12-10 08:49:42.374 [main] INFO  [,] com.example.demo.ApplicationKt - Starting ApplicationKt...
```

### OAuth2 Resource Server (JWT)
The application is secured with Spring Security and configured as an OAuth2 Resource Server that accepts JWT tokens:
- All endpoints except `/health` require JWT authentication
- `/health` endpoint is publicly accessible
- Returns HTTP 401 with `WWW-Authenticate: Bearer` header for unauthenticated requests

#### JWT Configuration
Configure the JWT issuer URI in `application.yaml` or via environment variable:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://your-auth-server.com
```

Or use environment variable:
```bash
JWT_ISSUER_URI=https://your-auth-server.com ./gradlew bootRun
```

For development/testing without a real OAuth2 provider, you must set the `JWT_SECRET` environment variable:
```bash
JWT_SECRET=your-development-secret-at-least-256-bits ./gradlew bootRun
```

## Build and Run

Build the project:
```bash
./gradlew build
```

Run the application:
```bash
./gradlew bootRun
```

The application will start on port 8080.

## Endpoints

- `GET /` - Returns "Hello, World!" (requires JWT authentication)
- `GET /health` - Returns "OK" (public, no authentication required)

## Test

Once the application is running, you can test it with:

Test the public health endpoint:
```bash
curl http://localhost:8080/health
# Returns: OK
```

Test the protected root endpoint (will return 401 without JWT):
```bash
curl http://localhost:8080/
# Returns: HTTP 401 Unauthorized
```

To access protected endpoints, include a valid JWT token:
```bash
curl -H "Authorization: Bearer <your-jwt-token>" http://localhost:8080/
# Returns: Hello, World!
```