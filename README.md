# PhotoAppApiUsers
 User Service - Eureka Client for Spring Boot Microservices

Description:
This project is a User Service implementation that acts as a Eureka client in a Spring Boot microservices architecture. The User Service is responsible for handling user-related functionalities, including user authentication, authorization, and user data management. As a Eureka client, it registers itself with the Eureka Discovery Server, allowing other microservices to discover and interact with it seamlessly.

Key Features:
User Authentication and Authorization: The User Service provides user authentication and authorization mechanisms, allowing users to securely log in and access specific functionalities based on their roles and permissions.

Eureka Client Registration: The User Service registers itself with the Eureka Discovery Server, making it discoverable to other microservices within the ecosystem.

User Data Persistence: It uses Spring Data JPA to persist user details in a database (e.g., H2 in-memory database or MySQL).

RESTful APIs: The User Service exposes RESTful APIs for user-related operations, enabling seamless communication with other microservices.

Security with JWT: User authentication is implemented using Spring Security and JSON Web Tokens (JWT) to ensure secure communication between clients and the service.

Centralized Configuration: The User Service can utilize the Spring Cloud Config Server to store and manage its configuration settings centrally.

Health Monitoring: The User Service integrates with Spring Boot Actuator to provide health monitoring and management endpoints.

 Part of Spring Cloud Practice:
* PhotoAppDiscoveryService - Eureka Discovery service
* PhotoAppAPIConfigServer - Config server
* ApiGateway
* PhotoAppApiUsers - Eureka Client service
* PhotoAppConfiguration - Repo to hold the properties files
* PhotoAppApiAlbums - Eureka Client service
