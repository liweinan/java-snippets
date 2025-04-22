# Java Snippets

A collection of Java code snippets and examples covering various topics and use cases.

## Requirements

- Java 21 or later
- Maven 3.8.0 or later

## Building the Project

```bash
mvn clean install
```

## Running Tests

The project uses JUnit Jupiter (5.11.4) for testing. To run all tests:

```bash
mvn clean test
```

## Dependencies

The project includes various dependencies for different purposes:

### Core Dependencies
- RxJava 3.1.10
- Jackson Core/Databind 2.18.3
- Lombok 1.18.38
- BouncyCastle 1.46
- JGroups 5.4.5.Final
- RESTEasy 6.2.12.Final

### Testing Dependencies
- JUnit Jupiter 5.11.4
- JUnit Platform 1.11.4
- TestNG 7.11.0
- AssertJ 3.27.3
- Spring Boot Test 3.4.4

### Other Dependencies
- GraalJS 24.2.1
- LangChain4j 0.36.2
- JavaFX (Java 21)
- MapStruct 1.6.3

## Project Structure

- `src/main/java`: Main source code
- `src/test/java`: Test source code
- `src/main/resources`: Resource files

## License

This project is licensed under the terms of the license specified in the project.