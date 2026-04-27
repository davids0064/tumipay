FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

# Copy maven wrapper and pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source
COPY src src

# Make mvnw executable
RUN chmod +x ./mvnw

# Build the application (skip tests for faster image build)
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy jar from build stage
COPY --from=build /workspace/target/tumipay-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","/app/app.jar"]
