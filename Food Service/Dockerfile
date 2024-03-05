# Use the official lightweight Java image from Eclipse.
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:17-jdk as build

# Set the working directory in the Docker image
WORKDIR /app

# Copy gradle executable and gradle configuration file into the image
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code into the image
COPY src src

# Build the application
RUN ./gradlew build -x test

# Run the application
FROM eclipse-temurin:17-jre
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]