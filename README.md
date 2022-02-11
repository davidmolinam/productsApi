# SimilarProducts API

## Descripción
REST API operation that provide for customers the product detail of the similar products for a given one.

Requests to other APIs are made by the [Spring Cloud Feign Project](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign).

The response of an executed request are being cached to have better response times, for this action we use [Spring Data Caching](https://spring.io/guides/gs/caching/).

To build the process and automatically generate our Java classes and inject dependencies, we use [Lombok Library ]([https://projectlombok.org).

To see the contract of the API with the endpoint and the models an endpoint is enable with swagger in the endpoint:  http://localhost:5000/swagger-ui/index.html

### Allowed Actions:
* Fetch all the similar products for a given one.

## Requirements
* It is necessary Java 11 for the correct build of the project.
* Is not necessary but recommended an installation of maven in your pc.

## Build
We use`maven` to build the services. Is not necessary install maven in the pc because there is an embedded maven in the root folder of the project.
For the build creation place your command line in the root folder :

### With the embedded distribution
* Linux OS.

```./mvn clean instal ```

* Windows OS.

```./mvn.cmd clean instal ```

* With the installed distribution in our pc (recommended)

```./mvn clean instal ```


## Run
When the build is finished the binaries of our application will be in the target folder of our project, and for run the application we have to execute the next script:

```./java -jar target/productsapi-1.0.0.jar```

The version 1.0.0 is the version when this document was wrote.

### Unit test
When the clean install was executed the unit test were executed too. But if only preffer execute the unit tests this is the command line script: 

```./mvn clean test```


## Configuration
The configuration properties of the API are:

| Property                                            | Description                                                                                                      | Value types |
|-----------------------------------------------------|------------------------------------------------------------------------------------------------------------------|-------------|
| server.port                                         | The request API port.                                                                                            | Number      | 
| similar.products.url                                | Url of the product API to fetch the data                                                                         | Url         |
| feign.client.config.similar-products.connectTimeout | Prevents blocking the caller due to the long server processing time                                              | Number      |
| feign.client.config.similar-products.readTimeout    | Is applied from the time of connection establishment and is triggered when returning the response takes too long | Number      | 
| feign.client.config.similar-products.loggerLevel    | Logger level of the feign client                                                                                 | String      |
| logging.file                                        | Location of the logs.                                                                                            | Path.       |
| logging.config                                      | Location of the logback config file                                                                              | Path        |

## API call example

After run the SimilarProducts API we can check it with the [Swagger UI url](http://localhost:5000/swagger-ui/index.html) , put the url in our internet browser or with curl, the endpoint for the test is:
```
http://localhost:5000/product/1/similar
```

and the result will be:

```json
[{"id":"2","name":"Dress","price":19.99,"availability":true},{"id":"3","name":"Blazer","price":29.99,"availability":false},{"id":"4","name":"Boots","price":39.99,"availability":true}]
```