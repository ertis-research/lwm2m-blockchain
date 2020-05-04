# MainApp
Spring is used for the backend of our application. The frontend is developed using Angular.

## MainAppBackEnd
[Spring boot](https://start.spring.io/) is used to create the spring project. The following options are selected:
- Project: Maven Project
- Languaje: Java
- Spring Boot: 2.2.6
- Packaging: jar
- Dependencies: Spring Web

Once the .zip file is downloaded, it is imported from Eclipse as a Maven project. Then the web3j dependency is added.
```
<dependency>
    <groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>4.5.11</version>
</dependency>
```
The latest version of this dependency fails, but version 4.5.11 works correctly.

The project folder structure is:
- beans
- controllers
- services
- utils
- wrappers

You can run the spring boot backend server by typing mvn spring-boot:run in the terminal. It will run on port 8080.

## MainAppFrontEnd
Para crear el proyecto Angular lanzar el siguiente comando desde la terminal.
```
ng new MainAppFrontEnd
```

The project folder structure in the app directory is:
- components
- models
- services

The Angular front-end can be run by typing ng serve command. It will start on port 4200.


## URLs
- https://www.callicoder.com/spring-boot-mongodb-angular-js-rest-api-tutorial/
- https://bezkoder.com/angular-spring-boot-mongodb/
- https://bezkoder.com/spring-boot-mongodb-crud/
- https://youtu.be/O13X14TGtm8
- https://www.youtube.com/watch?v=c8n6JsQuX2A