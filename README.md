# employee-demo
sd



## Build the Application
### Compile Applicaiton without JUnit Tests
```
mvn clean package -Dmaven.test.skip
```



### Compile Applicaiton with JUnit Tests
```
mvn clean package -Dspring.profiles.active=integration \
      -Dspring.datasource.username=bitnami \
      -Dspring.datasource.password=bitnami 
```

## API Access
curl -X GET http://localhost:8080/api/employees


## Actuator Access
curl -X GET http://localhost:8080/actuator | jq -r
