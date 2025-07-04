#!/bin/bash

echo "Building CBCF Namf Communication Service..."

# Clean and build the project
./mvnw clean package -DskipTests

echo "Build completed successfully!"
echo "JAR file created: target/namf-communication-service-1.0.0.jar"
