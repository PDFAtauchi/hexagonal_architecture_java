# Application using Hexagonal Architecture


## Stack
- Java 17
- Spring Boot 3.1.5
- JPA
- Hibernate
- PostgreSQL
- testContainers

## Tests
### Run Tests without jaCoCo
    - mvn test

### Run Maven Tests with JaCoCo:
    - mvn clean verify

### Generate Coverage Report:
    - mvn jacoco:report