# Scalable URL Shortener

This is a reliable and scalable URL shortener built using Java Spring Boot and H2 (in-memory) database for initial development and testing. The project is designed to be easily switched to MySQL or other databases for production.

## Features
- Shorten long URLs
- Redirect short URLs to original ones
- Expiry support (optional)
- Redis caching (optional, for future scalability)
- Base62 encoded IDs

## Tech Stack - Phase-1
- Java 17 + Spring Boot
- H2 Database (for development/testing)
- Streamlit (Python) - for UI Validation

### Planned for Phase-2
- Docker (for containerization)
- Swagger (API Docs)
- Kubernetes (optional for deployment)

## Architecture
See [`architecture.md`](urlshortener/architecture.md)

## Running Locally

By default, the application uses the H2 in-memory database.  
To start the application:

Run directly from IntelliJ IDE.

Or run directly with Maven:

```bash
./mvnw spring-boot:run
```

## API Documentation

Once running, access Swagger UI at:  
`http://localhost:8080/swagger-ui.html`

## Switching to MySQL or Other Databases

Update the `application.properties` file to use your preferred database. See comments in the file
