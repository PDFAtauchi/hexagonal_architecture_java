# Application using Hexagonal Architecture

![Hexagonal Architecture]((https://github.com/PDFAtauchi/hexagonal_architecture_java/raw/main/hexagonal-architecture.png))

## Stack
- Java 17
- Spring Boot 3.1.5

### To Run
    - mvn spring-boot:run

#### POST http://localhost:8080/api/pizza/
 ```
{
    "name": "Familiar",
    "toppings": ["cheese", "tomatoes"]
}
```
#### POST http://localhost:8080/api/pizza/
 ```
{
    "name": "Extra_big",
    "toppings": ["cheese", "cucumber"]
}
```
#### GET http://localhost:8080/api/pizza/Familiar/
#### GET http://localhost:8080/api/pizza/Extra_big/
#### GET http://localhost:8080/api/pizza/

##### 
## Tests
### Run Tests without jaCoCo
    - mvn test

### Run Maven Tests with JaCoCo:
    - mvn clean verify

### Generate Coverage Report:
    - mvn jacoco:report