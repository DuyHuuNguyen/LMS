# =========================
# Stage 1: Build
# =========================

FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Root Maven parent
COPY pom.xml ./pom.xml

# gRPC module
COPY grpc-protocol/pom.xml ./grpc-protocol/pom.xml
COPY grpc-protocol/src ./grpc-protocol/src

# User service
COPY user-service/pom.xml ./user-service/pom.xml
COPY user-service/src ./user-service/src

# Prevent parent aggregator from trying to build missing modules
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml || true

# Install parent POM into local repo (non-recursive) so dependencies that reference it resolve
RUN mvn -f pom.xml -N install -DskipTests -B

# Build grpc-protocol if present (dependency)
RUN if [ -f "grpc-protocol/pom.xml" ]; then mvn -f grpc-protocol/pom.xml clean install -DskipTests -B; fi

# Build user-service using its own pom (parent installed in local repo)
# Package and create executable jar in a single Maven invocation
RUN mvn -f user-service/pom.xml -DskipTests -B clean package org.springframework.boot:spring-boot-maven-plugin:3.3.7:repackage


# =========================
# Stage 2: Runtime
# =========================

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/user-service/target/*.jar user-service-lms.jar

EXPOSE 8083 9090

ENTRYPOINT ["java", "-jar", "user-service-lms.jar"]