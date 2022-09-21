# OnlineIDE

Simple online integrated development environment (IDE) built with microservices using the Spring framework. Features include:
- ability to connect to a microcontroller to toggle dark mode
- projects and files stored in a database (PostgreSQL)
- code compilation in Java and C
- UI developed with Angular
- authentication and authorization via OAuth (LRZ)
- shared projects between users
- impoved security

## Setup

To run the OnlineIDE locally, use the application-dev.yml in addition to the application.yml file in the api-gateway **and** the project microservice. The application-dev.yml file can be enabled by writing `spring.profiles.active: "dev"` in the respective application.yml files (already there, commented out).  This implies using a H2 database and another GitLab application that redirects to localhost:8000/login. In order to prevent a temporary forwarding error in the api-gateway service at startup it is best to run the microservices by the command `./mvnw spring-boot:run` in the following order:
1. discovery-service
2. ui-service, project-service, compiler-service, darkmode-service
3. api-gateway-service

Startup using `docker-compose`:
This is tested on a fresh Ubuntu 18.04 installation with maven 3.6.0, openjdk-11, docker, docker-compose, nodejs 12 and angular.
1. Enable the dev profile in the project service **and** the api-gateway.
2. In the project root directory:
	1. `mvn package -DskipTests=true`
	2. `sudo docker-compose -f docker-compose.dev.yml up --build`
3. Wait a few minutes until everything settles.
4. Access the Online IDE at http://localhost:8000

