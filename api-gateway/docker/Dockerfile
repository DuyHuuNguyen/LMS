FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml ./pom.xml

COPY api-gateway/pom.xml ./api-gateway/pom.xml
COPY api-gateway/src ./api-gateway/src

# Prevent parent aggregator from trying to build missing modules
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml || true

# Install parent POM into local repo (non-recursive)
RUN mvn -f pom.xml -N install -DskipTests -B

# Build api-gateway using its own pom
# Package and explicitly create executable jar in a single Maven invocation
RUN mvn -f api-gateway/pom.xml -DskipTests -B clean package org.springframework.boot:spring-boot-maven-plugin:3.3.7:repackage

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/api-gateway/target/*.jar api-gateway-lms.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "api-gateway-lms.jar"]