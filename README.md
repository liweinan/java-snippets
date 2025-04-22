# Java Snippets

A collection of Java code snippets and examples for learning and reference.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── io/
│   │       └── weli/
│   │           ├── ai/          # AI related examples
│   │           ├── alg/         # Algorithm examples
│   │           ├── concurrent/  # Concurrency examples
│   │           ├── generic/     # Generic programming examples
│   │           ├── lang/        # Java language features
│   │           ├── reflection/  # Reflection examples
│   │           ├── rest/        # REST API examples
│   │           └── ...
│   └── resources/
└── test/
    └── java/
        └── io/
            └── weli/
                └── ...
```

## Features

- REST API client examples using RestAssured
- Algorithm implementations and examples
- Concurrency patterns and examples
- Generic programming examples
- Java language features exploration
- Reflection API usage examples
- AI integration examples
- And more...

## Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.9.6 or later

### Building

```bash
mvn clean install
```

### Running Examples

#### REST API Client Demo

To run the REST API client demo:

```bash
# Without proxy
mvn clean compile exec:java -Dexec.mainClass="io.weli.rest.RestApiClientDemo"

# With proxy
mvn clean compile exec:java -Dexec.mainClass="io.weli.rest.RestApiClientDemo" -Dproxy.enabled=true -Dproxy.host=localhost -Dproxy.port=7890
```

The demo will:
1. Fetch all posts
2. Get a specific post
3. Create a new post
4. Update a post
5. Get user information
6. Delete a post

#### Running Tests

```bash
mvn clean test
```

## Proxy Configuration

The project supports proxy configuration for HTTP/HTTPS requests. You can configure the proxy settings in two ways:

1. Using system properties:
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="io.weli.rest.RestApiClientDemo" -Dproxy.enabled=true -Dproxy.host=localhost -Dproxy.port=7890
   ```

2. Programmatically in your code:
   ```java
   System.setProperty("http.proxyHost", "localhost");
   System.setProperty("http.proxyPort", "7890");
   System.setProperty("https.proxyHost", "localhost");
   System.setProperty("https.proxyPort", "7890");
   ```

## Contributing

Feel free to contribute by:
1. Forking the repository
2. Creating a new branch
3. Making your changes
4. Submitting a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.