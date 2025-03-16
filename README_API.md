# Create Employee:
curl -X POST -H "Content-Type: application/json" \
-d '{"firstName":"Alice","lastName":"Smith","email":"alice@example.com"}' \
http://localhost:8080/api/employees

# Update Employee:
curl -X PUT -H "Content-Type: application/json" \
-d '{"firstName":"Alice","lastName":"Johnson","email":"alice.j@example.com"}' \
http://localhost:8080/api/employees/1

# curl -X GET -H "Content-Type: application/json" http://localhost:8080/api/employees 2>/dev/null | jq -r

# Delete a User
curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/api/employees/1

# Get all Employees
curl -X GET -H "Content-Type: application/json" https://edb.apps.corelab.core-software.ch/api/employees 2> /dev/null | jq -r
curl -X GET -H "Content-Type: application/json" https://edb.apps.fortidemo.ch/api/employees 2> /dev/null | jq -r

# Get a single Employees
curl -X GET -H "Content-Type: application/json" https://edb.apps.corelab.core-software.ch/api/employees/1 2> /dev/null | jq -r
curl -X GET -H "Content-Type: application/json" https://edb.apps.Fortidemo.ch/api/employees/1 2> /dev/null | jq -r


# Bad requests
curl -X GET -H "Content-Type: application/json" https://edb.apps.corelab.core-software.ch/api/employees/xxx 2> /dev/null | jq -r
curl -X GET -H "Content-Type: application/json" https://edb.apps.Fortidemo.ch/api/employees/xxx 2> /dev/null | jq -r
curl -X GET -H "Content-Type: text/plain" https://edb.apps.Fortidemo.ch/api/employees/1 2> /dev/null | jq -r
curl -X GET -H "Content-Type: text/plain" https://edb.apps.corelab.core-software.ch/api/employees/xxx 2> /dev/null | jq -r

text/plain

curl -X GET -H "Content-Type: application/json" "http://localhost:8080/api/employees?limit=3&offset=7&sort_by=firstName" 2>/dev/null | jq -r


# BULK OPERATION 
curl -X POST -H "Content-Type: application/json" -d '[
  {"firstName": "Matthew", "lastName": "Aguilar", "email": "isampson@example.com"},
  {"firstName": "Breanna", "lastName": "Aguirre", "email": "sabrina30@example.org"},
  {"firstName": "Randall", "lastName": "Alexander", "email": "derek71@example.org"}
]' "http://localhost:8080/api/employees" 2>/dev/null | jq -r

curl -X POST -H "Content-Type: application/json" "http://localhost:8080/api/employees" -d @/tmp/random_users.json 2>/dev/null | jq -r
curl -X GET -H "Content-Type: application/json" "http://localhost:8080/api/employees" 2>/dev/null | jq -r
curl -X GET -H "Content-Type: application/json" "http://localhost:8080/api/employees?limit=3&offset=7&sort_by=firstName" 2>/dev/null | jq -r
curl -X GET -H "Content-Type: application/json" "http://localhost:8080/api/employees?limit=3&offset=7&sort_by=id" 2>/dev/null | jq -r
curl -X DELETE "http://localhost:8080/api/employees"

# SINGLE EMPLOYEE OPERATION
curl -X GET "http://localhost:8080/api/employee/1" 2>/dev/null | jq -r 
curl -X DELETE "http://localhost:8080/api/employee/1"
curl -X PUT -H "Content-Type: application/json" -d '{"firstName": "John", "lastName": "Doe", "email": "john.doe@example.com"}' "http://localhost:8080/api/employee/1" | jq -r


