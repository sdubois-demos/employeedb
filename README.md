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

# Docker

jenkins@buildhost:~$ docker tag employeedb:1.2.0 sadubois/employeedb:1.2.0
jenkins@buildhost:~$ docker push sadubois/employeedb:1.2.0


docker build -f Dockerfile --build-arg JAR_FILE=target/employeedb-1.4.1.jar --build-arg IMAGE_VERSION=1.4.1  -t employeedb:1.4.1 .
docker tag employeedb:1.4.1 sadubois/employeedb:1.4.1
docker push sadubois/employeedb:1.4.1
