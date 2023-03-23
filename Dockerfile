FROM openjdk:17-jdk-alpine
COPY . /app
WORKDIR /app
RUN ./mvnw package -DskipTests
CMD ["java", "-jar", "target/Spring_EC.jar"]