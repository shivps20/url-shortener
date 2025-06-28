# Scalable URL Shortener

This is a reliable and scalable URL shortener built using Java Spring Boot, Redis, and PostgreSQL, made to handle heavy usage in real-world applications.

## Features
- Shorten long URLs
- Redirect short URLs to original ones
- Expiry support
- Redis caching
- Base62 encoded IDs

## Tech Stack
- Java 17 + Spring Boot
- PostgreSQL
- Redis
- Docker
- Swagger (API Docs)
- Kubernetes (optional for deployment)

## Architecture
See [`design/architecture.md`](design/architecture.md)

## Running Locally
```bash
docker-compose up --build
