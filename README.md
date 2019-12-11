# Algafood API

This project is based on a course from Algaworks. Algafood API is a REST API for a food delivery solution.

## Running

This project was bootstrapped with Spring Boot, so:

```
./mvnw spring-boot:run
``` 

## Testing

There's a [Insomnia_requests.json](src/main/resources/static/Insomnia_requests.json) file. Import it into [Insomnia](https://insomnia.rest/download/) to test all endpoints (already with examples).

You may use it against [https://algafoodapi.herokuapp.com/](https://algafoodapi.herokuapp.com/). Some endpoints you can try via browser (GET requests):

- [/provinces](https://algafoodapi.herokuapp.com/provinces)
- [/provinces/1](/home/drugo/Projects/algafood-api/README.md)
- [/cities](https://algafoodapi.herokuapp.com/cities)
- [/cities/1](https://algafoodapi.herokuapp.com/cities/1)
- [/cuisines](https://algafoodapi.herokuapp.com/cuisines)
- [/cuisines/1](https://algafoodapi.herokuapp.com/cuisines/1)
- [/restaurants](https://algafoodapi.herokuapp.com/restaurants)
- [/restaurants/1](https://algafoodapi.herokuapp.com/restaurants/1)

```text
Note: HATEOAS soon...
```

## Media Types

The API works with `application/json`.

