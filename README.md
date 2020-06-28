[![Run in Insomnia}](https://insomnia.rest/images/run.svg)](https://insomnia.rest/run/?label=Drugo%20AlgafoodAPI&uri=https%3A%2F%2Falgafoodapi.herokuapp.com%2FInsomnia_requests.json)

# Algafood API

This project is based on a course from Algaworks. Algafood API is a REST API for a food delivery solution.

## Running

This project was bootstrapped with Spring Boot, so:

```
./mvnw spring-boot:run
``` 

Since Spring Boot 2.3, you can run:

```
./mvnw spring-boot:build-image
```

### Storage

If you use S3 to storage pictures, you must provide the key and secret keys. For security reasons they are not checked to the repository and you should provide on your run configuration.

### Known issues

I try to keep the application cross-compatible between MySQL and H2 (for demonstration purposes, of course), but there's one endpoint that I could not make to work which is the `/statistics/daily-sales`. If you want to see it working provide a MySQL instance and configure accordingly on `application.properties`.

This creates a docker image of the project.

## Testing

There's a [Insomnia_requests.json](src/main/resources/static/Insomnia_requests.json) file. Import it into [Insomnia](https://insomnia.rest/download/) to test all endpoints (already with examples). It's easier to just use the `Run in Insomnia` button above.

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

You may also use Apache jMeter or Apache Bench (`ab -n <requests> -c <how-many-in-paralell> <endpoint>`)

### CORS

To simulate the CORS situation you want to access your API through other domain, for example `http://api.algafood.local:<port>`. Start by adding the following to your `hosts` file:

```text
127.0.0.1       api.algafood.local
``` 

Now on line 19 of the `src/main/resources/templates/requests.html` file, add the address of where your API is running, like so:

```javascript
var host = "http://localhost:<port>";
```

Finally, run and access your API through `http://api.algafood.local:<port>/requests`.

This setup will activate the CORS implementation on any browser since the requests are from `api.algafood.local` to `localhost`. 

Of course the requests will work since they are being accepted by the server configuration at `src/main/java/dev/drugowick/algaworks/algafoodapi/config/WebConfig.java`. 

You can deactivate that and see for yourself CORS working. You can also  change the maxAge and see the browser re-doing the pre-flight requests after the "maxAge" time. 

### Flyway

This application can use H2 or MySQL as database. The H2 support was designed for self-contained demonstrations on heroku, [for example](https://algafoodapi.herokuapp.com/). So you may see some limitations on SQL code.

One of the limitations is the inability to set the auto_increment of MySQL to 1 when migrating sample data. H2 doesn't support this command. The consequence is that when running the application locally many times using MySQL as database, you'll have IDs incrementing through restarts (i.e., at some point you may have restaurants 57, 58, 59 and 60).

Don't worry, migration scripts are designed to search the IDs when creating entries with foreign keys, but if you still want to restart your IDs, you can:

- Drop the schema; OR
- Run `./mvnw flyway:clean -Dflyway.configFiles=src/main/resources/flyway.properties` from the project root directory.

In both cases, when starting the application, schema and data definition will be created and (includes or on english language) data will e migrated.

```text
Note: the second option was added to show Flyway capabilities.
```

## Media Types

The API works with `application/json`. There are endpoints which accept `image/jpeg` and `image/png`.
