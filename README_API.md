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
