FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Parent POM
COPY pom.xml ./pom.xml

# gRPC module
COPY grpc-protocol/pom.xml ./grpc-protocol/pom.xml
COPY grpc-protocol/src ./grpc-protocol/src

# Curriculum module
COPY curriculum-service/pom.xml ./curriculum-service/pom.xml
COPY curriculum-service/src ./curriculum-service/src

# Prevent parent aggregator from trying to build missing modules
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml || true

# Install parent POM into local repo (non-recursive)
RUN mvn -f pom.xml -N install -DskipTests -B

# Build grpc-protocol if present (dependency)
RUN if [ -f "grpc-protocol/pom.xml" ]; then mvn -f grpc-protocol/pom.xml clean install -DskipTests -B; fi

# Build curriculum-service using its own pom
# Package and create executable jar in a single Maven invocation
RUN mvn -f curriculum-service/pom.xml -DskipTests -B clean package org.springframework.boot:spring-boot-maven-plugin:3.3.7:repackage


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/curriculum-service/target/*.jar curriculum-service-lms.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "curriculum-service-lms.jar"]