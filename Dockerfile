# Step 1: Use official Maven image to build the app
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Create app directory
WORKDIR /app

# Copy all project files
COPY . .

# Step 2: Use a lightweight JDK image to run
FROM eclipse-temurin:17-jdk-jammy

# Create app directory
WORKDIR /app

# Copy the built jar file from previous stage
COPY --from=build /app/target/*.jar app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]