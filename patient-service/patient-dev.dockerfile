# Stage 1: Build with Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set working directory
WORKDIR /app

# Copy only pom.xml first for caching dependencies
COPY pom.xml .

# Pre-download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run with a lightweight JDK
FROM eclipse-temurin:21-jdk-alpine

# Set working directory in runtime image
WORKDIR /app

# Copy built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
