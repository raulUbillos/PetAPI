FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring && \
    apt-get update && \
    apt-get install -y gosu && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar app.jar
COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh && \
    mkdir -p /app/data && \
    chown -R spring:spring /app/data

EXPOSE 8080

ENTRYPOINT ["/docker-entrypoint.sh", "java", "-jar", "app.jar"]

