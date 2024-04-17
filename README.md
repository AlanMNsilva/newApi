# Default Springboot API using spring resources

### This code is free to use.

This is a simple project to be used as base for any API. It contains a JWT auth implemented and one free auth endpoint It contains a tomcat embedded so you can just run it without require a tomcat by running:
```
mvn spring-boot:run
```

### Swagger.ui
```
http://localhost:8080/swagger-ui.html
```

#### Database 
MySql 8.0 with default cofiguration: 

```
jdbc:mysql://localhost:3306/sakila
```

### Java Version

```
11
```

### Test
Contains some simple unit tests and integration tests.

To run the tests:
```
mvn verify
```
