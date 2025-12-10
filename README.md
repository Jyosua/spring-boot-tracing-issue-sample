# spring-boot-tracing-issue-sample

Minimal Spring Boot 3.5.8 web API project with Micrometer 1.6.0 for demonstrating tracing issues.

## Tech Stack

- Spring Boot 3.5.8
- Kotlin 1.9.25
- Gradle 8.5
- Micrometer Core 1.6.0
- Micrometer Tracing 1.6.0
- Java 17

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

- `GET /` - Returns "Hello, World!"
- `GET /health` - Returns "OK"

## Test

Once the application is running, you can test it with:
```bash
curl http://localhost:8080/
curl http://localhost:8080/health
```