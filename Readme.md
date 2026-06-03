# Spring Boot Security Learning Project

This project is a simple Spring Boot application created to learn and implement Spring Security step by step.

The goal is to start with a basic application and progressively add security features such as authentication, authorization, roles, custom users, and more.

---

## Technologies

- Java 21
- Spring Boot
- Spring Security
- Maven
- IntelliJ IDEA

---

## Project Structure

```text
src
 └── main
     ├── java
     │   └── com.spring.security.app_security
     │       ├── controllers
     │       │   ├── WelcomeController.java
     │       │   ├── AboutUsController.java
     │       │   ├── AccountsController.java
     │       │   ├── BalanceController.java
     │       │   ├── CardsController.java
     │       │   └── LoansController.java
     │       ├── security
     │       │   └── SecurityConfig.java
     │       └── AppSecurityApplication.java
     └── resources
         └── application.properties
```

---

## Available Endpoints

### Public Endpoints

```text
/welcome
/about_us
```

### Protected Endpoints

```text
/accounts/**
/balance/**
/cards/**
/loans/**
```

---

# Spring Security Journey

## Step 1 - Allow All Requests

Initially, all requests can be accessed without authentication.

```java
.authorizeHttpRequests(auth ->
    auth.anyRequest().permitAll()
)
```

---

## Step 2 - Protect Sensitive Endpoints

Require authentication for business-related resources.

```java
.authorizeHttpRequests(auth ->
    auth
        .requestMatchers(
            "/accounts/**",
            "/balance/**",
            "/cards/**",
            "/loans/**"
        ).authenticated()
)
```

---

## Step 3 - Keep Public Endpoints Open

Allow public access to informational pages.

```java
.authorizeHttpRequests(auth ->
    auth
        .requestMatchers(
            "/welcome",
            "/about_us"
        ).permitAll()
)
```

---

## Step 4 - Enable Form Login

Enable Spring Security's default login page.

```java
.formLogin(Customizer.withDefaults())
```

When an unauthenticated user attempts to access a protected resource, Spring redirects them to the login page.

---

## Step 5 - Enable HTTP Basic Authentication

Useful for testing with Postman, curl, or REST clients.

```java
.httpBasic(Customizer.withDefaults())
```

Example:

```bash
curl -u user:password http://localhost:8080/accounts
```

---

## Current Security Configuration

```java
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/loans/**",
                        "/balance/**",
                        "/accounts/**",
                        "/cards/**"
                ).authenticated()
                .requestMatchers(
                        "/welcome",
                        "/about_us"
                ).permitAll()
                .anyRequest().permitAll()
        )
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

---

## Running the Application

Using Maven Wrapper:

### Linux / macOS

```bash
./mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

Or simply run:

```java
AppSecurityApplication
```

from IntelliJ IDEA.

---

## Testing the Application

### Public Resources

```text
http://localhost:8080/welcome
http://localhost:8080/about_us
```

### Protected Resources

```text
http://localhost:8080/accounts
http://localhost:8080/balance
http://localhost:8080/cards
http://localhost:8080/loans
```

Accessing a protected endpoint without authentication will trigger Spring Security.

---

## Default User Credentials

If no custom user is configured, Spring Boot generates a temporary password at startup.

Console output:

```text
Using generated security password: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
```

Default username:

```text
user
```

---

# Next Steps

- Create custom users
- Configure in-memory authentication
- Store users in a database
- Implement password encoding
- Create USER and ADMIN roles
- Secure endpoints based on roles
- Customize the login page
- Configure logout functionality
- Add JWT authentication
- Build a REST API security layer
- Integrate OAuth2 and social login

---

## Learning Goal

This project serves as a hands-on guide for understanding how Spring Security works, from basic authentication to advanced authorization mechanisms commonly used in enterprise applications.