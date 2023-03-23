
#Freelancer-0.0.1-SNAPSHOT.jar
# Use latest openjdk image as the base image
FROM openjdk:17-jdk-alpine
RUN apk add --no-cache maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -f pom.xml
#RUN mvn clean install fix fix

# Expose port 8080
EXPOSE 8080

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "target/Freelancer-0.0.1-SNAPSHOT.jar"]

