# Step 1: Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Step 2: Set working directory
WORKDIR /app

# Step 3: Install MySQL client
RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*

# Step 4: Copy application JAR and .env file
COPY target/javaAPP-1.0-SNAPSHOT.jar app.jar
COPY .env .

# Step 5: Expose the application port
EXPOSE 8082

# Step 6: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

