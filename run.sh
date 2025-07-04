#!/bin/bash

echo "Starting CBCF Namf Communication Service..."

# Run the Spring Boot application
java -jar target/namf-communication-service-1.0.0.jar

echo "Service started on http://localhost:8080/cbcf"
echo "API Documentation available at: http://localhost:8080/cbcf/swagger-ui.html"
echo "H2 Database Console: http://localhost:8080/cbcf/h2-console"
