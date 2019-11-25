# Marfeelizables
A service that detects _marfeelizable_ websites.

> This service uses concurrent techniques to check multiple sites at once but this approach is not indefinitely 
scalable, a more robust and scalable one might make use of some sort of distributed architecture containing queues, workers, etc. 
Tune the different threading parameters according to the provisioned hardware (see `com.jlpiedrahita.marfeelizables.config.App`).  

> Marfeelizable results are stored into an in memory database (H2). Take it into account before restarting the service.

### Requirements

Marfeelizables requires [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 
or later to run  

### Running the API

Just use the Maven wrapper:

    ./mvnw
    
Or for Windows systems:

    ./mvnw.cmd
    
This will install all the necessary dependencies and start the API server at http://localhost:8080.
 
#### This API exposes two endpoints:

1. #### `GET /marfeelizables`
    
    Retrieves the list of all the checked websites.
    
    Request:
    
        curl -X GET http://localhost:8080/marfeelizables
    
    Response:
    
    ```json
    [
        {
            "id": 1,
            "visitStatus": "SUCCESS",
            "visitDate": "17/11/2019 11:20:51 UTC",
            "marfeelizable": false,
            "url": "http://20minutos.es"
        },
        {
            "id": 2,
            "visitStatus": "SUCCESS",
            "visitDate": "17/11/2019 11:20:51 UTC",
            "marfeelizable": true,
            "url": "http://elperiodico.com"
        },
        {
            "id": 3,
            "visitStatus": "SUCCESS",
            "visitDate": "17/11/2019 11:20:51 UTC",
            "marfeelizable": false,
            "url": "http://mo2o.com"
        }
    ]
    ```

2. #### `POST /marfeelizables`
    
    Checks whether a list of websites identified by URL is marfeelizable or not.
    
    Request:
    
    ```
    curl -X POST \
      http://localhost:8080/marfeelizables \
      -H 'Accept: application/json' \
      -H 'Content-Type: application/json' \
      -d '[{"url": "elperiodico.com"}, {"url": "20minutos.es"}, {"url": "mo2o.com"}]'
    ```
    
    Response:
    
    > _This endpoint returns an empty response_

### Running the tests

Using the Maven wrapper:

    ./mvnw test

### TODO

- Pagination
- Extra input validation
- Use/add an OpenAPI/Swagger spec
- Write more tests