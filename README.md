# Algafood API

This project is based on a course from Algaworks. Algafood API is a REST API for a food delivery solution.

## Running

This project was bootstrapped with Spring Boot, so:

```
./mvnw spring-boot:run
``` 

## Media Types

The API works with `application/json` ~~by default but works with `application/xml` if specified via `Accept` header by the client~~.
~~Although not specified for every method, this behavior is provided by just adding `com.fasterxml.jackson.dataformat / jackson-dataformat-xml` dependency.~~
