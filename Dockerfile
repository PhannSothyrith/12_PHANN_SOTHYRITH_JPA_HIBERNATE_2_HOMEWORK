# Step 1: Use a Gradle image to build the application
FROM eclipse-temurin:21-jdk AS build

WORKDIR /usr/app/

<<<<<<< HEAD
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew gradlew

# Give execution permission to gradlew
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew build -x test --no-daemon

# Copy project files
COPY . .

# Build jar
RUN ./gradlew bootJar --no-daemon

# Step 2: Use a JRE image to run the application
FROM eclipse-temurin:21-jre
=======
# Copy all project files to the container
COPY . .
RUN chmod +x gradlew
# Run the Gradle build
RUN ./gradlew bootJar
# Step 2: Use a JDK image to run the application
FROM eclipse-temurin:21-jdk
>>>>>>> 4af2abb319a6c3c56a0d92bf2f552a728b40310c

ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

COPY --from=build /usr/app/build/libs/*.jar app.jar

<<<<<<< HEAD
=======
# Expose port 8081
>>>>>>> 4af2abb319a6c3c56a0d92bf2f552a728b40310c
EXPOSE 8282

ENTRYPOINT ["java", "-jar", "app.jar"]

