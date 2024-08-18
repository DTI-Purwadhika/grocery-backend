FROM eclipse-temurin:22-jdk-alpine as build
WORKDIR /app
COPY . .
ARG SKIP_TESTS=true
RUN ./mvnw clean package -DskipTests=${SKIP_TESTS}

FROM eclipse-temurin:22-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]