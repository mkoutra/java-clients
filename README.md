# Java HTTP Clients

This project demonstrates how to consume a REST API using three different Java HTTP clients:

- **Apache HttpClient**
- **OkHttp**
- **HttpURLConnection** (built-in Java client)

The client communicates with the public [JSONPlaceholder API](https://jsonplaceholder.typicode.com).

## Requirements

- Java 8
- Maven

## Usage

Change the HTTP client used in `HttpClientApp.java` by setting:

```java
private static final HttpClientType CLIENT_TYPE = HttpClientType.OKHTTP;
