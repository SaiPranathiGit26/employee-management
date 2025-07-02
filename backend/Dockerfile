# Use an official OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy pom.xml and project files
COPY . /app

# Build the app using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Expose port 8081
EXPOSE 8081

# Run the jar file
CMD ["java", "-jar", "target/employee-backend-0.0.1-SNAPSHOT.jar"]
