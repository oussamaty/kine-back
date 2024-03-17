# Kine: Nutrition and Activity Tracking

Kine is an innovative app designed to revolutionize the way users track their nutrition and physical activity. By integrating with smart devices, Kine offers real-time data synchronization, meal planning, and comprehensive activity tracking, all aimed at enhancing the user's health and wellness journey.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Development Setup](#development-setup)
  - [Prerequisites](#1-prerequisites)
  - [Environment File Creation](#2-environment-file-creation)
  - [Config Service Setup](#3-config-service-setup)
  - [Service-Specific Configuration](#4-service-specific-configuration)
  - [Keycloak Setup](#5-keycloak-setup)
  - [Launching the Architecture](#6-launching-the-architecture)
  - [Testing the Backend](#7-testing-the-backend)
- [Conclusion](#conclusion)
- [Special Thanks](#special-thanks)

## Technologies Used

- **Spring Boot**: Powers the backend services, providing a robust framework for building microservices, including the config service and food-service, with ease and efficiency.
- **Keycloak**: Secures the application through comprehensive identity and access management, handling authentication and authorization seamlessly.
- **Spring Cloud Gateway**: Acts as the entry point for the application, routing requests to the appropriate microservices while providing cross-cutting concerns such as security.
- **Spring Cloud Config**: Manages externalized configuration in a distributed system, allowing for dynamic updates and centralized property management.
- **Docker & Docker Compose**: Ensures consistent environments and simplifies deployment with containerization, orchestrating the application's services with ease.
- **Eureka (Spring Cloud Netflix Eureka)**: Facilitates service discovery, enabling microservices to locate and communicate with each other in a dynamic, cloud-based architecture.


## Development Setup

### 1. Prerequisites

- Docker and Docker Compose installed on your machine.
- Git installed and configured.
- Access to a terminal or command prompt.


### 2. Environment File Creation

Begin by creating a `.env` file at the root of your project. This file will contain environment variables crucial for the setup process. We will populate this file with necessary variables as we progress.

### 3. Config Service Setup

Configure the Config Service by specifying the Git URI of your configuration repository. Add the following entry to your `.env` file:

```env
SPRING_CONFIG_GIT_URI=<Your-Git-Configuration-URI>
```

### 4. Service-Specific Configuration

For the Config Service to manage each service's configuration effectively, create a `service-name.yml` file within the configuration repository for each service: `discovery`, `gateway`, and `food-service`. The filename should match the service name for Config Server to correctly associate the configuration.

Structure your configuration repository as follows:

```
/config-repo
  discovery.yml
  gateway.yml
  food-service.yml
```

Populate each `service-name.yml` with the service-specific configurations.

Here's an example of what each `service-name.yml` might contain. Customize each according to the specific needs of the service it configures:

#### discovery.yml

```yaml
server:
  port: ${DISCOVERY_PORT}

origins: ${GATEWAY_URL}
permit-all: /actuator/**

spring:
  application:
    name: ${DISCOVERY_NAME}
  security:
    user:
      name: ${DISCOVERY_USER}
      password: ${DISCOVERY_PASSWORD}

eureka:
  instance:
    hostname: discovery
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: ${DISCOVERY_URL}
```

#### gateway.yml

```yaml

food-url: lb://${FOOD_SERVICE_NAME}
food-prefix: /api/v1/food

server:
  port: ${GATEWAY_PORT}

spring:
  application:
    name: ${GATEWAY_NAME}
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KC_ISSUER}
  cloud:
    gateway:
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
      routes:
        # Auth Service
        - id: auth-service
          uri: ${KC_HOSTNAME_URI}
          predicates:
            - Path=${KC_HTTP_RELATIVE_PATH}/**


        # Food Service
        - id: food-service
          uri: ${food-url}
          predicates:
            - Path=${food-prefix}/**

eureka:
  client:
    region: default
    registry-fetch-interval-seconds: 30
```

#### food-service.yml

```yaml
server:
  port: ${FOOD_SERVICE_PORT}
  error:
    include-message: always
  ssl:
    enabled: false

origins: ${GATEWAY_URL}
permit-all: /public/**

spring:
  application:
    name: ${FOOD_SERVICE_NAME}
  datasource:
    url: ${FOOD_SPRING_DATASOURCE_URL}
    username: ${FOOD_DB_USER}
    password: ${FOOD_DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format-sql: true
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KC_ISSUER}

eureka:
  client:
    region: default
    registry-fetch-interval-seconds: 30
```

### 5. Keycloak Setup

   Modify your `/etc/hosts` file by adding an entry for Keycloak to point to `127.0.0.1`. This step ensures that `keycloak` hostname resolves to your local machine:

   ```
   127.0.0.1 keycloak
   ```

   Update the `.env` file with the necessary Keycloak environment variables:

   ```
   # Keycloak Config
   KEYCLOAK_ADMIN=<your-keycloak-admin>
   KEYCLOAK_ADMIN_PASSWORD=<your-keycloak-admin-password>
   KEYCLOAK_PORT=8080
   KC_HTTP_RELATIVE_PATH=/auth
   KC_HOSTNAME=keycloak
   KC_HOSTNAME_URL=http://${KC_HOSTNAME}:${KEYCLOAK_PORT}
   KC_HOSTNAME_URI=${KC_HOSTNAME_URL}${KC_HTTP_RELATIVE_PATH}
   KC_ISSUER=${KC_HOSTNAME_URI}/realms/kine
   
   # Auth DB Config
   AUTH_DB_NAME=<your-auth-db-name>
   AUTH_DB_USER=<your-auth-db-user>
   AUTH_DB_PASSWORD=<your-auth-db-password>
   AUTH_DB_URL=jdbc:postgresql://auth-db:5432/${AUTH_DB_NAME}
   ```

   With Docker Compose, launch Keycloak to initiate the setup:

   ```bash
   docker-compose up -d keycloak
   ```

   Once Keycloak is running, navigate to the Keycloak admin console, in this case it would be http://localhost:8080/auth and create a new realm named `kine`.

   Import the `realm-export.json` file to configure the realm with the necessary settings, clients, roles, and users.

### 6. Launching the Architecture

With the Config Service and Keycloak ready, you can start the remaining components of your architecture. Ensure to start each service sequentially, allowing each component time to initialize before starting the next.

#### a. **Start the Config Service**:

   ```bash
   docker-compose up -d config-service
   ```

   Wait for the Config Service to fully start and initialize.

#### b. **Launch the Discovery Service**:

   After confirming the Config Service is up and running, start the Discovery Service:

   ```bash
   docker-compose up -d discovery-service
   ```

   Allow some time for the Discovery Service to register itself and discover other services.

#### c. **Start the Gateway and Food Service**:

   Finally, launch the Gateway and Food Service:

   ```bash
   docker-compose up -d gateway food-service
   ```

### 7. Testing the Backend

To test the backend and ensure everything is functioning as expected, follow these steps:

The PKCE flow enhances the security of the OAuth 2.0 authorization code grant type by preventing certain attack scenarios. Follow these steps to obtain an access token using PKCE and then use it to access the Food Service.

#### a. Generate Code Verifier and Challenge

PKCE starts by generating a code verifier and a code challenge. The code verifier is a high-entropy cryptographic random string. The code challenge is derived from the code verifier by transforming it through a method (S256).

- **Code Verifier**: Generate a random string of characters (e.g., `M25iVXpKQisyU2FsaXN1ZEVxVVR1ZGFCZQ==`).
- **Code Challenge**: Apply SHA256 hash to the verifier and then base64URL-encode it. This will be used in the authorization request.

#### b. Authorization Request

Make an authorization request through your browser to Keycloak's authorization endpoint, including the code challenge and specifying the PKCE transformation method (`S256`).

- **URL**: `http://keycloak:8080/auth/realms/{realm-name}/protocol/openid-connect/auth`
- **Parameters**:
    - `client_id`: Your client's ID.
    - `response_type`: Set this to `code`.
    - `code_challenge`: The code challenge generated in Step 1.
    - `code_challenge_method`: Set this to `S256`.

#### c. User Authentication

The user authenticates in response to the authorization request. If successful, Keycloaks redirects to a url like this 

`http://keycloak:8080/auth/admin/master/console/#state={state_id}&session_state={session_code}&iss=http%3A%2F%2Fkeycloak%3A8080%2Fauth%2Frealms%2Fmaster&code={authorization_code}`

You need to copy the authorization_code to generate the access token.

#### d. Exchange Authorization Code for an Access Token

Make a POST request to Keycloak's token endpoint to exchange the authorization code for an access token.

- **URL**: `http://keycloak:8080/auth/realms/{realm-name}/protocol/openid-connect/token`
- **Parameters**:
    - `grant_type`: Set this to `authorization_code`.
    - `client_id`: Your client's ID.
    - `code`: The authorization code received in Step 3.
    - `code_verifier`: The code verifier generated in Step 1.

#### e. Access the Food Service

Use the obtained access token to make authenticated requests to the Food Service. Include the access token in the HTTP `Authorization` header as a Bearer token.

   ```bash
   curl -H "Authorization: Bearer <your-access-token>" http://localhost:8080/api/v1/food
   ```

Following these steps allows you to securely test the backend services using the PKCE flow for authentication against Keycloak. This ensures that only authenticated users can access your services, providing an added layer of security to your application.


## Conclusion

This backend setup leverages powerful technologies like Spring Boot, Keycloak, and Docker to provide a secure, scalable foundation for our nutrition and activity tracking app. Following the setup and PKCE authentication instructions ensures a seamless development experience. Designed for flexibility and scalability, this guide aims to kickstart your journey towards innovating in health and wellness.

## Special Thanks

A heartfelt thank you goes out to [Jérôme Wacongne](https://github.com/ch4mpy). Your tutorials and insightful responses on StackOverflow have been instrumental in navigating the complexities of Keycloak.
