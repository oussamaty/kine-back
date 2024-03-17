FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copy the Gradle configuration files and source code into the container
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY src src

# Build the application
RUN gradle build -x test

# Run the application
FROM eclipse-temurin:17-jre
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]