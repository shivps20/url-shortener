# URL Shortener Architecture


mermaid
flowchart TD
    User[User / UI (Streamlit, Gradio, Postman, Browser)]
    API[Spring Boot REST API<br/>UrlShortenerController]
    Service[UrlShortenerService<br/>(Business Logic)]
    Repo[UrlMappingRepository<br/>(Spring Data JPA)]
    DB[(H2 / MySQL Database)]
    Cache[(Optional: Redis Cache)]
    Exception[GlobalExceptionHandler<br/>(@ControllerAdvice)]

    User -- HTTP Request --> API
    API -- Calls --> Service
    Service -- CRUD --> Repo
    Repo -- JPA --> DB

    Service -- (Optional) Cache Lookup --> Cache
    Service -- (Optional) Cache Store --> Cache
    
    API -- Exception Handling --> Exception
    Service -- Exception Handling --> Exception
    API -- HTTP Response --> User


**Description:**
- **User/UI**: Can be Streamlit, Gradio, Postman, or any HTTP client.
- **Spring Boot REST API**: Handles incoming HTTP requests and responses.
- **Service Layer**: Contains business logic for shortening and retrieving URLs, handling custom aliases, expiry, and click counts.
- **Repository Layer**: Uses Spring Data JPA to interact with the database.
- **Database**: Stores URL mappings (H2 for dev/testing, MySQL for production).
- **Cache (Optional)**: Redis can be added for faster lookups.
- **Global Exception Handler**: Handles and formats errors for the