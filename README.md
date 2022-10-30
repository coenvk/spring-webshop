# spring-webshop

Example project that shows how to implement a microservices architecture in Spring.
The architecture constitutes an API gateway with service registry to detect deployed microservices.
Microservices communicate with each other over a Kafka topic.
The saga pattern was implement to support distributed transactions.

#### Used technologies
- MapStruct: for domain -> dto mapping
- Hateoas
- Rate limiting
- Retry pattern
- Bulkhead pattern
- Circuit breaker pattern
- API gateway
- Service registry
