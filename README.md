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

# Build Docker Image
export BUILD_VERSION=1.4.8
docker build -f Dockerfile --build-arg JAR_FILE=target/employeedb-$BUILD_VERSION.jar --build-arg IMAGE_VERSION=$BUILD_VERSION  -t employeedb:$BUILD_VERSION .
docker tag employeedb:$BUILD_VERSION sadubois/employeedb:$BUILD_VERSION
docker push sadubois/employeedb:$BUILD_VERSION
