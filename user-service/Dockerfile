# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /

# Copy the pom.xml and download dependencies (to leverage Docker cache)
COPY pom.xml ./
RUN mvn dependency:resolve

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /

COPY --from=builder /target/*.jar user-service.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "user-service.jar"]

