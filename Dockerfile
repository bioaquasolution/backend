# Stage 1: Build the Spring Boot application
FROM maven:3.9.0-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Maven configuration files and download dependencies (cached for subsequent builds)
COPY pom.xml .
COPY src ./src
COPY firebase-adminsdk.json ./src/main/resources/firebase-adminsdk.json
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM eclipse-temurin:17-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
 
# Expose the application port
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
