# Step 1: Use a Gradle image to build the application
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /usr/app/

# Copy Gradle files and cache dependencies
COPY build.gradle settings.gradle ./
RUN ./gradlew build --no-daemon

# Copy all project files to the container
COPY . .

# Run the Gradle build
RUN ./gradlew bootJar --no-daemon

# Step 2: Use a smaller JDK runtime image to run the application
FROM eclipse-temurin:21-jre-slim

# Set environment variables
ENV JAR_NAME=app.jar
ENV APP_HOME=/usr/app/

# Set the working directory in the second stage
WORKDIR $APP_HOME

# Copy the built application from the Gradle container
COPY --from=build $APP_HOME/build/libs/*.jar app.jar

# Expose port (default Spring Boot port 8080)
EXPOSE 8282

# Command to run the application
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]
