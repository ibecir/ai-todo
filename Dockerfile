# Use an official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the application JAR into the container
COPY target/spring-api.jar /app/spring-api.jar

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/spring-api.jar"]
